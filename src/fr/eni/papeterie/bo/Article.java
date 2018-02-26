package fr.eni.papeterie.bo;

public abstract class Article {

	private int idArticle;
	private String reference;
	private String marque;
	private String designation;
	private float prixUnitaire;
	private int qteStock;
	
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public float getPrixUnitaire() {
		return prixUnitaire;
	}
	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	public int getQteStock() {
		return qteStock;
	}
	public void setQteStock(int qteStock) {
		this.qteStock = qteStock;
	}
	
	public Article(String reference, String marque, String designation, float prixUnitaire, int qteStock) {
		setReference(reference);
		setMarque(marque);
		setDesignation(designation);
		setPrixUnitaire(prixUnitaire);
		setQteStock(qteStock);
	}
	
	public Article(int idArticle, String reference, String marque, String designation, float prixUnitaire,
			int qteStock) {
		this(reference, marque, designation, prixUnitaire, qteStock);
		setIdArticle(idArticle);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [idArticle=").append(idArticle).append(", reference=").append(reference)
				.append(", marque=").append(marque).append(", designation=").append(designation)
				.append(", prixUnitaire=").append(prixUnitaire).append(", qteStock=").append(qteStock).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + idArticle;
		result = prime * result + ((marque == null) ? 0 : marque.hashCode());
		result = prime * result + Float.floatToIntBits(prixUnitaire);
		result = prime * result + qteStock;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;

		if (idArticle != other.idArticle)
			return false;

		return true;
	}
	
	
	
}
