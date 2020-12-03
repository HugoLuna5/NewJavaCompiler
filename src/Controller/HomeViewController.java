/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.AnalizadorSintactico;
import Utils.AutoCompleteProvider;
import Utils.FileManger;
import Utils.GenCod;
import Utils.Helper;
import Utils.TextAreaOutputStream;
import View.HomeView;
import exceptions.lexicas.ExcepcionLexica;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.sintacticas.ExcepcionSintactica;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import vm.CeIVMAPI;

/**
 *
 * @author Hugo Luna
 */
public class HomeViewController {

    private HomeView homeView;
    private File openFile;
    private FileManger fileManager;
    private JFrame frameDialog;

    public HomeViewController(HomeView homeView) {
        this.homeView = homeView;
        homeView.setVisible(true);
        initVars();
        events();
    }

    private void initVars() {
        fileManager = new FileManger();
        configAutoComplete();
    }

    private void events() {
        homeView.openDoc.addActionListener((ActionEvent ae) -> {
            actionOpenDoc();
        });

        homeView.newDoc.addActionListener((ActionEvent ae) -> {
            createNewDoc();
        });

        homeView.saveDoc.addActionListener((ActionEvent ae) -> {
            saveDocument();
        });

        homeView.saveDocAs.addActionListener((ActionEvent ae) -> {
            saveDocumentAs();
        });

        homeView.exitProg.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });

        homeView.executeProgram.addActionListener((ActionEvent ae) -> {
            runProgram();
        });

    }

    private void runProgram() {
        saveDocument();
        try {
            showAlert();
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Helper().setTimeout(() -> {

            if (openFile != null) {
                try {
                    //Extension valida .hdg
                    File archEntrada = openFile;
                    if (!archEntrada.exists()) {
                        JOptionPane.showMessageDialog(homeView, "[Error] No existe el archivo de entrada especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Si no ingresaron archivo de salida, preparo el nombre de uno con extension ceiasm
                        String salida = null;
                        salida = openFile.getAbsolutePath() + "asm";
                        homeView.setTitle("NewJava - " + openFile.getName());

                        // De ser posible, creo el nuevo archivo
                        File archSalida = new File(salida);
                        try {
                            if (!archSalida.exists()) {
                                archSalida.createNewFile();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(homeView, "[Error] Fallo al intentar crear el archivo de salida.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        if (archSalida.exists()) {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archSalida));
                            GenCod.setBuffer(bw);
                            BufferedReader br = new BufferedReader(new FileReader(archEntrada));
                            AnalizadorSintactico asintactico = new AnalizadorSintactico(br, homeView);
                            asintactico.analizar();

                            /*
                            ProcessBuilder builder = new ProcessBuilder(
                                    "cmd.exe", "/c", "java -jar \"C:\\Users\\Hugo Luna\\Documents\\NetBeansProjects\\NewJavaCompilerWithOuthGenCode\\libs\\CeIVM-cei2011.jar\" \"" + salida + "\" ");
                            builder.redirectErrorStream(true);
                            Process p = builder.start();
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            String line = "";
                            while (r.readLine() != null) {
                                line += r.readLine() + "\n";

                            }
                             */
                            CeIVMAPI ceivmApi = new CeIVMAPI();
                            try {
                                ceivmApi.disableListingGeneration();
                                ceivmApi.parseAndAssemble(salida);
                                ceivmApi.loadProgram();
                                ceivmApi.initializeVM();
                                ceivmApi.executeToCompletion();
                                //JOptionPane.showMessageDialog(homeView, line);
                                frameDialog.setVisible(true);
                            } catch (FileNotFoundException var4) {
                                System.err.println("Error: No se pudo abrir el archivo " + salida + ".\n");
                            } catch (Exception var5) {
                                System.err.println("\n" + var5.getMessage() + "\n");
                            }

                        }
                    }

                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(homeView, "Error de archivos. Revisar que el archivo de entrada sea correcto.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ExcepcionLexica e2) {
                    JOptionPane.showMessageDialog(homeView, "No se pudo completar el analisis lexico.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ExcepcionSintactica e3) {
                    JOptionPane.showMessageDialog(homeView, "No se pudo completar el analisis sintactico.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ExcepcionSemantica e4) {
                    JOptionPane.showMessageDialog(homeView, "No se pudo completar el analisis semantico.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e5) {
                    JOptionPane.showMessageDialog(homeView, "Se produjo un error.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
            }
        }, 1000);

    }

    public void showAlert() throws InterruptedException {
        frameDialog = new JFrame();
        frameDialog.add(new JLabel("Consola"), BorderLayout.NORTH);
        JTextArea ta = new JTextArea();
        Font f = new Font("Serif", Font.PLAIN, 15); 
        ta.setFont(f);
        TextAreaOutputStream taos = new TextAreaOutputStream(ta, 60);
        PrintStream ps = new PrintStream(taos);
        System.setOut(ps);
        System.setErr(ps);
        frameDialog.add(new JScrollPane(ta));

        frameDialog.pack();
        frameDialog.setSize(850, 300);

    }

    private void createNewDoc() {
        openFile = fileManager.createNewDocument(homeView, homeView.textEditor);
        homeView.setTitle("NewJava - " + openFile.getName());
    }

    private void saveDocument() {
        if (openFile != null) {
            openFile = fileManager.saveDocument(homeView, homeView.textEditor, openFile);
            homeView.setTitle("NewJava - " + openFile.getName());
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void saveDocumentAs() {
        if (openFile != null) {
            openFile = fileManager.saveDocumentAs(homeView, homeView.textEditor);
            homeView.setTitle("NewJava - " + openFile.getName());
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void actionOpenDoc() {
        openFile = fileManager.openDocument();
        if (openFile != null) {
            fileManager.readDocument(openFile, homeView, homeView.textEditor);
            homeView.setTitle("NewJava - " + openFile.getName());

        } else {
            JOptionPane.showMessageDialog(homeView, "Debes seleccionar un archivo valido");
        }

    }

    private void configAutoComplete() {

        CompletionProvider provider = new AutoCompleteProvider().createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(homeView.textEditor);
    }

}
