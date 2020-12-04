/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fife.rsta.ui.SizeGripIcon;

/**
 *
 * @author Hugo Luna
 */
public class StatusBar extends JPanel {

        private JLabel label;

        public StatusBar() {
            label = new JLabel("Listo");
            setLayout(new BorderLayout());
            add(label, BorderLayout.LINE_START);
            add(new JLabel(new SizeGripIcon()), BorderLayout.LINE_END);
        }

        public void setLabel(String label) {
            this.label.setText(label);
        }

    }
