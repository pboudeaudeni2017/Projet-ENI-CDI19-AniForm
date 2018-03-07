package fr.eni.clinique.ihm.gestionAnimaux;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.MainFrame;
import javafx.scene.input.MouseEvent;

public class EcranGestionAnimal extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tableAnimal;
	private AnimalTableModel model;

	private JScrollPane scrollPane;
	private BoutonMenuAnimal panelButtons;

	private JPanel buttonForm;
	private JPanel panelListAnimal;

	private JDialog creationView;

	private DetailAnimalPanel creationAnimalPanel;

	URL iconURL;
	ImageIcon icon;


	public EcranGestionAnimal() {
		add(getButtonForm());
		add(getPanelListAnimal());
		this.iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
		this.icon = new ImageIcon(iconURL);

		try {
			AnimalController.getInstance().registerToCurrentAnimal(this);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}


	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTableAnimal(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setAutoscrolls(true);
		}

		return scrollPane;
	}

	public void stateVisible() {
		getTableAnimal().setVisible(true);
		getButtonForm().setVisible(true);
	}

	public Animal getAnimalFromJTable(int row) {
		Animal animal = this.model.getAnimal(row);
		return animal;
	}

	public JPanel getPanelListAnimal() {
		if (this.panelListAnimal == null) {
			this.panelListAnimal = new JPanel();
			this.panelListAnimal.add(this.getScrollPane());
		}

		return panelListAnimal;
	}

	public JTable getTableAnimal() {
		if (tableAnimal == null) {
			try {
				model = new AnimalTableModel();
				tableAnimal = new JTable(model);
				tableAnimal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableAnimal.setRowHeight(30);
				tableAnimal.setPreferredScrollableViewportSize(new Dimension(820, 240));
				tableAnimal.setFillsViewportHeight(true);
				tableAnimal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tableAnimal.getSelectedRow();
						if (index >= 0 && tableAnimal.getRowCount() < index) {
							try {
								AnimalController.getInstance().setAnimal(index);
							} catch (AnimalNotFoundException e) {
								e.printStackTrace();
							} catch (BLLException e ){
								e.printStackTrace();
							}
						}
					}
				});

				tableAnimal.addMouseListener(new MouseListener() {


					public void mouseClicked(MouseEvent e) throws AnimalNotFoundException {
						if (e.getClickCount() == 2) {
							JTable target = (JTable)e.getSource();
							int row =  target.getSelectedRow();
							Animal animal = getAnimalFromJTable(row);
							getCreationViewOnUpdate(animal.getCodeAnimal());
							e.consume();
						}
					}

					@Override
					public void mouseClicked(java.awt.event.MouseEvent arg0) {

					}

					@Override
					public void mouseEntered(java.awt.event.MouseEvent arg0) {

					}

					@Override
					public void mouseExited(java.awt.event.MouseEvent arg0) {

					}

					@Override
					public void mousePressed(java.awt.event.MouseEvent arg0) {

					}

					@Override
					public void mouseReleased(java.awt.event.MouseEvent arg0) {

					}
				});
			} catch (BLLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		return tableAnimal;
	}

	private JPanel getButtonForm() {
		if (buttonForm == null) {
			buttonForm = new JPanel(new GridBagLayout());

			addComponentTo(getPanelButtons(), buttonForm, 0, 0, 2, 1, 1);
		}

		return buttonForm;
	}

	private BoutonMenuAnimal getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new BoutonMenuAnimal(this);
		}

		return panelButtons;
	}

	public JDialog getCreationView() {
		this.creationView = new JDialog();
		this.creationView.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.creationView.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				reloadView();

				if (!creationAnimalPanel.isSaved()) {
					int reply = JOptionPane.showConfirmDialog(getCreationAnimalPanel(), "Toutes modifications non enregistrées seront perdues !\nVoulez-vous vraiment quitter?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						getCreationAnimalPanel().resetDialog();
						creationView.setVisible(false);
						reloadView();
					}
				} else {
					getCreationAnimalPanel().resetDialog();
					creationView.setVisible(false);
					reloadView();
				}

				getTableAnimal().clearSelection();
			}
		});


		this.creationView.setContentPane(this.getCreationAnimalPanel());
		this.creationView.setVisible(true);
		this.creationView.setSize(400, 200);
		this.creationView.setLocation(600, 250);

		this.creationView.setIconImage(icon.getImage());
		this.creationView.setResizable(false);
		this.creationView.setTitle("Création animal");
		this.getCreationAnimalPanel().resetDialog();

		return creationView;
	}


	public JDialog getCreationViewOnUpdate(int id) throws AnimalNotFoundException {
		this.getCreationView();
		this.creationView.setTitle("Mise à jour de l'animal");
		this.getCreationAnimalPanel().writeInputs(id);

		return creationView;
	}


	public DetailAnimalPanel getCreationAnimalPanel() {
		if (this.creationAnimalPanel == null) {
			this.creationAnimalPanel = new DetailAnimalPanel();
		}

		return creationAnimalPanel;
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


	@Override
	public void onChanged(Object value) {

	}

	public void onCreate() {

	}

	public void reloadView() {
		try {
			this.model.updateData();
		} catch (BLLException e) {
			e.printStackTrace();
			AppliTestIHM.showError("Erreur rechargement des données", "Erreur de mise à jour des données:\n" + e.getMessage());
		}
	}

}
