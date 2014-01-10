/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke
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

package de.archivator.tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.archivator.entities.Archivale;

/**
 * Testet das Archivale-Entity
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 * 
 */
public class ArchivaleTest {

	Archivale proband;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new Archivale();
	}

	/**
	 * Test method for {@link de.archivator.entities.Archivale#getShortInhalt()}
	 * .
	 */
	@Test
	public void testGetShortInhaltEmpty() {
		proband.setInhalt("");

		String result = proband.getShortInhalt();

		assertEquals("", result);
	}

	/**
	 * Test method for {@link de.archivator.entities.Archivale#getShortInhalt()}
	 * .
	 */
	@Test
	public void testGetShortInhaltOneWord() {
		proband.setInhalt("test");

		String result = proband.getShortInhalt();

		assertEquals("test", result);
	}

	/**
	 * Test method for {@link de.archivator.entities.Archivale#getShortInhalt()}
	 * . Testet, ob der Inhalt korrekt gekürzt wird, wenn er länger als 120
	 * Zeichen ist.
	 */
	@Test
	public void testGetShortInhaltMoreThan120Characters() {
		String text = "1234567890 ";
		text += text; // 22
		text += text; // 44
		text += text; // 88
		text += text; // 176

		proband.setInhalt(text);

		String result = proband.getShortInhalt();

		assertTrue(result.length() < 120);
		assertTrue(result.length() == 112);
	}

	/**
	 * Test method for {@link de.archivator.entities.Archivale#getShortInhalt()}
	 * . Testet, ob der Inhalt korrekt gekürzt wird, wenn er länger als 120
	 * Zeichen ist.
	 */
	@Test
	public void testGetShortInhaltMoreThan120CharactersWithoutSpaces() {
		String text = "1234567890";
		text += text; // 20
		text += text; // 40
		text += text; // 80
		text += text; // 160

		proband.setInhalt(text);

		String result = proband.getShortInhalt();

		assertTrue("Die Länge des Inhalts muss 123 sein",
				result.length() == 123);
		assertTrue("Das Ende des Inhalts muss ... sein",
				result.substring(120, 123).equals("..."));
	}

	/**
	 * Test method for {@link de.archivator.entities.Archivale#getShortInhalt()}
	 * . Testet, ob der Inhalt korrekt gekürzt wird, wenn er null ist.
	 */
	@Test
	public void testGetShortInhaltNull() {
		proband.setInhalt(null);

		String result = proband.getShortInhalt();

		assertNull(result);
	}

}
