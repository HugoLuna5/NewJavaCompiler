package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionLOADHP extends CeIISAInstruction {
   public CeIISAInstructionLOADHP(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 126;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setSp(regs.getSp() - 1);
      mem.write(regs.getSp(), regs.getHp());
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
