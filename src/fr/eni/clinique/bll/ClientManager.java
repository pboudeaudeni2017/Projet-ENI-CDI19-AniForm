package fr.eni.clinique.bll;

import java.util.List;

import fr.eni.clinique.bo.Client;
import fr.eni.clinique.dal.DALException;
import fr.eni.clinique.dal.DAO;
import fr.eni.clinique.dal.DAOFactory;
import fr.eni.clinique.dal.jdbc.ClientDAOJdbcImpl;

public class ClientManager {
	
	private DAO<Client> clientDAO;
	
	public ClientManager() {
		this.clientDAO = DAOFactory.getClientDAO();
	}
	
	
	public Client getClient(int codeClient) throws BLLException {
		Client client = new Client();
		client.setCodeClient(codeClient);
		
		try {
			return this.clientDAO.selectById(client);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération du Client impossible", e);
		}
	}
	
	
	public Client getClient(String name) throws BLLException {
		Client client = new Client();
		client.setNomClient(name);
		
		try {
			client = ((ClientDAOJdbcImpl)this.clientDAO).selectByName(client);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération du Client impossible", e);
		}
		
		return client;
	}
	
	
	public List<Client> getClients() throws BLLException {
		try {
			return clientDAO.selectAll();
		} catch (DALException e ) {
			e.printStackTrace();
			throw new BLLException("Récupération de la liste des clients impossible", e);
		}
	}
	
	public void updateClient(Client client) throws BLLException {
		try {
			this.clientDAO.update(client);
		} catch (DALException e) {
			throw new BLLException("Mise à jour du client impossible", e);
		}
	}
	
	
	public void deleteClient(Client client) throws BLLException {
		try {
			this.clientDAO.delete(client);
		} catch (DALException e) {
			throw new BLLException("Suppression impossible");
		}
	}
	
	
	public void addClient(Client client) throws BLLException {
		try {
			this.clientDAO.insert(client);
		} catch (DALException e){
			throw new BLLException("Ajout du personnel impossible", e);
			
		}
	}
	
	
	public void validationClient(Client client) throws BLLException {
		BLLException exceptions = new BLLException();
		String needed = "Obligatoire";
		
		if (client == null) {
			throw new BLLException("Client null");
		}
		
		if (client.getCodeClient() >= 0) {
			exceptions.ajouterException(new ParameterException("CodeClient", "Doit être supérieur ou égal à 0"));
		}
		
		if (client.getNomClient() == null ||
			client.getNomClient().trim().length() == 0) {
				exceptions.ajouterException(new ParameterException("Nom", needed));
		}
		
		if (client.getPrenomClient() == null ||
				client.getPrenomClient().trim().length() == 0) {
			exceptions.ajouterException(new ParameterException("Prénom", needed));
		}
		
		
		if (exceptions.size() > 0) {
			throw exceptions;
		}
	}
		
}
