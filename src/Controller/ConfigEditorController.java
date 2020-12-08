/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ConfigEditorView;
import View.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import javax.swing.JFrame;

/**
 *
 * @author Hugo Luna
 */
public class ConfigEditorController {

    private ConfigEditorView configEditorView;

    public ConfigEditorController(ConfigEditorView configEditorView) {
        this.configEditorView = configEditorView;
        configEditorView.setVisible(true);

        events();
    }

    private void events() {
        configEditorView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        configEditorView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                close();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }

        });
        
        configEditorView.actionDone.addActionListener((ActionEvent ae) -> actionSaveEditorConfig());
        
        configEditorView.actionCancel.addActionListener((ActionEvent ae) -> close());
    }
    
    private void actionSaveEditorConfig(){
        Preferences prefs = Preferences.userNodeForPackage(newjavacompiler.NewJavaCompiler.class);
        String PREF_NAME = "editor";
        int newValue = configEditorView.listOptions.getSelectedIndex();
        prefs.putInt(PREF_NAME, newValue);
        close();
    }
    
    private void close(){
        configEditorView.setVisible(false);
        new HomeViewController(new HomeView());
    }

}
