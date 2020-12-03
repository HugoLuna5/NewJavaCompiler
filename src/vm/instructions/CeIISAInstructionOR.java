package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionOR extends CeIISAInstruction {
   public CeIISAInstructionOR(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 9;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp() + 1) == 0 && mem.read(regs.getSp()) == 0) {
         mem.write(regs.getSp() + 1, 0);
      } else {
         mem.write(regs.getSp() + 1, 1);
      }

      regs.setSp(regs.getSp() + 1);
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
