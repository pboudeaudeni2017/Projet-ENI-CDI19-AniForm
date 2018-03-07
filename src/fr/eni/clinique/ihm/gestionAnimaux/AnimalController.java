package fr.eni.clinique.ihm.gestionAnimaux;

import java.util.List;


import fr.eni.clinique.bll.AnimalManager;
import fr.eni.clinique.bll.BLLException;
import fr.eni.clinique.bo.Animal;
import fr.eni.clinique.bo.Observable;
import fr.eni.clinique.bo.Observable.Observer;

public class AnimalController {
	
	private List<Animal> listeAnimaux;
	private int indexAnimal;
	
	private Observable<Animal> currentAnimal = new Observable<>();
	
	private AnimalManager manager;
	private static AnimalController instance = null;
	
	
	private AnimalController() throws BLLException {
		manager = new AnimalManager();
		
		listeAnimaux = manager.getAnimaux();
		indexAnimal = 0;
	}
	
	public static AnimalController getInstance() throws BLLException {
		if (instance == null) {
			instance = new AnimalController();
		}
		
		return instance;
	}
	
	public void first() throws AnimalNotFoundException {
		setAnimal(0);
	}
	
	public void setAnimal (int index) throws AnimalNotFoundException {
		if (index >= 0 && index < listeAnimaux.size()) {
			indexAnimal = index;
			currentAnimal.set(listeAnimaux.get(indexAnimal));
		} else {
			throw new AnimalNotFoundException();
		}
	}
	
	public void setAnimal (Animal animal) throws AnimalNotFoundException {
		this.setAnimal(animal, true);
	}
	
	public void setAnimal (Animal animal, boolean full) throws AnimalNotFoundException {
		System.out.println(animal);
		
		if (animal.getCodeAnimal() > 0) {
			int index = 0;
			while (index < this.listeAnimaux.size() && this.listeAnimaux.get(index).getCodeAnimal() != animal.getCodeAnimal()) {
				index++;
			}
			
			if (index < this.listeAnimaux.size()) {
				if (!full) {
					animal = this.listeAnimaux.get(index).copy();
				}
				this.currentAnimal.set(animal);
				this.listeAnimaux.set(index, animal);
			} else {
				throw new AnimalNotFoundException();
				
			}
		} else {
			throw new AnimalNotFoundException();
		}
	}
	
	
	public void updateAnimal() throws BLLException {
		this.manager.updateAnimal(this.currentAnimal.get());
	}
	
	
	public Animal getCurrentAnimal() throws BLLException {
		return this.currentAnimal.get();
	}
	
	
	public void addAnimal (Animal animal) throws BLLException, AnimalNotFoundException {
		this.manager.addAnimal(animal);
		this.listeAnimaux.add(animal);
		this.setAnimal(this.listeAnimaux.indexOf(animal));
	}
	

	public void deleteAnimal(Animal animal) throws BLLException {
		this.manager.deleteAnimal(this.currentAnimal.get());
	}
	
	
	public void registerToCurrentAnimal (Observer obs) {
		this.currentAnimal.registerObserver(obs);
	}
	
	
	

}
