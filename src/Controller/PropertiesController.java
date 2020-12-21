/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.HomeView;
import View.PropertiesView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author Hugo Luna
 */
public class PropertiesController {

    private File file;
    private PropertiesView propertiesView;

    public PropertiesController(File file, PropertiesView propertiesView) {
        this.file = file;
        this.propertiesView = propertiesView;
        this.propertiesView.setVisible(true);
        initVars();
        events();
    }

    private void initVars() {
        propertiesView.filenameField.setText(file.getName());
        propertiesView.pathFile.setText(file.getAbsolutePath());

        propertiesView.setTitle("Propiedades del archivo "+file.getName());
        try {
            BasicFileAttributes attr;
            Path path = Paths.get(file.getAbsolutePath());
            long bytes = Files.size(path);
            String completeSize = String.format("%,d KB", bytes / 1024) + " (" + String.format("%,d bytes", bytes) + ")";
            propertiesView.sizeFile.setText(completeSize);

            attr = Files.readAttributes(path, BasicFileAttributes.class);

            Locale mxLocale = new Locale.Builder().setLanguage("es").setRegion("MX").build();
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, mxLocale);

            Date creationDate
                    = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
            Date updateDate
                    = new Date(attr.lastModifiedTime().to(TimeUnit.MILLISECONDS));
            Date lastAccess
                    = new Date(attr.lastAccessTime().to(TimeUnit.MILLISECONDS));

            propertiesView.createdAt.setText(df.format(creationDate));
            propertiesView.updatedDate.setText(df.format(updateDate));
            propertiesView.lastAccess.setText(df.format(lastAccess));

        } catch (IOException e) {
        }

        centreWindow(propertiesView);

    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void events() {

        propertiesView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        propertiesView.addWindowListener(new WindowAdapter() {

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

        propertiesView.actionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                close();
            }
        });
    }

    private void close() {
        propertiesView.setVisible(false);
    }

}
