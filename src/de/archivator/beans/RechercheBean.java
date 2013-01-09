/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke, bubi@charmides.in-berlin.de
 *                     e0_schulz
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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.config.CompassConfiguration;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import de.archivator.entities.Archivale;

/**
 * Stellt Eigenschaften und Funktionen für den View search.xthml sowie
 * results.xhtml zur Verfügung.
 * 
 * @author burghard.britzke
 * @author e0_schulz
 */
@Named
public class RechercheBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Ermöglicht den Zugriff auf die Datenbank
	 */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	/**
	 * Das Suchkriterium, dass der Benutzer in das Formular search.xhtml
	 * eingetragen hat.
	 */
	private String suchKriterium;
	/**
	 * Die Liste der Archivalien, die durch die zuletzt ausgeführte Recherche
	 * gefunden wurden.
	 */
	private List<Archivale> archivalien;

	/**
	 * Erzeugt eine neue RechercheBean. Initialisiert die Liste der Archivalien.
	 */
	public RechercheBean() {
		archivalien = new ArrayList<Archivale>();
		suchKriterium = "";
	}
	
	@PostConstruct
	public void init() {
	}

	/**
	 * @return the suchKriterium
	 */
	public String getSuchKriterium() {
		return suchKriterium;
	}

	/**
	 * @param suchKriterium
	 *            the suchKriterium to set
	 */
	public void setSuchKriterium(String suchKriterium) {
		this.suchKriterium = suchKriterium;
	}

	/**
	 * @return the archivalien
	 */
	public List<Archivale> getArchivalien() {
		return archivalien;
	}

	/**
	 * @param archivalien
	 *            the archivalien to set
	 */
	public void setArchivalien(List<Archivale> archivalien) {
		this.archivalien = archivalien;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> getDatalist(Class<T> c, EntityManager em) {
		List<T> list = new ArrayList<T>();
		try {
			String[] klasse_arr = c.getName().split("\\.");
			String klasse;
			if (klasse_arr.length != 0) {
				klasse = klasse_arr[klasse_arr.length - 1];
			} else {
				klasse = c.getName();
			}

			Query q = em.createQuery("select n from " + klasse + " n");
			list = q.getResultList();
			for (T t : list) {
				em.refresh(t);
			}
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return list;
		}
		em.close();
		return list;
	}

	/**
	 * Sucht nach den Archivalien, die durch die Eigenschaft "suchKriterium"
	 * beschrieben werden und speichert sie in die Liste "archivalien".
	 * 
	 * @return "index" konstant.
	 */
	public String search() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("archivator");
		CompassConfiguration conf = new CompassConfiguration().configure()
				.addClass(Archivale.class);
		Compass compass = conf.buildCompass();

		// A request scope operation
		CompassSession session = compass.openSession();
		CompassGps gps = new SingleCompassGps(compass);
		CompassGpsDevice jpaDevice = new JpaGpsDevice("jpa", emf);
		gps.addGpsDevice(jpaDevice);
		gps.start();
		gps.index();

		try {
			List<Archivale> l = this.getDatalist(Archivale.class,
					emf.createEntityManager());

			for (int i = 0; i < l.size(); i++) {
				session.save(l.get(i));
			}
			CompassHits hits = session.find(suchKriterium);
			for (int i = 0; i < hits.getLength(); i++) {
				archivalien.add((Archivale) hits.data(i));
			}
		} finally {
			session.close();
		}
		return "index";
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Betreff". Der Text
	 * "betreff = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void betreffClicked() {
		
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Name". Der Text "name = "
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void nameClicked() {
		
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Schlagwort". Der Text
	 * "schlagwort = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void schlagwortClicked() {
		
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Titel". Der Text
	 * "titel = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void titelClicked() {
		
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "and". Der Text
	 * " and " wird in die Eigenschaft suchKriterium gespeichert.
	 */
	public void andClicked() {
		
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "or". Der Text " or " wird in
	 * die Eigenschaft suchKriterium gespeichert.
	 */
	public void orClicked() {
		
	}
}
