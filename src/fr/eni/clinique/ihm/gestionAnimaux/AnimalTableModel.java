package fr.eni.clinique.ihm.gestionAnimaux;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import fr.eni.clinique.bll.AnimalManager;
import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Client;
import fr.eni.clinique.ihm.gestionClient.ClientController;

public class AnimalTableModel extends AbstractTableModel{
	
	private List<Animal> animaux = new ArrayList<>();
	
	private String[] nomColonnes = {"Code", "Nom", "Sexe", "Couleur", "Race", "Espece", "Tatouage"};

	private ClientController clientController;
		
	
	public AnimalTableModel() throws BLLException {
		this.clientController = ClientController.getInstance();
		updateData();
	}
	
	public void updateData() throws BLLException {
		animaux = new AnimalManager().getAnimaux(this.clientController.getCurrentClient().getCodeClient());
		System.out.println(animaux);
		fireTableDataChanged();
	}
	
	public Animal getAnimal(int row) {
		Animal animal = new Animal();
		if (row >= 0 && row < this.animaux.size()) {
			animal = this.animaux.get(row);
		}
		
		return animal;
	}
	
	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}
	
	@Override
	public int getRowCount() {
		return animaux.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return this.nomColonnes[column];
	}
	
	@Override
	public Class<?> getColumnClass (int columnIndex) {
		return String.class;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		Object value = null;
		
		if (rowIndex >= 0 && rowIndex < animaux.size()) {
			Animal animal = animaux.get(rowIndex);
			
			switch (colIndex) {
			case 0:
				value = animal.getCodeAnimal();
				break;
			case 1:
				value = animal.getNomAnimal();
				break;
			case 2:
				value = animal.getSexe();
				break;
			case 3:
				value = animal.getCouleur();
				break;
			case 4:
				value = animal.getRace_espece().getRace();
				break;
			case 5:
				value = animal.getRace_espece().getEspece();
				break;
			case 6:
				value = animal.getTatouage();
				break;				
			}			
		}
		
		return value;
	}
	
	public int getIndexOf(Animal animal) {
		System.out.println(animaux);
		return animaux.indexOf(animal);
		
	}
	

}
