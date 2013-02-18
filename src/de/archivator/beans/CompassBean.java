/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke bubi@charmides.in-berlin.de
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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassIndexSession;
import org.compass.core.config.CompassConfiguration;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import de.archivator.entities.Archivale;
import de.archivator.entities.Name;

/**
 * Stellt das Compass-Objekt zur Indizierung und zur Suche zur Verfügung.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
@ApplicationScoped
public class CompassBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;

	@Produces
	Compass compass;

	/**
	 * Erzeugt eine neue CompassBean. Die Eigenschaft compass wird mit einem
	 * neuen Compass-Objekt initialisiert.
	 */
	public CompassBean() {
		System.out.println("CompassBean<init>()");
		CompassConfiguration conf = new CompassConfiguration().configure();
		conf.addClass(Archivale.class);
		conf.addClass(Name.class);
		compass = conf.buildCompass();
		System.out.println("CompassBean<init>() - ende");
	}

	/**
	 * Initialisiert den Compass-Index.
	 */
	@PostConstruct
	public void init() {
		System.out.println("CompassBean::init()");

		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		CompassIndexSession session = compass.openIndexSession();
		CompassGps gps = new SingleCompassGps(compass);
		CompassGpsDevice jpaDevice = new JpaGpsDevice("jpa",
				entityManagerFactory);
		gps.addGpsDevice(jpaDevice);
		gps.start();
		gps.index();
		try {
			Query q = entityManager.createQuery("select a from Archivale a");

			List<Archivale> archivalien = q.getResultList();
			for (Archivale archivale : archivalien) {
				session.save(archivale);
			}
		} finally {
			session.close();
		}
	}

	/**
	 * @return the compass
	 */
	public Compass getCompass() {
		return compass;
	}

	/**
	 * @param compass
	 *            the compass to set
	 */
	public void setCompass(Compass compass) {
		this.compass = compass;
	}
}
