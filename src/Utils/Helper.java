/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Hugo Luna
 */
public class Helper {
 
     public static boolean esLetra(char chr) {
        return ((chr>='a' && chr<='z') || (chr>='A' && chr<='Z'));
    }

    public static boolean esMayuscula(char chr) {
        return (chr>='A' && chr<='Z');
    }

    public static boolean esDigito(char chr) {
        return (chr>='0' && chr<='9');
    }

    public static boolean esUnderscore(char chr) {
        return (chr=='_');
    }

    public static boolean esSeparador(char chr) {
        return (chr==' ') || (chr=='\n') || (chr=='\t') | (chr=='\r');
    }

    public static boolean esEnter(char chr) {
        return (chr=='\n');
    }

    public static boolean esIdentificador(char chr) {
        return esLetra(chr) || esUnderscore(chr) || esDigito(chr);
    }

    public static boolean esEOF(char chr) {
        return (chr==(char)-1);
    }

    public static boolean esComillas(char chr) {
        return (chr=='"');
    }

    public static boolean esApostrofo(char chr) {
        return (chr=='\'');
    }

    public static boolean esTipoValido(String msg) {
        switch (msg) {
            case "PR_Boolean":
            case "PR_Char" :
            case "PR_Int" :
            case "PR_Float":
            case "PR_String" :
            case "idClase" : return true;
            default: return false;
        }
    }
    
    public void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }).start();
}
    
}
