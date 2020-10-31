/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newjavacompiler;

import Utils.AnalizadorSintactico;
import Utils.GenCod;
import exceptions.lexicas.ExcepcionLexica;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.sintacticas.ExcepcionSintactica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Hugo Luna
 */
public class NewJavaCompiler {

     public static void main(String[] args) throws Exception{
        try {
            //Extensiones validas .txt o .hdg
            //File archEntrada = new File("C:\\Users\\Hugo Luna\\IdeaProjects\\MiniJava\\tests\\test06 - Asignaciones.txt");
            File archEntrada = new File("C:\\Users\\Hugo Luna\\IdeaProjects\\MiniJava\\tests\\test06 - Asignaciones.txt");
            if (!archEntrada.exists()) {
                System.out.println("[Error] No existe el archivo de entrada especificado.");
            } else {
                // Si no ingresaron archivo de salida, preparo el nombre de uno con extension ceiasm
                String salida = null;
                salida = "C:\\Users\\Hugo Luna\\IdeaProjects\\MiniJava\\tests\\test06 - Asignaciones.hdgasm";
               
                // De ser posible, creo el nuevo archivo
                File archSalida = new File(salida);
                try {
                    if (!archSalida.exists()) {
                        archSalida.createNewFile();
                    }
                } catch (Exception e) {
                    System.out.println("[Error] Fallo al intentar crear el archivo de salida");
                }

                if (archSalida.exists()) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archSalida));
                    GenCod.setBuffer(bw);
                    BufferedReader br = new BufferedReader(new FileReader(archEntrada));
                    AnalizadorSintactico asintactico = new AnalizadorSintactico(br);
                    asintactico.analizar();
                }
            }


        } catch (IOException e1) {
            System.out.println("Error de archivos. Revisar que el archivo de entrada sea correcto.");
        } catch (ExcepcionLexica e2) {
            System.out.println("No se pudo completar el analisis lexico.");
        } catch (ExcepcionSintactica e3) {
            System.out.println("No se pudo completar el analisis sintactico.");
        } catch (ExcepcionSemantica e4) {
            System.out.println("No se pudo completar el analisis semantico.");
        } catch (Exception e5) {
            System.out.println("Se produjo un error.");
        }
    }
    
}
