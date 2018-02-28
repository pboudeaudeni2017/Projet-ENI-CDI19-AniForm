package fr.eni.clinique.ihm;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private JLabel unLabel;

    URL iconURL;
    ImageIcon icon;

    public MainFrame() {
        setSize(280, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(600, 250);

        this.iconURL = getClass().getResource("ressources/ico_veto.png");
        this.icon = new ImageIcon(iconURL);

        setContentPane(this.getMainPanel());
        setIconImage(icon.getImage());
        setResizable(false);
        setTitle("Clinique Vétérinaire");
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
