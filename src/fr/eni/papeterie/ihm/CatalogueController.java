package fr.eni.papeterie.ihm;

import java.util.List;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Observable;
import fr.eni.papeterie.bo.Observable.Observer;

public class CatalogueController {

	private List<Article> listeArticles;
	private int indexArticle;
	
	private Observable<Article> currentArticle = new Observable<>();
	
	private CatalogueManager manager;
	
	private static CatalogueController instance = null;
	
	private CatalogueController() throws BLLException { 
		manager = new CatalogueManager();
		
		listeArticles = manager.getCatalogue();
		indexArticle = 0;
	}
	
	public static CatalogueController getInstance() throws BLLException {
		if(instance == null) {
			instance = new CatalogueController();
		}
		return instance;
	}
	
	public void first() throws ArticleNotFoundException {
		setArticle(0);		
	}
	
	public void setArticle(int index) throws ArticleNotFoundException {
		if(index >= 0 && index < listeArticles.size()) {
			indexArticle = index;
			currentArticle.set(listeArticles.get(indexArticle));
		} else {
			throw new ArticleNotFoundException();
		}		
	}
	
	public void next() throws ArticleNotFoundException {
		if(indexArticle + 1 < listeArticles.size()) {
//			indexArticle++;
//			return listeArticles.get(indexArticle);
			currentArticle.set(listeArticles.get(++indexArticle));
		} else {
			throw new ArticleNotFoundException();
		}
	}
	
	public void previous() throws ArticleNotFoundException {
		if(indexArticle - 1 >= 0) {
//			indexArticle--;
//			return listeArticles.get(indexArticle);	
			currentArticle.set(listeArticles.get(--indexArticle));
		} else {
			throw new ArticleNotFoundException();
		}
	}

	public void deleteCurrentArticle() throws BLLException, ArticleNotFoundException {
		//supprime l'article dans la BDD
		manager.removeArticle(listeArticles.get(indexArticle));
		
		//supprime l'article dans la liste ("affich�e")
		listeArticles.remove(indexArticle);
		
		try {
			previous();
		} catch (ArticleNotFoundException e) {
			if(indexArticle < listeArticles.size()) {
				currentArticle.set(listeArticles.get(indexArticle));
			}
		}
		
		throw new ArticleNotFoundException();
	}

	public void updateCurrentArticle(Article article) throws BLLException {
		manager.updateArticle(article);
		listeArticles.set(indexArticle, article);
	}

	public void createNewArticle(Article article) throws BLLException {
		manager.addArticle(article);
		listeArticles.add(article);
	}
	
	public void registerToCurrentArticle(Observer obs) {
		currentArticle.registerObserver(obs);
	}

	
}
