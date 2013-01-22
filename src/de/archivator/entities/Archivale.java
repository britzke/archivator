/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  junghans
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

import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Ein Archivale ist ein Archivgut, das in einem Archiv verwaltet wird.
 * 
 * @author junghans
 * @author burghard.britzke
 * @version 1.0
 */
@Entity
@Table(name = "ARCHIVALIEN", schema = "ARCHIVATOR")
@Searchable
public class Archivale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@SearchableId
	private int id;

	@Column(name = "ABTEILUNGEN_ID")
	private int abteilungenId;

	@SearchableProperty
	private String betreff;

	@SearchableProperty
	private int bisJahr;
	@SearchableProperty
	private int vonJahr;

	@Lob
	private byte[] datei;

	@Lob
	@SearchableProperty
	private String inhalt;

	private int mappe;

	private int schubfach;

	// bi-directional many-to-many association to Namen
	@ManyToMany(mappedBy = "archivalien")
	private List<Name> namen;

	// bi-directional many-to-many association to Dokumentarten
	@ManyToMany(mappedBy = "archivalien")
	private List<Dokumentart> dokumentarten;

	// bi-directional many-to-many association to Organisationseinheiten
	@ManyToMany(mappedBy = "archivalien")
	private List<Organisationseinheit> organisationseinheiten;

	// bi-directional many-to-many association to Schlagwörter
	@ManyToMany(mappedBy = "archivalien", cascade = CascadeType.ALL)
	private List<Schlagwort> schlagwörter;

	/**
	 * Erzeugt ein neues Archivale. Initialisiert die Liste der Namen,
	 * Dokumentarten, Organisationseinheiten und Schlagwörter für dieses
	 * Archivale.
	 */
	public Archivale() {
		namen = new ArrayList<Name>();
		organisationseinheiten = new ArrayList<Organisationseinheit>();
		dokumentarten = new ArrayList<Dokumentart>();
		schlagwörter = new ArrayList<Schlagwort>();
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

	public int getVonJahr() {
		return this.vonJahr;
	}

	public void setVonJahr(int vonJahr) {
		this.vonJahr = vonJahr;
	}

	public int getBisJahr() {
		return this.bisJahr;
	}

	public void setBisJahr(int bisJahr) {
		this.bisJahr = bisJahr;
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

	public void setOrganisationseinheiten(
			List<Organisationseinheit> organisationseinheiten) {
		this.organisationseinheiten = organisationseinheiten;
	}

	public List<Schlagwort> getSchlagwörter() {
		return this.schlagwörter;
	}

	public void setSchlagwörter(List<Schlagwort> schlagwörter) {
		this.schlagwörter = schlagwörter;
	}

	/**
	 * Liefert den Inhalt der Archivale derart gekürzt, dass er in der Liste der
	 * Rechercheresultate angezeigt werden kann.
	 * 
	 * @return An einer Wortgrenze abgeschnittene Zeichenkette des Attributs
	 *         Inhalt, die nicht länger als 120 Zeichen ist.
	 */
	public String getShortInhalt() {
		String shortInhalt;
		if (inhalt != null) {
			if (inhalt.length() > 120) {
				shortInhalt = inhalt.substring(0, 120);
				if (shortInhalt.lastIndexOf(" ") > 0) {
					shortInhalt = shortInhalt.substring(0,
							shortInhalt.lastIndexOf(" "))
							+ "...";
				}
			} else {
				shortInhalt = inhalt;
			}
			return shortInhalt.toString();
		} else {
			return null;
		}
	}

}