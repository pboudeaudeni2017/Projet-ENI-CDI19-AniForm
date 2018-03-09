package fr.eni.papeterie.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.ParameterException;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Observable.Observer;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.ihm.PanelButtons.IPanelButtonsObverser;

public class DetailFrame extends JFrame implements IPanelButtonsObverser, Observer {

	private JPanel panelForm;
	private JLabel lblReference;
	private JTextField txtReference;
	private JLabel lblDesignation;
	private JTextField txtDesignation;
	private JLabel lblMarque;
	private JTextField txtMarque;
	private JLabel lblStock;
	private JTextField txtStock;
	private JLabel lblPrix;
	private JTextField txtPrix;
	private JLabel lblType;
	private JRadioButton radioRamette;
	private JRadioButton radioStylo;
	private JLabel lblGrammage;
	private JCheckBox chk80;
	private JCheckBox chk100;
	private JLabel lblCouleur;
	private JComboBox<String> cmbCouleur;
	
	private PanelButtons panelButtons;

	private Article currentArticle = null;
	
	public DetailFrame() {
		setSize(400, 450);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		setContentPane(getPanelForm());
		
		try {
			CatalogueController.getInstance().registerToCurrentArticle(this);
			CatalogueController.getInstance().first();
		} catch (ArticleNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Le catalogue est vide");
		} catch (BLLException e) {
			JOptionPane.showMessageDialog(this, "Erreur lors du chargement du catalogue : " + e.getMessage());
		}
	}
	
	private JPanel getPanelForm() {
		
		if(panelForm == null) {
			panelForm = new JPanel(new GridBagLayout());
			
			addComponentTo(getLblReference(), panelForm, 0, 0, 1, 1, 0.2);
			addComponentTo(getTxtReference(), panelForm, 1, 0, 1, 1, 0.8);
			addComponentTo(getLblDesignation(), panelForm, 0, 1, 1, 1, 0.2);
			addComponentTo(getTxtDesignation(), panelForm, 1, 1, 1, 1, 0.8);
			addComponentTo(getLblMarque(), panelForm, 0, 2, 1, 1, 0.2);
			addComponentTo(getTxtMarque(), panelForm, 1, 2, 1, 1, 0.8);
			addComponentTo(getLblStock(), panelForm, 0, 3, 1, 1, 0.2);
			addComponentTo(getTxtStock(), panelForm, 1, 3, 1, 1, 0.8);
			addComponentTo(getLblPrix(), panelForm, 0, 4, 1, 1, 0.2);
			addComponentTo(getTxtPrix(), panelForm, 1, 4, 1, 1, 0.8);
			addComponentTo(getLblType(), panelForm, 0, 5, 1, 2, 0.2);
			
			
			ButtonGroup typeGroup = new ButtonGroup();
			typeGroup.add(getRadioRamette());
			typeGroup.add(getRadioStylo());
			
			addComponentTo(getRadioRamette(), panelForm, 1, 5, 1, 1, 0.8);
			addComponentTo(getRadioStylo(), panelForm, 1, 6, 1, 1, 0.8);
			
			addComponentTo(getLblGrammage(), panelForm, 0, 7, 1, 2, 0.2);
						
			ButtonGroup grammageGroup = new ButtonGroup();
			grammageGroup.add(getChk80());
			grammageGroup.add(getChk100());
			
			addComponentTo(getChk80(), panelForm, 1, 7, 1, 1, 0.8);
			addComponentTo(getChk100(), panelForm, 1, 8, 1, 1, 0.8);
			
			
			addComponentTo(getLblCouleur(), panelForm, 0, 9, 1, 1, 0.2);
			addComponentTo(getCmbCouleur(), panelForm, 1, 9, 1, 1, 0.8);
			
			addComponentTo(getPanelButtons(), panelForm, 0, 10, 2, 1, 1);
		}
		
		return panelForm;
	}
	
	private void addComponentTo(JComponent component, JPanel panel,
								int x, int y, int width, int height,
								double weightX) {
		addComponentTo(component, panel, x, y, width, height, weightX, true);
	}
	
