/**
 * 
 */
package de.archivator.altdaten;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.archivator.altdaten.model.ObjectFactory;
import de.archivator.altdaten.model.TabelleX0020Archiv;
import de.archivator.entities.Archivale;

/**
 * @author e0_naumann
 *
 */
public class AltdatenKonverter {
	
	Archivale archiv;
	TabelleX0020Archiv tabelle;
	/**
	 * Konverter der Daten aus einer xml Datei liest und diese in Java-objekte umwandelt
	 */
	public AltdatenKonverter() {
		// TODO Auto-generated constructor stub
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance("de.archivator.altdaten.model");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setEventHandler(new AltdatenValidationEventHandler());
			JAXBElement<TabelleX0020Archiv> bookingElement= (JAXBElement<TabelleX0020Archiv>)unmarshaller.unmarshal(new File("src/altdaten/Archivdatenbank.xml"));
			tabelle = bookingElement.getValue();
		
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Methode, um die umgewandelten Daten in die neue Datenbank zuspeichern
	 * 
	 */
	public void archivLaden(){
		archiv = new Archivale();
		
		archiv.setSchubfach(tabelle.getSchubfachX0020Nummer());
		archiv.setBetreff(tabelle.getBetreff());
		archiv.setInhalt(tabelle.getInhalt());
		
		
	}
	
}
