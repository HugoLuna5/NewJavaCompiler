package vm.exceptions;

public class CeIASMParseException extends CeIASMLangProcessException {
   public CeIASMParseException(String msg, int numLine, int numColumn, String filename) {
      super(msg, numLine, numColumn, filename);
   }

   public String getMessage() {
      return "Error durante el parsing: " + super.getMessage();
   }
}
