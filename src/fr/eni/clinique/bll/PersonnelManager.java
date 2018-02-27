package fr.eni.clinique.bll;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.dal.DAO;
import fr.eni.clinique.dal.DAOFactory;

public class PersonnelManager {
    private DAO<Personnel> personnelDAO;

    PersonnelManager(){
        this.personnelDAO = DAOFactory.getPersonnelDAO();
    }
}
