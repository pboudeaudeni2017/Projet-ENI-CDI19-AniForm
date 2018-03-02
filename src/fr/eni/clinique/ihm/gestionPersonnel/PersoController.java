package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bll.PersonnelManager;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.MainFrame;
import fr.eni.clinique.ihm.login.LoginController;

import javax.swing.*;

public class PersoController {
    private PersonnelManager personnelManager;

    public PersoController() {
        this.personnelManager = new PersonnelManager();
    }

    public Personnel getPerso(int id){
        Personnel personnel = new Personnel();
        if(id > 0){
            try {
                personnel = this.personnelManager.getPersonnel(id);
            } catch (BLLException e) {
                e.printStackTrace();
            }
        }
        return personnel;
    }

    public void addPerso(Personnel personnel) throws BLLException {
        this.personnelManager.addPersonnel(personnel);
    }

    public void updatePerso(Personnel personnel) throws BLLException{
        this.personnelManager.updatePersonnel(personnel);
    }

}
