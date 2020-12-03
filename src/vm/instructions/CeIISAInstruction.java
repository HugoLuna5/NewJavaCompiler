package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMHaltException;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public abstract class CeIISAInstruction {
   private String mnemonic;

   CeIISAInstruction(String mnemonic) {
      this.mnemonic = mnemonic;
   }

   public abstract int getOpcode();

   public String getMnemonic() {
      return this.mnemonic;
   }

   public abstract int getNumParameters();

   public int getInstructionSize() {
      return this.getNumParameters() + 1;
   }

   public void validateAndExecute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException, CeIVMHaltException {
      if (params != null && params.size() == this.getNumParameters()) {
         try {
            this.execute(params, mem, regs, io);
         } catch (CeIVMMemoryException var6) {
            var6.addRuntimeInfo(regs.getPc());
            throw var6;
         }
      } else {
         throw new CeIVMRuntimeException("Argumentos incorrectos al ejecutar la instrucción " + this.getMnemonic() + ".", regs.getPc());
      }
   }

   protected abstract void execute(ArrayList<Integer> var1, CeIVMAPIMemory var2, CeIVMAPISpecialRegs var3, CeIVMAPIIOSubSys var4) throws CeIVMMemoryException, CeIVMRuntimeException, CeIVMHaltException;
}
