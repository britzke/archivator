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
}
