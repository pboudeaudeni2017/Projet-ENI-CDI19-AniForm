package fr.eni.clinique.ihm.gestionAnimaux;

public class AnimalNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnimalNotFoundException() {
		super("Animal introuvable");
	}

}
