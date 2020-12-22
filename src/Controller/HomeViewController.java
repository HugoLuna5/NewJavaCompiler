/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.AnalizadorSintactico;
import Utils.AutoCompleteProvider;
import Utils.Config;
import Utils.CreateChildNodes;
import Utils.FileManger;
import Utils.GenCod;
import Utils.Helper;
import Utils.RedoAction;
import Utils.TextAreaOutputStream;
import Utils.TreePopup;
import Utils.UndoAction;
import Utils.UndoListener;
import Utils.Utils;
import View.ConfigEditorView;
import View.ConfigThemeView;
import View.HomeView;
import View.PropertiesView;
import exceptions.lexicas.ExcepcionLexica;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.sintacticas.ExcepcionSintactica;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.undo.UndoManager;
import model.FileNode;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.rsta.ui.GoToDialog;

import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;
import vm.CeIVMAPI;
import vm.exceptions.CeIVMAPIInvalidStateException;
import vm.exceptions.CeIVMMemoryException;
import vm.exceptions.CeIVMRuntimeException;

/**
 *
 * @author Hugo Luna
 */
public class HomeViewController implements SearchListener {

    private final HomeView homeView;
    private File openFile;
    private TreePopup treePopup;
    private FileManger fileManager;
    private Utils utils;
    private boolean validation = true;
    private String salida = null;
    private CeIVMAPI ceivmApiDebug;

    /**
     * Custom vars to editor
     */
    private FindDialog findDialog;
    private ReplaceDialog replaceDialog;
    private UndoManager undoManager;
    private UndoAction undoAction;
    private RedoAction redoAction;

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

    public HomeViewController(HomeView homeView, File file) {
        this.homeView = homeView;
        homeView.setVisible(true);
        initVars();
        initSearchDialogs();
        loadTreeDefault("Explorador");
        events();
        if (file != null) {
            openFile = file;
            loadDataInTree(openFile.getParent());
            openFileTree(openFile);
        }
    }

