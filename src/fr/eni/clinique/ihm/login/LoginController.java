package fr.eni.clinique.ihm.login;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.PersonnelManager;
import fr.eni.clinique.bo.Observable;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.bo.Personnel;

public class LoginController {
	
	private Observable<Personnel> currentPersonnel = new Observable<>();
	private PersonnelManager manager; 
	
	private LoginController() throws BLLException {
		manager = new PersonnelManager();
	}
	
	
	
	
	public void registerToCurrentPersonnel(Observer obs) {
		currentPersonnel.registerObserver(obs);
		
	}

}
