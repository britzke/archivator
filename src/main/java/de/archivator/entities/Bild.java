/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2014  e11_cheneaux
 *                     burghard.britzke bubi@charmides.in-berlin.de
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

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity für ein Bild eines Archivales.
 * 
 * @author e11_cheneaux
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
@Entity
@Table(name = "Bilder", schema = "ARCHIVATOR")
public class Bild implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String beschreibung;
	private String inhaltsTyp;

	@Lob
	private byte[] datei;

	// bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(name = "BILDER_ARCHIVALIEN", joinColumns = { @JoinColumn(name = "BILDER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
	private List<Archivale> archivalien;

	/**
	 * Antwortet mit dem Wert des id
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Antwortet mit dem Wert des beschreibung
	 * 
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @param beschreibung
	 *            the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * Antwortet mit dem Wert des inhaltsTyp
	 * 
	 * @return the inhaltsTyp
	 */
	public String getInhaltsTyp() {
		return inhaltsTyp;
	}

	/**
	 * @param inhaltsTyp
	 *            the inhaltsTyp to set
	 */
	public void setInhaltsTyp(String inhaltsTyp) {
		this.inhaltsTyp = inhaltsTyp;
	}

	/**
	 * Antwortet mit dem Wert des datei
	 * 
	 * @return the datei
	 */
	public byte[] getDatei() {
		return datei;
	}

	/**
	 * @param datei
	 *            the datei to set
	 */
	public void setDatei(byte[] datei) {
		this.datei = datei;
	}

	/**
	 * Antwortet mit dem Wert des archivalien
	 * 
	 * @return the archivalien
	 */
	public List<Archivale> getArchivalien() {
		return archivalien;
	}

	/**
	 * @param archivalien
	 *            the archivalien to set
	 */
	public void setArchivalien(List<Archivale> archivalien) {
		this.archivalien = archivalien;
	}

}