	private void addComponentTo(JComponent component, JPanel panel,
								int x, int y, int width, int height,
								double weightX, boolean fillHorizontal) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightX;
		if(fillHorizontal) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
		}
		gbc.insets = new Insets(7, 10, 5, 10);
		panel.add(component, gbc);
	}
	
	private JLabel getLblReference() {
		if(lblReference == null) {
			lblReference = new JLabel("Référence");
			lblReference.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblReference;
	}
	private JTextField getTxtReference() {
		if(txtReference == null) {
			txtReference = new JTextField();
		}
		return txtReference;
	}
	private JLabel getLblDesignation() {
		if(lblDesignation == null) {
			lblDesignation = new JLabel("Designation");
			lblDesignation.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblDesignation;
	}
	private JTextField getTxtDesignation() {
		if(txtDesignation == null) {
			txtDesignation = new JTextField();
		}
		return txtDesignation;
	}
	private JLabel getLblMarque() {
		if(lblMarque == null) {
			lblMarque = new JLabel("Marque");
			lblMarque.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblMarque;
	}
	private JTextField getTxtMarque() {
		if(txtMarque == null) {
			txtMarque = new JTextField();
		}
		return txtMarque;
	}
	private JLabel getLblStock() {
		if(lblStock == null) {
			lblStock = new JLabel("Stock");
			lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblStock;
	}
	private JTextField getTxtStock() {
		if(txtStock == null) {
			txtStock = new JTextField();
		}
		return txtStock;
	}
	private JLabel getLblPrix() {
		if(lblPrix == null) {
			lblPrix = new JLabel("Prix");
			lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblPrix;
	}
	private JTextField getTxtPrix() {
		if(txtPrix == null) {
			txtPrix = new JTextField();
		}
		return txtPrix;
	}
	private JLabel getLblType() {
		if(lblType == null) {
			lblType = new JLabel("Type");
			lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblType;
	}
	private JRadioButton getRadioRamette() {
		if(radioRamette == null) {
			radioRamette = new JRadioButton("Ramette");
			radioRamette.setSelected(true);
			radioRamette.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setTypeArticle(true);
				}
			});
		}
		return radioRamette;
	}
	private JRadioButton getRadioStylo() {
		if(radioStylo == null) {
			radioStylo = new JRadioButton("Stylo");
			radioStylo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setTypeArticle(false);
				}
			});
		}
		return radioStylo;
	}
	
	/**
	 * Permet d'activer ou de désactiver les contrôles en fonction
	 * du type d'article.
	 * @param isRamette true si c'est une ramette, false sinon
	 */
	private void setTypeArticle(boolean isRamette) {
		getCmbCouleur().setEnabled(!isRamette);
		getLblCouleur().setEnabled(!isRamette);
		getRadioStylo().setSelected(!isRamette);
		
		getChk80().setEnabled(isRamette);
		getChk100().setEnabled(isRamette);
		getLblGrammage().setEnabled(isRamette);
		getChk80().setSelected(isRamette);
		getRadioRamette().setSelected(isRamette);
	}
	
	private JLabel getLblGrammage() {
		if(lblGrammage == null) {
			lblGrammage = new JLabel("Grammage");
			lblGrammage.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblGrammage;
	}
	private JCheckBox getChk80() {
		if(chk80 == null) {
			chk80 = new JCheckBox("80 grammes");
		}
		return chk80;
	}
	private JCheckBox getChk100() {
		if(chk100 == null) {
			chk100 = new JCheckBox("100 grammes");
		}
		return chk100;
	}
	private JLabel getLblCouleur() {
		if(lblCouleur == null) {
			lblCouleur = new JLabel("Couleur");
			lblCouleur.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblCouleur;
	}
	private JComboBox<String> getCmbCouleur() {
		if(cmbCouleur == null) {
			String[] couleurs = {"bleu", "vert", "rouge", "noir", "jaune"};
			cmbCouleur = new JComboBox<>(couleurs);
		}
		return cmbCouleur;
	}
	
	private PanelButtons getPanelButtons() {
		if(panelButtons == null) {
			panelButtons = new PanelButtons();			
			panelButtons.register(this);
		}
		
		return panelButtons;
	}
	
	
	private void setArticle(Article article) {
		currentArticle = article;
		
		if(article != null) {
			getTxtDesignation().setText(article.getDesignation());
			getTxtMarque().setText(article.getMarque());
			getTxtPrix().setText(String.format("%.2f", article.getPrixUnitaire()).replace(',', '.'));
			getTxtReference().setText(article.getReference());
			getTxtStock().setText(String.valueOf(article.getQteStock()));
			
			if(article instanceof Ramette) {
				setTypeArticle(true);
				if(((Ramette)article).getGrammage() == 80) {
					getChk80().setSelected(true);
				} else {
					getChk100().setSelected(true);
				}
			} else {
				setTypeArticle(false);
				getCmbCouleur().setSelectedItem(((Stylo)article).getCouleur());
			}
		}
	}
	
	public Article getArticleFromFormulaire() throws BLLException {
		BLLException exception = new BLLException();
		
		Article article = null;
		
		int qteStock = 0;
		try {
			qteStock = Integer.parseInt(getTxtStock().getText());
		} catch (NumberFormatException e) {
			exception.ajouterException(new ParameterException("Stock", "Veuillez saisir un nombre entier."));
		}
		
		float prixUnitaire = 0;
		try {
			prixUnitaire = Float.parseFloat(getTxtPrix().getText());
		} catch (NumberFormatException e) {
			exception.ajouterException(new ParameterException("Prix", "Veuillez saisir un nombre."));
		}
		
		if(exception.size() > 0) {
			throw exception;
		}
		
		String reference = getTxtReference().getText();
		String designation = getTxtDesignation().getText();
		String marque = getTxtMarque().getText();
		if(getRadioRamette().isSelected()) {
			int grammage = getChk100().isSelected() ? 100 : 80;
			article = new Ramette(reference, marque, designation, prixUnitaire, qteStock, grammage);
		} else {
			String couleur = (String) getCmbCouleur().getSelectedItem();
			article = new Stylo(reference, marque, designation, prixUnitaire, qteStock, couleur);
		}
		
		if(currentArticle != null) {
			article.setIdArticle(currentArticle.getIdArticle());
		}
		
		currentArticle = article;
		
		return article;		
	}
	
	@Override
	public void onPrevious() {
		try {
			CatalogueController.getInstance().previous();
		} catch (ArticleNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Vous êtes déjà au début du catalogue");
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onNext() {
		try {
			CatalogueController.getInstance().next();
		} catch (ArticleNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Vous êtes déjà à la fin du catalogue");
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate() {
		getTxtDesignation().setText("");
		getTxtMarque().setText("");
		getTxtPrix().setText("");
		getTxtReference().setText("");
		getTxtStock().setText("");
		
		setTypeArticle(true);
		
		currentArticle = null;
	}
	
	@Override
	public void onSave() {
		if(currentArticle != null) { //on modifie un article
			try {
				CatalogueController.getInstance().updateCurrentArticle(getArticleFromFormulaire());
				JOptionPane.showMessageDialog(this, "Article modifié");
			} catch (BLLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de l'article : " + e.getMessage());
			}
		} else { //on ajoute un article
			try {
				CatalogueController.getInstance().createNewArticle(getArticleFromFormulaire());
				JOptionPane.showMessageDialog(this, "Article ajouté");
			} catch (BLLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erreur lors d'ajout de l'article : " + e.getMessage());
			}
		}
	}
	
	@Override
	public void onDelete() {
		//Si l'article existe
		if(currentArticle != null) {
			try {
				CatalogueController.getInstance().deleteCurrentArticle();
			} catch (BLLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'article");
			} catch (ArticleNotFoundException e) {
				/*
				 * cette exception est levée si la suppression s'est
				 * bien déroulée, mais qu'il n'y a plus rien à afficher
				 */				
				e.printStackTrace();
				onCreate();
			}
			
		} else {
			onCreate();
		}
	}

	@Override
	public void onChanged(Object value) {
		setArticle((Article)value);
	}
	
}
