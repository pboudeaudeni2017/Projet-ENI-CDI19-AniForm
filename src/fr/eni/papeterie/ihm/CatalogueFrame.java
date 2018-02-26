package fr.eni.papeterie.ihm;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Observable.Observer;

public class CatalogueFrame extends JFrame implements Observer {

	private JScrollPane scrollPane;
	
	private JTable tableCatalogue;
	private CatalogueTableModel model;
	
	public CatalogueFrame() {
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(getScrollPane());
		
		try {
			CatalogueController.getInstance().registerToCurrentArticle(this);
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTableCatalogue());
		}
		return scrollPane;
	}

	private JTable getTableCatalogue() {
		if(tableCatalogue == null) {
			try {
				model = new CatalogueTableModel();
				tableCatalogue = new JTable(model);
				tableCatalogue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableCatalogue.setRowHeight(30);
				tableCatalogue.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tableCatalogue.getSelectedRow();
						try {
							CatalogueController.getInstance().setArticle(index);
						} catch (ArticleNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BLLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (BLLException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		return tableCatalogue;
	}

	@Override
	public void onChanged(Object value) {
		int index = model.getIndexOf((Article)value);
		tableCatalogue.setRowSelectionInterval(index, index);
	}
	
	
}
