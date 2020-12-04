/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newjavacompiler;

import Controller.HomeViewController;
import Utils.Config;
import View.HomeView;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;

/**
 *
 * @author Hugo Luna
 */
public class NewJavaCompiler {

    public NewJavaCompiler() {
        initMaterial();
        launchScreen();
    }

    public static void main(String[] args) {
        try {
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

        new HomeViewController(new HomeView());

    }

}
