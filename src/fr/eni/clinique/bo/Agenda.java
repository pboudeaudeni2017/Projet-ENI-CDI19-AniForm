package fr.eni.clinique.bo;

import java.util.Date;

public class Agenda {
	
	private Animal animal;
	private Client codeClient;
	
	private Date dateRdv;

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Client getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(Client codeClient) {
		this.codeClient = codeClient;
	}

	public Date getDateRdv() {
		return dateRdv;
	}

	public void setDateRdv(Date dateRdv) {
		this.dateRdv = dateRdv;
	}
	
	
	
	public Agenda() {
		super();
		this.setAnimal(new Animal());
		this.setCodeClient(new Client());
		this.setDateRdv(null);
	}
	
	public Agenda(Animal animal, Client codeClient, Date dateRdv) {
		this();
		this.setAnimal(animal);
		this.setCodeClient(codeClient);
		this.setDateRdv(dateRdv);
	}

	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Agenda [animal=");
		builder.append(animal);
		builder.append(", codeClient=");
		builder.append(codeClient);
		builder.append(", dateRdv=");
		builder.append(dateRdv);
		builder.append("]");
		return builder.toString();
	}
		

}
