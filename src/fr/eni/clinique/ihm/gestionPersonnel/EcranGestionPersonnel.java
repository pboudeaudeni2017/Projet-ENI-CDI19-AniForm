package fr.eni.clinique.ihm.gestionPersonnel;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EcranGestionPersonnel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	URL iconURL = getClass().getResource("ico_veto.png");
	ImageIcon icon = new ImageIcon(iconURL);

	public EcranGestionPersonnel() {
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(530, 150);

		setIconImage(icon.getImage());
		setTitle("Gestion du personnel");
		setResizable(false);
		}

}
