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
		
		me.extractSchubfach();
		me.extractMappe();
		me.extractInhalt();
		me.extractBetreff();
		//me.extractVonDatum();
		me.extractBisDatum();
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
			}
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
		}
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
			}
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
			}
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
	 * Extrahiert das Datum aus den Altdaten um ihn als vonJahr in die Datenbank zu speichern
	  */
	private void extractVonDatum(){
		
		List<Integer> vonDaten = new ArrayList<Integer>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			String vonDatum = altarchivale.getDatumX00201();
			if(vonDatum != null){
				vonDatum=vonDatum.trim();
				// bsp. 1945-1956
				if (vonDatum.indexOf("-") != -1) {
					String[] datenTeil = vonDatum.split("-");
					String datenString=datenTeil[0];
					vonDaten.add(Integer.parseInt(datenString));
				}
				// bsp. 1945/19546
				else if(vonDatum.indexOf("/") != -1) {
					String[] datenTeil = vonDatum.split("/");
					String datenString=datenTeil[0];
					vonDaten.add(Integer.parseInt(datenString));
				}
				// bsp. ca. 1956
				else if (vonDatum.indexOf("ca") != -1) {
					vonDatum = vonDatum.replace("ca","");
						vonDatum = vonDatum.trim();			
					vonDaten.add(Integer.parseInt(vonDatum));
				}
				// bsp. um 1956
				else if (vonDatum.indexOf("um") != -1) {
					vonDatum = vonDatum.replace("um","");
						vonDatum = vonDatum.trim();			
					vonDaten.add(Integer.parseInt(vonDatum));
				}
				// bsp. 30.03.1933
				else if(vonDatum.indexOf(".") != -1) {									// AN dieser Stelle gibt es noch eine Fehlermeldung
					// bsp. 28.-29.08 ohne Jahr
						if(vonDatum.indexOf("-") != -1){
							String datenOhneJahr="0";
							vonDaten.add(Integer.parseInt(datenOhneJahr));			
						}
						else if(vonDatum.indexOf(" ") != -1){
						
							String datenOhneJahr="0";
							vonDaten.add(Integer.parseInt(datenOhneJahr));	
						}
						else {						
							String datenString=vonDatum.substring(6);
//							String[] datenTeilmitDot = vonDatum.split(".");
//							System.out.println(vonDatum+" 4 "+ datenTeilmitDot.length);
//							
//							String datenString=	datenTeilmitDot[datenTeilmitDot.length-1];
							vonDaten.add(Integer.parseInt(datenString));
							
						}							
				}
				else{vonDaten.add(Integer.parseInt(vonDatum)); }
			}
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (int vonDatum : vonDaten) {
			Archivale a= new Archivale();
			a.setVonJahr(vonDatum);
			a=em.merge(a);
		}
		et.commit();
		em.close();
		
	}
	
	/**
	 * Extrahiert das Datum aus den Altdaten um ihn  als bis Jahr in die Datenbank zu speichern
	  */
	private void extractBisDatum(){
		List<Integer> bisDaten = new ArrayList<Integer>();
		for (TabelleX0020Archiv altarchivale: tabelle){
			//wenn es einen zweiten datumseintrag gibt und keinen dritten
			if((altarchivale.getDatumX00202() != null)&&(altarchivale.getDatumX00203() == null)){
				String bisDatum = altarchivale.getDatumX00202();
				if (bisDatum.indexOf("-") != -1) {
					String[] datenTeil = bisDatum.split("-");
					String datenString=datenTeil[datenTeil.length-1];
					datenString = datenString.trim();
					bisDaten.add(Integer.parseInt(datenString));
				}
				else if(bisDatum.indexOf("/") != -1){
					String[] datenTeil = bisDatum.split("/");
					//bsp. 1978/1979
					if(datenTeil[1].length()==4){
					String datenString=datenTeil[(datenTeil.length)-1];
					datenString = datenString.trim();
					bisDaten.add(Integer.parseInt(datenString));
					}
					// bsp. 1978/79
					else if(datenTeil[1].length()==2){
						String datumsJahrhundert=datenTeil[0].substring(0,2);
						String datenString = datumsJahrhundert+datenTeil[1];
						bisDaten.add(Integer.parseInt(datenString));
					}
				}
			else{
				bisDaten.add(Integer.parseInt(bisDatum));
				}
			}
			if(altarchivale.getDatumX00203() != null){
				String bisDatum = altarchivale.getDatumX00203();
				if (bisDatum.indexOf("-") != -1) {
					String[] datenTeil = bisDatum.split("-");
					String datenString=datenTeil[(datenTeil.length)-1];
					datenString = datenString.trim();
					bisDaten.add(Integer.parseInt(datenString));
				}
				else if(bisDatum.indexOf("/") != -1){
					String[] datenTeil = bisDatum.split("/");
					//bsp. 1978/1979
					if(datenTeil[1].length()==4){
					String datenString=datenTeil[(datenTeil.length)-1];
					datenString = datenString.trim();
					bisDaten.add(Integer.parseInt(datenString));
					}
					// bsp. 1978/79
					else if(datenTeil[1].length()==2){
						String datumsJahrhundert=datenTeil[0].substring(0,2);
						String datenString = datumsJahrhundert+datenTeil[1];
						bisDaten.add(Integer.parseInt(datenString));
					}
				}
				else{
					bisDaten.add(Integer.parseInt(bisDatum));
				}
						
			}
		}
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (int bisDatum : bisDaten) {
			Archivale a= new Archivale();
			a.setBisJahr(bisDatum);
			a=em.merge(a);
		}
		et.commit();
		em.close();
				
	}
	
}
