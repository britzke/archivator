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
 * Classe für die Dokumentarten der Archivalien.
 * 
 * @author junghans
 * @version 1.0
 */
/**
 * @author bubi
 * 
 */
@Entity
@Table(name = "DOKUMENTARTEN", schema = "ARCHIVATOR")
@Searchable(root = false)
public class Dokumentart implements Serializable, Markable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@SearchableId
	private int id;

	@Column(unique = true)
	@SearchableProperty(name = "dokumentart")
	private String name;

	// bi-directional many-to-many association to Archivalien
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.PERSIST })
	@JoinTable(name = "DOKUMENTARTEN_ARCHIVALIEN", joinColumns = { @JoinColumn(name = "DOKUMENTARTEN_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
	private List<Archivale> archivalien;

	// dient zur Traversierung von Objekt-Listen
	private transient boolean marked;

	/**
	 * Erzeugt eine neue Dokumentart.
	 */
	public Dokumentart() {
		archivalien = new ArrayList<Archivale>();
	}

	/**
	 * Erzeugt eine neue Dokumentart mit dem angegebenen Namen.
	 * 
	 * @param dokumentartString
	 *            Name der Dokumentart.
	 */
	public Dokumentart(String dokumentartString) {
		archivalien = new ArrayList<Archivale>();
		name = dokumentartString;
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
	 * Stellt fest, ob die Dokumentart gleich der anderen ist. Dokumentarten
	 * sind gleich, wenn entweder die IDs gleich sind, oder die ID des Einen
	 * <i>null</i> und dann die Namen übereinstimmen.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof Dokumentart) {
			Dokumentart otherDokumentart = (Dokumentart) other;
			if (this.id != 0 && this.id == otherDokumentart.getId()) {
				return true;
			}
			if (name == null) {
				return otherDokumentart.getName() == null;
			} else {
				return this.name.equals(otherDokumentart.getName());
			}
		}
		return false;
	}
}