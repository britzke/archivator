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

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassSearchSession;

import de.archivator.entities.Archivale;

/**
 * Stellt Eigenschaften und Funktionen für den View search.xthml sowie
 * results.xhtml zur Verfügung.
 * 
 * @author burghard.britzke
 * @author e0_schulz
 * 
 */
@Named
@RequestScoped
public class SearchBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheBean rechercheBean;

	@Inject
	private Compass compass;

	/**
	 * Antwortet mit dem Wert des rechercheBean
	 * 
	 * @return the rechercheBean
	 */
	public RechercheBean getRechercheBean() {
		return rechercheBean;
	}

	/**
	 * @param rechercheBean
	 *            the rechercheBean to set
	 */
	public void setRechercheBean(RechercheBean rechercheBean) {
		this.rechercheBean = rechercheBean;
	}

	/**
	 * Sucht nach den Archivalien, die durch die Eigenschaft "suchKriterium"
	 * beschrieben werden und speichert sie in die Liste "archivalien".
	 * 
	 * @return "index" konstant.
	 */
	public String search(FacesContext ctx) {
		String suchKriterium = rechercheBean.getSuchKriterium();
		if (suchKriterium.length() == 0) {
			FacesMessage message = new FacesMessage(
					"Bitte Suchbegriff eingeben");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			ctx.addMessage("searchForm:inputSearch", message);
		} else {
			List<Archivale> archivalien = new ArrayList<Archivale>();
			rechercheBean.setArchivalien(archivalien);

			CompassSearchSession session = compass.openSearchSession();
			try {
				CompassHits hits = session.find(suchKriterium);
				System.out.println("Hits");
				for (int i = 0; i < hits.getLength(); i++) {
					archivalien.add((Archivale) hits.data(i));
					System.out.println(hits.hit(i).score() + " "
							+ hits.hit(i).getHighlightedText());
				}
			} finally {
				session.close();
			}
		}
		return "index";
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Betreff". Der Text
	 * "betreff:" wird in die Eigenschaft suchKriterium der RechercheBean
	 * gespeichert. Ist bereits ein Text im suchKriterium, so wird der text
	 * " AND " vorangestellt.
	 */
	public void betreffClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "betreff:";
		} else {
			query += " AND betreff:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Dokumentart". Der Text
	 * "dokumentart:" wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void dokumentartClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "dokumentart:";
		} else {
			query += " AND dokumentart:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Name". Der Text "name:" wird
	 * in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text im
	 * suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void nameClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "name:";
		} else {
			query += " AND name:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Name". Der Text
	 * "organisationseinheit:" wird in die Eigenschaft suchKriterium
	 * gespeichert. Ist bereits ein Text im suchKriterium, so wird der text
	 * " and " vorangestellt.
	 */
	public void organisationseinheitClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "körperschaft:";
		} else {
			query += " AND körperschaft:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Person". Der Text "person:"
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void personClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "person:";
		} else {
			query += " AND person:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Schlagwort". Der Text
	 * "schlagwort:" wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void schlagwortClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "schlagwort:";
		} else {
			query += " AND schlagwort:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Titel". Der Text "titel:"
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void titelClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "titel:";
		} else {
			query += " AND titel:";
		}
		rechercheBean.setSuchKriterium(query);
	}
	
	/**
	 * ActionListener-Methode für die Schaltfläche "von Jahr". Der Text "vonJahr:"
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void vonJahrClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "vonJahr:";
		} else {
			query += " AND vonJahr:";
		}
		rechercheBean.setSuchKriterium(query);
	}
	
	/**
	 * ActionListener-Methode für die Schaltfläche "bis Jahr". Der Text "bisJahr:"
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void bisJahrClicked() {
		String query = rechercheBean.getSuchKriterium();
		if (query.length() == 0) {
			query = "bisJahr:";
		} else {
			query += " AND bisJahr:";
		}
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "and". Der Text " and " wird
	 * in die Eigenschaft suchKriterium gespeichert.
	 */
	public void andClicked() {
		String query = rechercheBean.getSuchKriterium();
		query += " AND ";
		rechercheBean.setSuchKriterium(query);
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "or". Der Text " or " wird in
	 * die Eigenschaft suchKriterium gespeichert.
	 */
	public void orClicked() {
		String query = rechercheBean.getSuchKriterium();
		query += " OR ";
		rechercheBean.setSuchKriterium(query);
	}

}