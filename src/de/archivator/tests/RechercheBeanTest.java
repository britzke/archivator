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

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#search()}.
	 */
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#betreffClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde
	 * und ob das gewünschte "[betreff] = " hinzugefügt wurde
	 */
	@Test
	public void testBetreffClicked() {
		String oldSuchKriterium = proband.getSuchKriterium();
		proband.betreffClicked();
		String newSuchKriterium = proband.getSuchKriterium();
		if (oldSuchKriterium.compareTo(newSuchKriterium) == 0) {
			fail("Das Suchkriterium wurde nicht verändert");
		}
		try {

			if (!newSuchKriterium.split(oldSuchKriterium)[1]
					.contains("[betreff] = ")) {
				throw new Exception();
			}
		} catch (Exception e) {
			fail("Dem suchKriterium wurde nicht \"[betreff] = \" hinzugefügt");
			System.out.println("blubb");
		}

	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#nameClicked()}.
	 */
	@Test
	public void testNameClicked() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#schlagwortClicked()}.
	 */
	@Test
	public void testSchlagwortClicked() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#titelClicked()}.
	 */
	@Test
	public void testTitelClicked() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#andClicked()}.
	 */
	@Test
	public void testAndClicked() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#orClicked()}.
	 */
	@Test
	public void testOrClicked() {
		fail("Not yet implemented");
	}

}
