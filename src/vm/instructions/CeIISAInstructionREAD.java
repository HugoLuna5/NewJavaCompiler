/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Hugo Luna
 */
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

        String value = JOptionPane.showInputDialog("Ingresa un dato");
        System.out.println(value);
        mem.write(regs.getSp(), value.length());

        regs.setPc(regs.getPc() + this.getInstructionSize());
    }
}
