package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.ihm.MainFrame;
import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.bo.Personnel;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EcranGestionPersonnel extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tablePersonnel;
	private PersonnelTableModel model;

	private JScrollPane scrollPane;

	URL iconURL;
	ImageIcon icon;

	public EcranGestionPersonnel() {
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(530, 150);
		setContentPane(getScrollPane());

		this.iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
		this.icon = new ImageIcon(iconURL);

		setIconImage(icon.getImage());
		setTitle("Gestion du personnel");
		setResizable(false);

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


	private JTable getTablePersonnel() {
		if(tablePersonnel == null) {
			try {
				model = new PersonnelTableModel();
				tablePersonnel = new JTable(model);
				tablePersonnel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tablePersonnel.setRowHeight(30);
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


	@Override
	public void onChanged(Object value) {
		int index = model.getIndexOf((Personnel)value);
		tablePersonnel.setRowSelectionInterval(index, index);


	}
}
