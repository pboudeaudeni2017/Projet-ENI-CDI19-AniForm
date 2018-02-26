package fr.eni.papeterie.bo;

public class Ligne {

	private Article article;
	protected int qte;
	
	public Ligne(Article article, int qte) {
		setArticle(article);
		setQte(qte);
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public float getPrix() {
		return article.getPrixUnitaire() * qte;
	}
	
	@Override
	public String toString() {
		return "Ligne [ qte=" + qte + 
						", prix=" + getPrix() + 
						", article=" + article + "]";
	}
}
