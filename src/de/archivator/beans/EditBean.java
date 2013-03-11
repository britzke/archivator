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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassSession;

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

	@Inject
	private Compass compass;

	/**
	 * Das aktuelle Archivale, welches durch speichere() oder lösche() verändert
	 * wird.
	 */
	@Inject
	@AktuellesArchivale
	private Archivale aktuellesArchivale;

	@Inject
	private SearchBean searchBean;

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

	private String formularNames;
	private String formularSchlagwörter;
	private Dokumentart[] selectedDokumentarten;

	/**
	 * Erzeugt eine neue EditBean.
	 */
	public EditBean() {
		namen = new ArrayList<Name>();
		organisationseinheiten = new ArrayList<Organisationseinheit>();
		dokumentarten = new ArrayList<Dokumentart>();
		schlagworte = new ArrayList<Schlagwort>();

		formularSchlagwörter = new String();
		formularNames = new String();
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
	 * @param details
	 *            the details to set
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
	public List<Dokumentart> getDokumentarten() {
		return dokumentarten;
	}

	/**
	 * @param dokumentarten
	 *            the dokumentarten to set
	 */
	public void setDokumentarten(List<Dokumentart> dokumentarten) {
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
	 * @return the formularNames
	 */
	public String getFormularNames() {
		return formularNames;
	}

	/**
	 * @param formularNames
	 *            the formularNames to set
	 */
	public void setFormularNames(String formularNames) {
		this.formularNames = formularNames;
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
		// lösche aus der Datenbank
		entityManager = entityManagerFactory.createEntityManager();
		Archivale aktuellesArchivale = entityManager
				.merge(this.aktuellesArchivale);
		entityManager.getTransaction().begin();
		entityManager.remove(aktuellesArchivale);

		// lösche aus dem Compass-Index
		CompassSession session = compass.openSession();
		try {
			session.delete(aktuellesArchivale);
			session.commit();
		} catch (CompassException ce) {
			ce.printStackTrace();
			session.rollback();
		}

		entityManager.getTransaction().commit();
		session.close();
		entityManager.close();

		// lösche aktuellen Archivale aus den Beans
		searchBean.search();
		details.setAktuellesArchivale(null);
		return "index";
	}

	/**
	 * Speichert das aktuelle Archivale in die Datenbank.
	 * 
	 * @return "detail" immer
	 */
	public String speichere() {
		// speichere in die Datenbank
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		entityManager.getTransaction().commit();

		// speichere in den Compass-Index
		CompassSession session = compass.openSession();
		try {
			session.save(aktuellesArchivale);
			session.commit();
		} catch (CompassException ce) {
			ce.printStackTrace();
			session.rollback();
		}

		session.close();
		entityManager.close();

		// speichere in die Bean(s)
		searchBean.search();
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
	 * @return null... immer.
	 */
	public String loadNamen() {
		formularNames = "";
		List<Name> archivaleNames = aktuellesArchivale.getNamen();
		for (Name archivaleName : archivaleNames) {
			if (formularNames.length() != 0) {
				formularNames += "; ";
			}
			formularNames += archivaleName.getNachname() + ", "
					+ archivaleName.getVorname();
		}
		return null;
	}

	/**
	 * Speichert die Namen aus der komma- und semikolaseparierten Zeichenkette
	 * archivaleNamen in die Liste namen.
	 * 
	 * @return null... immer
	 */
	public String saveNamen() {
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		List<Name> archivaleNames = aktuellesArchivale.getNamen();

		Query q = entityManager
				.createQuery("select o from Name o where o.nachname = :nachname and o.vorname = :vorname");

		String[] fullNames = formularNames.split(";");
		for (String fullName : fullNames) {
			String[] nameParts = fullName.split(",");
			String firstName = (nameParts.length == 2) ? nameParts[1].trim()
					: "";
			String lastName = nameParts[0].trim();
			if (!firstName.equals("") || !lastName.equals("")) {
				// ein Name von beiden ist gesetzt
				q.setParameter("nachname", lastName);
				q.setParameter("vorname", firstName);
				@SuppressWarnings("unchecked")
				List<Name> selectedNames = q.getResultList();
				Name name;
				if (selectedNames.size() == 0) {
					// Name ist neu in der Datenbank
					name = new Name(lastName, firstName);
				} else {
					name = selectedNames.get(0);
				}
				if (!archivaleNames.contains(name)) {
					name = entityManager.merge(name);
					archivaleNames.add(name);
					List<Archivale> archivalien = name.getArchivalien();
					archivalien.add(aktuellesArchivale);
				}
				name.setMarked(true);
			}
		}
		for (int i = archivaleNames.size(); i > 0; i--) {
			Name archivaleName = archivaleNames.get(i - 1);
			if (!archivaleName.isMarked()) {
				archivaleName.getArchivalien().remove(aktuellesArchivale);
				archivaleNames.remove(i - 1);
			}
		}

		// speichere in den Compass-Index
		CompassSession session = compass.openSession();
		try {
			session.save(aktuellesArchivale);
			session.commit();
		} catch (CompassException ce) {
			ce.printStackTrace();
			session.rollback();
		}

		entityTransaction.commit();
		session.close();
		entityManager.close();

		details.setAktuellesArchivale(aktuellesArchivale);
		return null;
	}

	/**
	 * Lädt die Eigenschaften für das Formular zur Bearbeitung der
	 * Organisationseinheiten.
	 * 
	 * @return null... immer
	 */
	@SuppressWarnings("unchecked")
	public String loadOrganisationseinheiten() {
		entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager
				.createQuery("select o from Organisationseinheit o");
		organisationseinheiten = q.getResultList();
		return null;
	}

	/**
	 * Sichert die Eigenschaften aus dem Formular zur Bearbeitung der
	 * Organisationseinheiten.
	 * 
	 * @return null... immer
	 */
	public String saveOrganisationseinheiten() {
		List<Organisationseinheit> org = aktuellesArchivale
				.getOrganisationseinheiten();
		org.clear();
		for (Organisationseinheit o : organisationseinheiten) {
			org.add(o);
		}
		aktuellesArchivale.setOrganisationseinheiten(org);
		details.setAktuellesArchivale(aktuellesArchivale);
		return null;
	}

	@SuppressWarnings("unchecked")
	public String loadDokumentarten() {
		entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createQuery("select d from Dokumentart d");
		dokumentarten = q.getResultList();
		return null;
	}

	public String saveDokumentarten() {
		ArrayList<Dokumentart> selection = new ArrayList<Dokumentart>();
		for (Dokumentart d : selectedDokumentarten) {
			selection.add(d);
		}
		aktuellesArchivale.setDokumentarten(selection);
		if (!aktuellesArchivale.getDokumentarten().isEmpty()) {
			// for (Dokumentart d : aktuellesArchivale.getDokumentarten()) {
			// }
		}
		return null;
	}

	/**
	 * Lädt die Schlagworte, die dem Archivale zugeordnet ist, aus der Liste
	 * schlagworte als Komma separierten Text zur Bearbeitung in die Eigenschaft
	 * archivaleSchlagwörter.
	 * 
	 * @return null... immer.
	 */
	public String loadSchlagworte() {
		List<Schlagwort> schlagwörter = details.getAktuellesArchivale()
				.getSchlagwörter();
		String output = "";
		for (Schlagwort schlagwort : schlagwörter) {
			output += schlagwort.getName();
			output += ", ";
		}
		formularSchlagwörter = output.substring(0,
				Math.max(output.length() - 2, 0));
		return null;
	}

	/**
	 * Speichert die Schlagworte aus der kommaseparierten Zeichenkette
	 * formularSchlagwörter in die Liste schlagworte.
	 * 
	 * @return null... immer.
	 */
	public String saveSchlagworte() {
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		List<Schlagwort> archivaleSchlagwörter = aktuellesArchivale
				.getSchlagwörter();

		formularSchlagwörter = formularSchlagwörter.replace("\n", "");
		formularSchlagwörter = formularSchlagwörter.replace("\r", "");
		formularSchlagwörter = formularSchlagwörter.trim();
		formularSchlagwörter = formularSchlagwörter.replaceAll(",$", "");
		String[] wörter = formularSchlagwörter.split(",");

		Query q = entityManager
				.createQuery("select s from Schlagwort s where s.name = :name");

		for (String formularSchlagwort : wörter) {
			formularSchlagwort = formularSchlagwort.trim();
			if (formularSchlagwort.length() > 0) {
				Schlagwort schlagwort;
				q.setParameter("name", formularSchlagwort);
				@SuppressWarnings("unchecked")
				List<Schlagwort> selectedSchlagworts = q.getResultList();
				if (selectedSchlagworts.size() == 0) {
					// Schlagwort ist neu in der Datenbank
					schlagwort = new Schlagwort(formularSchlagwort);
				} else {
					schlagwort = selectedSchlagworts.get(0);
				}
				if (!archivaleSchlagwörter.contains(schlagwort)) {
					schlagwort = entityManager.merge(schlagwort);
					archivaleSchlagwörter.add(schlagwort);
					List<Archivale> archivalien = schlagwort.getArchivalien();
					archivalien.add(aktuellesArchivale);
				}
				// bearbeitete archivaleSchlagwörter markierten
				// weil die dann unten nicht gelöscht werden dürfen
				schlagwort.setMarked(true);
			}
		}

		// nicht markierte archivaleSchlagwörter löschen
		for (int i = archivaleSchlagwörter.size(); i > 0; i--) {
			Schlagwort archivaleSchlagwort = archivaleSchlagwörter.get(i - 1);
			if (!archivaleSchlagwort.isMarked()) {
				archivaleSchlagwort.getArchivalien().remove(aktuellesArchivale);
				archivaleSchlagwörter.remove(i - 1);
			}
		}

		// speichere in den Compass-Index
		CompassSession session = compass.openSession();
		try {
			session.save(aktuellesArchivale);
			session.commit();
		} catch (CompassException ce) {
			ce.printStackTrace();
			session.rollback();
		}

		entityTransaction.commit();
		session.close();
		entityManager.close();

		details.setAktuellesArchivale(aktuellesArchivale);

		return null;
	}

	/**
	 * @return the selectedDokumentarten
	 */
	public Dokumentart[] getSelectedDokumentarten() {
		return selectedDokumentarten;
	}

	/**
	 * @param selectedDokumentarten
	 *            the selectedDokumentarten to set
	 */
	public void setSelectedDokumentarten(Dokumentart[] selectedDokumentarten) {
		// if (!aktuellesArchivale.getDokumentarten().isEmpty()) {
		// selectedDokumentarten=(Dokumentart[])
		// aktuellesArchivale.getDokumentarten().toArray();
		// }
		this.selectedDokumentarten = selectedDokumentarten;
	}
}
