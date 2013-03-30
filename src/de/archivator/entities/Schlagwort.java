/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  Junghans,
 *                     burghard.britzke bubi@charmdes.in-berlin.de
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
 * Entity-Mapping für die Schlagwörter der Archivalien.
 * 
 * @author junghans, burghard.britzke bubi@charmides.in-berlin.de
 */
@Entity
@Table(name = "SCHLAGWÖRTER", schema = "ARCHIVATOR")
@Searchable(root = false)
public class Schlagwort implements Serializable, Markable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@SearchableId
	private int id;

	@Column(unique = true)
	@SearchableProperty(name = "schlagwort")
	private String name;

	// bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(name = "\"SCHLAGWÖRTER_ARCHIVALIEN\"", joinColumns = { @JoinColumn(name = "SCHLAGWÖRTER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
	private List<Archivale> archivalien;

	private transient boolean marked;

	/**
	 * Erzeugt ein neues Schlagwort.
	 */
	public Schlagwort() {
		archivalien = new ArrayList<Archivale>();
	}

	/**
	 * Erzeugt ein Neues Schlagwort mit dem angegebenen Namen.
	 * 
	 * @param name
	 *            Das Schlagwort
	 */
	public Schlagwort(String name) {
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
	 * @param marked
	 *            the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * Stellt fest, ob das Schlagwort gleich dem anderen ist. Schlagwörter sind
	 * gleich, wenn entweder die IDs gleich sind, oder die ID des Einen
	 * <i>null</i> und die Namen übereinstimmen.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof Schlagwort) {
			Schlagwort otherSchlagwort = (Schlagwort) other;
			if (this.id != 0 && this.id == otherSchlagwort.getId()) {
				return true;
			}
			if (name == null) {
				return otherSchlagwort.getName() == null;
			} else {
				return this.name.equals(otherSchlagwort.getName());
			}
		}
		return false;
	}

	/**
	 * Antwortet mit einer menschenlesbarern Form des Schlagwortes.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + id + "," + name + ")";
	}
}