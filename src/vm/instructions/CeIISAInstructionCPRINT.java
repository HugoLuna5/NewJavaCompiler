package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionCPRINT extends CeIISAInstruction {
   public CeIISAInstructionCPRINT(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 62;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      int c = mem.read(regs.getSp());
      if (c < 0 && c > 65535) {
         throw new CeIVMRuntimeException("Valor char fuera de rango en CPRINT.", regs.getPc());
      } else {
         io.getOutputStream().print((char)c);
         regs.setSp(regs.getSp() + 1);
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }
   }
}
