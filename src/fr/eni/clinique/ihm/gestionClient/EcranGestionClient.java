package fr.eni.clinique.ihm.gestionClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
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
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.ihm.AppliTestIHM;
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
	private JPanel buttonForm;
	
	private BoutonMenuClient panelButtons;
	
	private JDialog creationView;
	
	private CreationClientPanel creationClientPanel;
	
	URL iconURL;
	ImageIcon icon;


	public EcranGestionClient() {
		super (new BorderLayout());
		add(getButtonForm(), BorderLayout.NORTH);
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
	
	public Client getClientFromJTable(int row) {
		Client client = this.model.getClient(row);
		return client;
	}
	
	
	public JTable getTableClient() {
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
					
					tableClient.addMouseListener(new MouseListener() {
	                    @Override
	                    public void mouseClicked(MouseEvent e) {
	                        if (e.getClickCount() == 2) {
	                            JTable target = (JTable)e.getSource();
	                            int row = target.getSelectedRow();
	                            Client client = getClientFromJTable(row);
	                            getCreationViewOnUpdate(client.getCodeClient());
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
	
	private JPanel getButtonForm() {
		if (buttonForm == null) {
			buttonForm = new JPanel(new GridBagLayout());
			
			addComponentTo(getPanelButtons(), buttonForm, 0, 0, 2, 1, 1);
		}
		
		return buttonForm;
	}
	
	private BoutonMenuClient getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new BoutonMenuClient(this);
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
				if (!creationClientPanel.isSaved()) {
					int reply = JOptionPane.showConfirmDialog(getCreationClientPanel(), "Toutes modifications non enregistrées seront perdues !\nVoulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						getCreationClientPanel().resetDialog();
						creationView.setVisible(false);
						reloadView();
					}
				} else {
					getCreationClientPanel().resetDialog();
					creationView.setVisible(false);
					reloadView();
				}
				getTableClient().clearSelection();
			}
		});
		
		this.creationView.setContentPane(this.getCreationClientPanel());
		this.creationView.setVisible(true);
		this.creationView.setSize(400, 200);
		this.creationView.setLocation(600, 250);
		
		this.creationView.setIconImage(icon.getImage());
		this.creationView.setResizable(false);
		this.creationView.setTitle("Création Client");
		this.getCreationClientPanel().resetDialog();
		
		return creationView;
	}
	
	public JDialog getCreationViewOnUpdate(int id) {
		this.getCreationView();
		this.creationView.setTitle("Mise à jour du client");
		this.getCreationClientPanel().writeInputs(id);
		
		return creationView;
	}
	
	
	public CreationClientPanel getCreationClientPanel() {
		if(this.creationClientPanel == null) {
			this.creationClientPanel = new CreationClientPanel();
		}
		
		return creationClientPanel;
	}
	
	
	public void onChanged(Object value) {
		//int index = model.getIndexOf((Client)value);
		//tableClient.setRowSelectionInterval(index, index);
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
