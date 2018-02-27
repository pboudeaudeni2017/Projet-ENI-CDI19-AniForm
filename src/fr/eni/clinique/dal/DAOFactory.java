package fr.eni.clinique.dal;

import fr.eni.clinique.bo.Agenda;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.dal.jdbc.AgendaDAOJdbcImpl;
import fr.eni.clinique.dal.jdbc.ClientDAOJdbcImpl;
import fr.eni.clinique.dal.jdbc.PersonnelDAOJdbcImpl;

public class DAOFactory {

	public static DAO<Personnel> getPersonnelDAO() { return new PersonnelDAOJdbcImpl ();}

	public static DAO<Client> getClientDAO() { return new ClientDAOJdbcImpl (); }

	public static DAO<Agenda> getAgendaDAO() { return  new AgendaDAOJdbcImpl(); }
}
