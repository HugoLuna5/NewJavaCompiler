package vm;

import vm.exceptions.CeIASMParseException;
import java.util.LinkedList;
import java.util.Stack;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

class CUP$CeIASMParser$actions {
   private final CeIASMParser parser;

   public CeIASMAssemblerLinkerAndLoader getAsmLoader() {
      return this.parser.asmLoader;
   }

   public String getReportedFilename() {
      return this.parser.reportedFilename;
   }

   CUP$CeIASMParser$actions(CeIASMParser parser) {
      this.parser = parser;
   }

   public final Symbol CUP$CeIASMParser$do_action(int CUP$CeIASMParser$act_num, lr_parser CUP$CeIASMParser$parser, Stack CUP$CeIASMParser$stack, int CUP$CeIASMParser$top) throws Exception {
      Symbol CUP$CeIASMParser$result;
      Object RESULT;
      int cantleft;
      int cantright;
      CeIASMToken sid;
      int dupleft;
      int dupright;
      CeIASMToken dup;
      int i;
      int numInitright;
      LinkedList aux;
      LinkedList l;
      LinkedList l2;
      LinkedList aList;
      switch(CUP$CeIASMParser$act_num) {
      case 0:
         RESULT = null;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("Program", 0, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 1:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         Object start_val = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("$START", 0, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), start_val);
         CUP$CeIASMParser$parser.done_parsing();
         return CUP$CeIASMParser$result;
      case 2:
         RESULT = null;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("Program", 0, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 3:
         RESULT = null;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("NewLines", 1, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 4:
         RESULT = null;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("NewLines", 1, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 5:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         dup = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         i = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         numInitright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         aList = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction(sid.getLexema(), dup.getLexema(), aList, dup.getNumLine(), dup.getNumColumn(), this.getReportedFilename(), sid.getLexema() + " " + dup.getLexema());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 6:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         dup = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction(sid.getLexema(), dup.getLexema(), (LinkedList)null, dup.getNumLine(), dup.getNumColumn(), this.getReportedFilename(), sid.getLexema() + " " + dup.getLexema());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 7:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         dup = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction(sid.getLexema(), dup.getLexema(), (LinkedList)null, dup.getNumLine(), dup.getNumColumn(), this.getReportedFilename(), sid.getLexema() + " DUP");
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 8:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l2 = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction((String)null, sid.getLexema(), l2, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 9:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction((String)null, sid.getLexema(), (LinkedList)null, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 10:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleInstruction((String)null, sid.getLexema(), (LinkedList)null, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), "DUP");
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 11:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         dup = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         i = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         numInitright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         aList = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         CeIASMAssembledLocation asmLoc = (CeIASMAssembledLocation)aList.removeFirst();
         if (asmLoc.isAssembledIntegerLocation()) {
            aList.addFirst(new CeIASMAssembledLocation(sid.getLexema(), asmLoc.getContentInt(), asmLoc.getNumLine(), asmLoc.getNumColumn(), asmLoc.getFilename(), sid.getLexema() + " " + asmLoc.getDumpInfo()));
         } else {
            aList.addFirst(new CeIASMAssembledLocation(sid.getLexema(), asmLoc.getContentString(), asmLoc.getNumLine(), asmLoc.getNumColumn(), asmLoc.getFilename(), sid.getLexema() + " " + asmLoc.getDumpInfo()));
         }

         this.getAsmLoader().assembleDW(aList, dup.getNumLine(), dup.getNumColumn(), this.getReportedFilename());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 12:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l2 = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         this.getAsmLoader().assembleDW(l2, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 13:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         String s = sid.getLexema();
         if (s.compareToIgnoreCase(".CODE") == 0) {
            this.getAsmLoader().setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection.CODE, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         } else if (s.compareToIgnoreCase(".DATA") == 0) {
            this.getAsmLoader().setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection.DATA, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         } else if (s.compareToIgnoreCase(".HEAP") == 0) {
            this.getAsmLoader().setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection.HEAP, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         } else {
            if (s.compareToIgnoreCase(".STACK") != 0) {
               throw new CeIASMParseException("Directiva de cambio de sección \"" + s + "\" inválida.", sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
            }

            this.getAsmLoader().setCurrentSection(CeIASMAssemblerLinkerAndLoader.CeIVMSection.STACK, sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         }

         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 14:
         RESULT = null;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("LabelInstr", 2, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), RESULT);
         return CUP$CeIASMParser$result;
      case 15:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).right;
         l = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l2 = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         l.addAll(l2);
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentList", 4, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), l);
         return CUP$CeIASMParser$result;
      case 16:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentList", 4, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), l);
         return CUP$CeIASMParser$result;
      case 17:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, sid.getLexema(), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("Argument", 3, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 18:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, Integer.parseInt(sid.getLexema()), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("Argument", 3, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 19:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, sid.getLexema().charAt(1), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("Argument", 3, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 20:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).right;
         l = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l2 = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         l.addAll(l2);
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentListDW", 6, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 2)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), l);
         return CUP$CeIASMParser$result;
      case 21:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         l = (LinkedList)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentListDW", 6, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), l);
         return CUP$CeIASMParser$result;
      case 22:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, sid.getLexema(), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentDW", 5, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 23:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, Integer.parseInt(sid.getLexema()), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentDW", 5, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 24:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         String str = sid.getLexema();
         String str2 = str.substring(1, str.length() - 1);

         for(i = 0; i < str2.length(); ++i) {
            aux.addLast(new CeIASMAssembledLocation((String)null, str2.charAt(i), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), "'" + str2.charAt(i) + "'"));
         }

         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentDW", 5, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 25:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.peek())).value;
         aux = new LinkedList();
         aux.addLast(new CeIASMAssembledLocation((String)null, sid.getLexema().charAt(1), sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename(), sid.getLexema()));
         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentDW", 5, (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      case 26:
         RESULT = null;
         cantleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 4))).left;
         cantright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 4))).right;
         sid = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 4))).value;
         dupleft = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).left;
         dupright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).right;
         dup = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 3))).value;
         i = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).left;
         numInitright = ((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).right;
         CeIASMToken numInit = (CeIASMToken)((Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 1))).value;
         aux = new LinkedList();
         int n = Integer.parseInt(sid.getLexema());
         if (n <= 0) {
            throw new CeIASMParseException("Cantidad de celdas (" + n + ") en DUP inválida. Debe alojarse almenos una celda.", sid.getNumLine(), sid.getNumColumn(), this.getReportedFilename());
         }

         for(int x = 1; i <= n; ++i) {
            aux.addLast(new CeIASMAssembledLocation((String)null, Integer.parseInt(numInit.getLexema()), dup.getNumLine(), dup.getNumColumn(), this.getReportedFilename(), sid.getLexema() + " DUP (" + numInit.getLexema() + ")"));
         }

         CUP$CeIASMParser$result = this.parser.getSymbolFactory().newSymbol("ArgumentDW", 5, (Symbol)((Symbol)CUP$CeIASMParser$stack.elementAt(CUP$CeIASMParser$top - 4)), (Symbol)((Symbol)CUP$CeIASMParser$stack.peek()), aux);
         return CUP$CeIASMParser$result;
      default:
         throw new Exception("Invalid action number found in internal parse table");
      }
   }
}
