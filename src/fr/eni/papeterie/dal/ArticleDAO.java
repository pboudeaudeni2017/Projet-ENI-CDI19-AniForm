package fr.eni.papeterie.dal;

import java.util.List;

import fr.eni.papeterie.bo.Article;

public interface ArticleDAO extends DAO<Article> {

	List<Article> selectByMarque() throws DALException;
	
	List<Article> selectByMotCle() throws DALException;

}