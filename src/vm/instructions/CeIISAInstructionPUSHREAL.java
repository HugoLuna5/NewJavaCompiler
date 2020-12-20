package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionPUSHREAL extends CeIISAInstruction {

    public CeIISAInstructionPUSHREAL(String mnemonic) {
        super(mnemonic);
    }

    public int getOpcode() {
        return 17;
    }

    public int getNumParameters() {
        return 1;
    }

    protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {

        regs.setSp(regs.getSp() - 1);
        mem.write(regs.getSp(), (Integer) params.get(0));
        regs.setPc(regs.getPc() + this.getInstructionSize());

       
        String strComplete = "";
        while (mem.read(mem.read(regs.getSp())) != 0) {
            int c = mem.read(mem.read(regs.getSp()));
            if (c < 0 || c > 65535) {
                throw new CeIVMRuntimeException("Valor char = " + c + ", fuera de rango en SPRINT.", regs.getPc());
            }
            strComplete += c;
            mem.write(regs.getSp(), mem.read(regs.getSp()) + 1);
        }

        float value = Float.parseFloat(strComplete);
        mem.writeFloat(regs.getSp(), value);
        regs.setPc(regs.getPc() + this.getInstructionSize());
    }
}
