/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import View.HomeView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Hugo Luna
 */
public class FileManger {

    private JFileChooser selectorDeArchivos;

    /**
     * Open a document and show info in text editor
     * @return 
     */
    public File openDocument() {
        File file = null;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("HDG", "hdg");
        selectorDeArchivos = new JFileChooser("Abrir documento fuente");
        selectorDeArchivos.setFileFilter(new FileNameExtensionFilter("HDG", "hdg"));
        selectorDeArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int state = selectorDeArchivos.showOpenDialog(selectorDeArchivos);
        if (state == JFileChooser.APPROVE_OPTION) {
            file = selectorDeArchivos.getSelectedFile();
        }

        return file;
    }

    public File createNewDocument(HomeView homeView, JTextArea textPane) {
        File f = null;

        selectorDeArchivos = new JFileChooser("Crear documento fuente");
        int estado = selectorDeArchivos.showSaveDialog(selectorDeArchivos);
        if (estado == JFileChooser.APPROVE_OPTION) {
            f = selectorDeArchivos.getSelectedFile();

            BufferedWriter bw;

            try {

                bw = new BufferedWriter(new FileWriter(f));
                f = selectorDeArchivos.getSelectedFile();
                textPane.setText("");
                textPane.write(bw);
                bw.close();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(homeView, ex.getMessage(), ex.toString(), JOptionPane.ERROR_MESSAGE);
            }

        }

        return f;
    }
    
    /**
     * Save document with the content on the text editor with another name
     * @param homeView
     * @param textPane
     * @return 
     */
    public File saveDocumentAs(HomeView homeView, JTextArea textPane) {
        File f = null;

        selectorDeArchivos = new JFileChooser("Guardar documento fuente como");
        int estado = selectorDeArchivos.showSaveDialog(selectorDeArchivos);
        if (estado == JFileChooser.APPROVE_OPTION) {
            f = selectorDeArchivos.getSelectedFile();

            BufferedWriter bw;

            try {

                bw = new BufferedWriter(new FileWriter(f));
                f = selectorDeArchivos.getSelectedFile();
                textPane.write(bw);
                bw.close();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(homeView, ex.getMessage(), ex.toString(), JOptionPane.ERROR_MESSAGE);
            }

        }

        return f;
    }

    /**
     * Save document with the content on the text editor in file already open
     * @param homeView
     * @param textPane
     * @param f
     * @return 
     */
    public File saveDocument(HomeView homeView, JTextArea textPane, File f) {
        BufferedWriter bw;

        try {

            bw = new BufferedWriter(new FileWriter(f));
            f = selectorDeArchivos.getSelectedFile();

            textPane.write(bw);
            bw.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(homeView, ex.getMessage(), ex.toString(), JOptionPane.ERROR_MESSAGE);
        }

        return f;
    }

    /**
     * Read content of file selected and showing in text editor
     * @param file
     * @param homeView
     * @param txt
     * @return 
     */
    public void readDocument(File file, HomeView homeView, JTextArea txt) {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
           
            txt.read(br, null);
            br.close();

        } catch (IOException ex) {    //en caso de que ocurra una excepción
            JOptionPane.showMessageDialog(homeView, ex.getMessage(), ex.toString(), JOptionPane.ERROR_MESSAGE);
        }

       
    }

}
