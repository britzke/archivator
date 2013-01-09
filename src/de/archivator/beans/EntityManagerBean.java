/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke, bubi@charmides.in-berlin.de
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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Eine EntityManagerBean stellt der Applikation einen EntityManager zur
 * Verfügung. Die EntityManagerBean ist veraltet uns soll nicht mehr verwendet
 * werden. Bitte die EntityManagerFactoryBean anstatt dessen benutzen.
 * 
 * @author burghard.britzke
 * @deprecated
 */
@Deprecated
@ApplicationScoped
public class EntityManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Erzeugt eine neue EntityManagerBean. Initialisiert den EntityManager.
	 */
	public EntityManagerBean() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("archivator");
		entityManager = emf.createEntityManager();
	}

	/**
	 * Liefert die Eigenschaft entityManager.
	 * 
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Setzt die Eigenschaft entityManager.
	 * 
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
