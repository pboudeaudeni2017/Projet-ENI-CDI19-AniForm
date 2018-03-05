package fr.eni.clinique.ihm.gestionClient;

import java.util.List;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.ClientManager;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Observable;
import fr.eni.clinique.bo.Observable.Observer;

public class ClientController {
	
	private List<Client> listeClients;
	private int indexClient;
	
	private Observable<Client> currentClient = new Observable<>();
	
	private ClientManager manager;
	private static ClientController instance = null;
	
	private ClientController() throws BLLException {
		manager = new ClientManager();
		
		listeClients = manager.getClients();
		indexClient = 0;
	}
	
	public static ClientController getInstance() throws BLLException {
		if (instance == null) {
			instance = new ClientController();
		}
		
		return instance;
	}
	
	public void first() throws ClientNotFoundException {
		setClient(0);
	}
	
	public void setClient(int index) throws ClientNotFoundException {
		if (index >= 0 && index < listeClients.size()) {
			indexClient = index;
			currentClient.set(listeClients.get(indexClient));
		} else {
			throw new ClientNotFoundException();
		}
	}
	
	
	public void registerToCurrentClient(Observer obs) {
		currentClient.registerObserver(obs);
	}
	
	

}
