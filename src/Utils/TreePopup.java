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

    public TreePopup(JTree tree) {
        delete = new JMenuItem("Eliminar");
        open = new JMenuItem("Abrir");

        add(delete);
        add(new JSeparator());
        add(open);
    }


}
