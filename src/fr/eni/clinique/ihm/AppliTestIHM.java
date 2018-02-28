package fr.eni.clinique.ihm;

import fr.eni.clinique.ihm.login.EcranLogin;
import fr.eni.clinique.ihm.login.LoginController;

import javax.swing.*;

public class AppliTestIHM {
    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                EcranLogin loginFrame = new EcranLogin();

                LoginController loginControl = LoginController.getInstance();

                if(loginControl.getCurrentPersonnel().getCodePers() < 0) {
                    loginFrame.setVisible(true);
                }
                else {
                    loginFrame.setVisible(false);
                }
            }
        });
    }
}
