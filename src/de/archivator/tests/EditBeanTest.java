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
import javax.persistence.Query;

import org.compass.core.Compass;
import org.compass.core.CompassSession;
import org.junit.Before;
import org.junit.Test;

import de.archivator.beans.DetailBean;
import de.archivator.beans.EditBean;
import de.archivator.beans.SearchBean;
import de.archivator.entities.Archivale;
import de.archivator.entities.Name;
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
	private DetailBean detailBean;
	private List<Archivale> archivalien;
	private Compass compass;
	private CompassSession compassSession;
	private SearchBean searchBean;
	private Query query;
	private List<Name> nameList;
	private ArrayList<Schlagwort> schlagwortList;

	/**
	 * Erzeugt eine Umgebung, die von allen Tests benötigt wird.
	 * 
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		proband = new EditBean();
		aktuellesArchivale = new Archivale();
		// aktuellesArchivale injizieren
		Field f = proband.getClass().getDeclaredField("aktuellesArchivale");
		f.setAccessible(true);
		f.set(proband, aktuellesArchivale);

		archivalien = mock(List.class);
		f = proband.getClass().getDeclaredField("archivalien");
		f.setAccessible(true);
		f.set(proband, archivalien);

		detailBean = mock(DetailBean.class);
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);
		// detailBean injizieren
		f = proband.getClass().getDeclaredField("details");
		f.setAccessible(true);
		f.set(proband, detailBean);

		searchBean = mock(SearchBean.class);
		// searchBean injizieren
		f = proband.getClass().getDeclaredField("searchBean");
		f.setAccessible(true);
		f.set(proband, searchBean);

		entityManagerFactory = mock(EntityManagerFactory.class);
		entityManager = mock(EntityManager.class);
		when(entityManagerFactory.createEntityManager()).thenReturn(
				entityManager);
		entityTransaction = mock(EntityTransaction.class);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		query = mock(Query.class);
		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(entityManager.merge(aktuellesArchivale)).thenReturn(
				aktuellesArchivale);
		// entityManager injizieren
		f = proband.getClass().getDeclaredField("entityManagerFactory");
		f.setAccessible(true);
		f.set(proband, entityManagerFactory);

		compass = mock(Compass.class);
		compassSession = mock(CompassSession.class);
		when(compass.openSession()).thenReturn(compassSession);
		// compass injizieren
		f = proband.getClass().getDeclaredField("compass");
		f.setAccessible(true);
		f.set(proband, compass);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#back()} wenn ein
	 * neues Archivale bearbeitet wurde.
	 */
	@Test
	public void testBackNewArchivale() {

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
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);

		String navigation = proband.back();

		assertEquals("detail", navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#lösche()}.
	 */
	@Test
	public void testLösche() {

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
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);

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
		verify(detailBean).setAktuellesArchivale((Archivale) anyObject());
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadNamen()}.
	 */
	@Test
	public void testLoadNamen() {
		List<Name> namen = new ArrayList<Name>();
		namen.add(new Name("Müller", "Hans"));
		namen.add(new Name("Meier", "Franz"));
		aktuellesArchivale.setNamen(namen);

		String navigation = proband.loadNamen();

		assertNull("loadNamen() muss zum gleichen View navigieren", navigation);
		assertEquals("Müller, Hans; Meier, Franz", proband.getFormularNames());

	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveNamen()}.
	 */
	@Test
	public void testSaveNamen() {
		proband.setFormularNames("Müller, Hans;");
		nameList = new ArrayList<Name>();
		Name name = new Name("Müller", "Hans");
		nameList.add(name);
		List<Name> archivaleNamen = new ArrayList<Name>();
		aktuellesArchivale.setNamen(archivaleNamen);

		when(query.getResultList()).thenReturn(nameList);
		when(entityManager.merge(name)).thenReturn(new Name("Müller", "Hans"));

		String navigation = proband.saveNamen();
		assertNull("saveNamen() muss zum gleichen View navigieren", navigation);
		assertEquals(1, aktuellesArchivale.getNamen().size());
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#loadOrganisationseinheiten()}.
	 */
	@Test
	public void testLoadOrganisationseinheiten() {
		Query q = mock(Query.class);
		when(entityManager.createQuery(anyString())).thenReturn(q);
		String navigation = proband.loadOrganisationseinheiten();
		assertNull(
				"loadOrganisationseinheiten() muss zum gleichen View navigieren",
				navigation);
		verify(entityManager).createQuery(anyString());
	}

	/**
	 * Test method for
	 * {@link de.archivator.beans.EditBean#saveOrganisationseinheiten()}.
	 */
	@Test
	public void testSaveOrganisationseinheiten() {

		String navigation = proband.saveOrganisationseinheiten();

		assertNull(
				"saveOrganisationseinheiten() muss zum gleichen View navigieren",
				navigation);
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#loadSchlagworte()}.
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
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);

		String navigation = proband.loadSchlagworte();
		assertNull("loadSchlagworte() muss zum Edit-View navigieren",
				navigation);
		assertEquals("Der zu ladende Test soll 'Lette, Datenbank' sein",
				"Lette, Datenbank", proband.getFormularSchlagwörter());
	}

	/**
	 * Test method for {@link de.archivator.beans.EditBean#saveSchlagworte()}.
	 * Testet das Speichern von Schlagworten.
	 */
	@Test
	public void testSaveSchlagworte() {
		proband.setFormularSchlagwörter("Lette");

		schlagwortList = new ArrayList<Schlagwort>();
		Schlagwort schlagwort = new Schlagwort("test");
		schlagwortList.add(schlagwort);
		List<Schlagwort> archivaleSchlagworte = new ArrayList<Schlagwort>();
		aktuellesArchivale.setSchlagwörter(archivaleSchlagworte);

		when(query.getResultList()).thenReturn(schlagwortList);
		when(entityManager.merge(schlagwort))
				.thenReturn(new Schlagwort("test"));

		String navigation = proband.saveSchlagworte();
		assertNull("saveSchlaworte() muss zum gleichen View navigieren",
				navigation);
		assertEquals(1, aktuellesArchivale.getSchlagwörter().size());
	}
}
