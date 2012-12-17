package de.archivator.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Classe für die Namen der Archivalien.
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "NAMEN")
public class Name implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nachname;

	private String vorname;

	//bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(
		name="ARCHIVALIENNAMEN"
		, joinColumns={
			@JoinColumn(name="NAMEN_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ARCHIVALIEN_ID")
			}
		)
	private List<Archivale> archivalien;

	public Name() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public List<Archivale> getArchivalien() {
		return this.archivalien;
	}

	public void setArchivalien(List<Archivale> archivalien) {
		this.archivalien = archivalien;
	}

}