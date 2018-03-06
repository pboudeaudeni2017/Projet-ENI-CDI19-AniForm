package fr.eni.clinique.ihm.gestionClient;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.ihm.AppliTestIHM;

public class BoutonMenuClient extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton bttNew;
	private JButton bttDelete;
	
	private JLabel lblSearch;
	private JTextField txtSearch;

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

		setLayout(new GridBagLayout());
		addComponentTo(this.getBttNew(), this, 0, 0, 1, 1, 0.1);
		addComponentTo(this.getBttDelete(), this, 1, 0, 1, 1, 0.1);
		addComponentTo(this.getLblSearch(), this, 2, 0, 1, 1, 0.5);
		addComponentTo(this.getTxtSearch(), this, 3, 0, 1, 1, 0.5);
		
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
	
	public JLabel getLblSearch() {
		if (lblSearch == null) {
			lblSearch = new JLabel("Rechercher", SwingConstants.RIGHT);
			
		}

		return lblSearch;
	}


	public JTextField getTxtSearch() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			txtSearch.addActionListener((ActionEvent e) -> {
				EcranGestionClient ecranGestionClient = ((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel());
				JTable jTable = ecranGestionClient.getTableClient();
				TableRowSorter<ClientTableModel> sorter = new TableRowSorter<ClientTableModel>(((ClientTableModel) jTable.getModel())); 
			    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + getTxtSearch().getText(), 0));
			  

			    jTable.setRowSorter(sorter);			    
			});
		}

		return txtSearch;
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

}
