package fr.eni.clinique.ihm;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.login.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

public class MainFrame extends JFrame{
    private LoginController loginController;

    private JPanel mainPanel;
    private JLabel unLabel;
    private JLabel labelConnectedPersonnelName;
    private JMenuBar menuBar;
    private JMenu fichiers;
    private JMenuItem fichier_quit;
    private JMenu gestionPersonnel;

    URL iconURL;
    ImageIcon icon;

    public MainFrame() {
        this.loginController = LoginController.getInstance();

        setSize(800, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AppliTestIHM.loginFrame.reset();

            }
        });
        setLocation(600, 250);

        this.iconURL = getClass().getResource("ressources/ico_veto.png");
        this.icon = new ImageIcon(iconURL);

        setContentPane(this.getMainPanel());
        setIconImage(icon.getImage());
        setResizable(true);
        setTitle("Clinique Vétérinaire");
        setJMenuBar(this.getMenu());
    }

    public JPanel getMainPanel() {
        if(this.mainPanel == null) {
            this.mainPanel = new JPanel(new GridBagLayout());
            addComponentTo(this.getLabelConnectedPersonnelName(), this.mainPanel, 0, 0, 1, 1, 0.5);
            addComponentTo(this.getUnLabel(), this.mainPanel, 1, 1, 10, 25, 1);
        }
        return mainPanel;
    }

    public void reloadCurrentPersonnel() {
        Personnel currentPersonnel = this.loginController.getCurrentPersonnel();
        this.labelConnectedPersonnelName.setText(currentPersonnel.getNom());
    }

    public JLabel getLabelConnectedPersonnelName() {
        if(this.labelConnectedPersonnelName == null) {
            this.labelConnectedPersonnelName = new JLabel();
        }
        return labelConnectedPersonnelName;
    }

    public JLabel getUnLabel() {
        if(this.unLabel == null){
            this.unLabel = new JLabel("MainFrame works !");
        }
        return unLabel;
    }

    public JMenuBar getMenu() {
        if( this.menuBar == null){
            this.menuBar = new JMenuBar();
            this.menuBar.add(this.getFichiers());
            this.menuBar.add(this.getGestionPersonnel());
        }
        return this.menuBar;
    }

    public JMenu getFichiers() {
        if(this.fichiers == null){
            this.fichiers = new JMenu("Fichiers");
            this.fichiers.add(this.getFichier_quit());
        }
        return fichiers;
    }

    public JMenu getGestionPersonnel() {
        if(this.gestionPersonnel == null) {
            this.gestionPersonnel = new JMenu("Gestion du personnel");
        }
        return gestionPersonnel;
    }

    public JMenuItem getFichier_quit() {
        if(this.fichier_quit == null) {
            this.fichier_quit = new JMenuItem("Quitter");
        }
        return fichier_quit;
    }

    private void addComponentTo(JComponent component, JPanel panel,
                                int x, int y, int width, int height,
                                double weightX) {
        addComponentTo(component, panel, x, y, width, height, weightX, true);
    }

    private void addComponentTo(JComponent component, JPanel panel,
                                int x, int y, int width, int height,
                                double weightX, boolean fillHorizontal) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightX;
        if(fillHorizontal) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }
        gbc.insets = new Insets(7, 10, 5, 10);
        panel.add(component, gbc);
    }
}
