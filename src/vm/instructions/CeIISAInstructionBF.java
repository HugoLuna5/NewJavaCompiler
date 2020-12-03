package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionBF extends CeIISAInstruction {
   public CeIISAInstructionBF(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 41;
   }

   public int getNumParameters() {
      return 1;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp()) == 0) {
         regs.setPc((Integer)params.get(0));
      } else {
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }

      regs.setSp(regs.getSp() + 1);
   }
}
