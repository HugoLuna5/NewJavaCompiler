package vm;

import vm.exceptions.CeIASMAssembleException;
import vm.exceptions.CeIASMLoadException;
import vm.exceptions.CeIVMAPIInvalidStateException;
import vm.exceptions.CeIVMMemoryException;
import vm.instructions.CeIISAInstruction;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

class CeIASMAssemblerLinkerAndLoader {
   private CeIASMAssemblerLinkerAndLoader.CeIVMSection currentSection;
   LinkedList<CeIASMAssembledLocation> codeContent;
   LinkedList<CeIASMAssembledLocation> dataContent;
   LinkedList<CeIASMAssembledLocation> heapContent;
   LinkedList<CeIASMAssembledLocation> stackContent;
   private CeIISA instrSet;
   LinkedHashMap<String, Integer> labels;
   Hashtable<String, CeIASMPendingLabel> pendingLabels;
   private CeIVMAPIMemory mem;
   private int loadPtr;
   private int eocode;
   private int eodata;
   private int eoheap;
   private int eostack;
   private boolean programLoaded;
   private PrintStream dumpInfoOut;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIASMAssemblerLinkerAndLoader$CeIVMSection;

   CeIASMAssemblerLinkerAndLoader(CeIVMAPIMemory mem, CeIISA iSet) {
      this.currentSection = CeIASMAssemblerLinkerAndLoader.CeIVMSection.CODE;
      this.codeContent = new LinkedList();
      this.dataContent = new LinkedList();
      this.heapContent = new LinkedList();
      this.stackContent = new LinkedList();
      this.instrSet = null;
      this.labels = new LinkedHashMap();
      this.pendingLabels = new Hashtable();
      this.mem = null;
      this.loadPtr = 0;
      this.eocode = 0;
      this.eodata = 0;
      this.eoheap = 0;
      this.eostack = 0;
      this.programLoaded = false;
      this.dumpInfoOut = null;
      this.mem = mem;
      this.instrSet = iSet;
   }

