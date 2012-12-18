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
