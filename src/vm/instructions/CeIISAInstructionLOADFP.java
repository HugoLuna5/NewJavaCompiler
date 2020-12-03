package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionLOADFP extends CeIISAInstruction {
   public CeIISAInstructionLOADFP(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 124;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setSp(regs.getSp() - 1);
      mem.write(regs.getSp(), regs.getFp());
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
