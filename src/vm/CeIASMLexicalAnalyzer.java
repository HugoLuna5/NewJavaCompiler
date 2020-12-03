package vm;

import vm.exceptions.CeIASMParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

class CeIASMLexicalAnalyzer implements Scanner {
   public static final int YYEOF = -1;
   private static final int ZZ_BUFFERSIZE = 16384;
   public static final int YYINITIAL = 0;
   private static final int[] ZZ_LEXSTATE = new int[2];
   private static final String ZZ_CMAP_PACKED = "\t\u0000\u0001\b\u0001\u0007\u0001\u0013\u0001\u0014\u0001\u0006\u0012\u0000\u0001\b\u0001\u0000\u0001\u0004\u0001\u0012\u0001\u0001\u0002\u0000\u0001\u0005\u0001\u000f\u0001\u0010\u0002\u0000\u0001\u000e\u0001\u0011\u0001\r\u0001\u0000\n\u0002\u0001\u0003\u0001\u0012\u0004\u0000\u0001\u0001\u0003\u0001\u0001\t\u000b\u0001\u0001\f\u0004\u0001\u0001\u000b\u0001\u0001\u0001\n\u0003\u0001\u0004\u0000\u0001\u0001\u0001\u0000\u0003\u0001\u0001\t\u000b\u0001\u0001\f\u0004\u0001\u0001\u000b\u0001\u0001\u0001\n\u0003\u0001\n\u0000\u0001\u0013ᾢ\u0000\u0002\u0013\udfd6\u0000";
   private static final char[] ZZ_CMAP = zzUnpackCMap("\t\u0000\u0001\b\u0001\u0007\u0001\u0013\u0001\u0014\u0001\u0006\u0012\u0000\u0001\b\u0001\u0000\u0001\u0004\u0001\u0012\u0001\u0001\u0002\u0000\u0001\u0005\u0001\u000f\u0001\u0010\u0002\u0000\u0001\u000e\u0001\u0011\u0001\r\u0001\u0000\n\u0002\u0001\u0003\u0001\u0012\u0004\u0000\u0001\u0001\u0003\u0001\u0001\t\u000b\u0001\u0001\f\u0004\u0001\u0001\u000b\u0001\u0001\u0001\n\u0003\u0001\u0004\u0000\u0001\u0001\u0001\u0000\u0003\u0001\u0001\t\u000b\u0001\u0001\f\u0004\u0001\u0001\u000b\u0001\u0001\u0001\n\u0003\u0001\n\u0000\u0001\u0013ᾢ\u0000\u0002\u0013\udfd6\u0000");
   private static final int[] ZZ_ACTION = zzUnpackAction();
   private static final String ZZ_ACTION_PACKED_0 = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0002\u0001\u0002\u0004\u0001\u0005\u0001\u0002\u0001\u0001\u0001\u0006\u0001\u0007\u0001\b\u0002\u0001\u0001\t\u0001\u0000\u0001\n\u0001\u0000\u0001\u000b\u0001\u0002\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\u0011\u0001\u0012";
   private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
   private static final String ZZ_ROWMAP_PACKED_0 = "\u0000\u0000\u0000\u0015\u0000*\u0000?\u0000T\u0000i\u0000~\u0000\u0015\u0000\u0093\u0000¨\u0000½\u0000\u0015\u0000\u0015\u0000\u0015\u0000?\u0000Ò\u0000\u0015\u0000ç\u0000ç\u0000ü\u0000*\u0000đ\u0000Ħ\u0000Ļ\u0000ç\u0000\u0015\u0000*\u0000Ő\u0000\u0015\u0000Ļ\u0000\u0015";
   private static final int[] ZZ_TRANS = zzUnpackTrans();
   private static final String ZZ_TRANS_PACKED_0 = "\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0002\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0003\u0003\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0002\u0001\t\u0016\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0004\u0003\n\u0000\u0001\u0004\u0012\u0000\u0004\u0012\u0001\u0013\u0001\u0012\u0002\u0000\r\u0012\u0006\u0014\u0002\u0000\r\u0014\u0007\u0000\u0001\b\u0015\u0000\u0001\t\u000b\u0000\u0001\t\u0001\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0001\u0003\u0001\u0015\u0001\u0016\u0001\u0003\t\u0000\u0001\u0017\u0007\u0000\u0004\u0017\b\u0000\u0006\u0018\u0002\u0000\r\u0018\u0004\u0012\u0001\u0019\u0001\u0012\u0002\u0000\r\u0012\u0005\u0000\u0001\u001a\u0010\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0003\u0003\u0001\u001b\t\u0000\u0002\u0017\u0006\u0000\u0004\u0017\b\u0000\u0006\u0018\u0001\u001c\u0001\u001d\u000b\u0018\u0002\u001e\u0007\u0000\u0001\u001f\r\u0000";
   private static final int ZZ_UNKNOWN_ERROR = 0;
   private static final int ZZ_NO_MATCH = 1;
   private static final int ZZ_PUSHBACK_2BIG = 2;
   private static final String[] ZZ_ERROR_MSG = new String[]{"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"};
   private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
   private static final String ZZ_ATTRIBUTE_PACKED_0 = "\u0001\u0000\u0001\t\u0005\u0001\u0001\t\u0003\u0001\u0003\t\u0002\u0001\u0001\t\u0001\u0000\u0001\u0001\u0001\u0000\u0005\u0001\u0001\t\u0002\u0001\u0001\t\u0001\u0001\u0001\t";
   private Reader zzReader;
   private int zzState;
   private int zzLexicalState;
   private char[] zzBuffer;
   private int zzMarkedPos;
   private int zzCurrentPos;
   private int zzStartRead;
   private int zzEndRead;
   private int yyline;
   private int yychar;
   private int yycolumn;
   private boolean zzAtBOL;
   private boolean zzAtEOF;
   private boolean zzEOFDone;
   private String reportedFilename;

