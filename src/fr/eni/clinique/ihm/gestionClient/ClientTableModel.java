package fr.eni.clinique.ihm.gestionClient;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.ClientManager;
import fr.eni.clinique.bo.Client;

public class ClientTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Client> clients = new ArrayList<>();
	
	private String[] nomColonnes = {"Nom", "Prénom", "Adresse1", "Adresse2", "Code Postal", "Ville", "Téléphone", "Assurance", "Email", "Remarque", "Archive"};
	
	public ClientTableModel() throws BLLException {
		updateData();
	}
	
	public void updateData() throws BLLException {
		clients = new ClientManager().getClients();
		fireTableDataChanged();
	}

	public Client getClient(int row){
		Client client = new Client();
		if(row >= 0 && row < this.clients.size()){
			client = this.clients.get(row);
		}

		return client;
	}
	
	
	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}
	
	@Override
	public int getRowCount() {
		return clients.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return this.nomColonnes[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex != 10) {
			return String.class;
			
		} else {
			return boolean.class;
		}
		//return getValueAt(0, columnIndex).getClass();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		Object value = null;
		
		if (rowIndex >=0 && rowIndex < clients.size()) {
			Client client = clients.get(rowIndex);
			
			switch(colIndex) {
			case 0:
				value = client.getNomClient();
				break;
			case 1:
				value = client.getPrenomClient();
				break;
			case 2:
				value = client.getAdresse1();
				break;
			case 3:
				value = client.getAdresse2();
				break;
			case 4:
				value = client.getCodePostal();
				break;
			case 5:
				value = client.getVille();
				break;
			case 6:
				value = client.getNumTel();
				break;
			case 7:
				value = client.getAssurance();
				break;
			case 8:
				value = client.getEmail();
				break;
			case 9:
				value = client.getRemarque();
				break;
			case 10:
				value = client.isArchive();
				break;
			}
		}
		
		return value;
	}
	
	public int getIndexOf(Client client) {
		System.out.println(clients);
		return clients.indexOf(client);
	}
	
	

}
