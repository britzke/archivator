/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  <name of author>
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

import de.archivator.beans.DetailBean;
import de.archivator.entities.Archivale;

/**
 * @author e0_mmueller
 *
 */
public class DetailBeanTest {

	private DetailBean proband;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private Archivale aktuellesArchivale;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		proband = new DetailBean();
		aktuellesArchivale = mock(Archivale.class);
		proband.setAktuellesArchivale(aktuellesArchivale);
		
		entityManagerFactory = mock(EntityManagerFactory.class);
		Field f = DetailBean.class.getDeclaredField("entityManagerFactory");
		f.setAccessible(true);
		f.set(proband, entityManagerFactory);
		
		entityManager = mock(EntityManager.class);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);

		entityTransaction = mock(EntityTransaction.class);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityManager.merge(aktuellesArchivale)).thenReturn(aktuellesArchivale);
	}


	/**
	 * Test method for {@link de.archivator.beans.DetailBean#back()}.
	 */
	@Test
	public void testBack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.archivator.beans.DetailBean#lösche()}.
	 */
	@Test
	public void testLösche() {
		String navigation = proband.lösche();
		assertEquals("lösche() muss zur Index-Seite navigieren", "index", navigation);
		verify(entityManagerFactory).createEntityManager();
		verify(entityManager).merge(aktuellesArchivale);
		verify(entityTransaction).begin();
		verify(entityManager).remove(aktuellesArchivale);
		verify(entityTransaction).commit();
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
