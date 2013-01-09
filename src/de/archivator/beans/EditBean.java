/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  MIAHansen, burghard.britzke
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
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.archivator.entities.Archivale;
import de.archivator.entities.Name;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

/**
 * Stellt der Seite "edit.xhtml" Funktionen zur Verfügung. Für jede Funktion
 * wird eine action-Methode implementiert.
 * 
 * @author MIAHansen, burghard.britzke
 */
@Named
@SessionScoped
public class EditBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject
	private transient EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	/**
	 * Das aktuelle Archivale, welches durch speichere() oder lösche() verändert
	 * wird.
	 */
	private Archivale aktuellesArchivale;

	private List<String> betreffs;
	private List<Name> namen;
	private List<Organisationseinheit> organisationseinheiten;
	private List<Schlagwort> schlagworte;

	private String archivaleNames;
	private String archivaleSchlagwörter;

	/**
	 * Erzeugt eine neue EditBean.
	 */
	public EditBean() {
		System.out.println("EditBean<init>()");
	}

	/**
	 * @return the aktuellesArchivale
	 */
	public Archivale getAktuellesArchivale() {
		return aktuellesArchivale;
	}

	/**
	 * @param aktuellesArchivale
	 *            the aktuellesArchivale to set
	 */
	public void setAktuellesArchivale(Archivale aktuellesArchivale) {
		entityManager = entityManagerFactory.createEntityManager();
		this.aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		entityManager.getTransaction().begin();
	}

	/**
	 * @return the betreffs
	 */
	public List<String> getBetreffs() {
		return betreffs;
	}

	/**
	 * @param betreffs
	 *            the betreffs to set
	 */
	public void setBetreffs(List<String> betreffs) {
		this.betreffs = betreffs;
	}

	/**
	 * @return the namen
	 */
	public List<Name> getNamen() {
		return namen;
	}

	/**
	 * @param namen
	 *            the namen to set
	 */
	public void setNamen(List<Name> namen) {
		this.namen = namen;
	}

	/**
	 * @return the organisationseinheiten
	 */
	public List<Organisationseinheit> getOrganisationseinheiten() {
		return organisationseinheiten;
	}

	/**
	 * @param organisationseinheiten
	 *            the organisationseinheiten to set
	 */
	public void setOrganisationseinheiten(
			List<Organisationseinheit> organisationseinheiten) {
		this.organisationseinheiten = organisationseinheiten;
	}

	/**
	 * @return the schlagworte
	 */
	public List<Schlagwort> getSchlagworte() {
		return schlagworte;
	}

	/**
	 * @param schlagworte
	 *            the schlagworte to set
	 */
	public void setSchlagworte(List<Schlagwort> schlagworte) {
		this.schlagworte = schlagworte;
	}

	/**
	 * @return the archivaleNames
	 */
	public String getArchivaleNames() {
		return archivaleNames;
	}

	/**
	 * @param archivaleNames
	 *            the archivaleNames to set
	 */
	public void setArchivaleNames(String archivaleNames) {
		this.archivaleNames = archivaleNames;
	}

	/**
	 * @return the archivaleSchlagwörter
	 */
	public String getArchivaleSchlagwörter() {
		return archivaleSchlagwörter;
	}

	/**
	 * @param archivaleSchlagwörter
	 *            the archivaleSchlagwörter to set
	 */
	public void setArchivaleSchlagwörter(String archivaleSchlagwörter) {
		this.archivaleSchlagwörter = archivaleSchlagwörter;
	}

	// Action-Routinen
	
	/**
	 * Action-Routine für die Schaltfläche "zurück"
	 * @return "index" wenn ein neues Archivale erfasst werden sollte.
	 * "detail" wenn ein altes Archivale bearbeitet werden sollte.
	 */
	public String back() {
		entityManager.getTransaction().rollback();
		entityManager.close();
		if (aktuellesArchivale.getId() == 0) {
			return "index";
		}
		else
		{
			return "detail";
		}
	}
	/**
	 * Löscht das aktuelle Archivale aus der Datenbank.
	 */
	public String lösche() {
		entityManager.remove(aktuellesArchivale);
		entityManager.getTransaction().commit();
		entityManager.close();
		return "index";
	}

	/**
	 * Speichert das aktuelle Archivale in die Datenbank.
	 */
	public String speichere() {
		entityManager.getTransaction().commit();
		entityManager.close();
		return "detail";
	}

	/**
	 * Erstellt ein neues Archivale und initialisiert es mit den Standardwerten.
	 */
	public String erstelle() {
		entityManager = entityManagerFactory.createEntityManager();
		aktuellesArchivale = entityManager.merge(new Archivale());
		entityManager.getTransaction().begin();
		return "edit";
	}

	/**
	 * Lädt die Namen, die dem Archivale zugeordnet ist, aus der Liste namen als
	 * Komma separierten Text zur Bearbeitung in die Eigenschaft archivaleNamen.
	 * 
	 * @return "edit" immer.
	 */
	public String loadNamen() {
		return "edit";
	}

	/**
	 * Speichert die Namen aus der kommaseparierten Zeichenkette archivaleNamen
	 * in die Liste namen.
	 * 
	 * @return "edit" immer
	 */
	public String saveNamen() {
		return "edit";
	}

	public String loadOrganisationseinheiten() {
		return "edit";
	}
	public String saveOrganisationseinheiten() {
		return "edit";
	}
	/**
	 * Lädt die Schlagworte, die dem Archivale zugeordnet ist, aus der Liste
	 * schlagworte als Komma separierten Text zur Bearbeitung in die Eigenschaft
	 * archivaleSchlagworte.
	 * 
	 * @return "edit" immer.
	 */
	public String loadSchlagworte() {
		return "edit";
	}

	/**
	 * Speichert die Schlagworte aus der kommaseparierten Zeichenkette
	 * archivaleSchlagworte in die Liste schlagworte.
	 * 
	 * @return "edit" immer.
	 */
	public String saveSchlagworte() {
		
		return "edit";
	}
}
