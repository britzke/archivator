/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  burghard.britzke bubi@charmides.in-berlin.de
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import de.archivator.beans.DetailBean;
import de.archivator.beans.KörperschaftenBean;
import de.archivator.entities.Archivale;
import de.archivator.entities.Körperschaft;

/**
 * @author burghard.britzke bubi@charmides.in-berlin.de
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class KörperschaftenBeanTest {

	protected Körperschaft[] selectedItems = {
			new Körperschaft("KOERP1"), new Körperschaft("KOERP2"),
			new Körperschaft("KOERP3") };
	private Archivale aktuellesArchivale;
	private List<Körperschaft> archivaleItems;

	@Mock
	EntityManagerFactory entityManagerFactory;
	@Mock
	private EntityManager entityManager;
	@Mock
	private Query query;
	@Mock
	EntityTransaction entityTransaction;
	@Mock
	Compass compass;
	@Mock
	private CompassSession compassSession;
	@Mock
	DetailBean detailBean;
	@Mock
	private List<Körperschaft> allItems;

	@InjectMocks
	KörperschaftenBean proband = new KörperschaftenBean();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		archivaleItems = new ArrayList<Körperschaft>();
		archivaleItems.add(new Körperschaft("KOERP1"));

		aktuellesArchivale = new Archivale();
		aktuellesArchivale.setKörperschaften(archivaleItems);
		Field f = proband.getClass().getSuperclass()
				.getDeclaredField("aktuellesArchivale");
		f.setAccessible(true);
		f.set(proband, aktuellesArchivale);

		proband.setSelectedItems(selectedItems);

		when(entityManagerFactory.createEntityManager()).thenReturn(
				entityManager);
		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(query.getResultList()).thenReturn(archivaleItems);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.OrganisationseinheitenBean#init()}.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             Wenn die Eigenschaft queryForAllItems nicht in der
	 *             Super-Klasse des Probanden deklariert ist.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testInit() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field f;

		proband.init();

		f = proband.getClass().getSuperclass()
				.getDeclaredField("queryForAllItems");
		f.setAccessible(true);
		String queryForAllItems = (String) f.get(proband);
		assertNotNull(
				"Die Eigenschaft 'queryForAllItems' darf nicht 'null' sein",
				queryForAllItems);

		f = proband.getClass().getSuperclass()
				.getDeclaredField("archivaleItems");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Körperschaft> k = (List<Körperschaft>) f
				.get(proband);
		assertNotNull(k);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.KörperschaftenBean#refreshArchivaleItems()}
	 * Testet die protected Methode refreshArchivaleItems().
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 *             Wenn die Eigenschaft archivaleItems nicht in der Super-Klasse
	 *             des Probanden deklariert wurde.
	 */
	@Test
	public void testRefreshArchivaleItems() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException {
		Method m = proband.getClass()
				.getDeclaredMethod("refreshArchivaleItems");
		m.setAccessible(true);
		m.invoke(proband);

		Field f = proband.getClass().getSuperclass()
				.getDeclaredField("archivaleItems");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Archivale> archivaleItems = (List<Archivale>) f.get(proband);

		assertNotNull(archivaleItems);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.KörperschaftenBean#resizeSelectedItems()}
	 * .
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 *             Wenn die Methode resizeSelectedItems() nicht im Probanden
	 *             implementiert ist.
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 *             Wenn die Eigenschaft selectedItems nicht in der Super-Klasse
	 *             des Probanden implementiert ist.
	 */
	@Test
	public void testResizeSelectedItems() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException {
		proband.init();
		Method m = proband.getClass().getDeclaredMethod("resizeSelectedItems");
		m.setAccessible(true);
		m.invoke(proband);

		Field f = proband.getClass().getSuperclass()
				.getDeclaredField("selectedItems");
		f.setAccessible(true);
		Körperschaft[] selectedItems = (Körperschaft[]) f
				.get(proband);

		assertEquals(archivaleItems.size(), selectedItems.length);
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.MultiSelectionListBean#loadItems()}.
	 */
	@Test
	public void testLoadItems() {
		proband.init();

		proband.loadItems();

		assertEquals(archivaleItems.size(), proband.getSelectedItems().length);

	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.MultiSelectionListBean#saveItems()}.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testSaveItems() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		// if entityManager should merge by answering with the Object provided
		// as Parameter.
		when(entityManager.merge(any())).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return args[0];
			}
		});
		when(compass.openSession()).thenReturn(compassSession);

		proband.setSelectedItems(selectedItems);
		proband.init();

		proband.saveItems();

		verify(entityTransaction).commit();
		verify(compassSession).commit();
		verify(detailBean).setAktuellesArchivale(any(Archivale.class));
	}
}
