package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionEQ extends CeIISAInstruction {
   public CeIISAInstructionEQ(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 11;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp() + 1) == mem.read(regs.getSp())) {
         mem.write(regs.getSp() + 1, 1);
      } else {
         mem.write(regs.getSp() + 1, 0);
      }

      regs.setSp(regs.getSp() + 1);
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
