package vm;

import java.util.LinkedList;

public class CeIASMPendingLabel {
   private LinkedList<Integer> addresses = new LinkedList();
   private int numLine;
   private int numColumn;
   private String filename;

   public CeIASMPendingLabel(int address, int numLine, int numColumn, String filename) {
      this.addresses.addLast(new Integer(address));
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

   public LinkedList<Integer> getPendingAddresses() {
      return this.addresses;
   }

   public void addLocation(int address) {
      this.addresses.addLast(new Integer(address));
   }

   public String getFilename() {
      return this.filename;
   }
}
