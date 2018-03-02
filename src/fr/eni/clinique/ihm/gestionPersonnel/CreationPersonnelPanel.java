package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationPersonnelPanel extends JPanel {

    private JLabel labelNom;
    private JLabel labelMotDePasse;
    private JLabel labelRole;
    private JLabel labelArchive;

    private JTextField textNom;
    private JTextField textMotDePasse;
    private JTextField textRole;
    private JCheckBox checkBoxArchive;

    private Personnel currentPerso;
    private Personnel initPerso;
    private PersoController persoController;

    private JButton buttonSave;

    public CreationPersonnelPanel(){
        this(0);
    }

    public CreationPersonnelPanel(int id) {
        this.persoController = new PersoController();
        this.currentPerso = new Personnel();
        this.initPerso = this.currentPerso.copy();

        setLayout(new GridBagLayout());
        addComponentTo(this.getLabelNom(), this, 0, 0, 1, 1, 0.1);
        addComponentTo(this.getTextNom(), this, 1, 0, 1, 1, 0.9);
        addComponentTo(this.getLabelMotDePasse(), this, 0, 1, 1, 1, 0.1);
        addComponentTo(this.getTextMotDePasse(), this, 1, 1, 1, 1, 0.9);
        addComponentTo(this.getLabelRole(), this, 0, 2, 1, 1, 0.1);
        addComponentTo(this.getTextRole(), this, 1, 2, 1, 1, 0.9);
        addComponentTo(this.getLabelArchive(), this, 0, 3, 1, 1, 0.1);
        addComponentTo(this.getCheckBoxArchive(), this, 1, 3, 1, 1, 0.9);
        addComponentTo(this.getButtonSave(), this, 0, 6, 2, 1, 1);
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

    public void writeInputs(int id){
        this.currentPerso = this.persoController.getPerso(id);
        this.initPerso = this.currentPerso.copy();
        this.getTextNom().setText(this.currentPerso.getNom());
        this.getTextMotDePasse().setText(this.currentPerso.getMotPasse());
        this.getTextRole().setText(this.currentPerso.getRole());
        this.getCheckBoxArchive().setSelected(this.currentPerso.isArchive());
    }

    public JLabel getLabelNom() {
        if(this.labelNom == null){
            this.labelNom = new JLabel("Nom : ");
        }
        return labelNom;
    }

    public JLabel getLabelMotDePasse() {
        if(this.labelMotDePasse == null){
            this.labelMotDePasse = new JLabel("Mot de passe: ");
        }
        return labelMotDePasse;
    }

    public JLabel getLabelRole() {
        if(this.labelRole == null){
            this.labelRole = new JLabel("Rôle : ");
        }
        return labelRole;
    }

    public JLabel getLabelArchive() {
        if(this.labelArchive == null){
            this.labelArchive = new JLabel("Archive : ");
        }
        return labelArchive;
    }

    public JTextField getTextNom() {
        if(this.textNom == null){
            this.textNom = new JTextField();
        }
        return textNom;
    }

    public JTextField getTextMotDePasse() {
        if(this.textMotDePasse == null){
            this.textMotDePasse = new JTextField();
        }
        return textMotDePasse;
    }

    public JTextField getTextRole() {
        if(this.textRole == null){
            this.textRole = new JTextField();
        }
        return textRole;
    }

    public JCheckBox getCheckBoxArchive() {
        if(this.checkBoxArchive == null){
            this.checkBoxArchive = new JCheckBox();
        }
        return checkBoxArchive;
    }

    private void inputToPerso(){
        this.currentPerso.setNom(this.getTextNom().getText());
        this.currentPerso.setMotPasse(this.getTextMotDePasse().getText());
        this.currentPerso.setRole(this.getTextRole().getText());
        this.currentPerso.setArchive(this.getCheckBoxArchive().isSelected());
    }

    private void quitter(){
        EcranGestionPersonnel ecranGestionPersonnel =  ((EcranGestionPersonnel)AppliTestIHM.mainFrame.getCurrentPanel());
        ecranGestionPersonnel.reloadView();
        ecranGestionPersonnel.getCreationView().setVisible(false);
    }

    public boolean isSaved(){
        return this.currentPerso.equals(this.initPerso);
    }

    public void resetDialog(){
        this.currentPerso = new Personnel();
        this.writeInputs(this.currentPerso.getCodePers());
    }

    public JButton getButtonSave() {
        if(this.buttonSave == null){
            this.buttonSave = new JButton("Enregistrer");
            this.buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(currentPerso);
                    inputToPerso();
                    if(currentPerso.getCodePers() > 0){
                        try {
                            persoController.updatePerso(currentPerso);
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout du personnel");
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
                        }
                    }
                    else{
                        try {
                            persoController.addPerso(currentPerso);
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout du personnel\n" + currentPerso.toString());
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
                        }
                    }
                    System.out.println(currentPerso);
                }
            });
        }
        return buttonSave;
    }
}
