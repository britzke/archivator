/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke bubi@charmides.in-berlin.de
 *                     lightniglord2
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.EditBean;
import de.archivator.entities.Archivale;
import de.archivator.entities.Organisationseinheit;
import de.archivator.entities.Schlagwort;

/**
 * Testet die EditBean.
 * 
 * @author burghard.britzke
 * @author LightningLord2
 */
public class EditBeanTest {

	private EditBean proband;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private Archivale aktuellesArchivale;

	/**
	 * Erzeugt eine Umgebung, die von allen Tests benötigt wird.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new EditBean();
		aktuellesArchivale = new Archivale();
		entityManagerFactory = mock(EntityManagerFactory.class);
		entityManager = mock(EntityManager.class);
		when(entityManagerFactory.createEntityManager()).thenReturn(
				entityManager);
		entityTransaction = mock(EntityTransaction.class);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityManager.merge(aktuellesArchivale)).thenReturn(
				aktuellesArchivale);
		// entityManager muss manuell injiziert werden
		Field f = proband.getClass().getDeclaredField("entityManagerFactory");
		f.setAccessible(true);
		f.set(proband, entityManagerFactory);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#back()} wenn ein
	 * neues Archivale bearbeitet wurde.
	 */
	@Test
	public void testBackNewArchivale() {

		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.back();

		assertEquals("index", navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#back()} - wenn ein
	 * bestehendes Archivale bearbeitet wurde.
	 */
	@Test
	public void testBackOldArchivale() {
		aktuellesArchivale.setId(1);

		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.back();

		assertEquals("detail", navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#lösche()}.
	 */
	@Test
	public void testLösche() {
		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.lösche();
		assertEquals("lösche() muss auf die Index-Seite navigieren", "index",
				navigation);
		verify(entityManager, times(2)).getTransaction();
		verify(entityManager).remove(aktuellesArchivale);
		verify(entityTransaction).commit();
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#speichere()}.
	 */
	@Test
	public void testSpeichereNeuesArchivale() {

		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.speichere();
		assertEquals("lösche() muss auf die Detailseite navigieren", "detail",
				navigation);
		verify(entityManager, times(2)).getTransaction();
		verify(entityTransaction).commit();
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#speichere()}.
	 */
	@Test
	public void testSpeichereAltesArchivale() {
		aktuellesArchivale.setId(1);
		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.speichere(); // test

		assertEquals("lösche() muss auf die Detailseite navigieren", "detail",
				navigation);
		verify(entityManager, times(2)).getTransaction();
		verify(entityTransaction).commit();
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#erstelle()}.
	 */
	@Test
	public void testErstelle() {
		String navigation = proband.erstelle();
		assertEquals("erstelle() muss auf die edit-Seite navigieren", "edit",
				navigation);
		assertNotSame(aktuellesArchivale, proband.getAktuellesArchivale());
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadNamen()}.
	 */
	@Test
	public void testLoadNamen() {
		String navigation = proband.loadNamen();
		assertEquals("loadNamen() muss zum Edit-View navigieren", "edit",
				navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveNamen()}.
	 */
	@Test
	public void testSaveNamen() {
		String navigation = proband.saveNamen();
		assertEquals("saveNamen() muss zum Edit-View navigieren", "edit",
				navigation);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#loadOrganisationseinheiten()}.
	 */
	@Test
	public void testLoadOrganisationseinheiten() {
		String navigation = proband.loadOrganisationseinheiten();
		assertEquals(
				"loadOrganisationseinheiten() muss zum Edit-View navigieren",
				"edit", navigation);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#saveOrganisationseinheiten()}.
	 */
	@Test
	public void testSaveOrganisationseinheiten() {

		String navigation = proband.saveOrganisationseinheiten();

		assertEquals(
				"saveOrganisationseinheiten() muss zum Edit-View navigieren",
				"edit", navigation);
		fail("noch nicht fertig implementiert");
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadSchlagworte()}.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             Wenn es in der EditBean keine Eigenschaft namens
	 *             "aktuellesArchivale" gibt.
	 * @throws IllegalAccessException
	 *             Wenn der Zugriff zur EditBean verweigert wurde.
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testLoadSchlagworte() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		List<Schlagwort> schlagwörter = new ArrayList<Schlagwort>();
		Schlagwort s = new Schlagwort();
		s.setName("Lette");
		schlagwörter.add(s);
		s = new Schlagwort();
		s.setName("Datenbank");
		schlagwörter.add(s);
		aktuellesArchivale.setSchlagwörter(schlagwörter);
		Field f = EditBean.class.getDeclaredField("aktuellesArchivale");
		f.setAccessible(true);

		f.set(proband, aktuellesArchivale);

		String navigation = proband.loadSchlagworte();
		assertEquals("loadSchlagworte() muss zum Edit-View navigieren", "edit",
				navigation);
		assertEquals("Lette, Datenbank", proband.getArchivaleSchlagwörter());
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveSchlagworte()}.
	 * 
	 * @throws SecurityException
	 *             Wenn Reflection nicht erlaubt ist.
	 * @throws NoSuchFieldException
	 *             Wenn keine Eigenschaft aktuellesArchivale an der EditBean
	 *             existiert.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 *             Wenn die Eigenschaft aktuellesArchivale aus der EditBean
	 *             nicht vom Typ Archivale ist.
	 */
	@Test
	public void testSaveSchlagworte() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		proband.setArchivaleSchlagwörter("Lette, Projekt");
		Field f = proband.getClass().getDeclaredField("aktuellesArchivale");
		f.setAccessible(true);
		f.set(proband, aktuellesArchivale);

		String navigation = proband.saveSchlagworte();	// test

		assertEquals("saveSchlagworte() muss zum Edit-View navigieren", "edit",
				navigation);
		List<Schlagwort> schlagwörter = aktuellesArchivale.getSchlagwörter();
		boolean containsLette=false;
		boolean containsProjekt=false;
		for (Schlagwort schlagwort:schlagwörter) {
			if (schlagwort.getName().equals("Lette")) containsLette=true;
			if (schlagwort.getName().equals("Projekt")) containsProjekt=true;
		}
		assertTrue(
				"Im aktuellen Archivale muss das Schlagwort 'Lette' gesetzt sein",
				containsLette);
		assertTrue(
				"Im aktuellen Archivale muss das Schlagwort 'Projekt' gesetzt sein",
				containsProjekt);
	}
}
