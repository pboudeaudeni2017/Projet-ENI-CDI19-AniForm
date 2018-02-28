package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.ihm.MainFrame;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EcranGestionPersonnel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	URL iconURL;
	ImageIcon icon;

	public EcranGestionPersonnel() {
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(530, 150);

		this.iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
		this.icon = new ImageIcon(iconURL);

		setIconImage(icon.getImage());
		setTitle("Gestion du personnel");
		setResizable(false);
		}

}
