package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionDUP extends CeIISAInstruction {
   public CeIISAInstructionDUP(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 30;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setSp(regs.getSp() - 1);
      mem.write(regs.getSp(), mem.read(regs.getSp() + 1));
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
