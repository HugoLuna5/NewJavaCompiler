/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Image;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Hugo Luna
 */
public class Utils {

    public void customDialog(String title, String message, int type_message) {
        JOptionPane msg = new JOptionPane(message, type_message);
        final JDialog dlg = msg.createDialog(title);
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dlg.setVisible(false);
            }
        }).start();
        dlg.setVisible(true);
    }

    public Image getImageLocal(String name) {
        return new ImageIcon(getClass().getResource("/Images/" + name)).getImage();

    }
    
    public ImageIcon getImageIconLocal(String name) {
        return new ImageIcon(getClass().getResource("/Images/" + name));

    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
