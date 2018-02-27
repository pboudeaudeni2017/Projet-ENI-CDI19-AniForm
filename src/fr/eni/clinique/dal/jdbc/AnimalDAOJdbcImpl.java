package fr.eni.clinique.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.clinique.bo.Client;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Race;
import fr.eni.clinique.dal.DALException;
import fr.eni.clinique.dal.DAO;

public class AnimalDAOJdbcImpl implements DAO<Animal> {
	
	private static final String INSERT = "INSERT INTO Animaux(CodeAnimal, NomAnimal, Sexe, Couleur, Race, Espece, CodeClient, Tatouage, Antecedents, Archive) VALUES (?,?,?,?,?,?,?,?,?,?)";

	private static final String SELECT_ALL = "SELECT CodeAnimal, NomAnimal, Sexe, Couleur, Race, Espece, CodeClient, Tatouage, Antecedents, Archive, c.NomClient, c.PrenomClient, c.Adresse1, c.Adresse2, c.CodePostal, c.Ville, c.NumTel, c.Assurance, c.Email, c.Remarque, c.Archive FROM Animaux ani INNER JOIN Clients c ON ani.CodeClient = c.CodeClient INNER JOIN Race r ON ani.race = r.race AND ani.espece = r.espece";
	
	private static final String SELECT_BY_ID = SELECT_ALL + "WHERE CodeAnimal=?";
	
	private static final String SELECT_BY_NAME = SELECT_ALL + "WHERE NomAnimal=?";
	
	private static final String UPDATE = "UPDATE Animaux SET CodeAnimal=?, NomAnimal=?, Sexe=?, Couleur=?, Race=?, Espece=?, CodeClient=?, Tatouage=?, Antecedents=?, Archive=?";

	
	
	@Override
	public void insert(Animal o) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Animal selectById(Animal o) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Animal> selectAll() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Animal o) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Animal o) throws DALException {
		// TODO Auto-generated method stub
		
	}
	
	
	public static Animal map (ResultSet rs) throws SQLException {
		Animal animal = null;
		
		int codeAnimal = rs.getInt("CodeAnimal");
		String nomAnimal = rs.getString("NomAnimal");
		String sexe = rs.getString("Sexe");
		String couleur = rs.getString("Couleur");
		Race race_espece = RaceDAOJdbcImpl.map(rs);
		Client codeClient = ClientDAOJdbcImpl.map(rs);
		String tatouage = rs.getString("Tatouage");
		String antecedent = rs.getString("Antecedent");		
		boolean archive = rs.getBoolean("Archive");
						
		animal = new Animal(codeAnimal, nomAnimal, sexe, couleur, race_espece, codeClient, tatouage, antecedent, archive);
		
		return animal;
	}
	
	
	private void objectToStatement(PreparedStatement pStmt, Animal animal) throws SQLException {
		pStmt.setInt(1, animal.getCodeAnimal());
		pStmt.setString(2, animal.getNomAnimal());
		pStmt.setString(3, animal.getSexe());
		pStmt.setString(4, animal.getCouleur());
	}

	
	
	

	






}
