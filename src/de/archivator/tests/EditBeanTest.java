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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.EditBean;
import de.archivator.entities.Archivale;

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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveNamen()}.
	 */
	@Test
	public void testSaveNamen() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#loadOrganisationseinheiten()}.
	 */
	@Test
	public void testLoadOrganisationseinheiten() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#saveOrganisationseinheiten()}.
	 */
	@Test
	public void testSaveOrganisationseinheiten() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadSchlagworte()}.
	 */
	@Test
	public void testLoadSchlagworte() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveSchlagworte()}.
	 */
	@Test
	public void testSaveSchlagworte() {
		fail("Not yet implemented");
	}

}
