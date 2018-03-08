package fr.eni.clinique.bo;

import java.util.Objects;

public class Race {

	private String race;
	private String espece;


	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getEspece() {
		return espece;
	}
	public void setEspece(String espece) {
		this.espece = espece;
	}

	public Race() {
		super();
		this.setRace("");
		this.setEspece("");
	}


	public Race(String race, String espece) {
		this();
		this.setRace(race);
		this.setEspece(espece);
	}	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Race [race=");
		builder.append(race);
		builder.append(", espece=");
		builder.append(espece);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Race race1 = (Race) o;
		return Objects.equals(getRace(), race1.getRace()) &&
				Objects.equals(getEspece(), race1.getEspece());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getRace(), getEspece());
	}
}
