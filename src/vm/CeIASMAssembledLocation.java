package vm;

import vm.exceptions.CeIASMLoadException;

class CeIASMAssembledLocation {
   private String label = null;
   private int contentInt;
   private String contentString;
   private boolean assembledIntegerLocation;
   private int numLine;
   private int numColumn;
   private String filename;
   private String dumpInfo;

   public CeIASMAssembledLocation(String label, int content, int numLine, int numColumn, String filename, String dumpInfo) {
      this.label = label;
      this.assembledIntegerLocation = true;
      this.contentString = null;
      this.contentInt = content;
      this.numLine = numLine;
      this.numColumn = numColumn;
      this.filename = filename;
      this.dumpInfo = dumpInfo;
   }

   public CeIASMAssembledLocation(String label, String content, int numLine, int numColumn, String filename, String dumpInfo) {
      this.label = label;
      this.assembledIntegerLocation = false;
      this.contentString = content;
      this.contentInt = 0;
      this.numLine = numLine;
      this.numColumn = numColumn;
      this.filename = filename;
      this.dumpInfo = dumpInfo;
   }

   public boolean isAssembledIntegerLocation() {
      return this.assembledIntegerLocation;
   }

   public int getContentInt() throws CeIASMLoadException {
      if (!this.assembledIntegerLocation) {
         throw new CeIASMLoadException("Se intentó acceder al contenido entero de una locación ensamblada con etiqueta.", this.numLine, this.numColumn, this.filename);
      } else {
         return this.contentInt;
      }
   }

   public String getContentString() throws CeIASMLoadException {
      if (this.assembledIntegerLocation) {
         throw new CeIASMLoadException("Se intentó acceder a la etiqueta contenido de una locación ensamblada entera.", this.numLine, this.numColumn, this.filename);
      } else {
         return this.contentString;
      }
   }

   public String getLabel() {
      return this.label;
   }

   public int getNumColumn() {
      return this.numColumn;
   }

   public int getNumLine() {
      return this.numLine;
   }

   public String getDumpInfo() {
      return this.dumpInfo;
   }

   public String getFilename() {
      return this.filename;
   }
}
