package fr.eni.clinique.ihm.gestionClient;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.gestionPersonnel.EcranGestionPersonnel;
import fr.eni.clinique.ihm.gestionPersonnel.PersonnelController;

public class BoutonMenuClient extends JPanel {
	
	private JButton bttNew;
	private JButton bttDelete;

	private EcranGestionClient gestionClient;
	private ClientController clientController;

	public BoutonMenuClient(EcranGestionClient gestionClient) {
		super();
		this.gestionClient = gestionClient;
		try {
			this.clientController = ClientController.getInstance();
		} catch (BLLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
		}
		add(getBttNew());
		add(getBttDelete());
	}

	private JButton getBttNew() {
		if(bttNew == null) {
			bttNew = new JButton("Ajouter");
			bttNew.addActionListener((ActionEvent e) -> {
				this.gestionClient.getCreationView().setVisible(true);
			});
		}

		return bttNew;

	}

	private JButton getBttDelete() {
		if(bttDelete == null) {
			bttDelete = new JButton("Supprimer");
			bttDelete.addActionListener((ActionEvent e) -> {
				int selectedRow = this.gestionClient.getTableClient().getSelectedRow();
				Client client = this.gestionClient.getClientFromJTable(selectedRow);
				this.gestionClient.getTableClient().clearSelection();
				int reply = JOptionPane.showConfirmDialog(bttDelete, "Voulez-vous vraiment supprimer " + client.getNomClient() + " ?\nCette action est définitive", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					try {
						this.clientController.setClient(client);
						this.clientController.deleteClient();
						this.gestionClient.reloadView();
						JOptionPane.showMessageDialog(this, "Suppression du client " + client.getNomClient() + " réussie !");
					} catch (BLLException e1) {
						e1.printStackTrace();
						AppliTestIHM.showError("Erreur de suppression", "Erreur de suppression:\n" + e1.getMessage());
					} catch (ClientNotFoundException e1) {
						e1.printStackTrace();
						AppliTestIHM.showError("Erreur de suppression", "Erreur de suppression:\n" + e1.getMessage());
					}
				}
			});
		}

		return bttDelete;

	}

}
