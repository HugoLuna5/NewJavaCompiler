package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.io.IOException;
import java.util.ArrayList;

public class CeIISAInstructionREAD extends CeIISAInstruction {
   public CeIISAInstructionREAD(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 60;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      regs.setSp(regs.getSp() - 1);

      try {
         mem.write(regs.getSp(), io.getInputReader().read());
      } catch (IOException var6) {
         throw new CeIVMRuntimeException("Error al ejecutar el READ.", regs.getPc());
      }

      regs.setPc(regs.getPc() + this.getInstructionSize());
   }
}
