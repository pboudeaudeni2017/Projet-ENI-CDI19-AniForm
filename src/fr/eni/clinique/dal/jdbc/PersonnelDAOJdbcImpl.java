/**
 * 
 */
package fr.eni.clinique.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.eni.clinique.bo.Personnel;
import fr.eni.clinique.dal.DAO;
import fr.eni.clinique.dal.DALException;

/**
 * @author Eni Ecole
 * 
 */
public class PersonnelDAOJdbcImpl implements DAO<Personnel> {
	
	private static final String INSERT = "INSERT INTO Articles(reference, marque, "
										+ "designation, prixUnitaire, qteStock, grammage, couleur, type)"
										+ " VALUES(?,?,?,?,?,?,?,?)";
	
	private static final String SELECT_BY_ID = "SELECT CodePers, Nom, MotPasse, Role, Archive FROM Personnel WHERE CodePers=?";
	
	private final static String SELECT_ALL = "SELECT CodePers, Nom, MotPasse, Role, Archive FROM Personnel";
	
	private final static String UPDATE = "UPDATE Personnel SET CodePers=?, Nom=?, "
										+ "MotPasse=?, Role=?, Archive=?"
										+ " WHERE CodePers=?";
	
	private final static String DELETE = "DELETE FROM Personnel WHERE CodePers=?";
	
	/* (non-Javadoc)
	 * @see fr.eni.papeterie.dal.jdbc.ArticleDAO#insert(fr.eni.papeterie.bo.Article)
	 */
	@Override
	public void insert(Client c) throws DALException {
		try (Connection cnx = DBConnection.getConnexion()){
			//Préparation de la requête
			PreparedStatement pStmt = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			objectToStatement(pStmt, c);
			
			//Execution
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if(rs.next()) {
				c.setIdArticle(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DALException("Articles", e);
		}
	}

	/* (non-Javadoc)
	 * @see fr.eni.papeterie.dal.jdbc.ArticleDAO#selectById(int)
	 */
	@Override
	public Article selectById(int id) throws DALException {
		Article article = null;
		try (Connection cnx = DBConnection.getConnexion()){
			//Préparation de la requête
			PreparedStatement pStmt = cnx.prepareStatement(SELECT_BY_ID);
			pStmt.setInt(1, id);
			
			//Execution
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				article = map(rs);
			}
		} catch (SQLException e) {
			throw new DALException("Articles", e);
		}
		return article;
	}

	/* (non-Javadoc)
	 * @see fr.eni.papeterie.dal.jdbc.ArticleDAO#selectAll()
	 */
	@Override
	public List<Article> selectAll() throws DALException {
		List<Article> articles = new ArrayList<>();
		try (Connection cnx = DBConnection.getConnexion()){
			PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				articles.add(map(rs));
			}
		} catch (SQLException e) {
			throw new DALException("Articles", e);
		}
		return articles;
	}

	/* (non-Javadoc)
	 * @see fr.eni.papeterie.dal.jdbc.ArticleDAO#update(fr.eni.papeterie.bo.Article)
	 */
	@Override
	public void update(Article a) throws DALException {
		try (Connection cnx = DBConnection.getConnexion()){
			//Préparation de la requête
			PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
			objectToStatement(pStmt, a);			
			pStmt.setInt(9, a.getIdArticle());
			
			//Execution
			pStmt.executeUpdate();			
			
		} catch (SQLException e) {
			throw new DALException("Articles", e);
		}
	}

	/* (non-Javadoc)
	 * @see fr.eni.papeterie.dal.jdbc.ArticleDAO#delete(int)
	 */
	@Override
	public void delete(Article article) throws DALException {
		try (Connection cnx = DBConnection.getConnexion()){
			PreparedStatement pStmt = cnx.prepareStatement(DELETE);
			pStmt.setInt(1, article.getIdArticle());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Articles", e);
		}
	}

	private Article map(ResultSet rs) throws SQLException {
		Article article = null;
		
		String type = rs.getString("Type").trim();
		String reference = rs.getString("Reference");
		String marque = rs.getString("Marque");
		String designation = rs.getString("Designation");
		float prixUnitaire= rs.getFloat("prixUnitaire");
		int qteStock = rs.getInt("QteStock");
		int id = rs.getInt("idArticle");
		String couleur = rs.getString("couleur");
		int grammage = rs.getInt("grammage");			
		
		switch(type) {
		case TYPE_STYLO:
			article = new Stylo(id, reference, marque, designation, prixUnitaire, qteStock, couleur);
			break;
		case TYPE_RAMETTE:
			article = new Ramette(id, reference, marque, designation, prixUnitaire, qteStock, grammage);
			break;
		}
		
		return article;
	}
	
	private void objectToStatement(PreparedStatement pStmt, Article a) throws SQLException {
		pStmt.setString(1, a.getReference());
		pStmt.setString(2, a.getMarque());
		pStmt.setString(3, a.getDesignation());
		pStmt.setFloat(4, a.getPrixUnitaire());
		pStmt.setInt(5, a.getQteStock());
		
		if(a instanceof Stylo) {
			pStmt.setNull(6, Types.INTEGER);
			pStmt.setString(7, ((Stylo)a).getCouleur());
			pStmt.setString(8, TYPE_STYLO);
		} else {
			pStmt.setInt(6, ((Ramette)a).getGrammage());
			pStmt.setNull(7, Types.NVARCHAR);
			pStmt.setString(8, TYPE_RAMETTE);
		}
	}

	@Override
	public List<Article> selectByMarque() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectByMotCle() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
}
