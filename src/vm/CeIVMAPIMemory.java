package vm;

import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMWriteProtectedCodeMemoryException;

public class CeIVMAPIMemory {
   private int[] memory;
   private int memSize;
   private int endOfCode = 0;
   private boolean enabledMemoryProtection = false;

   CeIVMAPIMemory(int memSize, CeIVMAPISpecialRegs regFile) {
      if (memSize < 255) {
         memSize = 255;
      }

      this.memSize = memSize;
      this.memory = new int[this.memSize];
   }

   public int read(int address) throws CeIVMMemoryException {
      if (address < this.memSize && address >= 0) {
         return this.memory[address];
      } else {
         throw new CeIVMMemoryException("Locación de memoria no implementada.", address);
      }
   }

   public void write(int address, int value) throws CeIVMMemoryException {
      if (address < this.memSize && address >= 0) {
         if (this.enabledMemoryProtection && address <= this.endOfCode) {
            throw new CeIVMWriteProtectedCodeMemoryException(address);
         } else {
            this.memory[address] = value;
         }
      } else {
         throw new CeIVMMemoryException("Locación de memoria no implementada.", address);
      }
   }

   public int getMemSize() {
      return this.memSize;
   }

   int getLastLocation() {
      return this.memSize - 1;
   }

   void clearMemory() {
      this.memory = new int[this.memSize];
   }

   void enableMemoryWriteProtection(int address) {
      this.endOfCode = address;
      this.enabledMemoryProtection = true;
   }

   void disableMemoryWriteProtection() {
      this.enabledMemoryProtection = false;
   }
}
