/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  e0_mueller
 *                     burghard.britzke bubi@charmides.in-berlin.de
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
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.DetailBean;
import de.archivator.entities.Archivale;

/**
 * Testet die Funktionen der DetailBean.
 * 
 * @author e0_mmueller
 * @author burghard.britzke
 */
public class DetailBeanTest {

	private DetailBean proband;
	private Archivale aktuellesArchivale;

	/**
	 * Mocked die EntityManagerFactory und das aktuelleArchivale, injiziert
	 * diese und bereitet die Umgebungsobjekte auf die Tests vor.
	 * 
	 * @throws java.lang.Exception
	 * @author burghard.britzke
	 */
	@Before
	public void setUp() throws Exception {
		proband = new DetailBean();
		aktuellesArchivale = mock(Archivale.class);
		proband.setAktuellesArchivale(aktuellesArchivale);
	}

	/**
	 * Test method for {@link de.archivator.beans.DetailBean#back()}.
	 */
	@Test
	public void testBack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.DetailBean#sortBy()}.
	 */
	@Test
	public void testSortBy() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.DetailBean#showDocument()}.
	 */
	@Test
	public void testShowDocument() {
		fail("Not yet implemented");
	}

}
