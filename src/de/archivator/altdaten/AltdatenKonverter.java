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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.archivator.altdaten.model.Dataroot;
import de.archivator.altdaten.model.TabelleX0020Archiv;
import de.archivator.entities.Archivale;
import de.archivator.entities.Organisationseinheit;

/**
 * Konvertiert die Altdaten des Archivs des Lette-Vereins
 * aus einer XML-Datei in die neue Datenbank.
 * @author e0_naumann
 * @author burghard.britzke
 * 
 */
public class AltdatenKonverter {

	List<TabelleX0020Archiv> tabelle;
	EntityManagerFactory emf;
	Archivale archivale= new Archivale();

	/**
	 * Erzeugt einen AltdatenKonverter,
	 * der Daten aus einer XML-Datei liest
	 * und diese in Java-Objekte umwandelt.
	 */
	public AltdatenKonverter() {
		emf = Persistence.createEntityManagerFactory("archivator");
		
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
		//me.extractOrganisationseinheiten();
		me.extractArchivale(me);
	}

	public void extractArchivale(AltdatenKonverter me){
		
//		me.extractSchubfach();
//		me.extractMappe();
//		me.extractInhalt();
//		me.extractBetreff();
//		me.extractVonDatum();
//		me.extractBisDatum();
		extractDatum();
	}
	/**
	 * Extrahiert die Organisationseinheiten aus den Altdaten.
	 * und wird sie zukünftig hoffentlich in die Datenbank speichern.
	 */
	private void extractOrganisationseinheiten() {
		List<String> organisationseinheiten = new ArrayList<String>();
		for (TabelleX0020Archiv altArchivale : tabelle) {
			String abteilung = altArchivale.getAbteilung();
			if (abteilung != null) {
				abteilung = abteilung.replaceAll("\n", "");
				String[] abteilungen = {abteilung};
				if (abteilung.indexOf(",") != -1) {
					abteilungen = abteilung.split(",");
				} else if (abteilung.indexOf("/") != -1) {
					abteilungen = abteilung.split("/");
				}
				boolean abteilungVorhanden = false;
				for (String einzelAbteilung : abteilungen) {
					einzelAbteilung=einzelAbteilung.trim();
					for (String organisatonseinheit : organisationseinheiten) {
						if (einzelAbteilung.equals(organisatonseinheit)) {
							abteilungVorhanden = true;
						}
					}
					if (!abteilungVorhanden) {
						organisationseinheiten.add(einzelAbteilung);
					}
				}//TODO else leere Datensätze werden übersprungen
			}
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		System.out.println(organisationseinheiten.size());
		for (String organisationsheinheit : organisationseinheiten) {
			System.out.println(organisationsheinheit);
			Organisationseinheit o= new Organisationseinheit(organisationsheinheit);
			o=em.merge(o);
		}
		et.commit();
		em.close();
	}

	/**
	 * Extrahiert die Schubfachnummernn aus den Altdaten.
	 * um sie in die Datenbank zuspeichern.
	 */
	private void extractSchubfach(){
		List<Integer> schubfaecher = new ArrayList<Integer>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			int schubfach = altarchivale.getSchubfachX0020Nummer();
			if(schubfach != 0){
				
				schubfaecher.add(schubfach);
			}//TODO else leere Datensätze werden übersprungen
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		System.out.println(schubfaecher.size());
		for (int schubfach : schubfaecher) {
			Archivale a= new Archivale();
			a.setSchubfach(schubfach);
			a=em.merge(a);
		}
		et.commit();
		em.close();
		
	}
	/**
	 * Extrahiert die Objektnummern aus den Altdaten.
	 * um sie als Mappennummer in die Datenbank zuspeichern.
	 */
	private void extractMappe() {		
		List<Integer> mappen = new ArrayList<Integer>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			String mappe = altarchivale.getObjektX0020Nummer();
			if(mappe != null){
				String[]mappenTeile = mappe.split("/");
				String mappenString=mappenTeile[1];
				mappen.add(Integer.parseInt(mappenString));
			}
		}//TODO else leere Datensätze werden übersprungen
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		System.out.println(mappen.size());
		for (int mappe : mappen) {
			Archivale a= new Archivale();
			a.setMappe(mappe);
			a=em.merge(a);
		}
		et.commit();
		em.close();
	}
	
