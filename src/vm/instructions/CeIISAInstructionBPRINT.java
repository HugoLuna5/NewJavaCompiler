package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionBPRINT extends CeIISAInstruction {
   public CeIISAInstructionBPRINT(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 61;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp()) == 0) {
         io.getOutputStream().print("false");
      } else {
         io.getOutputStream().print("true");
      }

      regs.setSp(regs.getSp() + 1);
      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
