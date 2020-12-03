package vm;

import vm.exceptions.CeIVMDEPException;
import vm.exceptions.CeIVMHaltException;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import vm.instructions.CeIISAInstruction;
import java.util.ArrayList;

class CeIVMAPIExecutionEngine {
   private CeIVMAPIIOSubSys ioSubSys;
   private CeIVMAPIMemory mem;
   private CeIVMAPISpecialRegs regFile;
   private CeIISA decodeUnit;
   private int endOfCode = 0;
   private boolean enabledDEP = false;

   CeIVMAPIExecutionEngine(CeIVMAPIIOSubSys io, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIISA instrSet) {
      this.ioSubSys = io;
      this.mem = mem;
      this.regFile = regs;
      this.decodeUnit = instrSet;
   }

   public void executeToCompletion() throws CeIVMMemoryException, CeIVMRuntimeException, CeIVMHaltException {
      while(true) {
         this.executeNextStep();
      }
   }

   public void executeNextStep() throws CeIVMMemoryException, CeIVMRuntimeException, CeIVMHaltException {
      if (this.enabledDEP && this.regFile.getPc() > this.endOfCode) {
         throw new CeIVMDEPException(this.regFile.getPc());
      } else {
         int opcode = this.mem.read(this.regFile.getPc());
         CeIISAInstruction instr = this.decodeUnit.getCeIVMInstruction(opcode);
         ArrayList<Integer> params = new ArrayList();

         for(int i = 1; i <= instr.getNumParameters(); ++i) {
            params.add(this.mem.read(this.regFile.getPc() + i));
         }

         instr.validateAndExecute(params, this.mem, this.regFile, this.ioSubSys);
      }
   }

   void enableDEP(int address) {
      this.endOfCode = address;
      this.enabledDEP = true;
   }

   void disableDEP() {
      this.enabledDEP = false;
   }
}
