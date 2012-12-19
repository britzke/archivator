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

import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.archivator.entities.Archivale;

/**
 * Stellt die Funktionen f�r die view detail.xhtml zur Verf�gung. Die Attribute
 * einer Archivale werden in der DetailBean zwischengespeichert und beim Wechsel
 * auf die Detail Seite abgerufen und dargestellt. Dem Redakteur steht ausserdem
 * ein Bearbeiten-Button zur Verf�gung, mit diesem gelangt er auf die
 * Bearbeitungsansicht. Mit dem Zur�ck-Button kommt man auf die �Suche�-Seite
 * zur�ck.
 * 
 * @author mueller,dreher
 * 
 */
public class DetailBean {
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject EntityManager entityManager;

	/**
	 * das aktuelle Archivale, dass der Benutzer in der Ergebnisliste angew�hlt
	 * hat (wird �ber ein f:setPropertyActionListener -Tag gesetzt)
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
	 * Erm�glicht es die Eigenschaft aktuellesArchivale zu setzen.
	 * 
	 * @param aktuellesArchivale
	 *            Wert der aktuellenArchivale der gesetzt werden soll.
	 */
	public void setAktuellesArchivale(Archivale aktuellesArchivale) {
		this.aktuellesArchivale = aktuellesArchivale;
	}

	/**
	 * Diese Methode wird ausgef�hrt, wenn der �Zur�ck�-Button gedr�ckt wurde.
	 * Er f�hrt von der Detail-Anzeige eines Archivales zur�ck zur Recherche.
	 */
	public void back() {

	}

	/**
	 * Die Methode sortiert die Archivalien in der Liste nach dem angeklickten
	 * Kriterium, nach Alphabet oder numerischer Menge. Wenn das Kriterium
	 * nochmal angeklickt wird, wird die Liste umgedreht. Sie wird ausgef�hrt,
	 * wenn ein Sortierungskriterium in der obersten Zeile der Liste angeklickt
	 * wird.
	 */
	public void sortBy() {

	}

	/**
	 * Diese Methode dient als action-Methode und wird ausgef�hrt, 
	 * wenn der Benutzer die Schaltfl�che 
	 * zur Anzeige des Dokumentes anw�hlt.
	 * @return diese Methode gibt einen leeren String zur�ck.
	 */
	public String showDocument() {
		return "";

	}
}
