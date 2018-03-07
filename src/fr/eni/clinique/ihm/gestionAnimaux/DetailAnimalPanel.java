package fr.eni.clinique.ihm.gestionAnimaux;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Race;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.gestionClient.EcranGestionClient;

public class DetailAnimalPanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblClient;
	private JLabel lblCode;
	private JLabel lblNom;
	private JLabel lblSexe;
	private JLabel lblCouleur;
	private JLabel lblEspece;
	private JLabel lblRace;
	private JLabel lblTatouage;

	private JTextField txtClient;
	private JTextField txtCode;
	private JTextField txtNom;
	private JTextField txtSexe;
	private JTextField txtCouleur;
	private JTextField txtEspece;
	private JTextField txtRace;
	private JTextField txtTatouage;

	private Animal initAnimal;
	private Animal currentAnimal;
	private AnimalController animalController;
	
	private JButton buttonSave;

	public DetailAnimalPanel() {
		this(0);
	}

	public DetailAnimalPanel(int id) {
		this.currentAnimal = new Animal();
		this.initAnimal = this.currentAnimal.copy();

		try {
			this.animalController = AnimalController.getInstance();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		if (id == 0) {
			this.writeInputs();
		} else {
			try {
				this.writeInputs(id);
			} catch (AnimalNotFoundException e) {
				e.printStackTrace();
			}
		}

		setLayout(new GridBagLayout());
		addComponentTo(this.getLblClient(), this, 0, 0, 1, 1, 0.1);
		addComponentTo(this.getTxtClient(), this, 1, 0, 1, 1, 0.9);
		addComponentTo(this.getLblCode(), this, 0, 1, 1, 1, 0.1);
		addComponentTo(this.getTxtCode(), this, 1, 1, 1, 1, 0.9);
		addComponentTo(this.getLblNom(), this, 0, 2, 1, 1, 0.1);
		addComponentTo(this.getTxtNom(), this, 1, 2, 1, 1, 0.9);
		addComponentTo(this.getLblSexe(), this, 0, 3, 1, 1, 0.1);
		addComponentTo(this.getTxtSexe(), this, 1, 3, 1, 1, 0.9);
		addComponentTo(this.getLblCouleur(), this, 0, 4, 1, 1, 0.1);
		addComponentTo(this.getTxtCouleur(), this, 1, 4, 1, 1, 0.9);
		addComponentTo(this.getLblEspece(), this, 0, 5, 1, 1, 0.1);
		addComponentTo(this.getTxtEspece(), this, 1, 5, 1, 1, 0.9);
		addComponentTo(this.getLblRace(), this, 0, 6, 1, 1, 0.1);
		addComponentTo(this.getTxtRace(), this, 1, 6, 1, 1, 0.9);
		addComponentTo(this.getLblTatouage(), this, 0, 7, 1, 1, 0.1);
		addComponentTo(this.getTxtTatouage(), this, 1, 7, 1, 1, 0.9);
		
		addComponentTo(this.getButtonSave(), this, 0, 13, 2, 2, 1);

	}
	
	public void writeInputs(int id) throws AnimalNotFoundException {
		this.currentAnimal.setCodeAnimal(id);
		this.animalController.setAnimal(this.currentAnimal, false);
		try {
			this.currentAnimal = this.animalController.getCurrentAnimal();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		this.writeInputs();
	}
		
	public void writeInputs() {
		this.getTxtClient().setText(this.currentAnimal.getClient().getNomClient());
		this.getTxtCode().setText(String.valueOf(this.currentAnimal.getCodeAnimal()));
		this.getTxtNom().setText(this.currentAnimal.getNomAnimal());
		this.getTxtSexe().setText(this.currentAnimal.getSexe());
		this.getTxtCouleur().setText(this.currentAnimal.getCouleur());
		this.getTxtEspece().setText(this.currentAnimal.getRace_espece().getEspece());
		this.getTxtRace().setText(this.currentAnimal.getRace_espece().getRace());
		this.getTxtTatouage().setText(this.currentAnimal.getTatouage());
		this.inputToAnimal();
		this.initAnimal = this.currentAnimal.copy();	
	}
	
	public void inputToAnimal() {
		this.currentAnimal.setNomAnimal(this.getTxtNom().getText());
		this.currentAnimal.setSexe(this.getTxtSexe().getText());
		this.currentAnimal.setCouleur(this.getTxtCouleur().getText());
		
		Race race = new Race();
		race.setEspece((this.getTxtEspece().getText()));
		race.setRace(this.getTxtRace().getText());
		this.currentAnimal.setRace_espece(race);
		this.currentAnimal.setTatouage(this.getTxtTatouage().getText());	
	}
	
	private void quitter() {
		AnimalClientPanel animalClientPanel = ((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel()).getAnimalClientPanel();
		animalClientPanel.reloadView();
		animalClientPanel.getCreationView().setVisible(false);
	}
	
	public boolean isSaved() {
		this.inputToAnimal();
		System.out.println(this.currentAnimal);
		System.out.println(this.initAnimal);
		System.out.println(this.currentAnimal.equals(this.initAnimal));
		System.out.println((this.initAnimal.getCodeAnimal() == 0 && this.currentAnimal.getCodeAnimal() > 0));
		
		return this.currentAnimal.equals(this.initAnimal) || (this.initAnimal.getCodeAnimal() == 0 && this.currentAnimal.getCodeAnimal() > 0);
	}
	
	public void resetDialog() {
		this.currentAnimal = new Animal();
		this.initAnimal = this.currentAnimal.copy();
		this.writeInputs();
	}
	
	public JButton getButtonSave() {
		if (this.buttonSave == null) {
			this.buttonSave = new JButton("Enregistrer");
			this.buttonSave.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(currentAnimal);
					inputToAnimal();
					
					if (currentAnimal.getCodeAnimal() > 0) {
						try {
							animalController.setAnimal(currentAnimal);
							animalController.updateAnimal();
							((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel()).getAnimalClientPanel().reloadView();
							JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout de l'animal\n" + currentAnimal.toString() + " réussie");
						} catch (BLLException e1) {
							e1.printStackTrace();
							AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
						} catch (AnimalNotFoundException e1) {
							e1.printStackTrace();
							AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
						}
					}
					System.out.println(currentAnimal);
				}
			});
		}
		return buttonSave;
	}
	

	public JLabel getLblClient() {
		if (lblClient == null) {
			lblClient = new JLabel("Client");
		}
		return lblClient;
	}

	public JLabel getLblCode() {
		if (lblCode == null) {
			lblCode = new JLabel("Code");
		}
		return lblCode;
	}

	public JLabel getLblNom() {
		if (lblNom == null) {
			lblNom = new JLabel("Nom");
		}
		return lblNom;
	}

	public JLabel getLblSexe() {
		if (lblSexe == null) {
			lblSexe = new JLabel("Sexe");
		}
		return lblSexe;
	}

	public JLabel getLblCouleur() {
		if (lblCouleur == null) {
			lblCouleur = new JLabel("Couleur");
		}
		return lblCouleur;
	}

	public JLabel getLblEspece() {
		if (lblEspece == null) {
			lblEspece = new JLabel("Espece");
		}
		return lblEspece;
	}

	public JLabel getLblRace() {
		if (lblRace == null) {
			lblRace = new JLabel("Race");
		}
		return lblRace;
	}

	public JLabel getLblTatouage() {
		if (lblTatouage == null) {
			lblTatouage = new JLabel("Tatouage");
		}
		return lblTatouage;
	}

	public JTextField getTxtClient() {
		if (txtClient == null) {
			txtClient = new JTextField();
		}
		return txtClient;
	}

	public JTextField getTxtCode() {
		if (txtCode == null) {
			txtCode = new JTextField();
			txtCode.setEnabled(false);
		}
		return txtCode;
	}

	public JTextField getTxtNom() {
		if (txtNom == null) {
			txtNom = new JTextField();
		}
		return txtNom;
	}

	public JTextField getTxtSexe() {
		if (txtSexe == null) {
			txtSexe = new JTextField();
		}
		return txtSexe;
	}

	public JTextField getTxtCouleur() {
		if (txtCouleur == null) {
			txtCouleur = new JTextField();
		}
		return txtCouleur;
	}

	public JTextField getTxtEspece() {
		if (txtEspece == null) {
			txtEspece = new JTextField();
		}
		return txtEspece;
	}

	public JTextField getTxtRace() {
		if (txtRace == null) {
			txtRace = new JTextField();
		}
		return txtRace;
	}

	public JTextField getTxtTatouage() {
		if (txtTatouage == null) {
			txtTatouage = new JTextField();
		}
		return txtTatouage;
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




}
