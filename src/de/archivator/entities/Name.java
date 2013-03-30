/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  Junghans
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

import java.io.Serializable;
import javax.persistence.*;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Classe für die Namen der Archivalien.
 * 
 * @author junghans
 * @author burghard.britzke bubi@charmides.in-berlin.de
 * @version 1.0
 */
@Entity
@Table(name = "NAMEN", schema = "ARCHIVATOR", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"NACHNAME", "VORNAME" }) })
@Searchable(root = false)
public class Name implements Serializable, Markable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@SearchableId
	private int id;

	@SearchableProperty(name = "person")
	private String nachname;

	@SearchableProperty(name = "person")
	private String vorname;

	// bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(name = "NAMEN_ARCHIVALIEN", joinColumns = { @JoinColumn(name = "NAMEN_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
	// @SearchableComponent
	private List<Archivale> archivalien;

	// dient zur Traversierung von Namenslisten
	private transient boolean marked;

	/**
	 * Erzeugt einen neuen Namen. Initialisiert die Liste der Archivalien.
	 */
	public Name() {
		archivalien = new ArrayList<Archivale>();
	}

	/**
	 * Erzeugt einen neuen Namen unter Angabe von Nachname und Vorname.
	 * Initialisiert die Liste der Archivalien.
	 * 
	 * @param lastName
	 *            Der Nachname des Namen.
	 * @param firstName
	 *            Der Vorname des Namen.
	 */
	public Name(String lastName, String firstName) {
		archivalien = new ArrayList<Archivale>();
		nachname = lastName;
		vorname = firstName;
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

	/**
	 * @return the marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked
	 *            the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * Ein Name ist gleich mit einem anderen Namen, wenn entweder die IDs
	 * übereinstimmen, oder der Nachname und der Vorname.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof Name) {
			Name otherName = (Name) other;
			if (this.id != 0 && this.id == otherName.getId()) {
				return true;
			}
			if (vorname == null && nachname == null) {
				return otherName.getNachname() == null
						&& otherName.getVorname() == null;
			} else {
				if (vorname == null) {
					return nachname.equals(otherName.getNachname());
				} else {
					if (this.nachname.equals(otherName.getNachname())
							&& this.vorname.equals(otherName.getVorname())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}