package fr.eni.papeterie.dal;

import fr.eni.papeterie.dal.bouchon.ArticleDaoBouchonImpl;
import fr.eni.papeterie.dal.jdbc.ArticleDAOJdbcImpl;

public class DAOFactory {

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}
}
