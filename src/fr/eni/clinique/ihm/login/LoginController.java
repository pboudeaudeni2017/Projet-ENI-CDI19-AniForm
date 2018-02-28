package fr.eni.clinique.ihm.login;

import javax.swing.JDialog;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.PersonnelManager;
import fr.eni.clinique.bo.Observable;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.bo.Personnel;

public class LoginController {

	private static LoginController _instance;
	private Personnel CurrentPersonnel;

	private Observable<Personnel> currentPersonnel = new Observable<>();
	private PersonnelManager manager; 
	private JDialog jdog;
	
	private LoginController(){
		this.manager = new PersonnelManager();
		this.setCurrentPersonnel(new Personnel());
	}

	public static LoginController getInstance() {
		if(_instance == null){
			_instance = new LoginController();
		}
		return _instance;
	}

	public void registerToCurrentPersonnel(Observer obs) {
		this.currentPersonnel.registerObserver(obs);
		
	}


	public JDialog getJdog() {
		if (this.jdog == null) {
			this.jdog = new JDialog();
		}
		
		return this.jdog;
	}

	public Personnel getCurrentPersonnel() {
		return CurrentPersonnel;
	}

	public void setCurrentPersonnel(Personnel currentPersonnel) {
		CurrentPersonnel = currentPersonnel;
	}

	public void connexion(String nom, String motPasse) {
		try {
			Personnel resultat = this.manager.getPersonnel(nom);

			if (resultat == null) {
				throw new BLLException("Erreur: Le r�sultat n'existe pas");
			}

			if (resultat.getNom().equals(nom)) {
				System.out.println("M�me nom");
			} else {
				throw new BLLException("Erreur de r�cup�ration");
			}
			
			if (resultat.getMotPasse().equals(motPasse)) {
				System.out.println("M�me mot de passe");
			} else {
				throw new BLLException("Mauvaise concordance");
			}

			this.setCurrentPersonnel(resultat);

		} catch (BLLException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