	/**
	 * Extrahiert den Inhalt aus den Altdaten um ihn in die Datenbank zu speichern
	  */
	private void extractInhalt() {		
		List<String> inhalte = new ArrayList<String>();
		for (TabelleX0020Archiv altArchivale : tabelle) {
			String inhalt = altArchivale.getInhalt();
			if (inhalt != null) {
					inhalt= inhalt.trim();
					inhalte.add(inhalt);
			}//TODO else leere Datensätze werden übersprungen
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (String inhalt : inhalte) {
			Archivale a= new Archivale();
			a.setInhalt(inhalt);
			a=em.merge(a);
		}
		et.commit();
		em.close();
	}
	
	/**
	 * Extrahiert den Betreff aus den Altdaten um ihn in die Datenbank zu speichern
	  */
	private void extractBetreff(){
		List<String> betreffe = new ArrayList<String>();
		for (TabelleX0020Archiv altArchivale : tabelle) {
			String betreff = altArchivale.getBetreff();
			if (betreff != null) {
					betreff= betreff.trim();
					betreffe.add(betreff);
			}//TODO else leere Datensätze werden übersprungen
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (String betreff : betreffe) {
			Archivale a= new Archivale();
			a.setBetreff(betreff);
			a=em.merge(a);
		}
		et.commit();
		em.close();
		
	}
	
	/**
	 * Extrahiert das Datum aus den Altdaten um sie in die Datenbank zu speichern
	 */
		
	private void extractDatum(){
		List<Integer> vonDaten = new ArrayList<Integer>();
		List<Integer> bisDaten = new ArrayList<Integer>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			int datum1 = datumsUmwandlung(altarchivale.getDatumX00201());
			int datum2 = datumsUmwandlung(altarchivale.getDatumX00202());
			int datum3 = datumsUmwandlung(altarchivale.getDatumX00203());
			
			if (datum1 != 0) { 				// ist 1.datum vorhanden ist es immer vonJahr in der Archivalie
					vonDaten.add(datum1);
				if (datum2 != 0) {			//  ist 2.Datum vorhanden wird überprüft ob es ein drittes Datum gibt,
					if (datum3 != 0) {		//  das grössere von beiden wird als bisJahr in die Datenbank eingetragen
						if (datum2 > datum3)
							bisDaten.add(datum2);
						else
							bisDaten.add(datum3);
					} else
						bisDaten.add(datum2);
				} else						// gibt es weder 2tes oder 3tes datum ist vonJahr = bisJahr
					bisDaten.add(datum1);

			} 
			else{						// ist kein 1.datum vorhanden wird überprüft ob es ein 2tes oder 3tes gibt ansonsten wird 0 eingetragen
					if (datum2 > datum3)
						bisDaten.add(datum2);
					else
						bisDaten.add(datum3);
			}
		}
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (int vonDatum : vonDaten) {
			archivale.setVonJahr(vonDatum);
			archivale=em.merge(archivale);
		}
		for (int bisDatum : bisDaten) {
			archivale.setVonJahr(bisDatum);
			archivale=em.merge(archivale);
		}
		et.commit();
		em.close();
	}
	
	/**
	 * Wandelt das Datum von String in ganzzahlige Integerwerte um
	 * @param datum
	 * @return umgewandeltes Datum
	 */
	private int datumsUmwandlung(String datum){
		int Daten =0;
	
		if(datum != null){
			datum=datum.trim();
			if(datum.indexOf("/") != -1){
				String[] datenTeil = datum.split("/");
				//bsp. 1978/1979
				if(datenTeil[1].length()==4){
				String datenString=datenTeil[(datenTeil.length)-1];
				datenString = datenString.trim();
				Daten=Integer.parseInt(datenString);
				}
				// bsp. 1978/79
				else if(datenTeil[1].length()==2){
					String datumsJahrhundert=datenTeil[0].substring(0,2);
					String datenString = datumsJahrhundert+datenTeil[1];
					Daten=Integer.parseInt(datenString);
				}
			}
			// bsp. ca 1956
			else if (datum.indexOf("ca") != -1) {
				datum = datum.replace("ca","");
				datum = datum.trim();	
				// bsp. ca. 1956
				if(datum.indexOf(".") != -1) {
					datum = datum.replace(".","");
					datum = datum.trim();	
				}
				Daten = Integer.parseInt(datum);
			}
			// bsp. um 1956
			else if (datum.indexOf("um") != -1) {
				datum = datum.replace("um","");
					datum = datum.trim();			
				Daten = Integer.parseInt(datum);
			}
			else if((datum.indexOf(".-") != -1)) {	// wird .- werwendet gibt es dazu keine jahresangabe
				Daten = 0;
			}
			else if(datum.indexOf("-") != -1) {
				String[] datenTeil = datum.split("-");
				//bsp 1978-1979
				if(datenTeil[1].length()==4){
				String datenString=datenTeil[datenTeil.length-1];
				datenString = datenString.trim();
				Daten= Integer.parseInt(datenString);}
				//bsp 1978-79
				else if(datenTeil[1].length()==2){
					String datumsJahrhundert=datenTeil[0].substring(0,2);
					String datenString = datumsJahrhundert+datenTeil[1];
					Daten = Integer.parseInt(datenString);
				}
			} 
			// bsp. 30.03.1933
			else if (datum.indexOf(".") != -1) {						
					String datenString=datum.substring(6);
					datenString = datenString.trim();
					Daten = Integer.parseInt(datenString);									
			}	
		}
		else return 0; // wenn es keinen Eintrag gibt wird 0 zurück gegeben
	
		return Daten;
	}
	
	
}
