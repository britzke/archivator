/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke
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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.archivator.entities.Archivale;
import de.archivator.entities.Organisationseinheit;

/**
 * Die OrganisationseinheitenBean dient der Bearbeitung von
 * Organisationseinheiten der Eigenschaft aktuellesArchivale aus der
 * {@link DetailBean}.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
@Named
@RequestScoped
public class OrganisationseinheitenBean extends
		MultiSelectionListBean<Organisationseinheit> {

	/**
	 * Initialisiert die Organisationseinheiten mit einer Liste aller
	 * Organisationseinheiten, die in der Datenbank vorhanden sind.
	 */
	@PostConstruct
	public void init() {
		queryForAllItems = "select o from Organisationseinheit o order by o.name";
		archivaleItems = aktuellesArchivale.getOrganisationseinheiten();

		super.init();
	}

	@Override
	protected void refreshArchivaleItems() {
		archivaleItems = aktuellesArchivale.getOrganisationseinheiten();
	}

	@Override
	protected void resizeSelectedItems() {
		selectedItems = new Organisationseinheit[archivaleItems.size()];
	}

	@Override
	protected void addAktuellesArchivaleToItem(Archivale archivale,
			Organisationseinheit item) {
		item.getArchivalien().add(archivale);
		
	}
}
