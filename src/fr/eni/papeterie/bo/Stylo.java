package fr.eni.papeterie.bo;

public class Stylo extends Article {

	private String couleur;
	
	public Stylo(String reference, String marque, 
				 String designation, float prixUnitaire,
				 int qteStock, String couleur) {
		super(reference, marque, designation, prixUnitaire, qteStock);
		setCouleur(couleur);
	}

	public Stylo(int idArticle, String reference, 
				 String marque, String designation, 
				 float prixUnitaire, int qteStock, 
				 String couleur) {
		super(idArticle, reference, marque, designation, prixUnitaire, qteStock);
		setCouleur(couleur);
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return super.toString() + " Stylo [couleur=" + couleur + "]";
	}
	
}