   void setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection s, int numLine, int numColumn, String filename) throws CeIASMAssembleException {
      if (s != CeIASMAssemblerLinkerAndLoader.CeIVMSection.CODE && s != CeIASMAssemblerLinkerAndLoader.CeIVMSection.DATA && s != CeIASMAssemblerLinkerAndLoader.CeIVMSection.HEAP && s != CeIASMAssemblerLinkerAndLoader.CeIVMSection.STACK) {
         throw new CeIASMAssembleException("Se intentó cambiar a una sección de ensamblado inválida.", numLine, numColumn, filename);
      } else {
         this.currentSection = s;
      }
   }

   public void assembleInstruction(String label, String mnemonic, LinkedList<CeIASMAssembledLocation> params, int numLine, int numColumn, String filename, String dumpInfo) throws CeIASMAssembleException {
      CeIISAInstruction instr = this.instrSet.getCeIVMInstruction(mnemonic, numLine, numColumn, filename);
      if (params == null) {
         params = new LinkedList();
      }

      if (instr.getNumParameters() != params.size()) {
         throw new CeIASMAssembleException("Cantidad de argumentos incorrectos para la instrucción " + mnemonic + ".", numLine, numColumn, filename);
      } else {
         params.addFirst(new CeIASMAssembledLocation(label, instr.getOpcode(), numLine, numColumn, filename, dumpInfo));
         this.assembleInCurrentSection(params, numLine, numColumn, filename);
      }
   }

   public void assembleDW(LinkedList<CeIASMAssembledLocation> defs, int numLine, int numColumn, String filename) throws CeIASMAssembleException {
      this.assembleInCurrentSection(defs, numLine, numColumn, filename);
   }

   private void assembleInCurrentSection(LinkedList<CeIASMAssembledLocation> asm, int numLine, int numColumn, String filename) throws CeIASMAssembleException {
      switch($SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIASMAssemblerLinkerAndLoader$CeIVMSection()[this.currentSection.ordinal()]) {
      case 0:
         this.codeContent.addAll(asm);
         break;
      case 1:
         this.dataContent.addAll(asm);
         break;
      case 2:
         this.heapContent.addAll(asm);
         break;
      case 3:
         this.stackContent.addAll(asm);
         break;
      default:
         throw new CeIASMAssembleException("Se intentó ensamblar en una sección inválida de código.", numLine, numColumn, filename);
      }

   }

   public void flushSections() {
      this.codeContent = new LinkedList();
      this.dataContent = new LinkedList();
      this.heapContent = new LinkedList();
      this.stackContent = new LinkedList();
   }

   public void loadProgram() throws CeIASMLoadException, CeIVMMemoryException {
      this.mem.clearMemory();
      this.loadPtr = 0;
      this.eocode = 0;
      this.eodata = 0;
      this.eoheap = 0;
      this.eostack = 0;
      this.programLoaded = false;

      for(Iterator iCodeContent = this.codeContent.iterator(); iCodeContent.hasNext(); ++this.loadPtr) {
         CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iCodeContent.next();
         this.resolveAndLoadAssembledLocation(asmLoc);
      }

      this.eocode = this.loadPtr - 1;

      for(Iterator iDataContent = this.dataContent.iterator(); iDataContent.hasNext(); ++this.loadPtr) {
         CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iDataContent.next();
         this.resolveAndLoadAssembledLocation(asmLoc);
      }

      this.eodata = this.loadPtr - 1;

      for(Iterator iHeapContent = this.heapContent.iterator(); iHeapContent.hasNext(); ++this.loadPtr) {
         CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iHeapContent.next();
         this.resolveAndLoadAssembledLocation(asmLoc);
      }

      this.eoheap = this.loadPtr - 1;
      this.loadPtr = this.mem.getLastLocation();

      for(Iterator iStackContent = this.stackContent.iterator(); iStackContent.hasNext(); --this.loadPtr) {
         CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iStackContent.next();
         if (this.eoheap >= this.loadPtr) {
            throw new CeIVMMemoryException("Espacio insuficiente de memoria para cargar el programa. Colisión heap-stack.", this.loadPtr);
         }

         this.resolveAndLoadAssembledLocation(asmLoc);
      }

      this.eostack = this.loadPtr + 1;
      if (!this.pendingLabels.isEmpty()) {
         String label = (String)this.pendingLabels.keys().nextElement();
         CeIASMPendingLabel pLabelInfo = (CeIASMPendingLabel)this.pendingLabels.get(label);
         throw new CeIASMLoadException("La etiqueta " + label + " se encuentra indefinida.", pLabelInfo.getNumLine(), pLabelInfo.getNumColumn(), pLabelInfo.getFilename());
      } else {
         if (this.isListingGenerationEnabled()) {
            this.generateListings();
         }

         this.programLoaded = true;
      }
   }

   private void resolveAndLoadAssembledLocation(CeIASMAssembledLocation asmLoc) throws CeIASMLoadException, CeIVMMemoryException {
      String label;
      if ((label = asmLoc.getLabel()) != null) {
         label = label.toLowerCase().substring(0, label.length() - 1);
         if (this.labels.containsKey(label)) {
            throw new CeIASMLoadException("La etiqueta " + label + " está duplicada.", asmLoc.getNumLine(), asmLoc.getNumColumn(), asmLoc.getFilename());
         }

         this.labels.put(label, new Integer(this.loadPtr));
         if (this.pendingLabels.containsKey(label)) {
            Iterator iPendingCells = ((CeIASMPendingLabel)this.pendingLabels.get(label)).getPendingAddresses().iterator();

            while(iPendingCells.hasNext()) {
               this.mem.write((Integer)iPendingCells.next(), this.loadPtr);
            }

            this.pendingLabels.remove(label);
         }
      }

      if (asmLoc.isAssembledIntegerLocation()) {
         this.mem.write(this.loadPtr, asmLoc.getContentInt());
      } else {
         String contentLabel = asmLoc.getContentString().toLowerCase();
         if (this.labels.containsKey(contentLabel)) {
            this.mem.write(this.loadPtr, (Integer)this.labels.get(contentLabel));
         } else if (this.pendingLabels.containsKey(contentLabel)) {
            ((CeIASMPendingLabel)this.pendingLabels.get(contentLabel)).addLocation(this.loadPtr);
         } else {
            this.pendingLabels.put(contentLabel, new CeIASMPendingLabel(this.loadPtr, asmLoc.getNumLine(), asmLoc.getNumColumn(), asmLoc.getFilename()));
         }
      }

   }

   public int getEocode() throws CeIVMAPIInvalidStateException {
      if (this.programLoaded) {
         return this.eocode;
      } else {
         throw new CeIVMAPIInvalidStateException("No se puede acceder al valor EOCODE hasta que el programa esté correctamente cargado en memoria.");
      }
   }

   public int getEodata() throws CeIVMAPIInvalidStateException {
      if (this.programLoaded) {
         return this.eodata;
      } else {
         throw new CeIVMAPIInvalidStateException("No se puede acceder al valor EODATA hasta que el programa esté correctamente cargado en memoria.");
      }
   }

   public int getEoheap() throws CeIVMAPIInvalidStateException {
      if (this.programLoaded) {
         return this.eoheap;
      } else {
         throw new CeIVMAPIInvalidStateException("No se puede acceder al valor EOHEAP hasta que el programa esté correctamente cargado en memoria.");
      }
   }

   public int getEostack() throws CeIVMAPIInvalidStateException {
      if (this.programLoaded) {
         return this.eostack;
      } else {
         throw new CeIVMAPIInvalidStateException("No se puede acceder al valor EOSTACK hasta que el programa esté correctamente cargado en memoria.");
      }
   }

   public boolean isProgramLoaded() {
      return this.programLoaded;
   }

   public boolean isListingGenerationEnabled() {
      return this.dumpInfoOut != null;
   }

   public void enableListingGeneration(PrintStream listingOutput) {
      this.dumpInfoOut = listingOutput;
   }

   public void disableListingGeneration() {
      this.dumpInfoOut = null;
   }

   private void generateListings() throws CeIVMMemoryException {
      if (this.isListingGenerationEnabled()) {
         this.dumpInfoOut.println("Información de ensamblado, linkeo y carga (generado por el CeIASMAssemblerLinkerAndLoader)");
         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Límite de carga de las secciones ----------------------------------------------");
         this.dumpInfoOut.println();
         this.dumpInfoOut.format(".CODE:  de locación %10d a locación %10d%n", 0, this.eocode);
         this.dumpInfoOut.format(".DATA:  de locación %10d a locación %10d%n", this.eocode + 1, this.eodata);
         this.dumpInfoOut.format(".HEAP:  de locación %10d a locación %10d%n", this.eodata + 1, this.eoheap);
         this.dumpInfoOut.format(".STACK: de locación %10d a locación %10d%n", this.eostack, this.mem.getLastLocation());
         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Resolución de etiquetas -------------------------------------------------------");
         this.dumpInfoOut.println("Dirección   Etiqueta");
         this.dumpInfoOut.println();
         Iterator iLabels = this.labels.keySet().iterator();

         while(iLabels.hasNext()) {
            String label = (String)iLabels.next();
            this.dumpInfoOut.format("%10d  %s%n", (Integer)this.labels.get(label), label);
         }

         this.dumpInfoOut.println();
         this.dumpInfoOut.println("------------------------------------------------------------------------------------------");
         this.dumpInfoOut.println("----------- Contenido de la memoria ensamblada, linkeada y cargada -----------------------");
         this.dumpInfoOut.println("------------------------------------------------------------------------------------------");
         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Sección .CODE ----------------------------------------------------------------");
         this.dumpInfoOut.println("Dirección   Cont.Mem.   Código CeIASM                           Referencia");
         this.dumpInfoOut.println();
         int i = 0;

         for(Iterator iCode = this.codeContent.iterator(); iCode.hasNext(); ++i) {
            CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iCode.next();
            this.dumpInfoOut.format("%10d  %10d  %s%n", i, this.mem.read(i), this.makeOfLength(40, asmLoc.getDumpInfo()) + "; Archivo: " + asmLoc.getFilename() + " línea: " + asmLoc.getNumLine() + " columna: " + asmLoc.getNumColumn() + ".");
         }

         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Sección .DATA ----------------------------------------------------------------");
         this.dumpInfoOut.println("Dirección   Cont.Mem.   Código CeIASM                           Referencia");
         this.dumpInfoOut.println();

         for(Iterator iData = this.dataContent.iterator(); iData.hasNext(); ++i) {
            CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iData.next();
            this.dumpInfoOut.format("%10d  %10d  %s%n", i, this.mem.read(i), this.makeOfLength(40, asmLoc.getDumpInfo()) + "; Archivo: " + asmLoc.getFilename() + " línea: " + asmLoc.getNumLine() + " columna: " + asmLoc.getNumColumn() + ".");
         }

         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Sección .HEAP ----------------------------------------------------------------");
         this.dumpInfoOut.println("Dirección   Cont.Mem.   Código CeIASM                           Referencia");
         this.dumpInfoOut.println();

         for(Iterator iHeap = this.heapContent.iterator(); iHeap.hasNext(); ++i) {
            CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iHeap.next();
            this.dumpInfoOut.format("%10d  %10d  %s%n", i, this.mem.read(i), this.makeOfLength(40, asmLoc.getDumpInfo()) + "; Archivo: " + asmLoc.getFilename() + " línea: " + asmLoc.getNumLine() + " columna: " + asmLoc.getNumColumn() + ".");
         }

         this.dumpInfoOut.println();
         this.dumpInfoOut.println("----------- Sección .STACK (decreciente) --------------------------------------------------");
         this.dumpInfoOut.println("Dirección   Cont.Mem.   Código CeIASM                           Referencia");
         this.dumpInfoOut.println();
         i = this.mem.getLastLocation();

         for(Iterator iStack = this.stackContent.iterator(); iStack.hasNext(); --i) {
            CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)iStack.next();
            this.dumpInfoOut.format("%10d  %10d  %s%n", i, this.mem.read(i), this.makeOfLength(40, asmLoc.getDumpInfo()) + "; Archivo: " + asmLoc.getFilename() + " línea: " + asmLoc.getNumLine() + " columna: " + asmLoc.getNumColumn() + ".");
         }

         this.dumpInfoOut.println();
         this.dumpInfoOut.println("El volcado de la información de ensamblado, linkeo y carga se completó exitosamente...");
         this.dumpInfoOut.println();
      }

   }

   private String makeOfLength(int length, String str) {
      StringBuffer s = new StringBuffer(str);

      while(s.length() < length) {
         s.append(' ');
      }

      return s.toString();
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIASMAssemblerLinkerAndLoader$CeIVMSection() {
      int[] var10000 = $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIASMAssemblerLinkerAndLoader$CeIVMSection;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[CeIASMAssemblerLinkerAndLoader.CeIVMSection.values().length];

         try {
            var0[CeIASMAssemblerLinkerAndLoader.CeIVMSection.CODE.ordinal()] = 0;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[CeIASMAssemblerLinkerAndLoader.CeIVMSection.DATA.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[CeIASMAssemblerLinkerAndLoader.CeIVMSection.HEAP.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[CeIASMAssemblerLinkerAndLoader.CeIVMSection.STACK.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$ar$edu$uns$cs$cei$ceivmapi$CeIASMAssemblerLinkerAndLoader$CeIVMSection = var0;
         return var0;
      }
   }

   static enum CeIVMSection {
      CODE,
      DATA,
      HEAP,
      STACK;
   }
}
