package de.archivator.beans;

import de.archivator.enties.Archivale;

/**
 * Stellt die Funktionen für die view detail.xhtml zur Verfügung. Die Attribute
 * einer Archivale werden in der DetailBean zwischengespeichert und beim Wechsel
 * auf die Detail Seite abgerufen und dargestellt. Dem Redakteur steht ausserdem
 * ein Bearbeiten-Button zur Verfügung, mit diesem gelangt er auf die
 * Bearbeitungsansicht. Mit dem Zurück-Button kommt man auf die “Suche”-Seite
 * zurück.
 * 
 * @author mueller,dreher
 * 
 */
public class DetailBean {

	/**
	 * das aktuelle Archivale, dass der Benutzer in der Ergebnisliste angewählt
	 * hat (wird über ein f:setPropertyActionListener -Tag gesetzt)
	 */
	private Archivale aktuellesArchivale;

	/**
	 * Antwortet mit dem Wert der Eigenschaft aktuellesArchivale.
	 * 
	 * @return der Wert von aktuellesArchivale
	 */

	public Archivale getAktuellesArchivale() {
		return aktuellesArchivale;
	}

	/**
	 * Ermöglicht es die Eigenschaft aktuellesArchivale zu setzen.
	 * 
	 * @param aktuellesArchivale
	 *            Wert der aktuellenArchivale der gesetzt werden soll.
	 */
	public void setAktuellesArchivale(Archivale aktuellesArchivale) {
		this.aktuellesArchivale = aktuellesArchivale;
	}

	/**
	 * Diese Methode wird ausgeführt, wenn der “Zurück”-Button gedrückt wurde.
	 * Er führt von der Detail-Anzeige eines Archivales zurück zur Recherche.
	 */
	public void back() {

	}

	/**
	 * Die Methode sortiert die Archivalien in der Liste nach dem angeklickten
	 * Kriterium, nach Alphabet oder numerischer Menge. Wenn das Kriterium
	 * nochmal angeklickt wird, wird die Liste umgedreht. Sie wird ausgeführt,
	 * wenn ein Sortierungskriterium in der obersten Zeile der Liste angeklickt
	 * wird.
	 */
	public void sortBy() {

	}

	/**
	 * Diese Methode dient als action-Methode und wird ausgeführt, 
	 * wenn der Benutzer die Schaltfläche 
	 * zur Anzeige des Dokumentes anwählt.
	 * @return diese Methode gibt einen leeren String zurück.
	 */
	public String showDocument() {
		return "";

	}
}
