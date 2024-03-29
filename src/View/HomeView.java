/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utils.Utils;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

/**
 *
 * @author Hugo Luna
 */
public class HomeView extends javax.swing.JFrame {

    public RSyntaxTextArea textEditor;
    public RTextScrollPane sp;

    /**
     * Creates new form HomeView
     */
    public HomeView() {
        initComponents();
        textEditor = new RSyntaxTextArea(20, 60);
        textEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        
        textEditor.setCodeFoldingEnabled(true);
        textEditor.setMarkOccurrences(true);
        
        sp = new RTextScrollPane(textEditor);
        sp.setSize(850, 780);
        containerEditor.add(sp);
       setIconImage(new Utils().getImageLocal("icon-16.png"));
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainContainer = new javax.swing.JPanel();
        containerEditor = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        filesTree = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openDoc = new javax.swing.JMenuItem();
        openProject = new javax.swing.JMenuItem();
        newDoc = new javax.swing.JMenuItem();
        saveDoc = new javax.swing.JMenuItem();
        saveDocAs = new javax.swing.JMenuItem();
        exitProg = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        actionUndo = new javax.swing.JMenuItem();
        actionRedo = new javax.swing.JMenuItem();
        actionCopy = new javax.swing.JMenuItem();
        actionCut = new javax.swing.JMenuItem();
        actionPaste = new javax.swing.JMenuItem();
        actionSearch = new javax.swing.JMenuItem();
        actionReplace = new javax.swing.JMenuItem();
        actionGoToLine = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        executeProgramMenu = new javax.swing.JMenu();
        executeProgram = new javax.swing.JMenuItem();
        menuDebug = new javax.swing.JMenu();
        modeDebug = new javax.swing.JMenuItem();
        executeProgramSbS = new javax.swing.JMenuItem();
        menuConfig = new javax.swing.JMenu();
        actionEditorConfig = new javax.swing.JMenuItem();
        actionChangeTheme = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1086, 750));
        setResizable(false);

        mainContainer.setMaximumSize(new java.awt.Dimension(1086, 750));
        mainContainer.setMinimumSize(new java.awt.Dimension(1086, 750));
        mainContainer.setPreferredSize(new java.awt.Dimension(1086, 750));

        containerEditor.setMaximumSize(new java.awt.Dimension(850, 750));
        containerEditor.setMinimumSize(new java.awt.Dimension(850, 750));

        javax.swing.GroupLayout containerEditorLayout = new javax.swing.GroupLayout(containerEditor);
        containerEditor.setLayout(containerEditorLayout);
        containerEditorLayout.setHorizontalGroup(
            containerEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        containerEditorLayout.setVerticalGroup(
            containerEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        filesTree.setMaximumSize(null);
        filesTree.setMinimumSize(null);
        jScrollPane1.setViewportView(filesTree);

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
            .addComponent(containerEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("Archivo");

        openDoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openDoc.setText("Abrir");
        jMenu1.add(openDoc);

        openProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        openProject.setText("Abrir Carpeta");
        jMenu1.add(openProject);

        newDoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newDoc.setText("Nuevo");
        jMenu1.add(newDoc);

        saveDoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveDoc.setText("Guardar");
        jMenu1.add(saveDoc);

        saveDocAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveDocAs.setText("Guardar Como");
        jMenu1.add(saveDocAs);

        exitProg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exitProg.setText("Salir");
        jMenu1.add(exitProg);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        actionUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        actionUndo.setText("Deshacer");
        jMenu2.add(actionUndo);

        actionRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        actionRedo.setText("Rehacer");
        jMenu2.add(actionRedo);

        actionCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        actionCopy.setText("Copiar");
        jMenu2.add(actionCopy);

        actionCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        actionCut.setText("Cortar");
        jMenu2.add(actionCut);

        actionPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        actionPaste.setText("Pegar");
        jMenu2.add(actionPaste);

        actionSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        actionSearch.setText("Buscar");
        jMenu2.add(actionSearch);

        actionReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        actionReplace.setText("Reemplazar");
        jMenu2.add(actionReplace);

        actionGoToLine.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        actionGoToLine.setText("Ir a la linea");
        jMenu2.add(actionGoToLine);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ayuda");

        jMenuItem5.setText("Documentación");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Acerca");
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        executeProgramMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/play.png"))); // NOI18N
        executeProgramMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        executeProgramMenu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        executeProgramMenu.setMaximumSize(new java.awt.Dimension(55, 25));
        executeProgramMenu.setMinimumSize(new java.awt.Dimension(55, 25));
        executeProgramMenu.setPreferredSize(new java.awt.Dimension(55, 25));

        executeProgram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        executeProgram.setText("Ejecutar");
        executeProgramMenu.add(executeProgram);

        jMenuBar1.add(executeProgramMenu);

        menuDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bug.png"))); // NOI18N
        menuDebug.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuDebug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuDebug.setMaximumSize(new java.awt.Dimension(30, 25));
        menuDebug.setMinimumSize(new java.awt.Dimension(30, 25));
        menuDebug.setPreferredSize(new java.awt.Dimension(30, 25));

        modeDebug.setText("Modo depuración");
        menuDebug.add(modeDebug);

        executeProgramSbS.setText("Ejecutar SbS");
        menuDebug.add(executeProgramSbS);

        jMenuBar1.add(menuDebug);

        menuConfig.setText("Configuración");

        actionEditorConfig.setText("Editor");
        menuConfig.add(actionEditorConfig);

        actionChangeTheme.setText("Tema");
        menuConfig.add(actionChangeTheme);

        jMenuBar1.add(menuConfig);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem actionChangeTheme;
    public javax.swing.JMenuItem actionCopy;
    public javax.swing.JMenuItem actionCut;
    public javax.swing.JMenuItem actionEditorConfig;
    public javax.swing.JMenuItem actionGoToLine;
    public javax.swing.JMenuItem actionPaste;
    public javax.swing.JMenuItem actionRedo;
    public javax.swing.JMenuItem actionReplace;
    public javax.swing.JMenuItem actionSearch;
    public javax.swing.JMenuItem actionUndo;
    private javax.swing.JPanel containerEditor;
    public javax.swing.JMenuItem executeProgram;
    public javax.swing.JMenu executeProgramMenu;
    public javax.swing.JMenuItem executeProgramSbS;
    public javax.swing.JMenuItem exitProg;
    public javax.swing.JTree filesTree;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem5;
    public javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JPanel mainContainer;
    private javax.swing.JMenu menuConfig;
    private javax.swing.JMenu menuDebug;
    public javax.swing.JMenuItem modeDebug;
    public javax.swing.JMenuItem newDoc;
    public javax.swing.JMenuItem openDoc;
    public javax.swing.JMenuItem openProject;
    public javax.swing.JMenuItem saveDoc;
    public javax.swing.JMenuItem saveDocAs;
    // End of variables declaration//GEN-END:variables

}
