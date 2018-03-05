package fr.eni.clinique.ihm.gestionPersonnel;


import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.MainFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EcranGestionPersonnel extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tablePersonnel;
	private PersonnelTableModel model;

	private JScrollPane scrollPane;
	private BoutonMenuPersonnel panelButtons;

	private JPanel buttonForm;

	private JDialog creationView;

	private CreationPersonnelPanel creationPersonnelPanel;

	URL iconURL;
	ImageIcon icon;


	public EcranGestionPersonnel() {
		super(new GridBagLayout());
		addComponentTo(getButtonForm(), this, 0, 0, 1, 1, 1);
		addComponentTo(getScrollPane(), this, 0, 1, 1, 1, 1);
        this.iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
        this.icon = new ImageIcon(iconURL);
		try {
			PersonnelController.getInstance().registerToCurrentPersonnel(this);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTablePersonnel());
			
		}
		return scrollPane;
	}
	
	
	public void stateVisible() {
		getTablePersonnel().setVisible(true);
		getButtonForm().setVisible(true);
	}

	private JTable getTablePersonnel() {
		if(tablePersonnel == null) {
			try {
				model = new PersonnelTableModel();
				tablePersonnel = new JTable(model);
				tablePersonnel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tablePersonnel.setRowHeight(30);
				tablePersonnel.setPreferredScrollableViewportSize(tablePersonnel.getPreferredSize());
				tablePersonnel.setFillsViewportHeight(true);
				tablePersonnel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tablePersonnel.getSelectedRow();
						try {
							PersonnelController.getInstance().setPersonnel(index);
						} catch (PersonnelNotFoundException e) {
							e.printStackTrace();
						} catch (BLLException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (BLLException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		return tablePersonnel;
	}

	private JPanel getButtonForm() {
		if (buttonForm == null) {
			buttonForm = new JPanel(new GridBagLayout());

			addComponentTo(getPanelButtons(), buttonForm, 0, 0, 2, 1, 1);
		}

		return buttonForm;
	}


	private BoutonMenuPersonnel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new BoutonMenuPersonnel(this);
		}

		return panelButtons;

	}

    public JDialog getCreationView() {
        if(this.creationView == null){
            this.creationView = new JDialog();
            this.creationView.setContentPane(this.getCreationPersonnelPanel());
            this.creationView.setVisible(true);
            this.creationView.setSize(400, 200);
            this.creationView.setLocation(600, 250);

            this.creationView.setIconImage(icon.getImage());
            this.creationView.setResizable(false);
            this.creationView.setTitle("Création personnel");
        }
        return creationView;
    }

    public CreationPersonnelPanel getCreationPersonnelPanel() {
	    if(this.creationPersonnelPanel == null){
	        this.creationPersonnelPanel = new CreationPersonnelPanel();
        }
        return creationPersonnelPanel;
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
		int index = model.getIndexOf((Personnel)value);
		tablePersonnel.setRowSelectionInterval(index, index);
	}


	public void onCreate() {

	}
}
