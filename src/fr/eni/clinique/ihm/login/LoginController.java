package fr.eni.clinique.ihm.login;

import javax.swing.JDialog;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.PersonnelManager;
import fr.eni.clinique.bo.Observable;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.bo.Personnel;

public class LoginController {
	
	private Observable<Personnel> currentPersonnel = new Observable<>();
	private PersonnelManager manager; 
	private JDialog jdog;
	
	public LoginController(){
		this.manager = new PersonnelManager();
		
		
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
	
	public void connexion(String nom, String motPasse) {
		try {
			Personnel resultat = this.manager.getPersonnel(nom);

			if (resultat == null) {
				throw new BLLException("Erreur: Le résultat n'existe pas");
			}

			if (resultat.getNom().equals(nom)) {
				System.out.println("Même nom");
			} else {
				throw new BLLException("Erreur de récupération");
			}
			
			if (resultat.getMotPasse().equals(motPasse)) {
				System.out.println("Même mot de passe");
			} else {
				throw new BLLException("Mauvaise concordance");
			}

		} catch (BLLException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
