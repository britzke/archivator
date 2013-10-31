/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  Junghans
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

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;


import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Classe für die Koerperschaften der Archivalien.
 * 
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "KÖRPERSCHAFTEN", schema = "ARCHIVATOR")
@Searchable(root = false)
public class Körperschaft implements Serializable, MarkableArchvialeListContainer {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@SearchableId
	private int id;

	@Column(unique = true)
	@SearchableProperty(name = "Körperschaften")
	private String name;

	// bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(name = "KÖRPERSCHAFTEN_ARCHIVALIEN", joinColumns = { @JoinColumn(name = "KörperschaftEN_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
	private List<Archivale> archivalien;

	// dient zur Traversierung von Objekt-Listen
	private transient boolean marked;

	/**
	 * Erzeugt eine neue Körperschaft und initialisiert die Liste der
	 * Archivalien mit einer leeren Liste.
	 */
	public Körperschaft() {
		archivalien = new ArrayList<Archivale>();
	}

	/**
	 * Erzeugt eine neue Körperschaft unter Angabe des Namens.
	 * Initialisiert die Liste der Archivalien mit einer leeren Liste
	 * 
	 * @param name
	 *            Der Name der Körperschaft
	 */
	public Körperschaft(String name) {
		this.name = name;
		archivalien = new ArrayList<Archivale>();
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

	public List<Archivale> getArchivalien() {
		return this.archivalien;
	}

	public void setArchivalien(List<Archivale> archivalien) {
		this.archivalien = archivalien;
	}

	/**
	 * @return the marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * Vergleicht zwei Körperschaften. Wenn die ID gesetzt ist, dann
	 * zählt die ID - wenn nicht, dann werden die Namen verglichen.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof Körperschaft) {
			Körperschaft otherKörperschaft = (Körperschaft) other;
			if (this.id != 0 && this.id == otherKörperschaft.getId()) {
				return true;
			}
			if (name == null) {
				return otherKörperschaft.getName() == null;
			} else {
				return this.name.equals(otherKörperschaft.getName());
			}
		}
		return false;
	}

}