/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke, bubi@charmides.in-berlin.de
 *                     e0_schulz
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
package de.archivator.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.archivator.entities.Archivale;

/**
 * Stellt Eigenschaften und Funktionen für den View search.xthml sowie
 * results.xhtml zur Verfügung.
 * 
 * @author burghard.britzke
 * @author e0_schulz
 */
@Named
@SessionScoped
public class RechercheBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Das Suchkriterium, dass der Benutzer in das Formular search.xhtml
	 * eingetragen hat.
	 */
	private String suchKriterium;
	/**
	 * Die Liste der Archivalien, die durch die zuletzt ausgeführte Recherche
	 * gefunden wurden.
	 */
	@Produces @Named
	private List<Archivale> archivalien;

	/**
	 * Erzeugt eine neue RechercheBean. Initialisiert die Liste der Archivalien.
	 */
	public RechercheBean() {
		archivalien = new ArrayList<Archivale>();
		suchKriterium = "";
	}
	
	@PostConstruct
	public void init() {
	}

	/**
	 * @return the suchKriterium
	 */
	public String getSuchKriterium() {
		return suchKriterium;
	}

	/**
	 * @param suchKriterium
	 *            the suchKriterium to set
	 */
	public void setSuchKriterium(String suchKriterium) {
		this.suchKriterium = suchKriterium;
	}

	/**
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
