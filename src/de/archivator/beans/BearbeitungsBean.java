/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  <name of author>
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

import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.archivator.entities.Archivale;

/**
 * Diese Bean stellt der Seite "edit.xhtml" Funktionen zur verfügung.
 * Für jede Funktion wird eine action-Methode implementiert.
 * @author MIAHansen 
 */
public class BearbeitungsBean {
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject EntityManager entityManager;
	/**
	 * Das aktuelle Archival, welches durch bearbeite() oder 
	 * lösche() verändert wird.
	 */
	private Archivale aktuelleArchivale;
	/**
	 * Die Liste an dem Nutzer sichtbaren Archivalien.
	 */
	private ArrayList<Archivale> archivalien;
	
	/**
	 * Holt die aktuelle Arichvale aus der Datenbank
	 */
	public void getArchivale(){
		return;
	}
	/**
	 * 
	 */
	public void setArchivale(){
		
	}
	/**
	 * löscht die Archivale
	 * @param
	 * 		die aktuelle Archivale die Gelöscht werden soll.
	 */
	public void loesche(Archivale aktuelleArchivale){
		
	}
	
	/**
	 * bearbeitet die Archivale
	 */
	public void bearbeite(){
		
	}
	/**
	 * erstellt eine neue Archivale.
	 * Zeigt anschließend die Bearbeitungsansicht.
	 */
	public void erstelle(){
		
	}
}
