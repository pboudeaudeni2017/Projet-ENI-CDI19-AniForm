package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BoutonMenuPersonnel extends JPanel {

	private JButton bttNew;
	private JButton bttDelete;
	private JButton bttReset;

	private EcranGestionPersonnel gestionPersonnel;
	private PersonnelController personnelController;

	public BoutonMenuPersonnel(EcranGestionPersonnel gestionPersonnel) {
		super();
		this.gestionPersonnel = gestionPersonnel;
		try {
			this.personnelController = PersonnelController.getInstance();
		} catch (BLLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
		}
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
			bttDelete.addActionListener((ActionEvent e) -> {
				int selectedRow = this.gestionPersonnel.getTablePersonnel().getSelectedRow();
				Personnel personnel = this.gestionPersonnel.getPersonnelFromJTable(selectedRow);
				this.gestionPersonnel.getTablePersonnel().clearSelection();
				int reply = JOptionPane.showConfirmDialog(bttDelete, "Voulez-vous vraiment supprimer " + personnel.getNom() + " ?\nCette action est définitive", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					try {
						this.personnelController.deletePersonnel(personnel);
						this.gestionPersonnel.reloadView();
						JOptionPane.showMessageDialog(this, "Suppression du personnel " + personnel.getNom() + " réussite !");
					} catch (BLLException e1) {
						e1.printStackTrace();
						AppliTestIHM.showError("Erreur de suppression", "Erreur de suppression:\n" + e1.getMessage());
					}
				}
			});
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
