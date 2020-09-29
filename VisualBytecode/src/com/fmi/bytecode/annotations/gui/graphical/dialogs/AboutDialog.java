package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class AboutDialog extends JPanel {
    private JLabel labelTitle = new JLabel();
    private JLabel labelAuthor = new JLabel();
    private JLabel labelAutorName = new JLabel();
    private JLabel labelCopyright = new JLabel();
    private JLabel labelCompany = new JLabel();
    private JLabel labelCompanyName = new JLabel();    
    private JLabel labelToolName = new JLabel();
    private JLabel labelDate = new JLabel();
    private Border border = BorderFactory.createEtchedBorder();
    private GridBagLayout layoutMain = new GridBagLayout();
    
    public AboutDialog() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(layoutMain);
        this.setBorder(border);
        labelTitle.setText("Title");
        labelAuthor.setText("Author");
        labelCopyright.setText("Copyright");
        labelCompany.setText("Company");
        labelToolName.setText("Annotation Tool");
        labelAutorName.setText("Rositsa Panayotova");
        labelDate.setText("04 2007");
        labelCompanyName.setText("FMI");
        
        this.add(labelTitle, 
                 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                                        new Insets(5, 15, 0, 15), 0, 0));
        this.add(labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );
        this.add(labelCopyright, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );
        this.add(labelCompany, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 5, 15), 0, 0) );
        this.add(labelToolName, 
                 new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(labelAutorName, 
                 new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(labelDate, 
                 new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(labelCompanyName, 
                 new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
                                        new Insets(0, 0, 0, 0), 0, 0));
    }
}
