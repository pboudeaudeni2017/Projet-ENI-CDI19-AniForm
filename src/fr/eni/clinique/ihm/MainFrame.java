package fr.eni.clinique.ihm;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.gestionClient.EcranGestionClient;
import fr.eni.clinique.ihm.gestionPersonnel.EcranGestionPersonnel;
import fr.eni.clinique.ihm.login.LoginController;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class MainFrame extends JFrame{
    private LoginController loginController;

    private JPanel currentPanel;
    private String currentPanelName;

    private JLabel labelConnectedPersonnelName;

    private JMenuBar menuBar;
    private JMenu fichiers;
    private JMenuItem deconnexion;
    private JMenu gestionRdV;
    private JMenuItem priseGestionRdv;
    private JMenuItem gestionClient;
    private JMenu agenda;
    private JMenu gestionPersonnel;

    
    private EcranGestionPersonnel jPanelEcranGestionPerso;
    private EcranGestionClient jPanelEcranGestionClient;

    public static final String VIEW_GEST_PERSO = "Gestion Personnels";
    public static final String VIEW_GEST_CLIENT = "Gestion Clients";
    public static final String VIEW_AGENDA = "Agenda";
    public static final String VIEW_RdV = "Rendez-vous";

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
        setSize(1000, 475);
        setLocation(300, 250);
        
        this.iconURL = getClass().getResource("ressources/ico_veto.png");
        this.icon = new ImageIcon(iconURL);

        setIconImage(icon.getImage());
        setResizable(true);
        setTitle("Clinique Vétérinaire");
        setJMenuBar(this.getMenu());
    }

    public void reloadCurrentPersonnel() {
        Personnel currentPersonnel = this.loginController.getCurrentPersonnel();
        this.getLabelConnectedPersonnelName().setText("Utilisateur: " + currentPersonnel.getNom());
    }

    public void reloadCurrentView(){
        this.changeTheView(this.currentPanelName);
    }

    public void changeTheView(String nomView) {
    	if(currentPanelName != nomView) {
    		switch(nomView) {
    		case VIEW_GEST_PERSO:
    			this.setContentPane(this.getjPanelEcranGestionPerso());
    			this.getjPanelEcranGestionPerso().setVisible(false);
    			this.getjPanelEcranGestionPerso().setVisible(true);
    			this.getjPanelEcranGestionPerso().stateVisible();
    			this.getjPanelEcranGestionPerso().reloadView();
    			this.currentPanel = this.getjPanelEcranGestionPerso();
    			this.currentPanelName = nomView;
    			setTitle("Gestion du personnel");
    			break;

    		case VIEW_GEST_CLIENT:
    			this.setContentPane(this.getjPanelEcranGestionClient());
    			this.getjPanelEcranGestionClient().setVisible(false);
    			this.getjPanelEcranGestionClient().setVisible(true);
    			this.getjPanelEcranGestionClient().stateVisible();
    			this.getjPanelEcranGestionClient().reloadView();
    			this.currentPanel = this.getjPanelEcranGestionClient();
    			this.currentPanelName = nomView;
    			this.setResizable(false);
    			setTitle("Liste des clients");
    			break;

    		default:
    			System.out.println("Error not integreted");
    		}
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
            this.menuBar.add(this.getGestionRdV());
            this.menuBar.add(this.getAgenda());
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
            this.gestionPersonnel.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    changeTheView(VIEW_GEST_PERSO);
                }

                @Override
                public void menuDeselected(MenuEvent e) {

                }

                @Override
                public void menuCanceled(MenuEvent e) {

                }
            });
            
        }
        return gestionPersonnel;
    }
    
    public void displayScreenRole() {
    	if (LoginController.getInstance().getCurrentPersonnel().getRole().equals(Personnel.ADMINISTRATEUR)) {
        	gestionPersonnel.setEnabled(true);
        } else {
        	gestionPersonnel.setEnabled(false);
        }
    }



    public JMenuItem getDeconnexion() {
        if(this.deconnexion == null) {
            this.deconnexion = new JMenuItem("Quitter");
            this.deconnexion.addActionListener(e -> quitter());
        }
        return deconnexion;
    }

    public void quitter(){
        int reply = JOptionPane.showConfirmDialog(this.currentPanel, "Êtes-vous certain de vouloir quitter ?", "Déconnexion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(reply == JOptionPane.YES_OPTION){
            AppliTestIHM.loginFrame.reset();
            JOptionPane.showMessageDialog(this.currentPanel, "Vous êtes déconnecté", "Déconnecté", JOptionPane.INFORMATION_MESSAGE);
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
            priseGestionRdv.setEnabled(false);
        }
        return priseGestionRdv;
    }

    public JMenuItem getGestionClient() {
        if(this.gestionClient == null){
            this.gestionClient = new JMenuItem("Gestion des clients");
            this.gestionClient.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					changeTheView(VIEW_GEST_CLIENT);
				}
				
			});
        }
        return gestionClient;
    }

    public JMenu getAgenda() {
        if(this.agenda == null){
            this.agenda = new JMenu("Agenda");
            agenda.setEnabled(false);
        }
        return agenda;
    }

    private void addComponentTo(JComponent component, JPanel panel, int x, int y, int width, int height, double weightX) {
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

    public JPanel getCurrentPanel(){
        return this.currentPanel;
    }

	public EcranGestionPersonnel getjPanelEcranGestionPerso() {
		if (jPanelEcranGestionPerso == null) {
			this.jPanelEcranGestionPerso = new EcranGestionPersonnel();
		}
		
		return jPanelEcranGestionPerso;
	}
	
	public EcranGestionClient getjPanelEcranGestionClient() {
		if (jPanelEcranGestionClient == null) {
			this.jPanelEcranGestionClient = new EcranGestionClient();
		}
		
		return jPanelEcranGestionClient;
	}
}
