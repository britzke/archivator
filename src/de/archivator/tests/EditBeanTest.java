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
 */
public class EditBeanTest {

	private EditBean proband;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private Archivale aktuellesArchivale;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new EditBean();
		aktuellesArchivale = mock(Archivale.class);
		entityManagerFactory = mock(EntityManagerFactory.class);
		entityManager = mock(EntityManager.class);
		when(entityManagerFactory.createEntityManager()).thenReturn(
				entityManager);
		entityTransaction = mock(EntityTransaction.class);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityManager.merge(aktuellesArchivale)).thenReturn(aktuellesArchivale);
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
		when(aktuellesArchivale.getId()).thenReturn(0); // mock new Archivale
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
		when(aktuellesArchivale.getId()).thenReturn(1); // mock db existing
														// Archivale
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
		verify(entityManager,times(2)).getTransaction();
		verify(entityManager).remove(aktuellesArchivale);
		verify(entityTransaction).commit();
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#speichere()}.
	 */
	@Test
	public void testSpeichereNeuesArchivale() {
		when(aktuellesArchivale.getId()).thenReturn(0); // mock new Archivale
		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.speichere();
		assertEquals("lösche() muss auf die Detailseite navigieren", "detail",
				navigation);
		verify(entityManager,times(2)).getTransaction();
		verify(entityTransaction).commit();
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#speichere()}.
	 */
	@Test
	public void testSpeichereAltesArchivale() {
		when(aktuellesArchivale.getId()).thenReturn(1); // mock db-existend
														// Archivale
		proband.setAktuellesArchivale(aktuellesArchivale);

		String navigation = proband.speichere();
		assertEquals("lösche() muss auf die Detailseite navigieren", "detail",
				navigation);
		verify(entityManager,times(2)).getTransaction();
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
		assertEquals("loadNamen() muss zum Edit-View navigieren", "edit", navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveNamen()}.
	 */
	@Test
	public void testSaveNamen() {
		String navigation = proband.saveNamen();
		assertEquals("saveNamen() muss zum Edit-View navigieren", "edit", navigation);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#loadOrganisationseinheiten()}.
	 */
	@Test
	public void testLoadOrganisationseinheiten() {
		String navigation = proband.loadOrganisationseinheiten();
		assertEquals("loadOrganisationseinheiten() muss zum Edit-View navigieren", "edit", navigation);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#saveOrganisationseinheiten()}.
	 */
	@Test
	public void testSaveOrganisationseinheiten() {
		List<Organisationseinheit> organisationseinheiten = mock(List.class);
		when(aktuellesArchivale.getOrganisationseinheiten()).thenReturn(organisationseinheiten);
		String testOrganistationseinheiten = new String("Umschlag, Folie, Skizze");
		//proband.setArchivaleOrganisationseinheiten(testOrganistationseinheiten); Klasse fehlt
		
		String navigation = proband.saveOrganisationseinheiten();
		
		assertEquals("saveOrganisationseinheiten() muss zum Edit-View navigieren", "edit", navigation);
		List<Organisationseinheit> neueOrganisationseinheiten = aktuellesArchivale.getOrganisationseinheiten();
		assertTrue(neueOrganisationseinheiten.contains("Umschlag"));
		assertTrue(neueOrganisationseinheiten.contains("Folie"));
		assertTrue(neueOrganisationseinheiten.contains("Skizze"));
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadSchlagworte()}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException Wenn es in der EditBean keine Eigenschaft namens "aktuellesArchivale" gibt. 
	 * @throws IllegalAccessException Wenn der Zugriff zur EditBean verweigert wurde. 
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testLoadSchlagworte() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<Schlagwort> schlagwörter = new ArrayList<Schlagwort>();
		Schlagwort s = new Schlagwort();
		s.setName("Lette");
		schlagwörter.add(s);
		s = new Schlagwort();
		s.setName("Datenbank");
		schlagwörter.add(s);
		when(aktuellesArchivale.getSchlagwörter()).thenReturn(schlagwörter);
		Field f = EditBean.class.getDeclaredField("aktuellesArchivale");
		f.setAccessible(true);
		
		f.set(proband, aktuellesArchivale);
		
		String navigation = proband.loadSchlagworte();
		assertEquals("loadSchlagworte() muss zum Edit-View navigieren", "edit", navigation);
		assertEquals("Lette, Datenbank", proband.getArchivaleSchlagwörter());
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveSchlagworte()}.
	 */
	@Test
	public void testSaveSchlagworte() {
		List<Schlagwort> schlagwörter = mock(List.class);
		when(aktuellesArchivale.getSchlagwörter()).thenReturn(schlagwörter);
		String testSchlagworte = new String("Lette, Projekt, Datenbank");
		proband.setArchivaleSchlagwörter(testSchlagworte);
		
		String navigation = proband.saveSchlagworte();
		
		assertEquals("saveSchlagworte() muss zum Edit-View navigieren", "edit", navigation);
		List<Schlagwort> neueSchlagwörter = aktuellesArchivale.getSchlagwörter();
		assertTrue(neueSchlagwörter.contains("Lette"));
		assertTrue(neueSchlagwörter.contains("Projekt"));
		assertTrue(neueSchlagwörter.contains("Datenbank"));
	}

}
