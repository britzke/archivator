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

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
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
import de.archivator.entities.*;

/**
 * @author e0_schulz
 * 
 */
@ManagedBean
public class SearchBean {

	String searchText;
	List<Archivale> foundArchivalien;
	boolean searchHelp;

	public SearchBean() {
		searchText = "leer";
		searchHelp = false;
		foundArchivalien = new ArrayList<Archivale>();
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

	public void search(ActionEvent actionEvent) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Archivator_main");
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
			CompassHits hits = session.find(searchText);
			for (int i = 0; i < hits.getLength(); i++) {
				foundArchivalien.add((Archivale)hits.data(i));
			}
		} finally {
			session.close();
		}
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText
	 *            the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the archivalien
	 */
	public List<Archivale> getArchivalien() {
		return foundArchivalien;
	}

	/**
	 * @param archivalien
	 *            the archivalien to set
	 */
	public void setArchivalien(List<Archivale> archivalien) {
		this.foundArchivalien = archivalien;
	}

	/**
	 * @return the searchHelp
	 */
	public boolean isSearchHelp() {
		return searchHelp;
	}

	public void switchSearchHelp(ActionEvent actionEvent) {
		this.searchHelp = !this.searchHelp;
	}
	
	public void addAndToSearchText(ActionEvent actionEvent){
		this.searchText=this.searchText+" AND ";
	}
	
	public void addOrToSearchText(ActionEvent actionEvent){
		this.searchText=this.searchText+" OR ";
	}

}
