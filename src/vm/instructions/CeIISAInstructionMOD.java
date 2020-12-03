package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionMOD extends CeIISAInstruction {
   public CeIISAInstructionMOD(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 6;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      int divisor = mem.read(regs.getSp());
      if (divisor == 0) {
         throw new CeIVMRuntimeException("División por cero en MOD.", regs.getPc());
      } else {
         mem.write(regs.getSp() + 1, mem.read(regs.getSp() + 1) % divisor);
         regs.setSp(regs.getSp() + 1);
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }
   }
}
