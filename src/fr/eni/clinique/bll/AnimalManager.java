package fr.eni.clinique.bll;

import java.util.List;

import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.dal.DALException;
import fr.eni.clinique.dal.DAO;
import fr.eni.clinique.dal.DAOFactory;
import fr.eni.clinique.dal.jdbc.AnimalDAOJdbcImpl;

public class AnimalManager {

	private DAO<Animal> animalDAO;

	public AnimalManager() {
		this.animalDAO = DAOFactory.getAnimalDAO();
	}

	public Animal getAnimal (int codeAnimal) throws BLLException {
		Animal animal = new Animal();
		animal.setCodeAnimal(codeAnimal);

		try {
			return this.animalDAO.selectById(animal);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération de l'animal impossible", e);
		}
	}

	public Animal getAnimal(String name) throws BLLException {
		Animal animal = new Animal();
		animal.setNomAnimal(name);

		try {
			animal = ((AnimalDAOJdbcImpl)this.animalDAO).selectByName(animal);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération de l'animal impossible", e);
		}

		return animal;
	}
	
	public List<Animal> getAnimaux(int codeClient) throws BLLException {
		try {
			return ((AnimalDAOJdbcImpl)animalDAO).selectAllClient(codeClient);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Récupération de la liste des animaux impossible", e);
		}
	}
	
	public void updateAnimal(Animal animal) throws BLLException {
		try {
			this.validationAnimal(animal);
			this.animalDAO.update(animal);
		} catch (DALException e) {
			throw new BLLException("Mise à jour de l'animal impossible", e);
		}
	}
	
	public void deleteAnimal(Animal animal) throws BLLException {
		try {
			this.animalDAO.delete(animal);
		} catch (DALException e) {
			throw new BLLException("Suppression impossible");
		}
	}
	
	public void addAnimal(Animal animal) throws BLLException {
		try {
			this.validationAnimal(animal);
			this.animalDAO.insert(animal);
		} catch (DALException e) {
			throw new BLLException("Ajout de l'animal impossible", e);
		}
	}
	
	public void validationAnimal(Animal animal) throws BLLException {
		BLLException exceptions = new BLLException();
		String needed = "Obligatoire";
		
		if (animal == null) {
			throw new BLLException("Animal null");
		}
		
		if (animal.getCodeAnimal() >= 0) {
			exceptions.ajouterException(new ParameterException("CodeAnimal", "Doit être supérieur ou égal à 0"));
		}
		
		if (animal.getNomAnimal() ==  null ||
				animal.getNomAnimal().trim().length() == 0) {
			exceptions.ajouterException(new ParameterException("Nom", needed));
		}
		
		
		if (animal.getSexe() == null ||
				animal.getSexe().trim().length() != 1) {
			exceptions.ajouterException(new ParameterException("Sexe", needed));
		}
		
		if (animal.getRace_espece().getRace() == null ||
				animal.getRace_espece().getRace().trim().length() == 0) {
			exceptions.ajouterException(new ParameterException("Race", needed));
		}
		
		if (animal.getRace_espece().getEspece() == null ||
				animal.getRace_espece().getEspece().trim().length() == 0) {
			exceptions.ajouterException(new ParameterException("Espèce", needed));
		}
		
		if (exceptions.size() > 0) {
			throw exceptions;
		}
	}
}
