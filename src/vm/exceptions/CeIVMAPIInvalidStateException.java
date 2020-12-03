package vm.exceptions;

public class CeIVMAPIInvalidStateException extends CeIVMAPIException {
   private String ceIVMCurrentStatus;
   private boolean existCeIVMCurrentStatus;

   public CeIVMAPIInvalidStateException(String msg) {
      super(msg);
      this.ceIVMCurrentStatus = null;
      this.existCeIVMCurrentStatus = false;
   }

   public CeIVMAPIInvalidStateException(String msg, String ceIVMCurrentStatus) {
      this(msg);
      this.ceIVMCurrentStatus = ceIVMCurrentStatus;
      this.existCeIVMCurrentStatus = true;
   }

   public void addStatusInfo(String ceIVMCurrentStatus) {
      this.ceIVMCurrentStatus = ceIVMCurrentStatus;
      this.existCeIVMCurrentStatus = true;
   }

   public String getCeIVMCurrentStatus() {
      return this.ceIVMCurrentStatus;
   }

   public String getMessage() {
      return this.existCeIVMCurrentStatus ? "Estado inválido en la CeIVMAPI: " + super.getMessage() + " Estado actual de la CeIVMAPI: " + this.ceIVMCurrentStatus + "." : "Estado inválido en la CeIVMAPI: " + super.getMessage();
   }
}
