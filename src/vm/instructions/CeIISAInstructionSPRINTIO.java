package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionSPRINTIO extends CeIISAInstruction {
   public CeIISAInstructionSPRINTIO(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 58;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp()) == 0) {
         throw new CeIVMRuntimeException("Desreferenciameinto de puntero nulo en SPRINT.", regs.getPc());
      } else {
          String value = mem.readStr(mem.read(regs.getSp()));//mem.readStr(mem.readStr(regs.getSp());
          
          for (int i = 0; i < value.length(); i++) {
              io.getOutputStream().print((char)value.charAt(i));
              
          }
          io.getOutputStream().print((char)'\n');
         regs.setSp(regs.getSp() + 1);
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }
   }
}
