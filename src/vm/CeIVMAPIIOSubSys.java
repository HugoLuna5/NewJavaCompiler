package vm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class CeIVMAPIIOSubSys {
   private BufferedReader inputReader;
   private PrintStream outputStream;
   private PrintStream errorStream;

   CeIVMAPIIOSubSys(InputStream in, OutputStream out, OutputStream err, String encoding) {
      try {
         this.inputReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      } catch (UnsupportedEncodingException var8) {
         this.inputReader = new BufferedReader(new InputStreamReader(in));
      }

      try {
         this.outputStream = new PrintStream(out, false, "UTF-8");
      } catch (UnsupportedEncodingException var7) {
         this.outputStream = new PrintStream(out, false);
      }

      try {
         this.errorStream = new PrintStream(err, false, "UTF-8");
      } catch (UnsupportedEncodingException var6) {
         this.errorStream = new PrintStream(err, false);
      }

   }

   CeIVMAPIIOSubSys(InputStream in, OutputStream out, OutputStream err) {
      this(in, out, err, "UTF-8");
   }

   public PrintStream getErrorStream() {
      return this.errorStream;
   }

   public BufferedReader getInputReader() {
      return this.inputReader;
   }

   public PrintStream getOutputStream() {
      return this.outputStream;
   }
}
