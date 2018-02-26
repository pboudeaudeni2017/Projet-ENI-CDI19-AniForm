package fr.eni.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

public class Panier {

	private List<Ligne> lignesPanier;
	
	public Panier() {
		lignesPanier = new ArrayList<>();
	}

	public List<Ligne> getLignesPanier() {
		return lignesPanier;
	}
	
	public void addLigne(Article article, int qte) {
		lignesPanier.add(new Ligne(article, qte));
	}
	
	public Ligne getLigne(int index) {
		if(index >= 0 && index < lignesPanier.size()) {
			return lignesPanier.get(index);
		} else {
			return null;
		}
	}
	
	public void removeLigne(int index) {
		if(index >= 0 && index < lignesPanier.size()) {
			lignesPanier.remove(index);
		}
	}
	
	public void updateLigne(int index, int newQte) {
		if(index >= 0 && index < lignesPanier.size()) {
			//Ligne ligneAModifier = lignesPanier.get(index);
			//ligneAModifier.setQte(newQte);
			
			lignesPanier.get(index).setQte(newQte);
		}
	}
	
	public float getMontant() {
		float montant = 0;
		
		for(Ligne l : lignesPanier) {
			montant += l.getPrix();
		}
		
		return montant;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Panier :\n\n");
		for(int i = 0 ; i < lignesPanier.size() ; i++) {
			builder.append("ligne ");
			builder.append(i);
			builder.append(" :\t");
			builder.append(lignesPanier.get(i));
			builder.append("\n");
		}
		builder.append("\nValeur du panier : ");
		builder.append(getMontant());
		builder.append("\n");
		
		return builder.toString();
	}

	
}