    private void initVars() {
        new Config().setThemeConfig();
        homeView.setResizable(false);
        fileManager = new FileManger();
        configAutoComplete();
        new Config().setEditorConfig(homeView.textEditor);
        configUndoAndRedo();
        utils = new Utils();
        centreWindow(homeView);

    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void configUndoAndRedo() {
        undoManager = new UndoManager();
        undoAction = new UndoAction(undoManager);
        redoAction = new RedoAction(undoManager);
        undoAction.setRedoAction(redoAction);
        redoAction.setUndoAction(undoAction);
        homeView.setTitle("Rocket IDE");
        homeView.textEditor.getDocument().addUndoableEditListener(new UndoListener(undoManager, undoAction, redoAction));
    }

    private void events() {

        homeView.openProject.addActionListener((ActionEvent ae) -> {
            actionOpenProject();
        });

        homeView.openDoc.addActionListener((ActionEvent ae) -> {
            actionOpenDoc();
            validation = true;
        });

        homeView.newDoc.addActionListener((ActionEvent ae) -> {
            createNewDoc();
        });

        homeView.saveDoc.addActionListener((ActionEvent ae) -> {
            if (validation) {
                saveDocument();
            } else {
                saveDocumentTree(openFile.getAbsoluteFile().toString());
            }
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

        homeView.modeDebug.addActionListener((ActionEvent ae) -> {
            runProgramStepByStep();
        });
        homeView.executeProgramSbS.addActionListener((ActionEvent ae) -> {

            if (ceivmApiDebug != null) {
                try {
                    for (int i = 0; i < 10; i++) {
                        ceivmApiDebug.executeNextStep();

                    }
                } catch (CeIVMAPIInvalidStateException ex) {
                    System.err.println(ex.getMessage());
                } catch (CeIVMRuntimeException ex) {
                    System.err.println(ex.getMessage());
                } catch (CeIVMMemoryException ex) {
                    System.err.println(ex.getMessage());
                }

            } else {
                System.err.println("Nulo");
            }
        });

        /**
         * Edit Actions
         */
        homeView.actionUndo.addActionListener(undoAction);

        homeView.actionRedo.addActionListener(redoAction);

        homeView.actionCopy.addActionListener((ActionEvent ae) -> new DefaultEditorKit.CopyAction());

        homeView.actionCut.addActionListener((ActionEvent ae) -> new DefaultEditorKit.CutAction());

        homeView.actionPaste.addActionListener((ActionEvent ae) -> new DefaultEditorKit.PasteAction());

        homeView.actionSearch.addActionListener((ActionEvent ae) -> {
            if (replaceDialog.isVisible()) {
                replaceDialog.setVisible(false);
            }
            findDialog.setVisible(true);
        });

        homeView.actionReplace.addActionListener((ActionEvent ae) -> {
            if (findDialog.isVisible()) {
                findDialog.setVisible(false);
            }
            replaceDialog.setVisible(true);
        });

        homeView.actionGoToLine.addActionListener((ActionEvent ae) -> {
            if (findDialog.isVisible()) {
                findDialog.setVisible(false);
            }
            if (replaceDialog.isVisible()) {
                replaceDialog.setVisible(false);
            }
            GoToDialog dialog = new GoToDialog(homeView);
            dialog.setMaxLineNumberAllowed(homeView.textEditor.getLineCount());
            dialog.setVisible(true);
            int line = dialog.getLineNumber();
            if (line > 0) {
                try {
                    homeView.textEditor.setCaretPosition(homeView.textEditor.getLineStartOffset(line - 1));
                } catch (BadLocationException ble) { // Never happens
                    UIManager.getLookAndFeel().provideErrorFeedback(homeView.textEditor);
                }
            }
        });

        /**
         * Config Actions
         */
        homeView.actionEditorConfig.addActionListener((ActionEvent ae) -> {
            homeView.setVisible(false);
            new ConfigEditorController(new ConfigEditorView());
        });

        homeView.actionChangeTheme.addActionListener((ActionEvent ae) -> {
            homeView.setVisible(false);
            new ConfigThemeController(new ConfigThemeView());
        });

    }

    /**
     * Creates our Find and Replace dialogs.
     */
    private void initSearchDialogs() {

        findDialog = new FindDialog(homeView, this);
        replaceDialog = new ReplaceDialog(homeView, this);

        // This ties the properties of the two dialogs together (match case,
        // regex, etc.).
        SearchContext context = findDialog.getSearchContext();
        replaceDialog.setSearchContext(context);

    }

    private void runProgram() {

        if (frameDialog != null) {
            frameDialog.setVisible(false);
        }

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

        new Helper().setTimeout(() -> {

            if (openFile != null) {
                try {
                    //Extension valida .hdg
                    File archEntrada = openFile;
                    if (!archEntrada.exists()) {
                        JOptionPane.showMessageDialog(homeView, "[Error] No existe el archivo de entrada especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Si no ingresaron archivo de salida, preparo el nombre de uno con extension ceiasm

                        homeView.setTitle("Rocket IDE - " + openFile.getName());

                        // De ser posible, creo el nuevo archivo
                        File archSalida = new File(salida);
                        try {
                            if (!archSalida.exists()) {
                                archSalida.createNewFile();
                            }
                        } catch (IOException e) {
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
                                    "cmd.exe", "/c", "java -jar \"C:\\Users\\Hugo Luna\\Documents\\NetBeansProjects\\NewJavaCompiler\\libs\\CeIVM-cei2011.jar\" \"" + salida + "\" ");
                            builder.redirectErrorStream(true);
                            Process p = builder.start();
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            String line = "";
                            while (r.readLine() != null) {
                                line += r.readLine() + "\n";
                            }
                            JOptionPane.showMessageDialog(homeView, line);
                             */
                            CeIVMAPI ceivmApi = new CeIVMAPI();
                            try {

                                ceivmApi.disableListingGeneration();
                                ceivmApi.parseAndAssemble(salida);
                                ceivmApi.loadProgram();
                                ceivmApi.initializeVM();
                                ceivmApi.executeToCompletion();

                                new Helper().setTimeout(() -> {
                                    archSalida.delete();
                                }, 1500);

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

        }, 1000);

    }

    private void runProgramStepByStep() {

        if (frameDialog != null) {
            frameDialog.setVisible(false);
        }

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

        new Helper().setTimeout(() -> {

            if (openFile != null) {
                try {
                    //Extension valida .hdg
                    File archEntrada = openFile;
                    if (!archEntrada.exists()) {
                        JOptionPane.showMessageDialog(homeView, "[Error] No existe el archivo de entrada especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Si no ingresaron archivo de salida, preparo el nombre de uno con extension ceiasm

                        homeView.setTitle("Rocket IDE - " + openFile.getName());

                        // De ser posible, creo el nuevo archivo
                        File archSalida = new File(salida);
                        try {
                            if (!archSalida.exists()) {
                                archSalida.createNewFile();
                            }
                        } catch (IOException e) {
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
                                    "cmd.exe", "/c", "java -jar \"C:\\Users\\Hugo Luna\\Documents\\NetBeansProjects\\NewJavaCompiler\\libs\\CeIVM-cei2011.jar\" \"" + salida + "\" ");
                            builder.redirectErrorStream(true);
                            Process p = builder.start();
                            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            String line = "";
                            while (r.readLine() != null) {
                                line += r.readLine() + "\n";
                            }
                            JOptionPane.showMessageDialog(homeView, line);
                             */
                            ceivmApiDebug = new CeIVMAPI();
                            try {

                                ceivmApiDebug.disableListingGeneration();
                                ceivmApiDebug.parseAndAssemble(salida);
                                ceivmApiDebug.loadProgram();
                                ceivmApiDebug.initializeVM();
                                //ceivmApiDebug.executeToCompletion();

                                /*
                                 new Helper().setTimeout(() -> {
                                    archSalida.delete();
                                }, 1500);
                                 */
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

        }, 1000);

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
        frameDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // do whatever else
                frameDialog.setVisible(false);
            }
        });

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
        fileManager.readDocument(openFile, homeView, homeView.textEditor);
        homeView.setTitle("Rocket IDE - " + openFile.getName());
        loadDataInTree(openFile.getParent());

    }

    private void saveDocument() {
        if (openFile != null) {
            openFile = fileManager.saveDocument(homeView, homeView.textEditor, openFile);
            homeView.setTitle("Rocket IDE - " + openFile.getName());
            utils.customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void saveDocumentTree(String path) {
        if (openFile != null) {
            openFile = fileManager.saveDocumentTree(homeView, homeView.textEditor, path);
            homeView.setTitle("Rocket IDE - " + openFile.getName());
            utils.customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void saveDocumentAs() {
        if (openFile != null) {
            openFile = fileManager.saveDocumentAs(homeView, homeView.textEditor);
            homeView.setTitle("Rocket IDE - " + openFile.getName());
            utils.customDialog("Guardando", "Tu archivo se ha guardado", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(homeView, "Primero debes abrir un documento valido");
        }
    }

    private void actionOpenDoc() {
        openFile = fileManager.openDocument();
        if (openFile != null) {
            fileManager.readDocument(openFile, homeView, homeView.textEditor);
            homeView.setTitle("Rocket IDE - " + openFile.getName());
            loadTreeDefault(openFile.getName());

        } else {
            JOptionPane.showMessageDialog(homeView, "Debes seleccionar un archivo valido");
        }

    }

    private void actionOpenProject() {

        String route = fileManager.openFolder();
        loadDataInTree(route);

    }

    private void loadDataInTree(String route) {
        if (route != null) {

            File fileRoot = new File(route);
            root = new DefaultMutableTreeNode(new FileNode(fileRoot));
            treeModel = new DefaultTreeModel(root);

            homeView.filesTree.setModel(treeModel);

            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(new Utils().getImageIconLocal("icon-16.png"));
            homeView.filesTree.setCellRenderer(renderer);
            homeView.filesTree.setShowsRootHandles(true);
            homeView.filesTree.setRootVisible(true);

            Font f = new Font("SansSerif", Font.PLAIN, 11);

            homeView.filesTree.setFont(f);
            homeView.filesTree.updateUI();
            CreateChildNodes ccn
                    = new CreateChildNodes(fileRoot, root);
            homeView.filesTree.expandRow(0);
            //new Thread(ccn).start();

            try {

                homeView.filesTree.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) homeView.filesTree.getLastSelectedPathComponent();
                            if (node != null) {
                                Object nodeInfo = node.getUserObject();
                                // Cast nodeInfo to your object and do whatever you want
                                if (node.isLeaf()) {
                                    FileNode f1 = (FileNode) nodeInfo;
                                    openFileTree(f1.getFile());
                                }
                            }

                        }
                    }
                });

                homeView.filesTree.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            treePopup = new TreePopup(homeView.filesTree);
                            TreePath pathForLocation = homeView.filesTree.getPathForLocation(e.getX(), e.getY());
                            homeView.filesTree.setSelectionPath(pathForLocation);

                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) homeView.filesTree.getLastSelectedPathComponent();
                            if (node != null) {

                                Object nodeInfo = node.getUserObject();
                                if (node.isLeaf()) {
                                    FileNode f1 = (FileNode) nodeInfo;

                                    treePopup.open.addActionListener((ActionEvent ae) -> {
                                        openFileTree(f1.getFile());
                                    });

                                    treePopup.run.addActionListener((ActionEvent ae) -> {
                                        openFileTree(f1.getFile());
                                        runProgram();

                                    });

                                    treePopup.delete.addActionListener((ActionEvent ae) -> {
                                        int dialogButton = JOptionPane.YES_NO_OPTION;
                                        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este archivo?", "Eliminar", dialogButton);
                                        if (dialogResult == JOptionPane.YES_OPTION) {
                                            deleteOptionDialog(f1.getFile());
                                        }
                                    });

                                    treePopup.rename.addActionListener((ActionEvent ae) -> {
                                        renameOptionDialog(f1.getFile());
                                    });

                                    treePopup.properties.addActionListener((ActionEvent ae) -> {
                                        new PropertiesController(f1.getFile(), new PropertiesView());
                                    });

                                    treePopup.show(e.getComponent(), e.getX(), e.getY());

                                }

                            }

                        }
                    }
                });

            } catch (NullPointerException | NoSuchElementException nullPoin) {
                System.err.println("Error extension file: " + nullPoin.getMessage());
            }

        }
    }

    private void renameOptionDialog(File f) {

        try {
            String value = (String) JOptionPane.showInputDialog(homeView, "Cambia el nombre de tu archivo", "Editar", JOptionPane.INFORMATION_MESSAGE, null, null, f.getName());

            if (!value.isEmpty()) {

                if (!value.equals(f.getName())) {
                    Optional<String> extOp = new Utils().getExtensionByStringHandling(value);
                    String ext = extOp.get();
                    if (ext.equalsIgnoreCase("hdg")) {
                        String fullPath = f.getParent() + "\\" + value;
                        File newFile = new File(fullPath);

                        if (f.renameTo(newFile)) {
                            utils.customDialog("Renombrado", "Tu archivo se ha renombrado correctamente", JOptionPane.INFORMATION_MESSAGE);

                            if (openFile.getName().equals(f.getName())) {
                                openFileTree(newFile);
                            }
                            loadDataInTree(newFile.getParent());
                        } else {
                            JOptionPane.showMessageDialog(homeView, "Ocurrio un error al renombrar tu archivo", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(homeView, "El nombre del archivo debe ser diferente al actual", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(homeView, "Debes ingresar el nuevo nombre del archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException nullPoin) {
        } catch (NoSuchElementException noSuch) {
            JOptionPane.showMessageDialog(homeView, "El nombre del archivo debe contener la extensión .hdg", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void deleteOptionDialog(File f) {

        String parent = f.getParent();
        //File oldASMFile = new File(f.getAbsolutePath()+"asm");
        if (f.delete()) {
            utils.customDialog("Eliminado", "Tu archivo se ha eliminado correctamente", JOptionPane.INFORMATION_MESSAGE);

            // oldASMFile.delete();
            loadDataInTree(parent);
        } else {
            utils.customDialog("Error", "Ocurrio un error al eliminar tu archivo", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void openFileTree(File file) {
        openFile = file;
        fileManager.readDocument(openFile, homeView, homeView.textEditor);
        validation = false;
        homeView.setTitle("Rocket IDE - " + openFile.getName());
    }

    private void loadTreeDefault(String filename) {
        try {
            root = new DefaultMutableTreeNode(filename);
            treeModel = new DefaultTreeModel(root);

            homeView.filesTree.setModel(treeModel);
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(new Utils().getImageIconLocal("icon-16.png"));
            homeView.filesTree.setCellRenderer(renderer);
            homeView.filesTree.setShowsRootHandles(true);
            homeView.filesTree.setRootVisible(true);
            homeView.filesTree.updateUI();
        } catch (NullPointerException nullP) {
        }
    }

    private void configAutoComplete() {

        CompletionProvider provider = new AutoCompleteProvider().createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(homeView.textEditor);
    }

    @Override
    public void searchEvent(SearchEvent e) {
        SearchEvent.Type type = e.getType();
        SearchContext context = e.getSearchContext();
        SearchResult result;

        switch (type) {
            default: // Prevent FindBugs warning later
            case MARK_ALL:
                result = SearchEngine.markAll(homeView.textEditor, context);
                break;
            case FIND:
                result = SearchEngine.find(homeView.textEditor, context);
                if (!result.wasFound() || result.isWrapped()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(homeView.textEditor);
                }
                break;
            case REPLACE:
                result = SearchEngine.replace(homeView.textEditor, context);
                if (!result.wasFound() || result.isWrapped()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(homeView.textEditor);
                }
                break;
            case REPLACE_ALL:
                result = SearchEngine.replaceAll(homeView.textEditor, context);
                JOptionPane.showMessageDialog(null, result.getCount()
                        + " ocurrencias reemplazadas.");
                break;
        }

        String text;
        if (result.wasFound()) {
            text = "Texto encontrado; ocurrencias marcadas: " + result.getMarkedCount();
        } else if (type == SearchEvent.Type.MARK_ALL) {
            if (result.getMarkedCount() > 0) {
                text = "Las ocurrencias marcadas: " + result.getMarkedCount();
            } else {
                text = "";
            }
        } else {
            text = "Texto no encontrado";
        }
    }

    @Override
    public String getSelectedText() {
        return homeView.textEditor.getSelectedText();
    }

}
