/**
 * 
 */
package de.archivator.altdaten;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.archivator.altdaten.model.TabelleX0020Archiv;

/**
 * @author e0_naumann
 *
 */
public class AltdatenKonverter {
	
	
	/**
	 * 
	 */
	public AltdatenKonverter() {
		// TODO Auto-generated constructor stub
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance("de.archivator.altdaten.model");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<TabelleX0020Archiv> bookingElement= (JAXBElement<TabelleX0020Archiv>)unmarshaller.unmarshal(new File("src/altdaten/Archivdatenbank.xml"));
			TabelleX0020Archiv tabelle = bookingElement.getValue();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
