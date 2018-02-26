package fr.eni.papeterie.ihm;

public class ArticleNotFoundException extends Exception {

	public ArticleNotFoundException() {
		super("Article introuvable");
	}
}
