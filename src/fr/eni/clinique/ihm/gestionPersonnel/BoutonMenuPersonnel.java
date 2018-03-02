package fr.eni.clinique.ihm.gestionPersonnel;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;

public class BoutonMenuPersonnel extends JPanel {

	private JButton bttNew;
	private JButton bttDelete;
	private JButton bttReset;

	private EcranGestionPersonnel gestionPersonnel;

	public BoutonMenuPersonnel(EcranGestionPersonnel gestionPersonnel) {
		super();
		this.gestionPersonnel = gestionPersonnel;
		add(getBttNew());
		add(getBttDelete());
		add(getBttReset());
	}

	private JButton getBttNew() {
		if(bttNew == null) {
			bttNew = new JButton("Ajouter");
			bttNew.addActionListener((ActionEvent e) -> {
				this.gestionPersonnel.getCreationView().setVisible(true);
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
