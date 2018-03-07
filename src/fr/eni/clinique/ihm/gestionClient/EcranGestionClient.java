package fr.eni.clinique.ihm.gestionClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Observable.Observer;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.MainFrame;
import fr.eni.clinique.ihm.gestionAnimaux.AnimalClientPanel;


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
	
	private DetailClientPanel detailClientPanel;

	private AnimalClientPanel animalClientPanel;
	private JFrame clientAnimals;
	
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

	public Client getClientFromJTable(int row){
		Client client = this.model.getClient(row);
		return client;
	}

	public DetailClientPanel getDetailClientPanel() {
		if(this.detailClientPanel == null){
			this.detailClientPanel = new DetailClientPanel();
		}
		return detailClientPanel;
	}

	public JTable getTableClient() {
		if(tableClient == null) {
			try {
				model = new ClientTableModel();
				tableClient = new JTable(model);
				tableClient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableClient.setRowHeight(30);
				tableClient.setPreferredScrollableViewportSize(new Dimension(825, 320));
				tableClient.setFillsViewportHeight(true);
				tableClient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int index = tableClient.getSelectedRow();
						if(index >= 0) {
                            try {
                                ClientController.getInstance().setClient(index);
                            } catch (ClientNotFoundException e) {
                                e.printStackTrace();
                            } catch (BLLException e) {
                                e.printStackTrace();
                            }
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
                            //getCreationViewOnUpdate(client.getCodeClient());
                            getClientAnimals(client.getCodeClient());
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

	public JDialog getCreationView() {
		this.creationView = new JDialog();
		this.creationView.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.creationView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				reloadView();
				getDetailClientPanel().inputToPerso();
				if(!getDetailClientPanel().isSaved()) {
					int reply = JOptionPane.showConfirmDialog(getDetailClientPanel(), "Toutes modifications non enregistrées seront perdues !\nVoulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						getDetailClientPanel().resetDialog();
						creationView.setVisible(false);
						reloadView();
					}
				}
				else{
					getDetailClientPanel().resetDialog();
					creationView.setVisible(false);
					reloadView();
				}
				getTableClient().clearSelection();
			}
		});
		this.creationView.setContentPane(this.getDetailClientPanel());
		this.creationView.setVisible(true);
		this.creationView.setSize(400, 450);
		this.creationView.setLocation(600, 250);

		this.creationView.setIconImage(icon.getImage());
		this.creationView.setResizable(false);
		this.creationView.setTitle("Création personnel");
		this.getDetailClientPanel().resetDialog();
		return creationView;
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

	public AnimalClientPanel getAnimalClientPanel() {
		if(this.animalClientPanel == null){
			this.animalClientPanel = new AnimalClientPanel();
		}
		return animalClientPanel;
	}

	public JFrame getClientAnimals(int id) {
	    if(this.clientAnimals == null){
	        this.clientAnimals = new JFrame();
            this.clientAnimals.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.clientAnimals.setSize(1200, 475);
            this.clientAnimals.setLocation(300, 250);

            this.clientAnimals.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    reloadView();
                    getAnimalClientPanel().getDetailClientPanel().inputToPerso();
                    if(!getAnimalClientPanel().getDetailClientPanel().isSaved()) {
                        int reply = JOptionPane.showConfirmDialog(getDetailClientPanel(), "Toutes modifications non enregistrées seront perdues !\nVoulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (reply == JOptionPane.YES_OPTION) {
                            getDetailClientPanel().resetDialog();
                            clientAnimals.setVisible(false);
                            reloadView();
                        }
                    }
                    else{
                        getDetailClientPanel().resetDialog();
                        clientAnimals.setVisible(false);
                        reloadView();
                    }
                    getTableClient().clearSelection();
                }
            });

            this.clientAnimals.setContentPane(this.getAnimalClientPanel());

            this.clientAnimals.setIconImage(icon.getImage());
            this.clientAnimals.setResizable(true);
            this.clientAnimals.setTitle("Animaux du client");
        }
        this.clientAnimals.setVisible(true);
        this.getAnimalClientPanel().resetDialog(id);
        return clientAnimals;
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
	
			
	public JDialog getCreationViewOnUpdate(int id) {
		this.getCreationView();
		this.creationView.setTitle("Mise à jour du client");
		try {
			this.getDetailClientPanel().writeInputs(id);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}

		return creationView;
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
