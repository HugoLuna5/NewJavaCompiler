package vm.exceptions;

public class CeIVMDEPException extends CeIVMRuntimeException {
   public CeIVMDEPException(int pcValue) {
      super("Se intentó ejecutar una locación de memoria de datos con DEP habilitada.", pcValue);
   }
}
