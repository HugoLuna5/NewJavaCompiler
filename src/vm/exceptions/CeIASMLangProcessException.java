package vm.exceptions;

public abstract class CeIASMLangProcessException extends CeIVMAPIException {
   private int numLine;
   private int numColumn;
   private String filename;

   public CeIASMLangProcessException(String msg, int numLine, int numColumn, String filename) {
      super(msg);
      this.numLine = numLine;
      this.numColumn = numColumn;
      this.filename = filename;
   }

   public int getNumColumn() {
      return this.numColumn;
   }

   public int getNumLine() {
      return this.numLine;
   }

   public String getMessage() {
      return super.getMessage() + " [" + this.filename + " - línea " + this.numLine + ", columna " + this.numColumn + "].";
   }
}
