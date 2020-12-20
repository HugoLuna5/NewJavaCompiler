package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionSTOREPC extends CeIISAInstruction {
   public CeIISAInstructionSTOREPC(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 121;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setPc(mem.read(regs.getSp()));
      regs.setSp(regs.getSp() + 1);
   } 
}
