package vm;

import java.util.Stack;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java_cup.runtime.lr_parser;

public class CeIASMParser extends lr_parser {
   protected static final short[][] _production_table = unpackFromStrings(new String[]{"\u0000\u001b\u0000\u0002\u0002\u0005\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0003\u0000\u0002\u0003\u0004\u0000\u0002\u0003\u0002\u0000\u0002\u0004\u0006\u0000\u0002\u0004\u0005\u0000\u0002\u0004\u0005\u0000\u0002\u0004\u0004\u0000\u0002\u0004\u0003\u0000\u0002\u0004\u0003\u0000\u0002\u0004\u0006\u0000\u0002\u0004\u0004\u0000\u0002\u0004\u0003\u0000\u0002\u0004\u0002\u0000\u0002\u0006\u0005\u0000\u0002\u0006\u0003\u0000\u0002\u0005\u0003\u0000\u0002\u0005\u0003\u0000\u0002\u0005\u0003\u0000\u0002\b\u0005\u0000\u0002\b\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0007"});
   protected static final short[][] _action_table = unpackFromStrings(new String[]{"\u0000%\u0000\u0010\u0002\ufff3\u0004\u0005\u0005\t\u0006\u0007\u000b\ufff3\f\n\u000f\b\u0001\u0002\u0000\u0006\u0002&\u000b%\u0001\u0002\u0000\f\u0002\ufff8\u0004\u001c\b\u001f\n\u001d\u000b\ufff8\u0001\u0002\u0000\u0006\u0002\uffff\u000b\uffff\u0001\u0002\u0000\n\u0004\u0011\b\u0014\t\u0013\n\u0012\u0001\u0002\u0000\u0006\u0002\ufff7\u000b\ufff7\u0001\u0002\u0000\u0006\u0002\ufff4\u000b\ufff4\u0001\u0002\u0000\n\u0004�\u0006�\u000b�\u000f�\u0001\u0002\u0000\n\u0004\f\u0006\r\u000b\u000f\u000f\u000e\u0001\u0002\u0000\f\u0002\ufffb\u0004\u001c\b\u001f\n\u001d\u000b\ufffb\u0001\u0002\u0000\n\u0004\u0011\b\u0014\t\u0013\n\u0012\u0001\u0002\u0000\u0006\u0002\ufffa\u000b\ufffa\u0001\u0002\u0000\n\u0004\ufffe\u0006\ufffe\u000b\ufffe\u000f\ufffe\u0001\u0002\u0000\b\u0002\ufff6\u0007\u001a\u000b\ufff6\u0001\u0002\u0000\b\u0002￫\u0007￫\u000b￫\u0001\u0002\u0000\b\u0002￨\u0007￨\u000b￨\u0001\u0002\u0000\b\u0002￩\u0007￩\u000b￩\u0001\u0002\u0000\n\u0002￪\u0007￪\u000b￪\u000f\u0016\u0001\u0002\u0000\b\u0002￬\u0007￬\u000b￬\u0001\u0002\u0000\u0004\r\u0017\u0001\u0002\u0000\u0004\b\u0018\u0001\u0002\u0000\u0004\u000e\u0019\u0001\u0002\u0000\b\u0002\uffe7\u0007\uffe7\u000b\uffe7\u0001\u0002\u0000\n\u0004\u0011\b\u0014\t\u0013\n\u0012\u0001\u0002\u0000\b\u0002￭\u0007￭\u000b￭\u0001\u0002\u0000\b\u0002\ufff0\u0007\ufff0\u000b\ufff0\u0001\u0002\u0000\b\u0002￮\u0007￮\u000b￮\u0001\u0002\u0000\b\u0002￼\u0007!\u000b￼\u0001\u0002\u0000\b\u0002\uffef\u0007\uffef\u000b\uffef\u0001\u0002\u0000\b\u0002\ufff1\u0007\ufff1\u000b\ufff1\u0001\u0002\u0000\b\u0004\u001c\b\u001f\n\u001d\u0001\u0002\u0000\b\u0002\ufff2\u0007\ufff2\u000b\ufff2\u0001\u0002\u0000\b\u0002\ufff5\u0007\u001a\u000b\ufff5\u0001\u0002\u0000\b\u0002\ufff9\u0007!\u000b\ufff9\u0001\u0002\u0000\u0010\u0002\ufff3\u0004\u0005\u0005\t\u0006\u0007\u000b\ufff3\f\n\u000f\b\u0001\u0002\u0000\u0004\u0002\u0000\u0001\u0002\u0000\u0006\u0002\u0001\u000b\u0001\u0001\u0002"});
   protected static final short[][] _reduce_table = unpackFromStrings(new String[]{"\u0000%\u0000\u0006\u0002\u0003\u0004\u0005\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0005\u001f\u0006#\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0007\u0014\b\"\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u0003\n\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0005\u001f\u0006\u001d\u0001\u0001\u0000\u0006\u0007\u0014\b\u000f\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u0007\u001a\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u0005!\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u0004&\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001"});
   protected CUP$CeIASMParser$actions action_obj;
   CeIASMAssemblerLinkerAndLoader asmLoader;
   String reportedFilename;

   public CeIASMParser() {
      this.asmLoader = null;
      this.reportedFilename = "";
   }

   public CeIASMParser(Scanner s) {
      super(s);
      this.asmLoader = null;
      this.reportedFilename = "";
   }

   public CeIASMParser(Scanner s, SymbolFactory sf) {
      super(s, sf);
      this.asmLoader = null;
      this.reportedFilename = "";
   }

   public short[][] production_table() {
      return _production_table;
   }

   public short[][] action_table() {
      return _action_table;
   }

   public short[][] reduce_table() {
      return _reduce_table;
   }

   protected void init_actions() {
      this.action_obj = new CUP$CeIASMParser$actions(this);
   }

   public Symbol do_action(int act_num, lr_parser parser, Stack stack, int top) throws Exception {
      return this.action_obj.CUP$CeIASMParser$do_action(act_num, parser, stack, top);
   }

   public int start_state() {
      return 0;
   }

   public int start_production() {
      return 1;
   }

   public int EOF_sym() {
      return 0;
   }

   public int error_sym() {
      return 1;
   }

   public void syntax_error(Symbol cur_token) {
      CeIASMToken t = (CeIASMToken)cur_token.value;
      this.report_error("Error sintáctico. Se encontró: \"" + t.getLexema() + "\" en línea " + t.getNumLine() + ", columna " + t.getNumColumn() + ".", (Object)null);
   }

   public void unrecovered_syntax_error(Symbol cur_token) throws Exception {
      CeIASMToken t = (CeIASMToken)cur_token.value;

      try {
         this.report_fatal_error("No se pudo recuperar del error sintáctico. Se encontró: \"" + t.getLexema() + "\" en línea " + t.getNumLine() + ", columna " + t.getNumColumn() + ".", (Object)null);
      } catch (Exception var4) {
         throw new Exception("Se abortó el análisis sintáctico del archivo CeIASM.");
      }
   }

   public CeIASMParser(Scanner s, CeIASMAssemblerLinkerAndLoader asmLoader, String reportedFilename) {
      this(s);
      this.asmLoader = asmLoader;
      this.reportedFilename = reportedFilename;
   }
}
