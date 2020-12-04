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
import java.util.prefs.Preferences;

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
