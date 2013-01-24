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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.archivator.altdaten.model.Dataroot;
import de.archivator.altdaten.model.TabelleX0020Archiv;
import de.archivator.entities.Archivale;

/**
 * Konvertiert die Altdaten des Archivs des Lette-Vereins
 * aus einer XML-Datei in die neue Datenbank.
 * @author e0_naumann
 * @author burghard.britzke
 * 
 */
public class AltdatenKonverter {

	List<TabelleX0020Archiv> tabelle;

	/**
	 * Erzeugt einen AltdatenKonverter,
	 * der Daten aus einer XML-Datei liest
	 * und diese in Java-Objekte umwandelt.
	 */
	public AltdatenKonverter() {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext
					.newInstance("de.archivator.altdaten.model");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setEventHandler(new AltdatenValidationEventHandler());
			Dataroot archive = (Dataroot) unmarshaller.unmarshal(new File(
					"altdaten/Archivdatenbank.xml"));
			tabelle = archive.getTabelleX0020Archiv();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Haupteinstiegspunkt in das Programm.
	 * Die Altdaten werden aus einer XML-Datei gelesen
	 * und in die Datenbank gespeichert.
	 *  
	 * @param args Wird nicht beachtet.
	 */
	public static void main(String[] args) {
		AltdatenKonverter me = new AltdatenKonverter();
		me.extractOrganisationseinheiten();
	}

	/**
	 * Extrahiert die Organisationseinheiten aus den Altdaten.
	 * und wird sie  zukünftig hoffentlich in die Datenbank speichern.
	 */
	private void extractOrganisationseinheiten() {
		List<String> organisationseinheiten = new ArrayList<String>();
		for (TabelleX0020Archiv altArchivale : tabelle) {
			String abteilung = altArchivale.getAbteilung();
			if (abteilung != null) {
				// an nicht vorkommenden Zeichen splitten,
				// damit der Standardfall (eine Organisationseinheit) 
				// abgedeckt ist.
				String[] abteilungen = abteilung.split("∂");
				if (abteilung.indexOf(",") != -1) {
					abteilungen = abteilung.split(",");
				} else if (abteilung.indexOf("/") != -1) {
					abteilungen = abteilung.split("/");
				}
				abteilung = abteilung.trim();
				boolean abteilungVorhanden = false;
				for (String einzelAbteilung : abteilungen) {
					for (String organisatonseinheit : organisationseinheiten) {
						if (einzelAbteilung.equals(organisatonseinheit)) {
							abteilungVorhanden = true;
						}
					}
					if (!abteilungVorhanden) {
						organisationseinheiten.add(einzelAbteilung);
					}
				}
			}
		}
		System.out.println(organisationseinheiten.size());
		for (String organisationsheinheit : organisationseinheiten) {
			System.out.println(organisationsheinheit);
		}
	}

	/**
	 * Methode, um die umgewandelten Daten
	 * in die neue Datenbank zu speichern.
	 */
	public void archivLaden() {
		Archivale archiv = new Archivale();

//		archiv.setSchubfach(tabelle.getSchubfachX0020Nummer());
//		archiv.setBetreff(tabelle.getBetreff());
//		archiv.setInhalt(tabelle.getInhalt());

	}

}
