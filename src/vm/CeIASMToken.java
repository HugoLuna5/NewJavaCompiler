package vm;

class CeIASMToken {
   private String lexema;
   private int numLine;
   private int numColumn;

   public CeIASMToken(String lex, int numLine, int numCol) {
      this.lexema = lex;
      this.numLine = numLine;
      this.numColumn = numCol;
   }

   public String getLexema() {
      return this.lexema;
   }

   public int getNumLine() {
      return this.numLine;
   }

   public int getNumColumn() {
      return this.numColumn;
   }
}
