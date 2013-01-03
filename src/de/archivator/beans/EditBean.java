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
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
@ManagedBean
@ViewScoped
public class EditBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject
	EntityManager entityManager;
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
		//System.out.println("EditBean::<init>()");		
		aktuellesArchivale = new Archivale();
		aktuellesArchivale.setVonJahr(1980);
		aktuellesArchivale.setBisJahr(1983);
		aktuellesArchivale.setBetreff("Weihnachtsfeier MIA 80er");
		aktuellesArchivale
				.setInhalt("Lorem ipsum dolor sit amet, " +
						"consectetur adipisicing elit, " +
						"sed do eiusmod tempor incididunt ut labore " +
						"et dolore magna aliqua. Ut enim ad minim veniam, " +
						"quis nostrud exercitation ullamco laboris " +
						"nisi ut aliquip ex ea commodo consequat. " +
						"Duis aute irure dolor in reprehenderit " +
						"in voluptate velit esse cillum dolore eu " +
						"fugiat nulla pariatur. Excepteur sint occaecat " +
						"cupidatat non proident, sunt in culpa qui " +
						"officia deserunt mollit anim id est laborum.");
		aktuellesArchivale.setMappe(15);
		aktuellesArchivale.setSchubfach(35);
		Name name = new Name();
		name.setNachname("Beloracz");
		name.setVorname("Robert");
		ArrayList<Name> namen = new ArrayList<Name>();
		namen.add(name);
		name = new Name();
		name.setNachname("Sophia");
		name.setVorname("Weigel");
		namen.add(name);
		
		aktuellesArchivale.setNamen(namen);
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
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
		this.aktuellesArchivale = aktuellesArchivale;
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
	 * Löscht das aktuelle Archivale aus der Datenbank.
	 */
	public String lösche() {
		//System.out.println("EditBean::lösche()...");
		EntityTransaction entityTransaction =entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(aktuellesArchivale);
		entityTransaction.commit();
		return "index";
	}

	/**
	 * Speichert das aktuelle Archivale in die Datenbank.
	 */
	public String speichere() {
		//System.out.println("EditBean::speichere()...");
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (aktuellesArchivale.getId() == 0) {
			entityManager.persist(aktuellesArchivale);
		}
		entityTransaction.commit();
		return "detail";
	}

	/**
	 * Erstellt ein neues Archivale und initialisiert es mit den Standardwerten.
	 */
	public String erstelle() {
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
