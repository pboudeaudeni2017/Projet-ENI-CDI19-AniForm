package fr.eni.clinique.ihm.gestionAnimaux;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.ihm.AppliTestIHM;

public class BoutonMenuAnimal extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton bttNew;
	private JButton bttDelete;
	
	private AnimalClientPanel animalClientPanel;
	private AnimalController animalController;
	
	public BoutonMenuAnimal(AnimalClientPanel animalClientPanel) {
		super();
		this.animalClientPanel = animalClientPanel;
		
		try {
			this.animalController = AnimalController.getInstance();
		} catch (BLLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur de suppresion", JOptionPane.ERROR_MESSAGE);
		}
		
		add(getBttNew());
		add(getBttDelete());
	}
	
	
	private JButton getBttNew() {
		if (bttNew == null) {
			bttNew = new JButton("Ajouter");
			/* bttNew.addActionListener((ActionEvent e) -> {
				this.animalClientPanel.getCreationView().setVisible(true);
				System.out.println("Hello new");
			}); */
		}
		
		return bttNew;
	}
	
	
	private JButton getBttDelete() {
		if (bttDelete == null) {
			bttDelete = new JButton("Supprimer");
			/* bttDelete.addActionListener((ActionEvent e) -> {
				int selectedRow = this.animalClientPanel.getTableAnimal().getSelectedRow();

				Animal animal = this.gestionAnimal.getAnimalFromJTable(selectedRow);
				this.animalClientPanel.getTableAnimal().clearSelection();

				int reply = JOptionPane.showConfirmDialog(bttDelete, "Voulez-vous vraiment supprimer" + animal.getNomAnimal() + " ?\nCette action est définitive", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_NO_OPTION) {
					try {
						this.animalController.deleteAnimal(animal);
						this.animalClientPanel.reloadView();
						JOptionPane.showMessageDialog(this, "Suppression de l'animal" + animal.getNomAnimal() + " réussie !");
					} catch (BLLException e1) {
						e1.printStackTrace();
						AppliTestIHM.showError("Erreur de suppression", "Erreur de suppression:\n" + e1.getMessage());
					}
				}
			}); */
		}

		return bttDelete;
	}

}
