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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Eine EntityManagerFactoryBean stellt der Applikation eine
 * EntityManagerFactory zur Verfügung. Da die EntityManagerFactory nicht
 * Serializable ist, kann sie nur in <b>transient</b> Eigenschaften injiziert
 * werden, falls die Bean, in die injiziert wird {@code @SessionScoped} ist.
 * 
 * @see Serializable
 * @author burghard.britzke
 */
@ApplicationScoped
public class EntityManagerFactoryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Erzeugt eine neue EntityManagerFactoryBean.
	 */
	public EntityManagerFactoryBean() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("archivator");
	}

	/**
	 * Liefert die Eigenschaft entityManagerFactory.
	 * 
	 * @return the entityManager
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
}
