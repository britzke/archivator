/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2013  burghard.britzke, bubi@charmides.in-berlin.de
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
package de.archivator.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.RechercheBean;

/**
 * Testet die Funktionen der RechercheBean
 * 
 * @author burghard.britzke
 * @author e0_schulz
 * @author e0_naumann
 */
public class RechercheBeanTest {

	RechercheBean proband;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new RechercheBean();
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#betreffClicked()}. Überprüft ob
	 * eine Änderung des Suchkriteriums vorgenommen wurde und ob das gewünschte
	 * "betreff = " hinzugefügt wurde
	 */
	@Test
	public void testBetreffClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.betreffClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {
			if ((oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains("betreff = "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \"betreff = \" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND\" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND betreff ="))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND betreff =\" hinzugefügt");
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#nameClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte "name = " hinzugefügt wurde
	 */
	@Test
	public void testNameClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.betreffClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {
			if ((oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains("name = "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \"name = \" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND\" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND name ="))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND name =\" hinzugefügt");
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#schlagwortClicked()}. Überprüft
	 * ob eine Änderung des Suchkriteriums vorgenommen wurde und ob das
	 * gewünschte "schlagwort = " hinzugefügt wurde
	 */
	@Test
	public void testSchlagwortClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.betreffClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {
			if ((oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains("schlagwort = "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \"schlagwort = \" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND\" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND schlagwort ="))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND schlagwort =\" hinzugefügt");
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#titelClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte "titel = " hinzugefügt wurde
	 */
	@Test
	public void testTitelClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.betreffClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {
			if ((oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains("titel = "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \"titel = \" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND "))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND\" hinzugefügt");
			} else if ((!oldSuchKriterium.isEmpty())
					&& (!newSuchKriterium.split(oldSuchKriterium)[1]
							.contains(" AND titel ="))) {
				throw new Exception(
						"Dem suchKriterium wurde nicht \" AND titel =\" hinzugefügt");
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#andClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte " AND " hinzugefügt wurde
	 */
	@Test
	public void testAndClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.andClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {

			if (!newSuchKriterium.split(oldSuchKriterium)[1].contains(" AND ")) {
				throw new Exception();
			}
		} catch (Exception e) {
			fail("Dem suchKriterium wurde nicht \" AND \" hinzugefügt");
		}
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#orClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte " OR " hinzugefügt wurde
	 */
	@Test
	public void testOrClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.orClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		assertNotSame("Altes und neues Kriterium dürfen nicht gleich sein.",
				oldSuchKriterium, newSuchKriterium);
		try {

			if (!newSuchKriterium.split(oldSuchKriterium)[1].contains(" OR ")) {
				throw new Exception();
			}
		} catch (Exception e) {
			fail("Dem suchKriterium wurde nicht \" OR \" hinzugefügt");
		}
	}

}
