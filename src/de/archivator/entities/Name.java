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
import java.util.List;


/**
 * Classe für die Namen der Archivalien.
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "NAMEN", schema = "ARCHIV")
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