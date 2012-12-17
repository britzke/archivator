package de.archivator.entities;

import java.io.Serializable;
import javax.persistence.*;

import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

import java.util.List;


/**
 * The persistent class for the ARCHIVALIEN database table.
 * 
 */
@Entity
@Table(name = "ARCHIVALIEN")
public class Archivale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="ABTEILUNGEN_ID")
	private int abteilungenId;

	private String betreff;

	private int bis;

	@Lob
	private byte[] datei;

	private String inhalt;

	private int mappe;

	private int schubfach;

	private int von;

	//bi-directional many-to-many association to Namen
	@ManyToMany(mappedBy="archivaliens")
	private List<Name> namens;

	//bi-directional many-to-many association to Dokumentarten
	@ManyToMany(mappedBy="archivaliens")
	private List<Dokumentart> dokumentartens;

	//bi-directional many-to-many association to Organisationseinheiten
	@ManyToMany(mappedBy="archivaliens")
	private List<Organisationseinheit> organisationseinheitens;

	//bi-directional many-to-many association to Schlagwörter
	@ManyToMany(mappedBy="archivaliens")
	private List<Schlagwort> schlagwörters;

	public Archivale() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAbteilungenId() {
		return this.abteilungenId;
	}

	public void setAbteilungenId(int abteilungenId) {
		this.abteilungenId = abteilungenId;
	}

	public String getBetreff() {
		return this.betreff;
	}

	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}

	public int getBis() {
		return this.bis;
	}

	public void setBis(int bis) {
		this.bis = bis;
	}

	public byte[] getDatei() {
		return this.datei;
	}

	public void setDatei(byte[] datei) {
		this.datei = datei;
	}

	public String getInhalt() {
		return this.inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public int getMappe() {
		return this.mappe;
	}

	public void setMappe(int mappe) {
		this.mappe = mappe;
	}

	public int getSchubfach() {
		return this.schubfach;
	}

	public void setSchubfach(int schubfach) {
		this.schubfach = schubfach;
	}

	public int getVon() {
		return this.von;
	}

	public void setVon(int von) {
		this.von = von;
	}

	public List<Name> getNamens() {
		return this.namens;
	}

	public void setNamens(List<Name> namens) {
		this.namens = namens;
	}

	public List<Dokumentart> getDokumentartens() {
		return this.dokumentartens;
	}

	public void setDokumentartens(List<Dokumentart> dokumentartens) {
		this.dokumentartens = dokumentartens;
	}

	public List<Organisationseinheit> getOrganisationseinheitens() {
		return this.organisationseinheitens;
	}

	public void setOrganisationseinheitens(List<Organisationseinheit> organisationseinheitens) {
		this.organisationseinheitens = organisationseinheitens;
	}

	public List<Schlagwort> getSchlagwörters() {
		return this.schlagwörters;
	}

	public void setSchlagwörters(List<Schlagwort> schlagwörters) {
		this.schlagwörters = schlagwörters;
	}

}