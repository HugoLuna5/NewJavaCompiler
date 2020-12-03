package vm.exceptions;

public class CeIVMMemoryException extends CeIVMAPIException {
   private int location;
   private int pcValue;
   private boolean existPcValue;

   public CeIVMMemoryException(String msg, int location) {
      super(msg);
      this.location = location;
      this.pcValue = 0;
      this.existPcValue = false;
   }

   public CeIVMMemoryException(String msg, int location, int pcValue) {
      this(msg, location);
      this.pcValue = pcValue;
      this.existPcValue = true;
   }

   public void addRuntimeInfo(int pcValue) {
      this.pcValue = pcValue;
      this.existPcValue = true;
   }

   public int getLocation() {
      return this.location;
   }

   public int getPcValue() {
      return this.existPcValue ? this.pcValue : 0;
   }

   public String getMessage() {
      return this.existPcValue ? "Error de acceso a memoria en la locación " + this.location + ": " + super.getMessage() + " Program counter (pc) = " + this.pcValue + "." : "Error de acceso a memoria en la locación " + this.location + ": " + super.getMessage();
   }
}
