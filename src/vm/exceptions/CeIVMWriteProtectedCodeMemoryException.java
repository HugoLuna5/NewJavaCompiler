package vm.exceptions;

public class CeIVMWriteProtectedCodeMemoryException extends CeIVMMemoryException {
   public CeIVMWriteProtectedCodeMemoryException(int location) {
      super("Se intentó escribir en la sección .CODE con la protección contra escritura habilitada.", location);
   }

   public CeIVMWriteProtectedCodeMemoryException(int location, int pcValue) {
      super("Se intentó escribir en la sección .CODE con la protección contra escritura habilitada.", location, pcValue);
   }
}
