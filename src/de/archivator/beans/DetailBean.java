/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  müller, dreher, burghard.britzke
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

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.archivator.entities.Archivale;

/**
 * Stellt die Funktionen für die view detail.xhtml zur Verfügung. Die Attribute
 * einer Archivale werden in der DetailBean zwischengespeichert und beim Wechsel
 * auf die Detail Seite abgerufen und dargestellt. Dem Redakteur steht ausserdem
 * ein Bearbeiten-Button zur Verfügung, mit diesem gelangt er auf die
 * Bearbeitungsansicht. Mit dem Zurück-Button kommt man auf die "Suche"-Seite
 * zurück.
 * 
 * @author mueller,dreher
 * 
 */
@Named(value = "detailBean")
@SessionScoped
public class DetailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager entityManager;
	private Archivale aktuellesArchivale;

	public DetailBean() {
		System.out.println("DetailBean<init>()");
	}

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
	 * Diese Methode wird ausgeführt, wenn der "Zurück"-Button gedrückt wurde.
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
	 * Diese Methode dient als action-Methode und wird ausgeführt, wenn der
	 * Benutzer die Schaltfläche zur Anzeige des Dokumentes anwählt.
	 * 
	 * @return diese Methode gibt einen leeren String zurück.
	 */
	public String showDocument() {
		return "";

	}
}
