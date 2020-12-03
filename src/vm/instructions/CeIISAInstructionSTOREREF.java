package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionSTOREREF extends CeIISAInstruction {
   public CeIISAInstructionSTOREREF(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 83;
   }

   public int getNumParameters() {
      return 1;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      int pointer = mem.read(regs.getSp() + 1);
      if (pointer == 0) {
         throw new CeIVMRuntimeException("Desreferenciameinto de puntero nulo en STOREREF.", regs.getPc());
      } else {
         mem.write(pointer + (Integer)params.get(0), mem.read(regs.getSp()));
         regs.setSp(regs.getSp() + 2);
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }
   }
}
