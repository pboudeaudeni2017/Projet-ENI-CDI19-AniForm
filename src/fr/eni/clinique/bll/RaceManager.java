package fr.eni.clinique.bll;

import fr.eni.clinique.bo.Race;
import fr.eni.clinique.dal.DALException;
import fr.eni.clinique.dal.DAO;
import fr.eni.clinique.dal.DAOFactory;
import fr.eni.clinique.dal.jdbc.RaceDAOJdbcImpl;

import java.util.List;

public class RaceManager {
    private DAO<Race> raceDAO;

    public RaceManager() { this.raceDAO = DAOFactory.getRaceDAO(); }

    public List<Race> getRacesEspece() throws BLLException{
        try {
            return this.raceDAO.selectAll();
        } catch (DALException e) {
            throw new BLLException("Race SelectAll impossible", e);
        }
    }

    public List<Race> getRacesEspece(Race espece) throws BLLException{
        try {
            return ((RaceDAOJdbcImpl)this.raceDAO).selectByEspece(espece);
        } catch (DALException e) {
            throw new BLLException("Race SelectAll impossible", e);
        }
    }

    public List<String> getEspeces() throws BLLException{
        try {
            return ((RaceDAOJdbcImpl)this.raceDAO).selectAllEspeces();
        } catch (DALException e) {
            throw new BLLException("Race GetEspeces impossible", e);
        }
    }
}
