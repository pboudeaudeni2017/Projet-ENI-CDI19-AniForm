package fr.eni.papeterie.ihm;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Stylo;

public class CatalogueTableModel extends AbstractTableModel {

	private static ImageIcon imageStylo;
	private static ImageIcon imageRamette;
	
	private String[] nomColonnes = {"Type", "Reference", "Marque",
									"Designation", "Prix unitaire", "Stock"};
	
	private List<Article> articles = new ArrayList<>();
	
	static {
		imageStylo = new ImageIcon(CatalogueTableModel.class.getResource("./resources/pencil.gif"));
		imageRamette = new ImageIcon(CatalogueTableModel.class.getResource("./resources/ramette.gif"));
	}
	
	
	public CatalogueTableModel() throws BLLException {
		updateData();
	}
	
	public void updateData() throws BLLException {
		articles = new CatalogueManager().getCatalogue();
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}

	@Override
	public int getRowCount() {
		return articles.size();
	}

	@Override
	public String getColumnName(int column) {
		return nomColonnes[column];
	}
	
	/**
	 * Retourne le type de données (Class) pour une colonne donnée.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		Object value = null;
		
		if(rowIndex >= 0 && rowIndex < articles.size()) {
			Article article = articles.get(rowIndex);
			switch(colIndex) {
			case 0:
				if(article instanceof Stylo) {
					value = imageStylo;
				} else {
					value = imageRamette;
				}
				break;
			case 1:
				value = article.getReference();
				break;
			case 2:
				value = article.getMarque();
				break;
			case 3:
				value = article.getDesignation();
				break;
			case 4:
				value = String.format("%.2f €", article.getPrixUnitaire());
				break;
			case 5:
				value = article.getQteStock();
				break;				
			}
			
		}
		
		return value;
	}

	public int getIndexOf(Article article) {
		System.out.println(articles);
		return articles.indexOf(article);
	}
}
