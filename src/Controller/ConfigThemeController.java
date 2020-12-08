/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.Config;
import View.ConfigThemeView;
import View.HomeView;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
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
        
        configThemeView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        configThemeView.addWindowListener(new WindowAdapter() {

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
        
        
        
        configThemeView.listOptions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {

                LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();

                try {
                    int position = configThemeView.listOptions.getSelectedIndex();
                    if (position == 0) {
                        UIManager.setLookAndFeel(new MaterialLookAndFeel());
                    } else if (position == 6) {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                    } else if (position == 7) {
                        UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    } else if (position == 8) {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                    } else if (position == 9) {
                        UIManager.setLookAndFeel(new FlatDarculaLaf());
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
        configThemeView.actionCancel.addActionListener((ActionEvent ae) -> {
            new Config().setThemeConfig();
            close();
        });

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
