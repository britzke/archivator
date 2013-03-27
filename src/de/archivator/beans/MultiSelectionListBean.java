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

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassSession;

import de.archivator.annotations.AktuellesArchivale;
import de.archivator.entities.Archivale;

/**
 * Die MultipleSelectionListBean dient der Bearbeitung von DataTable Elementen
 * mit Multiple Selection Möglichkeit.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
@Named
@RequestScoped
public abstract class MultiSelectionListBean<T> {
	@Inject
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	@Inject
	protected Compass compass;

	@Inject
	protected DetailBean details;

	@Inject
	@AktuellesArchivale
	protected Archivale aktuellesArchivale;

	/**
	 * Liste aller Items, die in der Datenbank gespeichert sind.
	 */
	protected List<T> allItems;
	/**
	 * Array der in der angezeigten Liste ausgewählten Items.
	 */
	protected T[] selectedItems;
	/**
	 * Liste von Items, die dem Objekt aktuellesArchivale zugeordnet sind.
	 */
	protected List<T> archivaleItems;

	// initialize in the constructor of extending class
	protected String queryForAllItems;

	private final String ILLEGAL_STATE_MESSAGE = "Die Eigenschaft '%s' muss im Konstruktor/@PostConstruct der beerbenden Klasse initialisiert werden.";

	/**
	 * Initialisiert die Liste allerItems mit allen Items aus der Datenbank.
	 * Muss während des \@PostConstruct ausgeführt werden, da Managed-Beans
	 * angesprochen werden.
	 */
	@SuppressWarnings("unchecked")
	protected void init() {
		if (queryForAllItems == null)
			throw new IllegalStateException(String.format(
					ILLEGAL_STATE_MESSAGE, "queryForAllItems"));
		entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createQuery(queryForAllItems);
		allItems = q.getResultList();
		entityManager.close();
	}

	/**
	 * @return allItems
	 */
	public List<T> getAllItems() {
		return allItems;
	}

	/**
	 * @param allItems
	 *            A List of all Items the Dokumentarten to set
	 */
	public void setAllItems(List<T> allItems) {
		this.allItems = allItems;
	}

	/**
	 * @return the selectedItems
	 */
	public T[] getSelectedItems() {
		return selectedItems;
	}

	/**
	 * @param selectedItems
	 *            the selectedItems to set
	 */
	public void setSelectedItems(T[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	/**
	 * Erneuert die archivaleItems aus der aktuellesArchivale. Ein Refresh ist
	 * nach <code>entitymanager.merge(aktuellesArchivale)</code> notwendig,
	 * damit die archivaleItems auf die <i>managed</i> archivaleItems zeigen.
	 */
	protected abstract void refreshArchivaleItems();

	/**
	 * Erneuert die Größe der selectedItems.
	 */
	protected abstract void resizeSelectedItems();

	protected abstract void addAktuellesArchivaleToItem(Archivale archivale,
			T item);

	/**
	 * Lädt die Items zur Bearbeitung durch den Benutzer in das Formular.
	 * 
	 * @return Die Navigationszeichenkette für das JSF-Servlet
	 */
	public String loadItems() {
		int i = 0;
		if (archivaleItems == null)
			throw new IllegalStateException(String.format(
					ILLEGAL_STATE_MESSAGE, "archivaleItems"));
		resizeSelectedItems();
		for (T oe : archivaleItems) {
			selectedItems[i++] = oe;
		}
		return null;
	}

	/**
	 * Sichert die gewählten Items aus dem Formular.
	 * 
	 * @return Die Navigationszeichenkette für das JSF-Servlet. null... immer.
	 */
	public String saveItems() {
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		aktuellesArchivale = entityManager.merge(aktuellesArchivale);
		refreshArchivaleItems(); // operate on merged archivaleItems

		if (archivaleItems == null)
			throw new IllegalStateException(String.format(
					ILLEGAL_STATE_MESSAGE, "archivaleItems"));
		archivaleItems.clear();
		if (selectedItems == null)
			throw new IllegalStateException(String.format(
					ILLEGAL_STATE_MESSAGE, "selectedItems"));

		for (T item : selectedItems) {
			if (!archivaleItems.contains(item)) {
				item = entityManager.merge(item);
				archivaleItems.add(item);
				addAktuellesArchivaleToItem(aktuellesArchivale, item);
			}
		}

		// speichere in den Compass-Index
		CompassSession session = compass.openSession();
		try {
			session.save(aktuellesArchivale);
			session.commit();
		} catch (CompassException ce) {
			ce.printStackTrace();
			session.rollback();
		}

		entityTransaction.commit();
		session.close();
		entityManager.close();

		details.setAktuellesArchivale(aktuellesArchivale);
		return null;
	}
}
