package de.archivator.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ORGANISATIONSEINHEITEN database table.
 * 
 */
@Entity
@Table(name = "ORGANISATIONSEINHEITEN")
public class Organisationseinheit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(
		name="ORGANISATIONSEINHEITEN_ARCHIVALIEN"
		, joinColumns={
			@JoinColumn(name="ORGANISATIONSEINHEITEN_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ARCHIVALIEN_ID")
			}
		)
	private List<Archivale> archivaliens;

	public Organisationseinheit() {
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