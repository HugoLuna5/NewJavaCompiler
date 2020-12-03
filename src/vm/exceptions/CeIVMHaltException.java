package vm.exceptions;

public class CeIVMHaltException extends CeIVMAPIException {
   public CeIVMHaltException() {
      super("");
   }

   public String getMessage() {
      return "\nLa ejecución del programa finalizó exitosamente.\n";
   }
}
