package fr.eni.clinique.ihm.gestionPersonnel;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoutonMenuPersonnel extends JPanel {

	private JButton bttNew;
	private JButton bttDelete;
	private JButton bttReset;

	public BoutonMenuPersonnel(EcranGestionPersonnel gestionPersonnel) {
		add(getBttNew(gestionPersonnel));
		add(getBttDelete());
		add(getBttReset());
	}

	private JButton getBttNew(EcranGestionPersonnel gestionPersonnel) {
		if(bttNew == null) {
			bttNew = new JButton("Ajouter");
			bttNew.addActionListener(e -> {
				gestionPersonnel.getCreationView().setVisible(true);
			});
		}

		return bttNew;

	}

	private JButton getBttDelete() {
		if(bttDelete == null) {
			bttDelete = new JButton("Supprimer");
		}

		return bttDelete;

	}

	private JButton getBttReset() {
		if (bttReset == null) {
			bttReset = new JButton("Réinitaliser");
		}
		
		return bttReset;

	}



}
