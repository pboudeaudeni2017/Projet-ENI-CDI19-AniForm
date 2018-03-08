package fr.eni.clinique.ihm.gestionAnimaux;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Race;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.gestionClient.ClientController;
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

	private JLabel txtClient;
	private JLabel txtCode;
	private JTextField txtNom;
	private JTextField txtSexe;
	private JTextField txtCouleur;
	private JComboBox txtEspece;
	private JTextField txtRace;
	private JTextField txtTatouage;

	private JComboBox comboBoxSexe;
	private static final String[] Sexe = {"Male", "Femelle"}; 

	private Animal initAnimal;
	private Animal currentAnimal;
	private AnimalController animalController;
	private ClientController clientController;
	private List<String> listeEspece;

	private JButton buttonSave;

	public DetailAnimalPanel() {
		this(0);
	}

	public DetailAnimalPanel(int id) {
		this.currentAnimal = new Animal();
		this.initAnimal = this.currentAnimal.copy();

		try {
			this.clientController = ClientController.getInstance();
			this.animalController = AnimalController.getInstance();
			this.listeEspece = this.animalController.getEspeces();
			System.out.println(this.listeEspece);
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
		addComponentTo(this.getTxtClient(), this, 1, 0, 3, 1, 0.9);
		addComponentTo(this.getLblCode(), this, 0, 1, 1, 1, 0.1);
		addComponentTo(this.getTxtCode(), this, 1, 1, 1, 1, 0.9);
		addComponentTo(this.getLblNom(), this, 0, 2, 1, 1, 0.1);
		addComponentTo(this.getTxtNom(), this, 1, 2, 3, 1, 0.5);
		addComponentTo(this.getComboBoxSexe(), this, 4, 2, 1, 1, 0.5);
		addComponentTo(this.getLblCouleur(), this, 0, 4, 1, 1, 0.1);
		addComponentTo(this.getTxtCouleur(), this, 1, 4, 4, 1, 0.9);
		addComponentTo(this.getLblEspece(), this, 0, 5, 1, 1, 0.1);
		addComponentTo(this.getTxtEspece(), this, 1, 5, 1, 1, 0.4);
		addComponentTo(new JLabel(), this, 2, 5, 1, 1, 0);
		addComponentTo(this.getLblRace(), this, 3, 5, 1, 1, 0.1);
		addComponentTo(this.getTxtRace(), this, 4, 5, 1, 1, 0.4);
		addComponentTo(this.getLblTatouage(), this, 0, 7, 1, 1, 0.1);
		addComponentTo(this.getTxtTatouage(), this, 1, 7, 4, 1, 0.9);

		addComponentTo(this.getButtonSave(), this, 2, 13, 2, 2, 1);

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
		if(this.currentAnimal.getCodeAnimal() < 1) {
			this.getTxtCode().setVisible(false);
			this.getLblCode().setVisible(false);
		}
		else {
			this.getTxtCode().setVisible(true);
			this.getLblCode().setVisible(true);
		}
		
		try {
			Client currentClient = this.clientController.getCurrentClient();
			this.getTxtClient().setText(currentClient.getNomClient() + " " + currentClient.getPrenomClient());
		} catch(BLLException e) {
			e.printStackTrace();
			this.getTxtClient().setText("");
		}
		
		
		this.getTxtCode().setText(String.valueOf(this.currentAnimal.getCodeAnimal()));
		this.getTxtNom().setText(this.currentAnimal.getNomAnimal());
		int selectedSexe = 0;
		switch (this.currentAnimal.getSexe()) {
		case "M":
			selectedSexe = 0;
			break;
		case "F":
			selectedSexe = 1;
			break;
		}
		this.getComboBoxSexe().setSelectedIndex(selectedSexe);
		this.getTxtCouleur().setText(this.currentAnimal.getCouleur());
		int indexEspece = this.indexOfEspeceInListe(this.currentAnimal.getRace_espece().getEspece());
		System.out.println("indexEspece: " + indexEspece + " : " + this.currentAnimal.getRace_espece().getEspece());
		if(indexEspece < this.listeEspece.toArray().length){
		    this.getTxtEspece().setSelectedIndex(indexEspece);
        }

		this.getTxtRace().setText(this.currentAnimal.getRace_espece().getRace());
		this.getTxtTatouage().setText(this.currentAnimal.getTatouage());
		this.inputToAnimal();
		this.initAnimal = this.currentAnimal.copy();	
	}

	private int indexOfEspeceInListe(String _espece){
	    int i = 0;
	    for(String espece: this.listeEspece){
            if(_espece.equals(espece)){
                break;
            }
            i++;
        }

        return i;
    }

	public void inputToAnimal() {
		this.currentAnimal.setNomAnimal(this.getTxtNom().getText());
		
		switch (Sexe[this.getComboBoxSexe().getSelectedIndex()]) {
		case "Male":
			this.currentAnimal.setSexe("M");
			break;
		case "Femelle":
			this.currentAnimal.setSexe("F");
			break;
		}
		
		this.currentAnimal.setCouleur(this.getTxtCouleur().getText());
		try {
			Client client = this.clientController.getCurrentClient();
			this.currentAnimal.setClient(client);
		} catch (BLLException e) {
			e.printStackTrace();
		}
		Race race = new Race();
		if(this.getTxtEspece().getSelectedIndex() >= 0){
            race.setEspece((this.listeEspece.get(this.getTxtEspece().getSelectedIndex())));
        }
        else{
		    race.setEspece("");
        }
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
							initAnimal = currentAnimal.copy();
							JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Mise à jour de l'animal " + currentAnimal.getNomAnimal() + " réussie");
						} catch (BLLException e1) {
							e1.printStackTrace();
							AppliTestIHM.showError("Erreur de mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
						} catch (AnimalNotFoundException e1) {
							e1.printStackTrace();
							AppliTestIHM.showError("Erreur de mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
						}
					} else {
						try {
							animalController.addAnimal(currentAnimal);
							((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel()).reloadView();
							initAnimal = currentAnimal.copy();
							JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout de l'animal réussie");
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
			lblRace = new JLabel("Race", SwingConstants.RIGHT);
		}
		return lblRace;
	}

	public JLabel getLblTatouage() {
		if (lblTatouage == null) {
			lblTatouage = new JLabel("Tatouage");
		}
		return lblTatouage;
	}

	public JLabel getTxtClient() {
		if (txtClient == null) {
			txtClient = new JLabel();
		}
		return txtClient;
	}

	public JLabel getTxtCode() {
		if (txtCode == null) {
			txtCode = new JLabel();
		}
		return txtCode;
	}

	public JTextField getTxtNom() {
		if (txtNom == null) {
			txtNom = new JTextField(SwingConstants.RIGHT);
		}
		return txtNom;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox getComboBoxSexe() {
		if (this.comboBoxSexe == null) {
			comboBoxSexe = new JComboBox(Sexe);
		}

		return comboBoxSexe;
	}

	public JTextField getTxtCouleur() {
		if (txtCouleur == null) {
			txtCouleur = new JTextField();
		}
		return txtCouleur;
	}

	public JComboBox getTxtEspece() {
		if (txtEspece == null) {
			txtEspece = new JComboBox(this.listeEspece.toArray());
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
