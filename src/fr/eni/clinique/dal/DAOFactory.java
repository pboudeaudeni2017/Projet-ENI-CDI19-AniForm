package fr.eni.clinique.dal;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.dal.jdbc.PersonnelDAOJdbcImpl;
import fr.eni.papeterie.dal.bouchon.ArticleDaoBouchonImpl;
import fr.eni.papeterie.dal.jdbc.ArticleDAOJdbcImpl;

public class DAOFactory {

	public static DAO<Personnel> getPersonnelDAO() { return new PersonnelDAOJdbcImpl ();}
}
