/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import mdlaf.MaterialLookAndFeel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

/**
 *
 * @author Hugo Luna
 */
public class Config {

    private Preferences prefs;

    public Config() {
        prefs = Preferences.userNodeForPackage(newjavacompiler.NewJavaCompiler.class);
    }

    public void setThemeConfig() {
        String PREF_NAME = "theme";
        int defaultValue = 0;
        int position = prefs.getInt(PREF_NAME, defaultValue);
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();

        try {
            if (position == 0) {
                UIManager.setLookAndFeel(new MaterialLookAndFeel());
            } else {

                UIManager.setLookAndFeel(infos[position - 1].getClassName());

            }

        } catch (RuntimeException re) {
            throw re; // FindBugs
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setEditorConfig(RSyntaxTextArea textEditor) {
        String PREF_NAME = "editor";
        int defaultValue = 0;
        int position = prefs.getInt(PREF_NAME, defaultValue);
        try {
            Theme theme = null;
            switch (position) {
                case 0:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/default.xml"));
                    break;
                case 1:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/default-alt.xml"));
                    break;
                case 2:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/dark.xml"));
                    break;
                case 3:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/druid.xml"));
                    break;
                case 4:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml"));
                    break;

                case 5:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
                    break;

                case 6:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
                    break;
                case 7:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/vs.xml"));
                    break;
                default:
                    theme = Theme.load(getClass().getResourceAsStream(
                            "/org/fife/ui/rsyntaxtextarea/themes/default.xml"));
                    break;
            }

            theme.apply(textEditor);
        } catch (IOException ioe) { // Never happens
            ioe.printStackTrace();
        }
    }

}
