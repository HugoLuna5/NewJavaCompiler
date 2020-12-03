package vm;

import vm.exceptions.CeIASMAssembleException;
import vm.exceptions.CeIASMLoadException;
import vm.exceptions.CeIVMAPIInvalidStateException;
import vm.exceptions.CeIVMHaltException;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class CeIVMAPI {
   private CeIVMAPIMemory memory;
   private CeIVMAPISpecialRegs regFile;
   private CeIVMAPIIOSubSys io;
   private CeIISA instrSet;
   private CeIASMAssemblerLinkerAndLoader asmLinkerLoader;
   private CeIVMAPIExecutionEngine execEngine;
   private boolean codeWriteProtected;
   private boolean dataExecutionPreventionEnabled;
   private CeIVMAPI.CeIVMAPIPhase status;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIVMAPI$CeIVMAPIPhase;

   public String getVersion() {
      return "1.1.0";
   }

   public CeIVMAPI() {
      this(8192, System.in, System.out, System.err, System.getProperty("file.encoding"));
   }

   public CeIVMAPI(int memSize) {
      this(memSize, System.in, System.out, System.err, System.getProperty("file.encoding"));
   }

   public CeIVMAPI(int memSize, InputStream in, OutputStream out, OutputStream err, String encoding) {
      this.codeWriteProtected = true;
      this.dataExecutionPreventionEnabled = true;
      this.status = CeIVMAPI.CeIVMAPIPhase.CREATED;
      this.regFile = new CeIVMAPISpecialRegs();
      this.memory = new CeIVMAPIMemory(memSize, this.regFile);
      this.io = new CeIVMAPIIOSubSys(in, out, err, encoding);
      this.instrSet = new CeIISA(this.regFile);
      this.asmLinkerLoader = new CeIASMAssemblerLinkerAndLoader(this.memory, this.instrSet);
      this.execEngine = new CeIVMAPIExecutionEngine(this.io, this.memory, this.regFile, this.instrSet);
      this.status = CeIVMAPI.CeIVMAPIPhase.CREATED;
   }

   private String getStatus() {
      switch($SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIVMAPI$CeIVMAPIPhase()[this.status.ordinal()]) {
      case 0:
         return "CeIVM creada";
      case 1:
         return "Programa parseado y ensamblado";
      case 2:
         return "Programa cargado";
      case 3:
         return "CeIVM inicializada";
      case 4:
         return "CeIVM ejecutando";
      case 5:
         return "CeIVM detenida";
      default:
         return "Estado inválido";
      }
   }

   public void parseAndAssemble(String filename) throws Exception {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.CREATED && this.status != CeIVMAPI.CeIVMAPIPhase.PARSEDANDASSEMBLED) {
         throw new CeIVMAPIInvalidStateException("No es posible parsear y ensamblar.", this.getStatus());
      } else {
         this.asmLinkerLoader.setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection.CODE, 0, 0, filename);
         BufferedReader r = new BufferedReader(new FileReader(filename));

         try {
            (new CeIASMParser(new CeIASMLexicalAnalyzer(r, filename), this.asmLinkerLoader, filename)).parse();
         } catch (Exception var4) {
            this.asmLinkerLoader.flushSections();
            this.status = CeIVMAPI.CeIVMAPIPhase.CREATED;
            throw var4;
         }

         this.status = CeIVMAPI.CeIVMAPIPhase.PARSEDANDASSEMBLED;
      }
   }

   public void loadProgram() throws CeIVMMemoryException, CeIASMLoadException, CeIVMAPIInvalidStateException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.PARSEDANDASSEMBLED) {
         throw new CeIVMAPIInvalidStateException("No es posible cargar el programa.", this.getStatus());
      } else {
         this.asmLinkerLoader.loadProgram();
         this.status = CeIVMAPI.CeIVMAPIPhase.LOADED;
      }
   }

   public void initializeVM() throws CeIVMRuntimeException, CeIASMAssembleException, CeIVMAPIInvalidStateException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.LOADED) {
         throw new CeIVMAPIInvalidStateException("No es posible inicializar la CeIVM.", this.getStatus());
      } else {
         this.initializeRegFile();
         this.status = CeIVMAPI.CeIVMAPIPhase.INITIALIZED;
      }
   }

   private void initializeRegFile() throws CeIVMRuntimeException, CeIVMAPIInvalidStateException {
      try {
         this.regFile.setPc(0);
         this.regFile.setSp(this.asmLinkerLoader.getEostack());
         this.regFile.setFp(this.asmLinkerLoader.getEostack() - 1);
         this.regFile.setHp(this.asmLinkerLoader.getEodata() + 1);
         this.regFile.setHl(this.asmLinkerLoader.getEoheap());
         if (this.codeWriteProtected) {
            this.memory.enableMemoryWriteProtection(this.asmLinkerLoader.getEocode());
         } else {
            this.memory.disableMemoryWriteProtection();
         }

         if (this.dataExecutionPreventionEnabled) {
            this.execEngine.enableDEP(this.asmLinkerLoader.getEocode());
            this.regFile.enablePreDEP(this.asmLinkerLoader.getEocode());
         } else {
            this.execEngine.disableDEP();
            this.regFile.disablePreDEP();
         }

      } catch (CeIVMAPIInvalidStateException var2) {
         var2.addStatusInfo(this.getStatus());
         throw var2;
      }
   }

   public void resetMemory() throws CeIVMAPIInvalidStateException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.PARSEDANDASSEMBLED && this.status != CeIVMAPI.CeIVMAPIPhase.LOADED && this.status != CeIVMAPI.CeIVMAPIPhase.INITIALIZED) {
         throw new CeIVMAPIInvalidStateException("No es posible parsear y ensamblar.", this.getStatus());
      } else {
         this.memory.clearMemory();
         this.asmLinkerLoader.flushSections();
         this.status = CeIVMAPI.CeIVMAPIPhase.CREATED;
      }
   }

   public void executeToCompletion() throws CeIVMAPIInvalidStateException, CeIVMRuntimeException, CeIVMMemoryException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.INITIALIZED && this.status != CeIVMAPI.CeIVMAPIPhase.EXECUTING) {
         throw new CeIVMAPIInvalidStateException("No es posible iniciar la ejecución.", this.getStatus());
      } else {
         this.status = CeIVMAPI.CeIVMAPIPhase.EXECUTING;
         if (this.codeWriteProtected) {
            this.memory.enableMemoryWriteProtection(this.asmLinkerLoader.getEocode());
         } else {
            this.memory.disableMemoryWriteProtection();
         }

         try {
            this.execEngine.executeToCompletion();
         } catch (CeIVMHaltException var2) {
            this.io.getOutputStream().println();
            this.io.getOutputStream().println(var2.getMessage());
            this.io.getOutputStream().println();
            this.status = CeIVMAPI.CeIVMAPIPhase.HALTED;
         }

         this.memory.disableMemoryWriteProtection();
      }
   }

   public void executeNextStep() throws CeIVMAPIInvalidStateException, CeIVMRuntimeException, CeIVMMemoryException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.INITIALIZED && this.status != CeIVMAPI.CeIVMAPIPhase.EXECUTING) {
         throw new CeIVMAPIInvalidStateException("No es posible iniciar la ejecución.", this.getStatus());
      } else {
         this.status = CeIVMAPI.CeIVMAPIPhase.EXECUTING;
         if (this.codeWriteProtected) {
            this.memory.enableMemoryWriteProtection(this.asmLinkerLoader.getEocode());
         } else {
            this.memory.disableMemoryWriteProtection();
         }

         try {
            this.execEngine.executeNextStep();
         } catch (CeIVMHaltException var2) {
            this.io.getOutputStream().println();
            this.io.getOutputStream().println(var2.getMessage());
            this.status = CeIVMAPI.CeIVMAPIPhase.HALTED;
         }

         this.memory.disableMemoryWriteProtection();
      }
   }

   public void cpuReset() throws CeIVMAPIInvalidStateException, CeIVMRuntimeException, CeIASMAssembleException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.HALTED && this.status != CeIVMAPI.CeIVMAPIPhase.EXECUTING) {
         throw new CeIVMAPIInvalidStateException("No es posible iniciar la ejecución.", this.getStatus());
      } else {
         this.initializeRegFile();
         this.status = CeIVMAPI.CeIVMAPIPhase.INITIALIZED;
      }
   }

   public CeIVMAPIMemory getMemoryAccess() throws CeIVMAPIInvalidStateException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.HALTED && this.status != CeIVMAPI.CeIVMAPIPhase.INITIALIZED && this.status != CeIVMAPI.CeIVMAPIPhase.EXECUTING) {
         throw new CeIVMAPIInvalidStateException("No es posible acceder a memoria.", this.getStatus());
      } else {
         return this.memory;
      }
   }

   public CeIVMAPISpecialRegs getRegisterFileAccess() throws CeIVMAPIInvalidStateException {
      if (this.status != CeIVMAPI.CeIVMAPIPhase.HALTED && this.status != CeIVMAPI.CeIVMAPIPhase.INITIALIZED && this.status != CeIVMAPI.CeIVMAPIPhase.EXECUTING) {
         throw new CeIVMAPIInvalidStateException("No es posible acceder a los registros.", this.getStatus());
      } else {
         return this.regFile;
      }
   }

   public String disassembleOpcode(int opcode) {
      try {
         return this.instrSet.getCeIVMInstruction(opcode).getMnemonic();
      } catch (CeIVMRuntimeException var3) {
         return "Invalid Opcode";
      }
   }

   public boolean isCodeWriteProtected() {
      return this.codeWriteProtected;
   }

   public void setCodeWriteProtected(boolean codeWriteProtected) {
      this.codeWriteProtected = codeWriteProtected;
   }

   public boolean isDataExecutionPReventionEnabled() {
      return this.dataExecutionPreventionEnabled;
   }

   public void setDataExecutionPReventionEnabled(boolean dataExecutionPReventionEnabled) {
      this.dataExecutionPreventionEnabled = dataExecutionPReventionEnabled;
   }

   public boolean isListingGenerationEnabled() {
      return this.asmLinkerLoader.isListingGenerationEnabled();
   }

   public void enableListingGeneration(PrintStream listingOutput) {
      this.asmLinkerLoader.enableListingGeneration(listingOutput);
   }

   public void disableListingGeneration() {
      this.asmLinkerLoader.disableListingGeneration();
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIVMAPI$CeIVMAPIPhase() {
      int[] var10000 = $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIVMAPI$CeIVMAPIPhase;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[CeIVMAPI.CeIVMAPIPhase.values().length];

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.CREATED.ordinal()] = 0;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.PARSEDANDASSEMBLED.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.LOADED.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.INITIALIZED.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.EXECUTING.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[CeIVMAPI.CeIVMAPIPhase.HALTED.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIVMAPI$CeIVMAPIPhase = var0;
         return var0;
      }
   }

   private static enum CeIVMAPIPhase {
      CREATED,
      PARSEDANDASSEMBLED,
      LOADED,
      INITIALIZED,
      EXECUTING,
      HALTED;
   }
}
