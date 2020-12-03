package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionSWAP extends CeIISAInstruction {
   public CeIISAInstructionSWAP(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 32;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      mem.write(regs.getSp() - 1, mem.read(regs.getSp() + 1));
      mem.write(regs.getSp() + 1, mem.read(regs.getSp()));
      mem.write(regs.getSp(), mem.read(regs.getSp() - 1));
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
