package fr.eni.clinique.ihm.gestionClient;

public class ClientNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientNotFoundException() {
		super("Client introuvable");
	}

}
