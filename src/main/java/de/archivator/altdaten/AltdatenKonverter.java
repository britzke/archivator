/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  e0_naumann
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
package de.archivator.altdaten;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import de.archivator.altdaten.model.Dataroot;
import de.archivator.altdaten.model.TabelleX0020Archiv;
import de.archivator.entities.Archivale;
import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Körperschaft;
import de.archivator.entities.Schlagwort;

/**
 * Konvertiert die Altdaten des Archivs des Lette-Vereins aus einer XML-Datei in
 * die neue Datenbank.
 * 
 * @author e0_naumann
 * @author e0_schulz
 * @author mueller
 * @author burghard.britzke bubi@charmides.in-berlin.de
 * 
 */
public class AltdatenKonverter {

	List<TabelleX0020Archiv> tabelle;
	static EntityManagerFactory emf;
	EntityManager em;
	Archivale archivale = new Archivale();
	String[] schulen = {
			"<falsche Wert>", // Zählung in den Altdaten fängt mit 1 an
			"Lette-Verein", "GBF", "FGM", "TBF", "HBF", "KBF", "PhoLA",
			"Setzerinnen-Schule", "Kunsthandarbeitsschule",
			"Fortbildungsschule", "Kunstgewerbeschule",
			"Fachschneiderei-Schule", "Werkstätte für Putz", "Handelsschule",
			"PFH", "sonstige", "Lette-Kolonie" };

	/**
	 * Erzeugt einen AltdatenKonverter, der Daten aus einer XML-Datei liest und
	 * diese in Java-Objekte umwandelt.
	 */
	public AltdatenKonverter(InputStream is) {
		emf = Persistence.createEntityManagerFactory("archivator");

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext
					.newInstance("de.archivator.altdaten.model");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setEventHandler(new AltdatenValidationEventHandler());
			Dataroot archive = (Dataroot) unmarshaller.unmarshal(is);
			tabelle = archive.getTabelleX0020Archiv();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Haupteinstiegspunkt in das Programm. Die Altdaten werden aus einer
	 * XML-Datei gelesen und in die Datenbank gespeichert.
	 * 
	 * @param args
	 *            Wird nicht beachtet.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		AltdatenKonverter me = new AltdatenKonverter(new FileInputStream(
				"altdaten/Archivdatenbank.xml"));

		me.extractArchivale();

		CompassConfiguration conf = new CompassConfiguration().configure();
		conf.addClass(Archivale.class);
		conf.addClass(Dokumentart.class);
		conf.addClass(Name.class);
		conf.addClass(Organisationseinheit.class);
		conf.addClass(Schlagwort.class);
		Compass compass = conf.buildCompass();

		CompassGps gps = new SingleCompassGps(compass);
		CompassGpsDevice jpaDevice = new JpaGpsDevice("jpa", emf);
		gps.addGpsDevice(jpaDevice);
		gps.start();
		gps.index();
	}

