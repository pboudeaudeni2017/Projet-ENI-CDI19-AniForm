package fr.eni.clinique.ihm.gestionClient;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.gestionPersonnel.EcranGestionPersonnel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationClientPanel extends JPanel {

    private JLabel labelNom;
    private JLabel labelPrenom;
    private JLabel labelAdresse;
    private JLabel labelAdresse2;
    private JLabel labelCodePostal;
    private JLabel labelVille;
    private JLabel labelNumTel;
    private JLabel labelAssurance;
    private JLabel labelEmail;
    private JLabel labelRemarque;
    private JLabel labelArchive;

    private JTextField textNom;
    private JTextField textPrenom;
    private JTextField textAdresse;
    private JTextField textAdresse2;
    private JTextField textCodePostal;
    private JTextField textVille;
    private JTextField textNumTel;
    private JTextField textAssurance;
    private JTextField textEmail;
    private JTextField textRemarque;
    private JCheckBox checkBoxArchive;

    private Client initClient;
    private Client currentClient;
    private ClientController clientController;

    private JButton buttonSave;

    public CreationClientPanel(){
        this(0);
    }

    public CreationClientPanel(int id) {
        try {
            this.clientController = ClientController.getInstance();
        } catch (BLLException e) {
            e.printStackTrace();
        }
        this.currentClient = new Client();
        this.initClient = this.currentClient.copy();

        setLayout(new GridBagLayout());
        addComponentTo(this.getLabelNom(), this, 0, 0, 1, 1, 0.1);
        addComponentTo(this.getTextNom(), this, 1, 0, 1, 1, 0.9);
        addComponentTo(this.getLabelPrenom(), this, 0, 1, 1, 1, 0.1);
        addComponentTo(this.getTextPrenom(), this, 1, 1, 1, 1, 0.9);
        addComponentTo(this.getLabelAdresse(), this, 0, 2, 1, 1, 0.1);
        addComponentTo(this.getTextAdresse(), this, 1, 2, 1, 1, 0.9);
        addComponentTo(this.getLabelAdresse2(), this, 0, 3, 1, 1, 0.1);
        addComponentTo(this.getTextAdresse2(), this, 1, 3, 1, 1, 0.9);
        addComponentTo(this.getLabelCodePostal(), this, 0, 4, 1, 1, 0.1);
        addComponentTo(this.getTextCodePostal(), this, 1, 4, 1, 1, 0.9);
        addComponentTo(this.getLabelVille(), this, 0, 5, 1, 1, 0.1);
        addComponentTo(this.getTextVille(), this, 1, 5, 1, 1, 0.9);
        addComponentTo(this.getLabelNumTel(), this, 0, 6, 1, 1, 0.1);
        addComponentTo(this.getTextNumTel(), this, 1, 6, 1, 1, 0.9);
        addComponentTo(this.getLabelAssurance(), this, 0, 7, 1, 1, 0.1);
        addComponentTo(this.getTextAssurance(), this, 1, 7, 1, 1, 0.9);
        addComponentTo(this.getLabelEmail(), this, 0, 8, 1, 1, 0.1);
        addComponentTo(this.getTextEmail(), this, 1, 8, 1, 1, 0.9);
        addComponentTo(this.getLabelRemarque(), this, 0, 9, 1, 1, 0.1);
        addComponentTo(this.getTextRemarque(), this, 1, 9, 1, 1, 0.9);
        addComponentTo(this.getLabelArchive(), this, 0, 10, 1, 1, 0.1);
        addComponentTo(this.getCheckBoxArchive(), this, 1, 10, 1, 1, 0.9);

        addComponentTo(this.getLabelArchive(), this, 0, 13, 1, 1, 0.1);
        addComponentTo(this.getCheckBoxArchive(), this, 1, 13, 1, 1, 0.9);
        addComponentTo(this.getButtonSave(), this, 0, 16, 2, 1, 1);
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
        this.getTextNom().setText(this.currentClient.getNomClient());
        this.getTextPrenom().setText(this.currentClient.getPrenomClient());
        this.getTextAdresse().setText(this.currentClient.getAdresse1());
        this.getTextAdresse2().setText(this.currentClient.getAdresse2());
        this.getTextCodePostal().setText(this.currentClient.getCodePostal());
        this.getTextVille().setText(this.currentClient.getVille());
        this.getTextNumTel().setText(this.currentClient.getNumTel());
        this.getTextAssurance().setText(this.currentClient.getAssurance());
        this.getTextEmail().setText(this.currentClient.getEmail());
        this.getTextRemarque().setText(this.currentClient.getRemarque());
        this.getCheckBoxArchive().setSelected(this.currentClient.isArchive());
    }

    public JLabel getLabelNom() {
        if(this.labelNom == null){
            this.labelNom = new JLabel("Nom : ");
        }
        return labelNom;
    }

    public JLabel getLabelPrenom() {
        if(this.labelPrenom == null){
            this.labelPrenom = new JLabel("Prénom");
        }
        return labelPrenom;
    }

    public JLabel getLabelAdresse() {
        if(this.labelAdresse == null){
            this.labelAdresse = new JLabel("Adresse");
        }
        return labelAdresse;
    }

    public JLabel getLabelAdresse2() {
        if(this.labelAdresse2 == null){
            this.labelAdresse2 = new JLabel("Adresse 2");
        }
        return labelAdresse2;
    }

    public JLabel getLabelCodePostal() {
        if(this.labelCodePostal == null){
            this.labelCodePostal = new JLabel("Code postal");
        }
        return labelCodePostal;
    }

    public JLabel getLabelVille() {
        if(this.labelVille == null){
            this.labelVille = new JLabel("Ville");
        }
        return labelVille;
    }

    public JLabel getLabelNumTel() {
        if(this.labelNumTel == null){
            this.labelNumTel = new JLabel("Numéro Téléphone");
        }
        return labelNumTel;
    }

    public JLabel getLabelAssurance() {
        if(this.labelAssurance == null){
            this.labelAssurance = new JLabel("Assurance");
        }
        return labelAssurance;
    }

    public JLabel getLabelEmail() {
        if(this.labelEmail == null){
            this.labelEmail = new JLabel("Email");
        }
        return labelEmail;
    }

    public JLabel getLabelRemarque() {
        if(this.labelRemarque == null){
            this.labelRemarque = new JLabel("Remarque");
        }
        return labelRemarque;
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

    public JTextField getTextPrenom() {
        if(this.textPrenom == null){
            this.textPrenom = new JTextField();
        }
        return textPrenom;
    }

    public JTextField getTextAdresse() {
        if(this.textAdresse == null){
            this.textAdresse = new JTextField();
        }
        return textAdresse;
    }

    public JTextField getTextAdresse2() {
        if(this.textAdresse2 == null){
            this.textAdresse2 = new JTextField();
        }
        return textAdresse2;
    }

    public JTextField getTextCodePostal() {
        if(this.textCodePostal == null){
            this.textCodePostal = new JTextField();
        }
        return textCodePostal;
    }

    public JTextField getTextVille() {
        if(this.textVille == null){
            this.textVille = new JTextField();
        }
        return textVille;
    }

    public JTextField getTextNumTel() {
        if(this.textNumTel == null){
            this.textNumTel = new JTextField();
        }
        return textNumTel;
    }

    public JTextField getTextAssurance() {
        if(this.textAssurance == null){
            this.textAssurance = new JTextField();
        }
        return textAssurance;
    }

    public JTextField getTextEmail() {
        if(this.textEmail == null){
            this.textEmail = new JTextField();
        }
        return textEmail;
    }

    public JTextField getTextRemarque() {
        if(this.textRemarque == null){
            this.textRemarque = new JTextField();
        }
        return textRemarque;
    }

    public JCheckBox getCheckBoxArchive() {
        if(this.checkBoxArchive == null){
            this.checkBoxArchive = new JCheckBox();
        }
        return checkBoxArchive;
    }

    private void inputToPerso(){
        this.currentClient.setNomClient(this.getTextNom().getText());
        this.currentClient.setPrenomClient(this.getTextPrenom().getText());
        this.currentClient.setAdresse1(this.getTextAdresse().getText());
        this.currentClient.setAdresse2(this.getTextAdresse2().getText());
        this.currentClient.setCodePostal(this.getTextCodePostal().getText());
        this.currentClient.setNumTel(this.getTextNumTel().getText());
        this.currentClient.setAssurance(this.getTextAssurance().getText());
        this.currentClient.setEmail(this.getTextEmail().getText());
        this.currentClient.setRemarque(this.getTextRemarque().getText());
        this.currentClient.setArchive(this.getCheckBoxArchive().isSelected());
    }

    private void quitter(){
        EcranGestionPersonnel ecranGestionPersonnel =  ((EcranGestionPersonnel)AppliTestIHM.mainFrame.getCurrentPanel());
        ecranGestionPersonnel.reloadView();
        ecranGestionPersonnel.getCreationView().setVisible(false);
    }

    public boolean isSaved(){
        this.inputToPerso();
        System.out.println(this.currentClient);
        System.out.println(this.initClient);
        return this.currentClient.equals(this.initClient) || (this.initClient.getCodeClient() == 0 && this.currentClient.getCodeClient() > 0) ;
    }

    public void resetDialog(){
        this.currentClient = new Client();
        this.writeInputs(this.currentClient.getCodeClient());
    }

    public JButton getButtonSave() {
        if(this.buttonSave == null){
            this.buttonSave = new JButton("Enregistrer");
            this.buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(currentClient);
                    inputToPerso();
                    if(currentClient.getCodeClient() > 0){
                        try {
                            clientController.setClient(currentClient);
                            clientController.updateClient();
                            ((EcranGestionPersonnel)AppliTestIHM.mainFrame.getCurrentPanel()).reloadView();
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Modifications enregistrées");
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
                        } catch (ClientNotFoundException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
                        }
                    }
                    else{
                        try {
                            clientController.addClient(currentClient);
                            ((EcranGestionClient)AppliTestIHM.mainFrame.getCurrentPanel()).reloadView();
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout du personnel\n" + currentClient.toString() + " réussite");
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
                        } catch (ClientNotFoundException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
                        }
                    }
                    System.out.println(currentClient);
                }
            });
        }
        return buttonSave;
    }
}