   private static int[] zzUnpackAction() {
      int[] result = new int[31];
      int offset = 0;
      zzUnpackAction("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0002\u0001\u0002\u0004\u0001\u0005\u0001\u0002\u0001\u0001\u0001\u0006\u0001\u0007\u0001\b\u0002\u0001\u0001\t\u0001\u0000\u0001\n\u0001\u0000\u0001\u000b\u0001\u0002\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\u0011\u0001\u0012", offset, result);
      return result;
   }

   private static int zzUnpackAction(String packed, int offset, int[] result) {
      int i = 0;
      int j = offset;
      int l = packed.length();

      while(i < l) {
         int count = packed.charAt(i++);
         char value = packed.charAt(i++);

         while(true) {
            result[j++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return j;
   }

   private static int[] zzUnpackRowMap() {
      int[] result = new int[31];
      int offset = 0;
      zzUnpackRowMap("\u0000\u0000\u0000\u0015\u0000*\u0000?\u0000T\u0000i\u0000~\u0000\u0015\u0000\u0093\u0000¨\u0000½\u0000\u0015\u0000\u0015\u0000\u0015\u0000?\u0000Ò\u0000\u0015\u0000ç\u0000ç\u0000ü\u0000*\u0000đ\u0000Ħ\u0000Ļ\u0000ç\u0000\u0015\u0000*\u0000Ő\u0000\u0015\u0000Ļ\u0000\u0015", offset, result);
      return result;
   }

   private static int zzUnpackRowMap(String packed, int offset, int[] result) {
      int i = 0;
      int j = offset;

      int high;
      for(int l = packed.length(); i < l; result[j++] = high | packed.charAt(i++)) {
         high = packed.charAt(i++) << 16;
      }

      return j;
   }

   private static int[] zzUnpackTrans() {
      int[] result = new int[357];
      int offset = 0;
      zzUnpackTrans("\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0002\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0003\u0003\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0002\u0001\t\u0016\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0004\u0003\n\u0000\u0001\u0004\u0012\u0000\u0004\u0012\u0001\u0013\u0001\u0012\u0002\u0000\r\u0012\u0006\u0014\u0002\u0000\r\u0014\u0007\u0000\u0001\b\u0015\u0000\u0001\t\u000b\u0000\u0001\t\u0001\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0001\u0003\u0001\u0015\u0001\u0016\u0001\u0003\t\u0000\u0001\u0017\u0007\u0000\u0004\u0017\b\u0000\u0006\u0018\u0002\u0000\r\u0018\u0004\u0012\u0001\u0019\u0001\u0012\u0002\u0000\r\u0012\u0005\u0000\u0001\u001a\u0010\u0000\u0002\u0003\u0001\u0011\u0005\u0000\u0003\u0003\u0001\u001b\t\u0000\u0002\u0017\u0006\u0000\u0004\u0017\b\u0000\u0006\u0018\u0001\u001c\u0001\u001d\u000b\u0018\u0002\u001e\u0007\u0000\u0001\u001f\r\u0000", offset, result);
      return result;
   }

   private static int zzUnpackTrans(String packed, int offset, int[] result) {
      int i = 0;
      int j = offset;
      int l = packed.length();

      while(i < l) {
         int count = packed.charAt(i++);
         int value = packed.charAt(i++);
         value = value - 1;

         while(true) {
            result[j++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return j;
   }

   private static int[] zzUnpackAttribute() {
      int[] result = new int[31];
      int offset = 0;
      zzUnpackAttribute("\u0001\u0000\u0001\t\u0005\u0001\u0001\t\u0003\u0001\u0003\t\u0002\u0001\u0001\t\u0001\u0000\u0001\u0001\u0001\u0000\u0005\u0001\u0001\t\u0002\u0001\u0001\t\u0001\u0001\u0001\t", offset, result);
      return result;
   }

   private static int zzUnpackAttribute(String packed, int offset, int[] result) {
      int i = 0;
      int j = offset;
      int l = packed.length();

      while(i < l) {
         int count = packed.charAt(i++);
         char value = packed.charAt(i++);

         while(true) {
            result[j++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return j;
   }

   CeIASMLexicalAnalyzer(Reader in, String reportedFilename) {
      this.zzLexicalState = 0;
      this.zzBuffer = new char[16384];
      this.zzAtBOL = true;
      this.reportedFilename = "";
      this.reportedFilename = reportedFilename;
      this.zzReader = in;
   }

   CeIASMLexicalAnalyzer(InputStream in, String reportedFilename) {
      this((Reader)(new InputStreamReader(in)), reportedFilename);
   }

   private static char[] zzUnpackCMap(String packed) {
      char[] map = new char[65536];
      int i = 0;
      int var3 = 0;

      while(i < 104) {
         int count = packed.charAt(i++);
         char value = packed.charAt(i++);

         while(true) {
            map[var3++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return map;
   }

   private boolean zzRefill() throws IOException {
      if (this.zzStartRead > 0) {
         System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
         this.zzEndRead -= this.zzStartRead;
         this.zzCurrentPos -= this.zzStartRead;
         this.zzMarkedPos -= this.zzStartRead;
         this.zzStartRead = 0;
      }

      if (this.zzCurrentPos >= this.zzBuffer.length) {
         char[] newBuffer = new char[this.zzCurrentPos * 2];
         System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
         this.zzBuffer = newBuffer;
      }

      int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
      if (numRead > 0) {
         this.zzEndRead += numRead;
         return false;
      } else if (numRead == 0) {
         int c = this.zzReader.read();
         if (c == -1) {
            return true;
         } else {
            this.zzBuffer[this.zzEndRead++] = (char)c;
            return false;
         }
      } else {
         return true;
      }
   }

   public final void yyclose() throws IOException {
      this.zzAtEOF = true;
      this.zzEndRead = this.zzStartRead;
      if (this.zzReader != null) {
         this.zzReader.close();
      }

   }

   public final void yyreset(Reader reader) {
      this.zzReader = reader;
      this.zzAtBOL = true;
      this.zzAtEOF = false;
      this.zzEOFDone = false;
      this.zzEndRead = this.zzStartRead = 0;
      this.zzCurrentPos = this.zzMarkedPos = 0;
      this.yyline = this.yychar = this.yycolumn = 0;
      this.zzLexicalState = 0;
   }

   public final int yystate() {
      return this.zzLexicalState;
   }

   public final void yybegin(int newState) {
      this.zzLexicalState = newState;
   }

   public final String yytext() {
      return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
   }

   public final char yycharat(int pos) {
      return this.zzBuffer[this.zzStartRead + pos];
   }

   public final int yylength() {
      return this.zzMarkedPos - this.zzStartRead;
   }

   private void zzScanError(int errorCode) {
      String message;
      try {
         message = ZZ_ERROR_MSG[errorCode];
      } catch (ArrayIndexOutOfBoundsException var4) {
         message = ZZ_ERROR_MSG[0];
      }

      throw new Error(message);
   }

   public void yypushback(int number) {
      if (number > this.yylength()) {
         this.zzScanError(2);
      }

      this.zzMarkedPos -= number;
   }

   private void zzDoEOF() throws IOException {
      if (!this.zzEOFDone) {
         this.zzEOFDone = true;
         this.yyclose();
      }

   }

   public Symbol next_token() throws IOException, Exception {
      int zzEndReadL = this.zzEndRead;
      char[] zzBufferL = this.zzBuffer;
      char[] zzCMapL = ZZ_CMAP;
      int[] zzTransL = ZZ_TRANS;
      int[] zzRowMapL = ZZ_ROWMAP;
      int[] zzAttrL = ZZ_ATTRIBUTE;

      while(true) {
         int zzMarkedPosL = this.zzMarkedPos;
         boolean zzR = false;

         int zzCurrentPosL;
         for(zzCurrentPosL = this.zzStartRead; zzCurrentPosL < zzMarkedPosL; ++zzCurrentPosL) {
            switch(zzBufferL[zzCurrentPosL]) {
            case '\n':
               if (zzR) {
                  zzR = false;
               } else {
                  ++this.yyline;
                  this.yycolumn = 0;
               }
               break;
            case '\u000b':
            case '\f':
            case '\u0085':
            case '\u2028':
            case '\u2029':
               ++this.yyline;
               this.yycolumn = 0;
               zzR = false;
               break;
            case '\r':
               ++this.yyline;
               this.yycolumn = 0;
               zzR = true;
               break;
            default:
               zzR = false;
               ++this.yycolumn;
            }
         }

         boolean eof;
         if (zzR) {
            if (zzMarkedPosL < zzEndReadL) {
               eof = zzBufferL[zzMarkedPosL] == '\n';
            } else if (this.zzAtEOF) {
               eof = false;
            } else {
               eof = this.zzRefill();
               zzEndReadL = this.zzEndRead;
               zzMarkedPosL = this.zzMarkedPos;
               zzBufferL = this.zzBuffer;
               if (eof) {
                  eof = false;
               } else {
                  eof = zzBufferL[zzMarkedPosL] == '\n';
               }
            }

            if (eof) {
               --this.yyline;
            }
         }

         int zzAction = -1;
         zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
         this.zzState = ZZ_LEXSTATE[this.zzLexicalState];

         int zzInput;
         while(true) {
            if (zzCurrentPosL < zzEndReadL) {
               zzInput = zzBufferL[zzCurrentPosL++];
            } else {
               if (this.zzAtEOF) {
                  zzInput = -1;
                  break;
               }

               this.zzCurrentPos = zzCurrentPosL;
               this.zzMarkedPos = zzMarkedPosL;
               eof = this.zzRefill();
               zzCurrentPosL = this.zzCurrentPos;
               zzMarkedPosL = this.zzMarkedPos;
               zzBufferL = this.zzBuffer;
               zzEndReadL = this.zzEndRead;
               if (eof) {
                  zzInput = -1;
                  break;
               }

               zzInput = zzBufferL[zzCurrentPosL++];
            }

            int zzNext = zzTransL[zzRowMapL[this.zzState] + zzCMapL[zzInput]];
            if (zzNext == -1) {
               break;
            }

            this.zzState = zzNext;
            int zzAttributes = zzAttrL[this.zzState];
            if ((zzAttributes & 1) == 1) {
               zzAction = this.zzState;
               zzMarkedPosL = zzCurrentPosL;
               if ((zzAttributes & 8) == 8) {
                  break;
               }
            }
         }

         this.zzMarkedPos = zzMarkedPosL;
         switch(zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
         case 1:
            throw new CeIASMParseException("Error léxico: símbolo inválido (" + this.yytext() + ").", this.yyline + 1, this.yycolumn + 1, this.reportedFilename);
         case 2:
            return new Symbol(2, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 3:
            return new Symbol(6, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 4:
            return new Symbol(9, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 5:
         case 13:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
            break;
         case 6:
            return new Symbol(5, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 7:
            return new Symbol(11, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 8:
            return new Symbol(12, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 9:
            return new Symbol(10, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 10:
            throw new CeIASMParseException("Error léxico: no se permiten cadenas nulas.", this.yyline + 1, this.yycolumn + 1, this.reportedFilename);
         case 11:
            return new Symbol(4, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 12:
            return new Symbol(3, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 14:
            return new Symbol(7, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 15:
            return new Symbol(8, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 16:
            return new Symbol(13, new CeIASMToken(this.yytext(), this.yyline + 1, this.yycolumn + 1));
         case 17:
            this.yypushback(1);
            break;
         case 18:
            this.yypushback(2);
            break;
         default:
            if (zzInput == -1 && this.zzStartRead == this.zzCurrentPos) {
               this.zzAtEOF = true;
               this.zzDoEOF();
               return new Symbol(0);
            }

            this.zzScanError(1);
         }
      }
   }
}
