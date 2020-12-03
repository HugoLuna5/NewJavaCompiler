package vm.exceptions;

public class CeIASMLoadException extends CeIASMLangProcessException {
   public CeIASMLoadException(String msg, int numLine, int numColumn, String filename) {
      super(msg, numLine, numColumn, filename);
   }

   public String getMessage() {
      return "Error durante el linkeo/carga: " + super.getMessage();
   }
}
