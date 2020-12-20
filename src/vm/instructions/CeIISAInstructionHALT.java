package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMHaltException;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;

public class CeIISAInstructionHALT extends CeIISAInstruction {
    public CeIISAInstructionHALT(String mnemonic) {
        super(mnemonic);
    }

    public int getOpcode() {
        return 104;
    }

    public int getNumParameters() {
        return 0;
    }

    protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException, CeIVMHaltException {
        throw new CeIVMHaltException();
    }
}
