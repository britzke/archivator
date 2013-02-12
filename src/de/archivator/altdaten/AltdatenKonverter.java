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
import java.util.Arrays;
import java.util.Collections;
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
		me.extractOrganisationseinheiten();
		me.extractArchivale();
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
	 * Extrahiert die Archivalieneigenschaften aus den Altdaten um sie in die Datenbank zu speichern
	 */
	private void extractArchivale(){
		List<Archivale> archivalienSammlung = new ArrayList<Archivale>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			Archivale archivale = new Archivale();
			List<Integer> daten = new ArrayList<Integer>();
			datumsUmwandlung(altarchivale.getDatumX00201(),daten);
			datumsUmwandlung(altarchivale.getDatumX00202(),daten);
		    datumsUmwandlung(altarchivale.getDatumX00203(),daten);
						
			if(daten.size() !=0){ 
			Collections.sort(daten); // sortiert die einträge in der Liste
			archivale.setVonJahr(daten.get(0)); //speichert den ersten Eintarg(niedrigstes Jahr) aus der Liste
			archivale.setBisJahr(daten.get(daten.size()-1)); // speichert den letzten Eintrag(höchstes Jahr) aus der Liste
			}
					
			String betreff = altarchivale.getBetreff(); // extrahiert alle Betreffe aus der altdatenxml
			if (betreff != null) {
					betreff= betreff.trim();
					archivale.setBetreff(betreff);
			}
			String inhalt = altarchivale.getInhalt(); // extrahiert alle Inhalte aus der altdatenxml
			if (inhalt != null) {
					inhalt= inhalt.trim();
					archivale.setInhalt(inhalt);
			}
			String mappe = altarchivale.getObjektX0020Nummer(); // extrahiert alle Mappennummern aus der altdatenxml
			if(mappe != null){
				String[]mappenTeile = mappe.split("/");
				String mappenString=mappenTeile[1];
				archivale.setMappe(Integer.parseInt(mappenString));
			}
			int schubfach = altarchivale.getSchubfachX0020Nummer();// extrahiert alle Schubfachnummern aus der altdatenxml
			if(schubfach != 0){
				archivale.setSchubfach(schubfach);
			}
						
			archivalienSammlung.add(archivale);
		}
		EntityManager em = emf.createEntityManager();
		for (Archivale archivale : archivalienSammlung) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			archivale=em.merge(archivale);
			et.commit();
		}
		em.close();
	}
	
	
	/**
	 * Wandelt das Datum von String in ganzzahlige Integerwerte um und speichert sie in eine Liste
	 * @param datum
	 * @param daten
	 * @return Liste mit den extrahierten und umgewandelten Jahreszahlen
	 */
	private List<Integer> datumsUmwandlung(String datum,List<Integer> daten){
		if(datum != null){
			datum=datum.trim();
			if(datum.indexOf("/") != -1){
				String[] datenTeil = datum.split("/");
				for(int i=0;i<datenTeil.length;i++){
				//bsp. 1978/1979
					if(datenTeil[i].length()==4){
						String datenString=datenTeil[i];
						datenString = datenString.trim();
						daten.add(Integer.parseInt(datenString));
					}
				// bsp. 1978/79
					else if(datenTeil[i].length()==2){
						String datumsJahrhundert=datenTeil[0].substring(0,2);
						String datenString = datumsJahrhundert+datenTeil[i];
						daten.add(Integer.parseInt(datenString));
					}
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
				daten.add(Integer.parseInt(datum));
			}
			// bsp. um 1956
			else if (datum.indexOf("um") != -1) {
				datum = datum.replace("um","");
					datum = datum.trim();			
					daten.add(Integer.parseInt(datum));
			}
			else if((datum.indexOf(".-") != -1)) {	// wird .- verwendet gibt es dazu keine jahresangabe, deswegen nicht konvertierbar
				return daten;
			}
			else if(datum.indexOf("-") != -1) {
				String[] datenTeil = datum.split("-");
				for(int i=0;i<datenTeil.length;i++){
				//bsp 1978-1979
					if(datenTeil[i].length()==4){
						String datenString=datenTeil[i];
						datenString = datenString.trim();
						daten.add(Integer.parseInt(datenString));}
				//bsp 1978-79
					else if(datenTeil[i].length()==2){
						String datumsJahrhundert=datenTeil[0].substring(0,2);
						String datenString = datumsJahrhundert+datenTeil[i];
						daten.add(Integer.parseInt(datenString));
					}
				}
			} 
			// bsp. 30.03.1933
			else if (datum.indexOf(".") != -1) {						
					String datenString=datum.substring(6);
					datenString = datenString.trim();
					daten.add(Integer.parseInt(datenString));	
			}
		else{ daten.add(Integer.parseInt(datum));}
		}	
		return daten;
	}
	
}
