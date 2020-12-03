package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionLOADHL extends CeIISAInstruction {
   public CeIISAInstructionLOADHL(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 128;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setSp(regs.getSp() - 1);
      mem.write(regs.getSp(), regs.getHl());
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
