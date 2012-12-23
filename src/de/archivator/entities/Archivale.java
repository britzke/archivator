/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  junghans
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.archivator.entities;

import java.io.Serializable;
import javax.persistence.*;

import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

import java.util.Date;
import java.util.List;


/**
 * Classe für die Archivalien.
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "ARCHIVALIEN", schema = "ARCHIV")
public class Archivale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="ABTEILUNGEN_ID")
	private int abteilungenId;

	private String betreff;

	@Temporal(TemporalType.DATE)
	private Date bis;

	@Lob
	private byte[] datei;

	private String inhalt;

	private int mappe;

	private int schubfach;

	@Temporal(TemporalType.DATE)
	private Date von;

	//bi-directional many-to-many association to Namen
	@ManyToMany(mappedBy="archivalien")
	private List<Name> namen;

	//bi-directional many-to-many association to Dokumentarten
	@ManyToMany(mappedBy="archivalien")
	private List<Dokumentart> dokumentarten;

	//bi-directional many-to-many association to Organisationseinheiten
	@ManyToMany(mappedBy="archivalien")
	private List<Organisationseinheit> organisationseinheiten;

	//bi-directional many-to-many association to Schlagwörter
	@ManyToMany(mappedBy="archivalien")
	private List<Schlagwort> schlagwörter;

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

	public Date getBis() {
		return this.bis;
	}

	public void setBis(Date bis) {
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

	public Date getVon() {
		return this.von;
	}

	public void setVon(Date von) {
		this.von = von;
	}

	public List<Name> getNamen() {
		return this.namen;
	}

	public void setNamen(List<Name> namen) {
		this.namen = namen;
	}

	public List<Dokumentart> getDokumentarten() {
		return this.dokumentarten;
	}

	public void setDokumentarten(List<Dokumentart> dokumentarten) {
		this.dokumentarten = dokumentarten;
	}

	public List<Organisationseinheit> getOrganisationseinheiten() {
		return this.organisationseinheiten;
	}

	public void setOrganisationseinheiten(List<Organisationseinheit> organisationseinheiten) {
		this.organisationseinheiten = organisationseinheiten;
	}

	public List<Schlagwort> getSchlagwörter() {
		return this.schlagwörter;
	}

	public void setSchlagwörter(List<Schlagwort> schlagwörter) {
		this.schlagwörter = schlagwörter;
	}

}