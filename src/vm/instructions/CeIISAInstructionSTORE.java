package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionSTORE extends CeIISAInstruction {
   public CeIISAInstructionSTORE(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 81;
   }

   public int getNumParameters() {
      return 1;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      mem.write(regs.getFp() + (Integer)params.get(0), mem.read(regs.getSp()));
      regs.setSp(regs.getSp() + 1);
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
