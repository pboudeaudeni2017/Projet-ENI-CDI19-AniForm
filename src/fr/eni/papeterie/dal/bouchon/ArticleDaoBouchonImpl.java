package fr.eni.papeterie.dal.bouchon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;

public class ArticleDaoBouchonImpl implements ArticleDAO {

	private static List<Article> articles = new ArrayList<>();
	
	static {
		articles.add(new Stylo(0, "ref1", "marque", "designation", 10.5f, 5, "rouge"));
		articles.add(new Stylo(1, "ref2", "marque", "designation", 15.5f, 5, "vert"));
	}
	
	@Override
	public void insert(Article o) throws DALException {
		articles.add(o);
	}

	@Override
	public Article selectById(int id) throws DALException {
		return articles.get(id);
	}

	@Override
	public List<Article> selectAll() throws DALException {
		List<Article> copyOfList = new ArrayList<>();
		
		for(Article a : articles) {
			copyOfList.add(a);
		}
		
		return copyOfList;
	}

	@Override
	public void update(Article o) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Article o) throws DALException {
		articles.remove(o);
	}

	@Override
	public List<Article> selectByMarque() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectByMotCle() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

}
