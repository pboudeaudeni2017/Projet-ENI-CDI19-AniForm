package fr.eni.clinique.ihm;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.gestionPersonnel.CreationPersonnelPanel;
import fr.eni.clinique.ihm.login.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class MainFrame extends JFrame{
    private LoginController loginController;

    private JPanel mainPanel;
    private JLabel labelConnectedPersonnelName;

    private JMenuBar menuBar;
    private JMenu fichiers;
    private JMenuItem deconnexion;
    private JMenu gestionRdV;
    private JMenuItem priseGestionRdv;
    private JMenuItem gestionClient;
    private JMenu agenda;
    private JMenu gestionPersonnel;

    private JDialog creationView;
    private JButton addTest;

    private final String VIEW_GEST_PERSO = "Gestion Personnels";
    private final String VIEW_GEST_CLIENT = "Gestion Clients";
    private final String VIEW_AGENDA = "Agenda";
    private final String VIEW_RdV = "Rendez-vous";

    URL iconURL;
    ImageIcon icon;

    public MainFrame() {
        this.loginController = LoginController.getInstance();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitter();
            }
        });
        setSize(800, 400);
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
            addComponentTo(this.getAddTest(), this.mainPanel, 0, 0, 1, 1, 1);
            addComponentTo(this.getLabelConnectedPersonnelName(), this.mainPanel, 0, 2, 1, 1, 1);
        }
        return mainPanel;
    }

    public void reloadCurrentPersonnel() {
        Personnel currentPersonnel = this.loginController.getCurrentPersonnel();
        this.getLabelConnectedPersonnelName().setText("Utilisateur: " + currentPersonnel.getNom());
    }

    public void changeView(String nomView) {
        switch(nomView) {
            case VIEW_GEST_PERSO:
                this.setContentPane(this.getjPanelEcranGestionPerso());
                break;

            case VIEW_GEST_CLIENT:
                System.out.println("Écran Gestion des clients");
                break;

            default:
                System.out.println("Error not integreted");
        }
    }

    public JLabel getLabelConnectedPersonnelName() {
        if(this.labelConnectedPersonnelName == null) {
            this.labelConnectedPersonnelName = new JLabel("Aucun utilisateur connecté");
            this.labelConnectedPersonnelName.setForeground(Color.GRAY);
        }
        return labelConnectedPersonnelName;
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
            this.fichiers.add(this.getDeconnexion());
        }
        return fichiers;
    }

    public JMenu getGestionPersonnel() {
        if(this.gestionPersonnel == null) {
            this.gestionPersonnel = new JMenu("Gestion du personnel");
        }
        return gestionPersonnel;
    }

    public JDialog getCreationView() {
        if(this.creationView == null){
            this.creationView = new JDialog();
            this.creationView.setContentPane(new CreationPersonnelPanel());
            this.creationView.setVisible(true);
            this.creationView.setSize(800, 400);
            this.creationView.setLocation(600, 250);

            this.creationView.setIconImage(icon.getImage());
            this.creationView.setResizable(false);
            this.creationView.setTitle("Création personnel");
            this.creationView.setJMenuBar(this.getMenu());
        }
        return creationView;
    }

    public JMenuItem getDeconnexion() {
        if(this.deconnexion == null) {
            this.deconnexion = new JMenuItem("Quitter");
            this.deconnexion.addActionListener(e -> quitter());
        }
        return deconnexion;
    }

    public void quitter(){
        int reply = JOptionPane.showConfirmDialog(this.mainPanel, "Êtes-vous certain de vouloir quitter ?", "Déconnexion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(reply == JOptionPane.YES_OPTION){
            AppliTestIHM.loginFrame.reset();
            this.getCreationView().setVisible(false);
            JOptionPane.showMessageDialog(this.mainPanel, "Vous êtes déconnecté", "Déconnecté", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JMenu getGestionRdV() {
        if(this.gestionRdV == null){
            this.gestionRdV = new JMenu("Gestion des rendez-vous");
            this.gestionRdV.add(this.getPriseGestionRdv());
            this.gestionRdV.add(this.getGestionClient());
        }
        return gestionRdV;
    }

    public JMenuItem getPriseGestionRdv() {
        if(this.priseGestionRdv == null){
            this.priseGestionRdv = new JMenuItem("Prise de rendez-vous");
            this.priseGestionRdv.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }
        return priseGestionRdv;
    }

    public JMenuItem getGestionClient() {
        if(this.gestionClient == null){
            this.gestionClient = new JMenuItem("Gestion des clients");
        }
        return gestionClient;
    }

    public JMenu getAgenda() {
        return agenda;
    }

    public JButton getAddTest() {
        if(this.addTest == null){
            this.addTest = new JButton("+");
            this.addTest.addActionListener(e -> {
                this.getCreationView().setVisible(true);
            });
        }
        return addTest;
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
