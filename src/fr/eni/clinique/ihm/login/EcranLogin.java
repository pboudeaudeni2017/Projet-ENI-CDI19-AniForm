package fr.eni.clinique.ihm.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.eni.clinique.bll.PersonnelManager;




public class EcranLogin extends JFrame {


	private static final long serialVersionUID = 1L;

	private JPanel panelConnexion;

	private JLabel lblLogin;
	private JTextField txtLogin;

	private JLabel lblMotPasse;
	private JTextField txtMotPasse;

	private JButton valider;
	
	URL iconURL = getClass().getResource("ico_veto.png");
	ImageIcon icon = new ImageIcon(iconURL);

	public EcranLogin() {
		setSize(280, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(600, 250);

		setContentPane(getLoginForm());
		setIconImage(icon.getImage());
		setResizable(false);
		}
	


	private JPanel getLoginForm() {

		if (panelConnexion == null) {
			panelConnexion = new JPanel(new GridBagLayout());

			addComponentTo(getLblLogin(), panelConnexion, 0, 0, 1, 1, 0.1);
			addComponentTo(getTxtLogin(), panelConnexion, 1, 0, 1, 1, 0.9);
			addComponentTo(getLblMotPasse(), panelConnexion, 0, 1, 1, 1, 0.1);
			addComponentTo(getTxtMotPasse(), panelConnexion, 1, 1, 1, 1, 0.9);

			addComponentTo(getValider(), panelConnexion, 0, 5, 2, 2, 1);
		}

		return panelConnexion;

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


	public JLabel getLblLogin() {
		if(lblLogin == null) {
			lblLogin = new JLabel("Login: ");
			lblLogin.setHorizontalAlignment(SwingConstants.LEFT);
		}

		return lblLogin;
	}


	public JTextField getTxtLogin() {
		if(txtLogin == null) {
			txtLogin = new JTextField();
		}
		return txtLogin;
	}


	public JLabel getLblMotPasse() {
		if(lblMotPasse == null) {
			lblMotPasse = new JLabel("Mot de passe: ");
			lblMotPasse.setHorizontalAlignment(SwingConstants.LEFT);
		}

		return lblMotPasse;
	}


	public JTextField getTxtMotPasse() {
		if(txtMotPasse == null) {
			txtMotPasse = new JTextField();
		}
		return txtMotPasse;
	}

	public JButton getValider() {
		if(valider == null) {
			valider = new JButton("Connexion");
			valider.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//notifyObservers(connexion_on);
					LoginController logCon = LoginController.getInstance();
					logCon.connexion(getTxtLogin().getText(), getTxtMotPasse().getText()); 
					}
			});
		}
		return valider;
	}
}
