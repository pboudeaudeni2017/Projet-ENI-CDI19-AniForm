package fr.eni.clinique.ihm.gestionAnimaux;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.gestionClient.*;
import fr.eni.clinique.ihm.gestionAnimaux.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnimalClientPanel extends JPanel{
    private DetailClientPanel detailClientPanel;
    private JPanel panelScrollTable;
    private JTable tableAnimal;
    private JScrollPane scrollPane;
    private AnimalTableModel model;
    
    private BoutonMenuAnimal panelButtons;
    
    private EcranGestionAnimal ecranGestionAnimal;

    public AnimalClientPanel() {
        super(new GridBagLayout());
        addComponentTo(this.getDetailClientPanel(), this, 0, 0, 1, 1, 0.8);
        addComponentTo(this.getPanelScrollTable(), this, 1, 0, 1, 1, 0.2);
        //addComponentTo(this.getPanelButtons(), this, 1, 1, 1, 1, 1);
        /*add(this.getDetailClientPanel(), BorderLayout.NORTH);
        add(this.getPanelScrollTable(), BorderLayout.SOUTH);*/
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
            //this.panelScrollTable.add(this.getEcranGestionAnimal());
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
    
    
  

    public BoutonMenuAnimal getPanelButtons() {
    	if (panelButtons == null) {
    		panelButtons = new BoutonMenuAnimal(this);
    	}
		return panelButtons;
	}

	public void stateVisible() {
        getTableClient().setVisible(true);
    }

    public Animal getAnimalFromJTable(int row){
        Animal animal = this.model.getAnimal(row);
        return animal;
    }

    public JTable getTableClient() {
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
                        if(index >= 0) {
                            /*try {
                                ClientController.getInstance().setClient(index);
                            } catch (ClientNotFoundException e) {
                                e.printStackTrace();
                            } catch (BLLException e) {
                                e.printStackTrace();
                            }*/
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
                            //getCreationViewOnUpdate(client.getCodeClient());
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

    public void resetDialog(int id){
        this.getDetailClientPanel().resetDialog();
        try {
            this.getDetailClientPanel().writeInputs(id);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        this.getDetailClientPanel().setVisible(true);
        try {
            this.model.updateData();
        } catch (BLLException e) {
            e.printStackTrace();
        }

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
    
    private EcranGestionAnimal getEcranGestionAnimal() {
    	if (ecranGestionAnimal == null) {
    		ecranGestionAnimal = new EcranGestionAnimal();
    	}
    	
    	return ecranGestionAnimal;
    }

}
