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
import static javax.persistence.GenerationType.IDENTITY;


/**
 * Classe für die Schlagwörter der Archivalien.
 * @author junghans
 * @version 1.0
 */
@Entity
@Table(name = "SCHLAGWÖRTER", schema = "ARCHIVATOR")
public class Schlagwort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-many association to Archivalien
	@ManyToMany
	@JoinTable(
		name="\"SCHLAGWÖRTER_ARCHIVALIEN\""
		, joinColumns={
			@JoinColumn(name="SCHLAGWÖRTER_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ARCHIVALIEN_ID")
			}, schema = "ARCHIVATOR"
		)
	private List<Archivale> archivalien;

	public Schlagwort() {
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

}