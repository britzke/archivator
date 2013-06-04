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

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * EventHandler zur Behandlung von Validierungsfehlern
 * in den Altdaten.
 * @author e0_naumann
 * @author burghard.britzke
 */
public class AltdatenValidationEventHandler implements ValidationEventHandler{

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