	/**
	 * Extrahiert die Archivalieneigenschaften aus den Altdaten um sie in die
	 * Datenbank zu speichern
	 */
	public void extractArchivale() {
		em = emf.createEntityManager();

		for (TabelleX0020Archiv altarchivale : tabelle) {
			Archivale archivale = new Archivale();
			List<Integer> daten = new ArrayList<Integer>();
			datumsUmwandlung(altarchivale.getDatumX00201(), daten);
			datumsUmwandlung(altarchivale.getDatumX00202(), daten);
			datumsUmwandlung(altarchivale.getDatumX00203(), daten);

			if (daten.size() != 0) {
				// sortiert die einträge in der Liste
				Collections.sort(daten);
				// speichert den ersten Eintarg(niedrigstes Jahr) aus der Liste
				archivale.setVonJahr(daten.get(0));
				// speichert den letzten Eintrag(höchstes Jahr) aus der Liste
				archivale.setBisJahr(daten.get(daten.size() - 1));
			}
			// extrahiert alle Betreffe aus der altdatenxml
			String betreff = altarchivale.getBetreff();
			if (betreff != null) {
				betreff = betreff.trim();
				archivale.setBetreff(betreff);
			}
			// extrahiert alle Inhalte aus der altdatenxml
			String inhalt = altarchivale.getInhalt();
			if (inhalt != null) {
				inhalt = inhalt.trim();
				archivale.setInhalt(inhalt);
			}
			// extrahiert alle Mappennummern aus der altdatenxml
			String mappe = altarchivale.getObjektX0020Nummer();
			if (mappe != null) {
				String[] mappenTeile = mappe.split("/");
				String mappenString = mappenTeile[1];
				archivale.setMappe(Integer.parseInt(mappenString));
			}
			// extrahiert alle Schubfachnummern aus der altdatenxml
			int schubfach = altarchivale.getSchubfachX0020Nummer();
			if (schubfach != 0) {
				archivale.setSchubfach(schubfach);
			}

			EntityTransaction et = em.getTransaction();
			et.begin();
			archivale = em.merge(archivale);

			// Dokumentart(en) hinzufügen
			Query q = em.createQuery("select d from Dokumentart d");
			@SuppressWarnings("unchecked")
			List<Dokumentart> databaseDokumentarten = q.getResultList();
			addArchivaleDokumentart(databaseDokumentarten, archivale,
					altarchivale.getDokumentenartX00201());
			addArchivaleDokumentart(databaseDokumentarten, archivale,
					altarchivale.getDokumentenartX00202());
			addArchivaleDokumentart(databaseDokumentarten, archivale,
					altarchivale.getDokumentenartX00203());

			// Organisationseinheit(en) hinzufügen
			q = em.createQuery("select o from Organisationseinheit o");
			@SuppressWarnings("unchecked")
			List<Organisationseinheit> databaseOrganisationeinheiten = q
					.getResultList();
			addArchivaleOrganisationseinheit(databaseOrganisationeinheiten,
					archivale, altarchivale.getAbteilung());

			int schule = altarchivale.getSchule().intValue();
			if (schule != 0) {
				addArchivaleOrganisationseinheit(databaseOrganisationeinheiten,
						archivale, schulen[schule]);
			}
			// Namen hinzufügen
			q = em.createQuery("select n from Name n");
			@SuppressWarnings("unchecked")
			List<Name> databaseNames = q.getResultList();
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00201());
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00202());
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00203());
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00204());
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00205());
			addArchivaleName(databaseNames, archivale,
					altarchivale.getNameX00206());

			// Schlagworte hinzufügen
			q = em.createQuery("select s from Schlagwort s");
			@SuppressWarnings("unchecked")
			List<Schlagwort> databaseSchlagwörter = q.getResultList();
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00201());
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00202());
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00203());
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00204());
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00205());
			addArchivaleSchlagwort(databaseSchlagwörter, archivale,
					altarchivale.getSchlagwortX00206());

			et.commit();
		}
		em.close();
	}

	/**
	 * Fügt dem Archivale eine Dokumentart hinzu. Existiert die Dokumentart in
	 * der Datenbank noch nicht, so wird sie der Datenbank hinzugefügt,
	 * ansonsten wird die bestehende Dokumentart referenziert.
	 * 
	 * @param databaseDokumentarten
	 *            Liste von Dokumentarten aus der Datenbank.
	 * @param archivale
	 *            Das Archviale, dem die Dokumentart hinzugefügt werden soll.
	 * @param dokumentenartString
	 *            Dokumentart, die dem Archivale hinzugefügt werden soll.
	 */
	private void addArchivaleDokumentart(
			List<Dokumentart> databaseDokumentarten, Archivale archivale,
			String dokumentartName) {
		if (dokumentartName != null) {
			List<Dokumentart> archivaleDokumentarten = archivale
					.getDokumentarten();
			dokumentartName = dokumentartName.replaceAll("\n", "");
			String[] dokumentartNamen = { dokumentartName };
			if (dokumentartName.indexOf(",") != -1) {
				dokumentartNamen = dokumentartName.split(",");
			} else if (dokumentartName.indexOf("/") != -1) {
				dokumentartNamen = dokumentartName.split("/");
			}
			for (String daName : dokumentartNamen) {
				Dokumentart dokumentart = new Dokumentart(daName.trim());
				for (Dokumentart databaseDokumentart : databaseDokumentarten) {
					if (dokumentart.equals(databaseDokumentart)) {
						dokumentart = databaseDokumentart;
						break; // das erste wird genommen
					}
				}
				if (dokumentart.getId() == 0) {
					databaseDokumentarten.add(dokumentart);
				}
				boolean schonVorhanden = false;
				for (Dokumentart archivaleDokumentart : archivaleDokumentarten) {
					if (dokumentart.equals(archivaleDokumentart)) {
						schonVorhanden = true;
						break;
					}
				}
				if (!schonVorhanden) {
					archivaleDokumentarten.add(dokumentart);
					dokumentart.getArchivalien().add(archivale);
				}
			}
		}
	}

	/**
	 * Fügt dem Archivale eine Organisationseinheit hinzu. Existiert die
	 * Organisationseinheit in der Datenbank noch nicht, so wird sie der
	 * Datenbank hinzugefügt, ansonsten wird die bestehende Organisationseinheit
	 * refernziert.
	 * 
	 * @param databaseOrganisationeinheiten
	 *            Die Organisationeinheiten die in der Datenbank schon vorhanden
	 *            sind.
	 * @param archivale
	 *            Das Archivale, dem die Organisationseinheit hinzugefügt wird.
	 * @param organisationseinheitName
	 *            Der Name der Organisationseinheit.
	 */
	private void addArchivaleOrganisationseinheit(
			List<Organisationseinheit> databaseOrganisationeinheiten,
			Archivale archivale, String organisationseinheitName) {
		if (organisationseinheitName != null) {
			organisationseinheitName = organisationseinheitName.replaceAll(
					"\n", "");
			String[] organisationseinheitenNamen = { organisationseinheitName };
			if (organisationseinheitName.indexOf(",") != -1) {
				organisationseinheitenNamen = organisationseinheitName
						.split(",");
			} else if (organisationseinheitName.indexOf("/") != -1) {
				organisationseinheitenNamen = organisationseinheitName
						.split("/");
			}
<<<<<<< HEAD
			for (String koerpName : koerperschaftenNamen) {
				List<Körperschaft> archivaleKoerperschaften = archivale
						.getKörperschaften();
				Körperschaft körperschaft = new Körperschaft(
						koerpName.trim());
				for (Körperschaft databaseKoerperschaft : databaseKoerperschaften) {
					if (körperschaft
							.equals(databaseKoerperschaften)) {
						körperschaft = databaseKoerperschaft;
=======
			for (String oeName : organisationseinheitenNamen) {
				List<Organisationseinheit> archivaleOrganisationseinheiten = archivale
						.getOrganisationseinheiten();
				Organisationseinheit organisationseinheit = new Organisationseinheit(
						oeName.trim());
				for (Organisationseinheit databaseOrganisationseinheit : databaseOrganisationeinheiten) {
					if (organisationseinheit
							.equals(databaseOrganisationseinheit)) {
						organisationseinheit = databaseOrganisationseinheit;
>>>>>>> branch 'master' of https://github.com/britzke/archivator.git
						break; // nur der erste Treffer wird genommen
					}
				}
				if (organisationseinheit.getId() == 0) {
					databaseOrganisationeinheiten.add(organisationseinheit);
				}
				boolean schonVorhanden = false;
				for (Organisationseinheit archivaleOrganisationseinheit : archivaleOrganisationseinheiten) {
					if (organisationseinheit
							.equals(archivaleOrganisationseinheit)) {
						schonVorhanden = true;
						break;
					}
				}
				if (!schonVorhanden) {
					archivaleOrganisationseinheiten.add(organisationseinheit);
					organisationseinheit.getArchivalien().add(archivale);
				}
			}
		}
	}

	/**
	 * Extrahiert einen Name-Objekt aus einer Zeichenkette.
	 * 
	 * @param nameString
	 *            Zeichenkette mit einem Namen. Die Zeichenkette darf nicht null
	 *            sein.
	 * @return Das Name-Objekt.
	 */
	private Name extractName(String nameString) {
		String nachname = nameString.split(",").length > 0 ? nameString
				.split(",")[0].trim() : null;
		String vorname = nameString.split(",").length > 1 ? nameString
				.split(",")[1].trim() : null;
		Name actualName = new Name(nachname, vorname);
		return actualName;
	}

	/**
	 * Fügt einem Archivale den angegebenen Namen hinzu, wenn dieser nicht null
	 * ist.
	 * 
	 * @param databaseNames
	 *            Liste von Namen, die in der Datenbank vorhanden sind.
	 * @param archivale
	 *            Das Archivale, dem der Name hinzugefügt werden soll.
	 * @param nameString
	 *            Der Name, der dem Archivale hinzugefügt werden soll.
	 */
	private void addArchivaleName(List<Name> databaseNames,
			Archivale archivale, String nameString) {
		if (nameString != null) {
			List<Name> namen = archivale.getNamen();
			Name name = extractName(nameString);
			for (Name databaseName : databaseNames) {
				if (name.equals(databaseName)) {
					name = databaseName;
				}
			}
			if (name.getId() == 0) {
				databaseNames.add(name);
			}
			namen.add(name);
			name.getArchivalien().add(archivale);
		}
	}

	/**
	 * Fügt dem Archivale ein Schlagwort hinzu. Existiert das Schlagwort in der
	 * Datenbank noch nicht, so wird es der Datenbank hinzugefügt, ansonsten
	 * wird das bestehende Schlagwort referenziert.
	 * 
	 * @param databaseSchlagwörter
	 *            Liste aller Schlagwörter, die bereits in der Datenbank sind.
	 * @param archivale
	 *            Das Archivale, dem das Schlagwort zugeordnet werden soll.
	 * @param schlagwortString
	 *            Das Schlagwort, das dem Archivale zugeordnet werden soll.
	 */
	private void addArchivaleSchlagwort(List<Schlagwort> databaseSchlagwörter,
			Archivale archivale, String schlagwortString) {
		if (schlagwortString != null) {
			List<Schlagwort> archivaleSchlagwörter = archivale
					.getSchlagwörter();
			Schlagwort schlagwort = new Schlagwort(schlagwortString);
			for (Schlagwort databaseSchlagwort : databaseSchlagwörter) {
				if (schlagwort.equals(databaseSchlagwort)) {
					schlagwort = databaseSchlagwort;
					break; // das erste wird genommen
				}
			}
			if (schlagwort.getId() == 0) {
				databaseSchlagwörter.add(schlagwort);
			}
			boolean schonVorhanden = false;
			for (Schlagwort archivaleSchlagwort : archivaleSchlagwörter) {
				if (schlagwort.equals(archivaleSchlagwort)) {
					schonVorhanden = true;
					break;
				}
			}
			if (!schonVorhanden) {
				archivaleSchlagwörter.add(schlagwort);
				schlagwort.getArchivalien().add(archivale);
			}
		}
	}

	/**
	 * Wandelt das Datum von String in ganzzahlige Integerwerte um und speichert
	 * sie in eine Liste
	 * 
	 * @param datum
	 * @param daten
	 * @return Liste mit den extrahierten und umgewandelten Jahreszahlen
	 */
	private List<Integer> datumsUmwandlung(String datum, List<Integer> daten) {
		if (datum != null) {
			datum = datum.trim();
			if (datum.indexOf("/") != -1) {
				String[] datenTeil = datum.split("/");
				for (int i = 0; i < datenTeil.length; i++) {
					// bsp. 1978/1979
					if (datenTeil[i].length() == 4) {
						String datenString = datenTeil[i];
						datenString = datenString.trim();
						daten.add(Integer.parseInt(datenString));
					}
					// bsp. 1978/79
					else if (datenTeil[i].length() == 2) {
						String datumsJahrhundert = datenTeil[0].substring(0, 2);
						String datenString = datumsJahrhundert + datenTeil[i];
						daten.add(Integer.parseInt(datenString));
					}
				}
			}
			// bsp. ca 1956
			else if (datum.indexOf("ca") != -1) {
				datum = datum.replace("ca", "");
				datum = datum.trim();
				// bsp. ca. 1956
				if (datum.indexOf(".") != -1) {
					datum = datum.replace(".", "");
					datum = datum.trim();
				}
				daten.add(Integer.parseInt(datum));
			}
			// bsp. um 1956
			else if (datum.indexOf("um") != -1) {
				datum = datum.replace("um", "");
				datum = datum.trim();
				daten.add(Integer.parseInt(datum));
			} else if ((datum.indexOf(".-") != -1)) { // wird .- verwendet gibt
														// es dazu keine
														// jahresangabe,
														// deswegen nicht
														// konvertierbar
				return daten;
			} else if (datum.indexOf("-") != -1) {
				String[] datenTeil = datum.split("-");
				for (int i = 0; i < datenTeil.length; i++) {
					// bsp 1978-1979
					if (datenTeil[i].length() == 4) {
						String datenString = datenTeil[i];
						datenString = datenString.trim();
						daten.add(Integer.parseInt(datenString));
					}
					// bsp 1978-79
					else if (datenTeil[i].length() == 2) {
						String datumsJahrhundert = datenTeil[0].substring(0, 2);
						String datenString = datumsJahrhundert + datenTeil[i];
						daten.add(Integer.parseInt(datenString));
					}
				}
			}
			// bsp. 30.03.1933
			else if (datum.indexOf(".") != -1) {
				String datenString = datum.substring(6);
				datenString = datenString.trim();
				daten.add(Integer.parseInt(datenString));
			} else {
				daten.add(Integer.parseInt(datum));
			}
		}
		return daten;
	}
}
