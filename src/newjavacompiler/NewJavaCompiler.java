/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newjavacompiler;

import Controller.HomeViewController;
import Utils.AnalizadorSintactico;
import Utils.GenCod;
import View.HomeView;
import exceptions.lexicas.ExcepcionLexica;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.sintacticas.ExcepcionSintactica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;
/**
 *
 * @author Hugo Luna
 */
public class NewJavaCompiler {
    
    
    
    public NewJavaCompiler(){
        initMaterial();
        launchScreen();
    }
    
    public static void main(String[] args) {
        try{
            new NewJavaCompiler();
        }catch(NullPointerException nullP){
            System.err.println("Error: "+nullP.getMessage());
        }
    }
     
     
      public void initMaterial() {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
      
       public void launchScreen() {
       
        new HomeViewController(new HomeView());

      
    }
     
}
