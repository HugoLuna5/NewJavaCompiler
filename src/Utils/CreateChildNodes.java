/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.util.Optional;
import javax.swing.tree.DefaultMutableTreeNode;
import model.FileNode;

/**
 *
 * @author Hugo Luna
 */
public class CreateChildNodes {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    public CreateChildNodes(File fileRoot,
            DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
        createChildren(fileRoot, root);
    }

  

    private void createChildren(File fileRoot,
            DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {

            Optional<String> extOp = getExtensionByStringHandling(file.getName());
            String ext = extOp.get();

            
            if (ext.equalsIgnoreCase("hdg")) {
                DefaultMutableTreeNode childNode
                        = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
            } else {
                if (file.isDirectory()) {
                    DefaultMutableTreeNode childNode
                            = new DefaultMutableTreeNode(new FileNode(file));
                    node.add(childNode);
                    createChildren(file, childNode);
                }
            }

        }
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
