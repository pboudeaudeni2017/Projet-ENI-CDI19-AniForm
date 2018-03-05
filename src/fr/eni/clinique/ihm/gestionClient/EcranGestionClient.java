package fr.eni.clinique.ihm.gestionClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.ihm.MainFrame;
import javafx.scene.layout.Border;

public class EcranGestionClient extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tableClient;
	private ClientTableModel model;
		
	private JScrollPane scrollPane;
	
	private JPanel panelScrollTable;
	
	URL iconURL;
	ImageIcon icon;


	public EcranGestionClient() {
		super (new BorderLayout());
        add(getPanelScrollTable(), BorderLayout.CENTER);
		this.iconURL = MainFrame.class.getResource("ressources/ico_veto.png");
        this.icon = new ImageIcon(iconURL);
        
        
		try {
			ClientController.getInstance().registerToCurrentClient(this);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	private JPanel getPanelScrollTable() {
		if(this.panelScrollTable == null) {
			this.panelScrollTable = new JPanel();
			this.panelScrollTable.add(this.getScrollPane());
			//this.setPreferredSize(new Dimension(600, 200));
		}
		return this.panelScrollTable;
	}
		
	
	private JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTableClient());
			scrollPane.setAutoscrolls(true);
			//scrollPane.setViewportView(getTableClient());
		}
		return scrollPane;
	}
	
	public void stateVisible() {
		getTableClient().setVisible(true);
	}
	
	
	
	private JTable getTableClient() {
		if(tableClient == null) {
			try {
				model = new ClientTableModel();
				tableClient = new JTable(model);
				tableClient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableClient.setRowHeight(30);
				tableClient.setPreferredScrollableViewportSize(new Dimension(825, 320));
				tableClient.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tableClient.setFillsViewportHeight(true);
				tableClient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tableClient.getSelectedRow();
						try {
							ClientController.getInstance().setClient(index);
						} catch (ClientNotFoundException e) {
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
		return tableClient;
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
	
	
	
	public void onChanged(Object value) {
		//int index = model.getIndexOf((Client)value);
		//tableClient.setRowSelectionInterval(index, index);
	}
	
	public void onCreate() {
		
	}


}
