package fr.eni.clinique.ihm.gestionPersonnel;

import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.ihm.AppliTestIHM;
import fr.eni.clinique.ihm.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationPersonnelPanel extends JPanel {

    private JLabel labelNom;
    private JLabel labelMotDePasse;
    private JLabel labelRole;

    private JTextField textNom;
    private JTextField textMotDePasse;
    private JComboBox comboBoxRole;

    private Personnel currentPerso;
    private Personnel initPerso;
    private PersoController persoController;

    private static final String[] ROLES = {Personnel.ADMINISTRATEUR_LABEL, Personnel.VETERINAIRE_LABEL, Personnel.SECRETAIRE_LABEL};

    private JButton buttonSave;

    public CreationPersonnelPanel() {
        this.persoController = new PersoController();
        this.currentPerso = new Personnel();
        this.initPerso = this.currentPerso.copy();

        setLayout(new GridBagLayout());
        addComponentTo(this.getLabelNom(), this, 0, 0, 1, 1, 0.1);
        addComponentTo(this.getTextNom(), this, 1, 0, 1, 1, 0.9);
        addComponentTo(this.getLabelMotDePasse(), this, 0, 1, 1, 1, 0.1);
        addComponentTo(this.getTextMotDePasse(), this, 1, 1, 1, 1, 0.9);
        addComponentTo(this.getLabelRole(), this, 0, 2, 1, 1, 0.1);
        addComponentTo(this.getComboBoxRole(), this, 1, 2, 1, 1, 0.9);
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
        if (fillHorizontal) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }
        gbc.insets = new Insets(7, 10, 5, 10);
        panel.add(component, gbc);
    }

    public void writeInputs(int id) {
        this.currentPerso = this.persoController.getPerso(id);
        this.initPerso = this.currentPerso.copy();
        this.getTextNom().setText(this.currentPerso.getNom());
        this.getTextMotDePasse().setText(this.currentPerso.getMotPasse());
        int selectedRole = 2;
        switch (this.currentPerso.getRole()) {
            case Personnel.ADMINISTRATEUR:
                selectedRole = 0;
                break;

            case Personnel.VETERINAIRE:
                selectedRole = 1;
                break;

            case Personnel.SECRETAIRE:
                selectedRole = 2;
                break;
        }
        this.getComboBoxRole().setSelectedIndex(selectedRole);
    }

    public JComboBox getComboBoxRole() {
        if (this.comboBoxRole == null) {
            this.comboBoxRole = new JComboBox(ROLES);
        }
        return comboBoxRole;
    }

    public JLabel getLabelNom() {
        if (this.labelNom == null) {
            this.labelNom = new JLabel("Nom : ");
        }
        return labelNom;
    }

    public JLabel getLabelMotDePasse() {
        if (this.labelMotDePasse == null) {
            this.labelMotDePasse = new JLabel("Mot de passe: ");
        }
        return labelMotDePasse;
    }

    public JLabel getLabelRole() {
        if (this.labelRole == null) {
            this.labelRole = new JLabel("Rôle : ");
        }
        return labelRole;
    }

    public JTextField getTextNom() {
        if (this.textNom == null) {
            this.textNom = new JTextField();
        }
        return textNom;
    }

    public JTextField getTextMotDePasse() {
        if (this.textMotDePasse == null) {
            this.textMotDePasse = new JTextField();
        }
        return textMotDePasse;
    }

    private void inputToPerso() {
        this.currentPerso.setNom(this.getTextNom().getText());
        this.currentPerso.setMotPasse(this.getTextMotDePasse().getText());
        switch (ROLES[this.getComboBoxRole().getSelectedIndex()]) {
            case Personnel.ADMINISTRATEUR_LABEL:
                this.currentPerso.setRole(Personnel.ADMINISTRATEUR);
                break;

            case Personnel.VETERINAIRE_LABEL:
                this.currentPerso.setRole(Personnel.VETERINAIRE);
                break;

            case Personnel.SECRETAIRE_LABEL:
                this.currentPerso.setRole(Personnel.SECRETAIRE);
                break;
        }
    }

    private void quitter() {
        EcranGestionPersonnel ecranGestionPersonnel = ((EcranGestionPersonnel) AppliTestIHM.mainFrame.getCurrentPanel());
        ecranGestionPersonnel.reloadView();
        ecranGestionPersonnel.getCreationView().setVisible(false);
    }

    public boolean isSaved() {
        this.inputToPerso();
        System.out.println("Current: " + this.currentPerso);
        System.out.println("Last: " + this.initPerso);
        return this.currentPerso.equals(this.initPerso) || (this.initPerso.getCodePers() == 0 && this.currentPerso.getCodePers() > 0);
    }

    public void resetDialog() {
        this.currentPerso = new Personnel();
        this.writeInputs(this.currentPerso.getCodePers());
    }

    public JButton getButtonSave() {
        if (this.buttonSave == null) {
            this.buttonSave = new JButton("Enregistrer");
            this.buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(currentPerso);
                    inputToPerso();
                    if (currentPerso.getCodePers() > 0) {
                        try {
                            persoController.updatePerso(currentPerso);
                            EcranGestionPersonnel ecranGestionPersonnel = ((EcranGestionPersonnel) AppliTestIHM.mainFrame.getCurrentPanel());
                            ecranGestionPersonnel.reloadView();
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Modifications enregistrées");
                            initPerso = currentPerso.copy();
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur mise à jour", "Erreur de mise à jour:\n" + e1.getMessage());
                        }
                    } else {
                        try {
                            persoController.addPerso(currentPerso);
                            ((EcranGestionPersonnel) AppliTestIHM.mainFrame.getCurrentPanel()).reloadView();
                            JOptionPane.showMessageDialog(AppliTestIHM.dialog, "Ajout du personnel\n" + currentPerso.toString() + " réussite");
                            initPerso = currentPerso.copy();
                        } catch (BLLException e1) {
                            e1.printStackTrace();
                            AppliTestIHM.showError("Erreur de création", "Erreur de création:\n" + e1.getMessage());
                        }
                    }
                }
            });
        }
        return buttonSave;
    }
}
