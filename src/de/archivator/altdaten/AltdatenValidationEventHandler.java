/**
 * 
 */
package de.archivator.altdaten;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * @author User
 *
 */
public class AltdatenValidationEventHandler implements ValidationEventHandler{

	

	@Override
	public boolean handleEvent(ValidationEvent ve) {
		
		if(ve.getSeverity()==ValidationEvent.FATAL_ERROR || ve.getSeverity()==ValidationEvent.ERROR){
			ValidationEventLocator locator = ve.getLocator();
			System.out.println("Falsches Archivaliendocument: "+locator.getURL());
			System.out.println("Fehler: "+ve.getMessage());
			System.out.println("Fehler in der Zeile "+locator.getColumnNumber() +", an der Stelle "+locator.getLineNumber());
		}
		
		return true;
	}
}
