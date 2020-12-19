/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import View.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Hugo Luna
 */
public class TreePopup extends JPopupMenu {

    public JMenuItem delete;
    public JMenuItem open;
    public JMenuItem run;
    public JMenuItem rename;
    public JMenuItem properties;

    public TreePopup(JTree tree) {

        open = new JMenuItem("Abrir");
        run = new JMenuItem("Ejecutar");
        delete = new JMenuItem("Eliminar");
        rename = new JMenuItem("Renombrar");
        properties = new JMenuItem("Propiedades");
        add(open);
        add(new JSeparator());
        add(run);
        add(new JSeparator());
        add(delete);
        add(new JSeparator());
        add(rename);
        add(new JSeparator());
        add(properties);
    }

}
