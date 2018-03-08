package fr.eni.clinique.ihm.gestionAnimaux;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.MainFrame;
import fr.eni.clinique.ihm.gestionClient.*;
import fr.eni.clinique.ihm.gestionAnimaux.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class AnimalClientPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DetailClientPanel detailClientPanel;
	private JPanel panelScrollTable;
	private JTable tableAnimal;
	private JScrollPane scrollPane;
	private AnimalTableModel model;

	private BoutonMenuAnimal panelButtons;

	private DetailAnimalPanel creationAnimalPanel;

	private JDialog creationView;

	public AnimalClientPanel() {
		super(new GridBagLayout());
		addComponentTo(this.getDetailClientPanel(), this, 0, 0, 1, 1, 0.8);
		addComponentTo(this.getPanelScrollTable(), this, 1, 0, 1, 1, 0.2);
	}

	public DetailClientPanel getDetailClientPanel() {
		if(this.detailClientPanel == null){
			this.detailClientPanel = ((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel()).getDetailClientPanelAnimal();
			this.detailClientPanel.resetDialog();
		}
		return detailClientPanel;
	}

	private JPanel getPanelScrollTable() {
		if(this.panelScrollTable == null) {
			this.panelScrollTable = new JPanel(new BorderLayout());

			this.panelScrollTable.add(this.getScrollPane(), BorderLayout.NORTH);
			this.panelScrollTable.add(this.getPanelButtons(), BorderLayout.SOUTH);
		}
		return this.panelScrollTable;
	}

	private JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTableAnimal());
			scrollPane.setAutoscrolls(true);
		}
		return scrollPane;
	}


	public DetailAnimalPanel getCreationAnimalPanel() {
		if (this.creationAnimalPanel == null) {
			this.creationAnimalPanel = new DetailAnimalPanel();
		}

		return creationAnimalPanel;
	}


	public BoutonMenuAnimal getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new BoutonMenuAnimal(this);
		}
		return panelButtons;
	}

	public void stateVisible() {
		getTableAnimal().setVisible(true);
	}

	public Animal getAnimalFromJTable(int row){
		Animal animal = this.model.getAnimal(row);
		return animal;
	}

	public JTable getTableAnimal() {
		if(tableAnimal == null) {
			try {
				model = new AnimalTableModel();
				tableAnimal = new JTable(model);
				tableAnimal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableAnimal.setRowHeight(30);
				tableAnimal.setPreferredScrollableViewportSize(new Dimension(825, 320));
				tableAnimal.setFillsViewportHeight(true);
				tableAnimal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tableAnimal.getSelectedRow();
						if(index >= 0 && tableAnimal.getRowCount() < index) {
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
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							JTable target = (JTable)e.getSource();
							int row = target.getSelectedRow();
							Animal animal = getAnimalFromJTable(row);
							try {
								getCreationViewOnUpdate(animal.getCodeAnimal());
							} catch (AnimalNotFoundException e1) {
								e1.printStackTrace();
							}
							e.consume();
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {

					}

					@Override
					public void mouseReleased(MouseEvent e) {

					}

					@Override
					public void mouseEntered(MouseEvent e) {

					}

					@Override
					public void mouseExited(MouseEvent e) {

					}
				});
			} catch (BLLException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		return tableAnimal;
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
						reloadView();					}
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
		this.creationView.setSize(400, 300);
		this.creationView.setLocation(600, 250);


		URL iconURL;
		ImageIcon icon;

		iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
		icon = new ImageIcon(iconURL);

		this.creationView.setIconImage(icon.getImage());
		this.creationView.setResizable(true);
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

	public void reloadView() {
		try {
			this.model.updateData();
		} catch (BLLException e) {
			e.printStackTrace();
			AppliTestIHM.showError("Erreur rechargement des données", "Erreur de mise à jour des données:\n" + e.getMessage());
		}
	}	


	public void resetDialog(int id){
		this.getDetailClientPanel().resetDialog();
		try {
			this.getDetailClientPanel().writeInputs(id);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}

		this.getDetailClientPanel().setVisible(true);
		this.reloadView();

	}

	private void addComponentTo(JComponent component, JPanel panel, int x, int y, int width, int height, double weightX) {
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
