package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionFLOATPRINT extends CeIISAInstruction {
   public CeIISAInstructionFLOATPRINT(String mnemonic) {
      super(mnemonic);
   }

   public int getOpcode() {
      return 18;
   }

   public int getNumParameters() {
      return 0;
   }

   protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
      if (mem.read(regs.getSp()) == 0) {
         throw new CeIVMRuntimeException("Desreferenciameinto de puntero nulo en SPRINT.", regs.getPc());
      } else {
         while(mem.read(mem.read(regs.getSp())) != 0) {
            int c = mem.read(mem.read(regs.getSp()));
            if (c < 0 || c > 65535) {
               throw new CeIVMRuntimeException("Valor char = " + c + ", fuera de rango en SPRINT.", regs.getPc());
            }

            io.getOutputStream().print((char)c);
            mem.write(regs.getSp(), mem.read(regs.getSp()) + 1);
         }

         regs.setSp(regs.getSp() + 1);
         regs.setPc(regs.getPc() + this.getInstructionSize());
      }
   }
}
