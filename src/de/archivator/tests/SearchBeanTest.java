/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2013  burghard.britzke, bubi@charmides.in-berlin.de
 *                     e0_schulz,
 *                     e0_naumann
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

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.RechercheBean;
import de.archivator.beans.SearchBean;

/**
 * Testet die Funktionen der SearchBean.
 * 
 * @author burghard.britzke
 * @author e0_schulz
 * @author e0_naumann
 */
public class SearchBeanTest {

	SearchBean proband = new SearchBean();
	RechercheBean mockRechercheBean;

	/**
	 * Setzt die Umgebung auf, die alle Tests nutzen.
	 */
	@Before
	public void setUp() {
		mockRechercheBean = mock(RechercheBean.class);
		proband.setRechercheBean(mockRechercheBean);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#betreffClicked()}. Wenn das
	 * Suchkriterium leer ist, muss "betreff =" hinzugefügt werden.
	 */
	@Test
	public void testBetreffClickedWithEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("");

		proband.betreffClicked(); // test

		verify(mockRechercheBean).setSuchKriterium("betreff = ");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#betreffClicked()}. Wenn das
	 * Suchkriterium nicht leer ist, muss " AND betreff=" hinzugefügt werden.
	 */
	@Test
	public void testBetreffClickedWithNotEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("test");

		proband.betreffClicked(); // test
		verify(mockRechercheBean).getSuchKriterium();
		verify(mockRechercheBean).setSuchKriterium("test AND betreff = ");
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#nameClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte "name = " hinzugefügt wurde
	 */
	@Test
	public void testNameClickedWithEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("");

		proband.nameClicked();

		verify(mockRechercheBean).getSuchKriterium();
		verify(mockRechercheBean).setSuchKriterium("name = ");
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#nameClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte " AND name = " hinzugefügt wurde
	 */
	@Test
	public void testNameClickedWithNotEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("test");

		proband.nameClicked();

		verify(mockRechercheBean).getSuchKriterium();
		verify(mockRechercheBean).setSuchKriterium("test AND name = ");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#schlagwortClicked()}. Überprüft
	 * ob die "schlagwort = " hinzugefügt wurde, wenn das Suckkriterium zuvor
	 * leer war.
	 */
	@Test
	public void testSchlagwortClickedWithEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn(""); // mock empty
																	// query
		proband.schlagwortClicked();
		verify(mockRechercheBean).setSuchKriterium(eq("schlagwort = "));
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.RechercheBean#schlagwortClicked()}. Überprüft
	 * ob "AND schlagwort = " hinzugefügt wurde, wenn das Suckkriterium zuvor
	 * nicht leer war.
	 */
	@Test
	public void testSchlagwortClickedWithNotEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("test"); // mock
																		// empty
																		// query
		proband.schlagwortClicked();
		verify(mockRechercheBean)
				.setSuchKriterium(eq("test AND schlagwort = "));
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#titelClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte "titel = " hinzugefügt wurde
	 */
	@Test
	public void testTitelClickedWithEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn(""); // mock
																	// empty
		// query
		proband.titelClicked();
		verify(mockRechercheBean).setSuchKriterium(eq("titel = "));
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#titelClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte "titel = " hinzugefügt wurde
	 */
	@Test
	public void testTitelClickedWithNotEmptyQuery() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn("test"); // mock
																		// not
																		// empty
		// query
		proband.titelClicked();
		verify(mockRechercheBean).setSuchKriterium(eq("test AND titel = "));
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#andClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte " AND " hinzugefügt wurde
	 */
	@Test
	public void testAndClicked() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn(""); // mock empty

		proband.andClicked();

		verify(mockRechercheBean).setSuchKriterium(eq(" AND "));
	}

	/**
	 * Test method for {@link de.archivator.beans.RechercheBean#orClicked()}.
	 * Überprüft ob eine Änderung des Suchkriteriums vorgenommen wurde und ob
	 * das gewünschte " OR " hinzugefügt wurde
	 */
	@Test
	public void testOrClicked() {
		when(mockRechercheBean.getSuchKriterium()).thenReturn(""); // mock empty

		proband.orClicked();

		verify(mockRechercheBean).setSuchKriterium(eq(" OR "));
	}
}
