package fr.eni.clinique.ihm;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private JLabel unLabel;

    public MainFrame() {
        this.setLayout(new GridBagLayout());
        add(this.getUnLabel());
    }

    public JPanel getMainPanel() {
        if(this.mainPanel == null) {
            this.mainPanel = new JPanel();
        }
        return mainPanel;
    }

    public JLabel getUnLabel() {
        if(this.unLabel == null){
            this.unLabel = new JLabel("MainFrame works !");
        }
        return unLabel;
    }
}
