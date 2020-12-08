/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

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
    
}
