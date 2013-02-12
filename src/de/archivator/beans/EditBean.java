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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import de.archivator.annotations.AktuellesArchivale;
import de.archivator.entities.Archivale;
import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

/**
 * Stellt der Seite "edit.xhtml" Funktionen zur Verfügung. Für jede Funktion
 * wird eine action-Methode implementiert.
 * 
 * @author MIAHansen
 * @author burghard.britzke
 */
@Named
@RequestScoped
public class EditBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	/**
	 * Das aktuelle Archivale, welches durch speichere() oder lösche() verändert
	 * wird.
	 */
	@Inject @AktuellesArchivale
	private Archivale aktuellesArchivale;
	
	@Inject
	private DetailBean details;
	
	@Inject
	private List<Archivale> archivalien;
	
	private List<String> betreffs;
	/**
	 * Liste aller Namen, die im System gespeichert sind.
	 */
	private List<Name> namen;
	/**
	 * Liste aller Organisationseinheiten, die im System gespeichert sind.
	 */
	private List<Organisationseinheit> organisationseinheiten;
	/**
	 * Liste aller Dokumentarten, die im System gespeichert sind.
	 */
	private List<Dokumentart> dokumentarten;
	/**
	 * Liste aller Schlagworte, die im System gespeichert sind.
	 */
	private List<Schlagwort> schlagworte;

	private String archivaleNames;
	private String formularSchlagwörter;

	/**
	 * Erzeugt eine neue EditBean.
	 */
	public EditBean() {
		namen = new ArrayList<Name>();
		organisationseinheiten = new ArrayList<Organisationseinheit>();
		dokumentarten= new ArrayList<Dokumentart>();
		schlagworte = new ArrayList<Schlagwort>();

		formularSchlagwörter = new String();
		archivaleNames = new String();
	}

	/**
	 * Antwortet mit dem aktuellen Archivale
	 * 
	 * @return the aktuellesArchivale
	 */
	public Archivale getAktuellesArchivale() {
		return aktuellesArchivale;
	}

	/**
	 * @return the details
	 */
	public DetailBean getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(DetailBean details) {
		this.details = details;
	}

	/**
	 * Antwortet mit einer Liste von Betreffs.
	 * 
	 * @return the betreffs
	 */
	public List<String> getBetreffs() {
		return betreffs;
	}

	/**
	 * Setzt die Liste von Betreffs.
	 * 
	 * @param betreffs
	 *            the betreffs to set
	 */
	public void setBetreffs(List<String> betreffs) {
		this.betreffs = betreffs;
	}

	/**
	 * Antwortet mit der Liste von Namen.
	 * 
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
	 * @return the dokumentarten
	 */
	public List<Dokumentart> getDokumentartenheiten() {
		return dokumentarten;
	}

	/**
	 * @param organisationseinheiten
	 *            the dokumentarten to set
	 */
	public void setDokumentarten(
			List<Dokumentart> dokumentarten) {
		this.dokumentarten = dokumentarten;
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
	 * @return the formularSchlagwörter
	 */
	public String getFormularSchlagwörter() {
		return formularSchlagwörter;
	}

	/**
	 * @param formularSchlagwörter
	 *            the formularSchlagwörter to set
	 */
	public void setFormularSchlagwörter(String formularSchlagwörter) {
		this.formularSchlagwörter = formularSchlagwörter;
	}

	// Action-Routinen

	/**
	 * Action-Routine für die Schaltfläche "zurück".
	 * 
	 * @return "index" wenn ein neues Archivale erfasst werden sollte. "detail"
	 *         wenn ein altes Archivale bearbeitet werden sollte.
	 */
	public String back() {
		if (aktuellesArchivale.getId() == 0) {
			return "index";
		} else {
			return "detail";
		}
	}

	/**
	 * Löscht das aktuelle Archivale aus der Datenbank.
	 */
	public String lösche() {
		entityManager = entityManagerFactory.createEntityManager();
		Archivale aktuellesArchivale = entityManager.merge(this.aktuellesArchivale);
		entityManager.getTransaction().begin();
		entityManager.remove(aktuellesArchivale);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		archivalien.remove(this.aktuellesArchivale);
		details.setAktuellesArchivale(null);
		return "index";
	}
	
	/**
	 * Speichert das aktuelle Archivale in die Datenbank.
	 * 
	 * @return "detail" immer
	 */
	public String speichere() {
		entityManager = entityManagerFactory.createEntityManager();
		aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		entityManager.getTransaction().begin();

		entityManager.getTransaction().commit();
		entityManager.close();
		details.setAktuellesArchivale(aktuellesArchivale);
		return "detail";
	}

	/**
	 * Erstellt ein neues Archivale und initialisiert es mit den Standardwerten.
	 * 
	 * @return "edit" immer.
	 */
	public String erstelle() {
		aktuellesArchivale = new Archivale();
		details.setAktuellesArchivale(aktuellesArchivale);
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
		entityManager = entityManagerFactory.createEntityManager();
		Query q=entityManager.createQuery("select o from Organisationseinheit o");
		organisationseinheiten = q.getResultList();
		return "edit";
	}

	public String saveOrganisationseinheiten() {
		return "edit";
	}
	
	public String loadDokumentarten() {
		entityManager = entityManagerFactory.createEntityManager();
		Query q=entityManager.createQuery("select d from Dokumentart d");
		dokumentarten = q.getResultList();
		return "edit";
	}

	public String saveDokumentarten() {
		//TODO
		return "edit";
	}

	/**
	 * Lädt die Schlagworte, die dem Archivale zugeordnet ist, aus der Liste
	 * schlagworte als Komma separierten Text zur Bearbeitung in die Eigenschaft
	 * archivaleSchlagwörter.
	 * 
	 * @return "edit" immer.
	 */
	public String loadSchlagworte() {
		List<Schlagwort> schlagwörter = details.getAktuellesArchivale().getSchlagwörter();
		System.out.println(schlagwörter);
		String output = "";
		for (Schlagwort schlagwort : schlagwörter) {
			output += schlagwort.getName();
			output += ", ";
		}
		formularSchlagwörter = output.substring(0, output.length() - 2);
		return "edit";
	}

	/**
	 * Speichert die Schlagworte aus der kommaseparierten Zeichenkette
	 * archivaleSchlagworte in die Liste schlagworte.
	 * 
	 * @return "edit" immer.
	 */
	public String saveSchlagworte() {
		List<Schlagwort> archivaleSchlagwörter = aktuellesArchivale
				.getSchlagwörter();
		String[] wörter = formularSchlagwörter.split(",");
		Map<Schlagwort, Boolean> wörterMap = new HashMap<Schlagwort, Boolean>();
		for (String wort : wörter) {
			wort = wort.trim();
			for (Schlagwort s : schlagworte) {
				wörterMap.put(s, false);
				if (s.getName().equals(wort)) {
					// Wort existiert bereits im System
					wörterMap.put(s, true);
					// ist es dem Archivale schon zugeordnet?
					if (!archivaleSchlagwörter.contains(s)) {
						archivaleSchlagwörter.add(s);
					}
				}
			}

			Schlagwort newEntry = new Schlagwort(wort);
			boolean neu = false;
			if (schlagworte.isEmpty()) {
				archivaleSchlagwörter.add(newEntry);
				neu = true;
			} else {
				for (Schlagwort s : schlagworte) {
					if (!wörterMap.get(s)) {
						archivaleSchlagwörter.add(newEntry);
						neu = true;
					}
				}
			}
			if (neu) {
				schlagworte.add(newEntry);
			}
		}
		List<Schlagwort> zuEntfernen = new ArrayList<Schlagwort>();;
		for (Schlagwort s : schlagworte) {
			boolean imFormularVorhanden = false;
			for (String wort : wörter) {
				if (s.getName().equals(wort.trim())) {
					imFormularVorhanden = true;
				}
			}
			if (!imFormularVorhanden) {
				zuEntfernen.add(s);
			}
		}
			for (Schlagwort del : zuEntfernen) {
				schlagworte.remove(del);
			}
		return "edit";
	}
}
