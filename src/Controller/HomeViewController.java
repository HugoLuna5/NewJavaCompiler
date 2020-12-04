/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.AnalizadorSintactico;
import Utils.AutoCompleteProvider;
import Utils.CreateChildNodes;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import model.FileNode;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import vm.CeIVMAPI;
import vm.CeIVMAPIIOSubSys;
import vm.CeIVMAPIMemory;
import vm.CeIVMAPISpecialRegs;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;

/**
 *
 * @author Hugo Luna
 */
public class HomeViewController {

    private HomeView homeView;
    private File openFile;
    private FileManger fileManager;
    private boolean validation = true;
    /**
     * Custom vars to fileTree
     */
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;

    /**
     * Custom vars to dialgo console
     */
    private JFrame frameDialog;
    private JButton closeConsole;
    public JTextArea consoleEditor;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JButton sendData;

    public HomeViewController(HomeView homeView) {
        this.homeView = homeView;
        homeView.setVisible(true);
        initVars();
        loadTreeDefault("Explorador");
        events();
    }

    private void initVars() {
        homeView.setResizable(false);
        fileManager = new FileManger();
        configAutoComplete();
    }

    private void events() {

        homeView.openProject.addActionListener((ActionEvent ae) -> {
            fileManager = new FileManger();
            actionOpenProject();
        });

        homeView.openDoc.addActionListener((ActionEvent ae) -> {
            fileManager = new FileManger();
            actionOpenDoc();
            validation = true;
        });

        homeView.newDoc.addActionListener((ActionEvent ae) -> {
            fileManager = new FileManger();
            createNewDoc();
        });

        homeView.saveDoc.addActionListener((ActionEvent ae) -> {
            fileManager = new FileManger();
            saveDocument();
        });

        homeView.saveDocAs.addActionListener((ActionEvent ae) -> {
            fileManager = new FileManger();
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
        String salida = null;

        if (validation) {
            salida = openFile.getAbsolutePath() + "asm";
            saveDocument();
        } else {
            salida = openFile.getAbsoluteFile() + "asm";
            saveDocumentTree(openFile.getAbsoluteFile().toString());
        }

        try {
            showAlert();

        } catch (InterruptedException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        frameDialog.setVisible(true);

        if (openFile != null) {
            try {
                //Extension valida .hdg
                File archEntrada = openFile;
                if (!archEntrada.exists()) {
                    JOptionPane.showMessageDialog(homeView, "[Error] No existe el archivo de entrada especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Si no ingresaron archivo de salida, preparo el nombre de uno con extension ceiasm

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

                        CeIVMAPI ceivmApi = new CeIVMAPI();
                        try {

                            ceivmApi.disableListingGeneration();
                            ceivmApi.parseAndAssemble(salida);
                            ceivmApi.loadProgram();
                            ceivmApi.initializeVM();
                            ceivmApi.executeToCompletion();

                        } catch (FileNotFoundException var4) {
                            System.err.println("Error: No se pudo abrir el archivo " + salida + ".\n");
                        } catch (Exception var5) {
                            System.err.println("\n" + var5.getMessage() + "\n");
                        }

                    }
                }

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(homeView, "Error de archivos. Revisar que el archivo de entrada sea correcto: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    }

    public void showAlert() throws InterruptedException {
        frameDialog = new JFrame();
        frameDialog.setTitle("Consola");
        frameDialog.add(new JLabel("Consola"), BorderLayout.NORTH);

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleEditor = new javax.swing.JTextArea();
        closeConsole = new javax.swing.JButton();

        frameDialog.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frameDialog.setMaximumSize(new java.awt.Dimension(850, 352));
        frameDialog.setMinimumSize(new java.awt.Dimension(850, 352));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        Font f = new Font("SansSerif", Font.PLAIN, 11);
        consoleEditor.setFont(f);
        consoleEditor.setColumns(20);
        consoleEditor.setRows(5);
        jScrollPane1.setViewportView(consoleEditor);

        closeConsole.setText("Cerrar consola");
        closeConsole.setMaximumSize(new java.awt.Dimension(155, 45));
        closeConsole.setMinimumSize(new java.awt.Dimension(155, 45));
        closeConsole.setPreferredSize(new java.awt.Dimension(155, 45));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(closeConsole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeConsole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frameDialog.getContentPane());
        frameDialog.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TextAreaOutputStream taos = new TextAreaOutputStream(consoleEditor, 60);
        PrintStream ps = new PrintStream(taos);
        System.setOut(ps);
        System.setErr(ps);

        frameDialog.setSize(850, 300);
        frameDialog.pack();

        eventsDialog();

    }

    private void eventsDialog() {

        closeConsole.addActionListener((ActionEvent ae) -> {
            frameDialog.setVisible(false);
        });

    }

    private void createNewDoc() {
        openFile = fileManager.createNewDocument(homeView, homeView.textEditor);
        homeView.setTitle("NewJava - " + openFile.getName());
    }

    private void saveDocument() {
        if (openFile != null) {
            openFile = fileManager.saveDocument(homeView, homeView.textEditor, openFile);
            homeView.setTitle("NewJava - " + openFile.getName());
            customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void saveDocumentTree(String path) {
        if (openFile != null) {
            openFile = fileManager.saveDocumentTree(homeView, homeView.textEditor, path);
            homeView.setTitle("NewJava - " + openFile.getName());
            customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void saveDocumentAs() {
        if (openFile != null) {
            openFile = fileManager.saveDocumentAs(homeView, homeView.textEditor);
            homeView.setTitle("NewJava - " + openFile.getName());
            customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void actionOpenDoc() {
        openFile = fileManager.openDocument();
        if (openFile != null) {
            fileManager.readDocument(openFile, homeView, homeView.textEditor);
            homeView.setTitle("NewJava - " + openFile.getName());
            loadTreeDefault(openFile.getName());

        } else {
            JOptionPane.showMessageDialog(homeView, "Debes seleccionar un archivo valido");
        }

    }

    private void actionOpenProject() {

        String route = fileManager.openFolder();
        if (route != null) {

            File fileRoot = new File(route);
            root = new DefaultMutableTreeNode(new FileNode(fileRoot));
            treeModel = new DefaultTreeModel(root);

            homeView.filesTree.setModel(treeModel);
            homeView.filesTree.setShowsRootHandles(true);
            Font f = new Font("SansSerif", Font.PLAIN, 11);

            homeView.filesTree.setFont(f);

            CreateChildNodes ccn
                    = new CreateChildNodes(fileRoot, root);
            //new Thread(ccn).start();

            homeView.filesTree.addTreeSelectionListener((TreeSelectionEvent tse) -> {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) homeView.filesTree.getLastSelectedPathComponent();
                if (node == null) //Nothing is selected.
                {
                    System.err.println("Nada seleccionado");
                }
                Object nodeInfo = node.getUserObject();
                if (node.isLeaf()) {
                    FileNode f1 = (FileNode) nodeInfo;
                    openFile = f1.getFile();
                    fileManager.readDocument(openFile, homeView, homeView.textEditor);
                    validation = false;
                    homeView.setTitle("NewJava - " + openFile.getName());
                }
            });

        }
    }

    private void loadTreeDefault(String filename) {
        root = new DefaultMutableTreeNode(filename);
        treeModel = new DefaultTreeModel(root);

        homeView.filesTree.setModel(treeModel);
        homeView.filesTree.updateUI();
    }

    private void configAutoComplete() {

        CompletionProvider provider = new AutoCompleteProvider().createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(homeView.textEditor);
    }

    private void customDialog(String title, String message, int type_message) {
        JOptionPane msg = new JOptionPane(message, type_message);
        final JDialog dlg = msg.createDialog(title);
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dlg.setVisible(false);
            }
        }).start();
        dlg.setVisible(true);
    }

}
