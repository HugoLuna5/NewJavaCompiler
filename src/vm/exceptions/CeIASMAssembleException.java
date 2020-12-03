package vm.exceptions;

public class CeIASMAssembleException extends CeIASMLangProcessException {
   public CeIASMAssembleException(String msg, int numLine, int numColumn, String filename) {
      super(msg, numLine, numColumn, filename);
   }

   public String getMessage() {
      return "Error durante el ensamblado: " + super.getMessage();
   }
}
