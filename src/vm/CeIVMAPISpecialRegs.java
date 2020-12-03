package vm;

import vm.exceptions.CeIVMRuntimeException;

public class CeIVMAPISpecialRegs {
   private int pc = 0;
   private int sp = 0;
   private int fp = 0;
   private int hp = 0;
   private int hl = 0;
   private int endOfCode = 0;
   private boolean enabledPreDEP = false;

   public int getFp() {
      return this.fp;
   }

   public void setFp(int fp) {
      this.fp = fp;
   }

   public int getHl() {
      return this.hl;
   }

   public void setHl(int hl) throws CeIVMRuntimeException {
      if (hl >= this.sp) {
         throw new CeIVMRuntimeException("Memoria insuficiente al incrementar el .heap.", this.pc);
      } else {
         this.hl = hl;
      }
   }

   public int getHp() {
      return this.hp;
   }

   public void setHp(int hp) {
      this.hp = hp;
   }

   public int getPc() {
      return this.pc;
   }

   public void setPc(int pc) throws CeIVMRuntimeException {
      if (this.enabledPreDEP && pc > this.endOfCode) {
         throw new CeIVMRuntimeException("Una instrucción intentó asignar al registro pc el valor " + pc + " con DEP habilitada.", this.pc);
      } else {
         this.pc = pc;
      }
   }

   public int getSp() {
      return this.sp;
   }

   public void setSp(int sp) throws CeIVMRuntimeException {
      if (sp <= this.hl) {
         throw new CeIVMRuntimeException("Desbordamiento de pila (stack overflow).", this.pc);
      } else {
         this.sp = sp;
      }
   }

   void enablePreDEP(int address) {
      this.endOfCode = address;
      this.enabledPreDEP = true;
   }

   void disablePreDEP() {
      this.enabledPreDEP = false;
   }
}
