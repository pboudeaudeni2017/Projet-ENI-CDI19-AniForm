package fr.eni.papeterie.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.eni.papeterie.dal.Settings;

public class DBConnection {

	private static String url;
	private static String user;
	private static String password;
	
	static {
		url = Settings.getProperty("url");
		user = Settings.getProperty("user");
		password = Settings.getProperty("password");
	}
	
	/**
	 * 
	 * @return Connexion vers la base de donn�es.
	 * @throws SQLException
	 */
	public static Connection getConnexion() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
