package vm;

import vm.exceptions.CeIASMAssembleException;
import vm.exceptions.CeIVMRuntimeException;
import vm.instructions.CeIISAInstruction;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

public class CeIISA {
   private Hashtable<Integer, CeIISAInstruction> instrSetByOpcode = new Hashtable();
   private Hashtable<String, CeIISAInstruction> instrSetByMnemonic = new Hashtable();
   private CeIVMAPISpecialRegs regFile;
   // $FF: synthetic field
   static Class class$0;

   CeIISA(CeIVMAPISpecialRegs regFile) {
      this.regFile = regFile;
   }

   CeIISAInstruction getCeIVMInstruction(int opcode) throws CeIVMRuntimeException {
      CeIISAInstruction i = (CeIISAInstruction)this.instrSetByOpcode.get(new Integer(opcode));
      if (i == null) {
         throw new CeIVMRuntimeException("Opcode inválido (" + opcode + ") o instrucción no registrada en el CeIISA.", this.regFile.getPc());
      } else {
         return i;
      }
   }

   CeIISAInstruction getCeIVMInstruction(String mnemonic, int numLine, int numColumn, String filename) throws CeIASMAssembleException {
      CeIISAInstruction i = (CeIISAInstruction)this.instrSetByMnemonic.get(mnemonic.toLowerCase());
      if (i == null) {
         i = this.registerInstructionIntoCeIISA(mnemonic, numLine, numColumn, filename);
      }

      return i;
   }

   private CeIISAInstruction registerInstructionIntoCeIISA(String mnemonic, int numLine, int numColumn, String filename) throws CeIASMAssembleException {
      Class c;
      try {
         c = Class.forName("vm.instructions.CeIISAInstruction" + mnemonic.toUpperCase());
      } catch (ClassNotFoundException var12) {
         throw new CeIASMAssembleException("Menemónico inválido (" + mnemonic + "). No existe la instrucción en el CeIISA.", numLine, numColumn, filename);
      }

      Object instruction;
      try {
         Constructor ctor = c.getDeclaredConstructor(String.class);
         ctor.setAccessible(true);
         instruction = ctor.newInstance(mnemonic);
      } catch (NoSuchMethodException var10) {
         throw new CeIASMAssembleException("Error al registrar la instrucción " + mnemonic + " en el CeIISA. La instrucción debe implementar un constructor con un parámetro de tipo String.", numLine, numColumn, filename);
      } catch (Exception var11) {
         throw new CeIASMAssembleException("Error al registrar la instrucción " + mnemonic + " en el CeIISA.", numLine, numColumn, filename);
      }

      if (!(instruction instanceof CeIISAInstruction)) {
         throw new CeIASMAssembleException("La instrucción " + mnemonic + " no es una instrucción válida en el CeIISA (no hereda de CeIISAInstruction).", numLine, numColumn, filename);
      } else {
         CeIISAInstruction instrCeIISA = (CeIISAInstruction)instruction;
         Integer opC = new Integer(instrCeIISA.getOpcode());
         if (this.instrSetByOpcode.containsKey(opC)) {
            CeIISAInstruction conflictingInstr = (CeIISAInstruction)this.instrSetByOpcode.get(opC);
            throw new CeIASMAssembleException("La instrucción " + mnemonic + " presenta conflicto con el opcode de la instrucción " + conflictingInstr.getMnemonic() + ".", numLine, numColumn, filename);
         } else {
            this.instrSetByMnemonic.put(mnemonic.toLowerCase(), instrCeIISA);
            this.instrSetByOpcode.put(opC, instrCeIISA);
            return instrCeIISA;
         }
      }
   }
}
