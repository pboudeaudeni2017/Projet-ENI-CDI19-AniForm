package fr.eni.clinique.ihm.gestionPersonnel;

import javax.swing.SwingUtilities;

public class AppliTestIHMGestion {
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				EcranGestionPersonnel frame = new EcranGestionPersonnel();
				frame.setVisible(true);
			}
		});
	}

}
