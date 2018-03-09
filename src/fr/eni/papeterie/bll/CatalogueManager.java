package fr.eni.papeterie.bll;

import java.util.List;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DAOFactory;

public class CatalogueManager {

	private ArticleDAO daoArticle;
	
	public CatalogueManager() {
		daoArticle = DAOFactory.getArticleDAO();
	}
	
	public List<Article> getCatalogue() throws BLLException {
		try {
			return daoArticle.selectAll();
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération du catalogue impossible");
		}
	}
	
	public Article getArticle(int index) throws BLLException {
		try {
			return daoArticle.selectById(index);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération de l'article impossible");
		}
	}
	
	public void updateArticle(Article article) throws BLLException {
		validerArticle(article);
		
		try {
			daoArticle.update(article);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Mise à jour de l'article impossible");
		}
	}
	
	public void removeArticle(Article article) throws BLLException {
		try {
			if(article != null) {
				daoArticle.delete(article);
			} else {
				throw new BLLException("Suppression de l'article impossible (article null)");
			}
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Suppression de l'article impossible");
		}
	}
	
	public void addArticle(Article article) throws BLLException {
		validerArticle(article);
		
		try {
			daoArticle.insert(article);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Ajout de l'article impossible");
		}
	}

	private void validerArticle(Article article) throws BLLException {
		BLLException exception = new BLLException();
		
		if(article == null) {
			throw new BLLException("Article null !");
		}
		
		if(article.getReference() == null ||
				article.getReference().trim().length() == 0) {
			exception.ajouterException(new ParameterException("Reference", "Obligatoire"));
		}
		
		if(article.getMarque() == null ||
				article.getMarque().trim().length() == 0) {
			exception.ajouterException(new ParameterException("Marque", "Obligatoire"));
		}
		
		if(article.getDesignation() == null ||
				article.getDesignation().trim().length() == 0) {
			exception.ajouterException(new ParameterException("Designation", "Obligatoire"));
		}

		if(article.getPrixUnitaire() < 0) {
			exception.ajouterException(new ParameterException("Prix unitaire", "Nombre >= 0 attendu"));
		}
		
		if(article.getQteStock() < 0) {
			exception.ajouterException(new ParameterException("Quantité stock", "Nombre >= 0 attendu"));
		}
		
		if(article instanceof Stylo) {
			if(((Stylo)article).getCouleur() == null ||
					((Stylo)article).getCouleur().trim().length() == 0) {
				exception.ajouterException(new ParameterException("Couleur", "Obligatoire"));
			}
		} else {
			if(((Ramette)article).getGrammage() < 0) {
				exception.ajouterException(new ParameterException("Grammage", "Nombre >= 0 attendu"));
			}
		}
		
		if(exception.size() > 0) {
			throw exception;
		}
	}
	
	
	
	
}
