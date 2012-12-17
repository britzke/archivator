package de.archivator.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Classe für die Schlagwörter der Archivalien.
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "SCHLAGW�RTER")
public class Schlagwort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(
		name="\"SCHLAGW�RTER_Archivalien\""
		, joinColumns={
			@JoinColumn(name="SCHLAGW�RTER_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ARCHIVALIEN_ID")
			}
		)
	private List<Archivale> archivaliens;

	public Schlagwort() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Archivale> getArchivaliens() {
		return this.archivaliens;
	}

	public void setArchivaliens(List<Archivale> archivaliens) {
		this.archivaliens = archivaliens;
	}

}