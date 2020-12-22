/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newjavacompiler;

import Controller.HomeViewController;
import Utils.Config;
import View.HomeView;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;

/**
 *
 * @author Hugo Luna
 */
public class NewJavaCompiler {

    private static File file;
    
    public NewJavaCompiler() {
        initMaterial();
        launchScreen();
    }

    public static void main(String[] args) {
        try {

            if (args.length >= 1) {
                String route = args[0];
                file = new File(route);
            }
            new NewJavaCompiler();

        } catch (NullPointerException nullP) {
            System.err.println("Error: " + nullP.getMessage());
        }
    }

    public void initMaterial() {
        try {
            new Config().setThemeConfig();
        } catch (NullPointerException nullP) {
            System.err.println("Error: " + nullP.getMessage());
        }
    }

    public void launchScreen() {
        if (file != null) {
            new HomeViewController(new HomeView(), file);
        }else{
             new HomeViewController(new HomeView(), null);
        }

       

    }

}
