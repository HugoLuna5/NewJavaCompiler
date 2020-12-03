package vm.exceptions;

public class CeIVMRuntimeException extends CeIVMAPIException {
   private int pcValue;

   public CeIVMRuntimeException(String msg, int pcValue) {
      super(msg);
      this.pcValue = pcValue;
   }

   public int getPcValue() {
      return this.pcValue;
   }

   public String getMessage() {
      return "Error en tiempo de ejecución: " + super.getMessage() + " Program counter (pc) = " + this.pcValue + ".";
   }
}
