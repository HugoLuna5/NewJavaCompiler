/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;

/**
 *
 * @author Hugo Luna
 */
public class CeIISAInstructionREADSTRING extends CeIISAInstruction {

    public CeIISAInstructionREADSTRING(String mnemonic) {
        super(mnemonic);
    }

    public int getOpcode() {
        return 59;
    }

    public int getNumParameters() {
        return 0;
    }

    protected void execute(ArrayList<Integer> params, CeIVMAPIMemory mem, CeIVMAPISpecialRegs regs, CeIVMAPIIOSubSys io) throws CeIVMMemoryException, CeIVMRuntimeException {
        regs.setSp(regs.getSp() - 1);

        String value = JOptionPane.showInputDialog("Ingresa un dato");
        System.out.println(value);
        mem.write(regs.getSp(), value.length());

        regs.setPc(regs.getPc() + this.getInstructionSize());
    }
    
}
