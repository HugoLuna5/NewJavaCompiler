/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ConfigThemeView;
import View.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.prefs.Preferences;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import mdlaf.MaterialLookAndFeel;

/**
 *
 * @author Hugo Luna
 */
public class ConfigThemeController {

    private ConfigThemeView configThemeView;

    public ConfigThemeController(ConfigThemeView configThemeView) {
        this.configThemeView = configThemeView;
        configThemeView.setVisible(true);
        configViews();
        events();
    }

    private void configViews() {
        configThemeView.listOptions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {

                LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();

                try {
                    int position = configThemeView.listOptions.getSelectedIndex();
                    if (position == 0) {
                        UIManager.setLookAndFeel(new MaterialLookAndFeel());
                    } else {

                        UIManager.setLookAndFeel(infos[position - 1].getClassName());

                    }
                    //
                    SwingUtilities.updateComponentTreeUI(configThemeView);
                    configThemeView.pack();
                } catch (RuntimeException re) {
                    throw re; // FindBugs
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void events() {
        configThemeView.actionCancel.addActionListener((ActionEvent ae) -> close());

        configThemeView.actionDone.addActionListener((ActionEvent ae) -> saveConfig());
    }

    private void saveConfig() {
        Preferences prefs = Preferences.userNodeForPackage(newjavacompiler.NewJavaCompiler.class);
        String PREF_NAME = "theme";
        int newValue = configThemeView.listOptions.getSelectedIndex();
        prefs.putInt(PREF_NAME, newValue);
        close();
    }

    private void close() {
        configThemeView.setVisible(false);
        new HomeViewController(new HomeView());
    }
}
