/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2013, 2014
 * 						Jan Müller
 * 						burghard.britzke bubi@charmides.in-berlin.de
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.Document;

import de.archivator.beans.DetailBean;
import de.archivator.beans.PdfExportBean;
import de.archivator.beans.RechercheBean;
import de.archivator.entities.Archivale;

/**
 * Testet die PdfExportBean.
 * @author Jan Müller
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
public class PdfExportBeanTest {

	private PdfExportBean proband;
	private Archivale aktuellesArchivale;
	private DetailBean detailBean;
	private RechercheBean rechercheBean;
	private List<Archivale> archivalien;
	private FacesContext facesContext;
	private ExternalContext externalContext;
	private Document document;
	private HttpServletResponse response;
	private ServletOutputStream servletOutputStream;

	/**
	 * Erzeugt eine Umgebung, die von allen Tests benötigt wird.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		aktuellesArchivale = new Archivale();
		proband = new PdfExportBean();

		archivalien = mock(List.class);
		Field f = proband.getClass().getDeclaredField("archivalien");
		f.setAccessible(true);
		f.set(proband, archivalien);

		document = mock(Document.class);
		f = proband.getClass().getDeclaredField("document");
		f.setAccessible(true);
		f.set(proband, document);

		detailBean = mock(DetailBean.class);
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);
		f = proband.getClass().getDeclaredField("detailBean");
		f.setAccessible(true);
		f.set(proband, detailBean);

		rechercheBean = mock(RechercheBean.class);
		f = proband.getClass().getDeclaredField("rechercheBean");
		f.setAccessible(true);
		f.set(proband, rechercheBean);

		servletOutputStream =mock(ServletOutputStream.class);
		
		response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		externalContext = mock(ExternalContext.class);
		when(externalContext.getResponse()).thenReturn(response);

		facesContext = mock(FacesContext.class);
		when(facesContext.getExternalContext()).thenReturn(externalContext);
	}

	/**
	 * Testet ob die Liste der Archivalien nach der Erzeugung des Dokuments
	 * nicht null ist
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreatePdfFromRecord() throws IOException {
		// testen ob archivalien Objekt das übergebene Objekt ist
		//
		aktuellesArchivale.setId(1);
		aktuellesArchivale.setBetreff("TestBetreff");
		aktuellesArchivale.setVonJahr(2000);
		aktuellesArchivale.setBisJahr(2011);
		aktuellesArchivale.setInhalt("blabla");
		ArrayList<Archivale> alist = new ArrayList<Archivale>();
		alist.add(aktuellesArchivale);
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);

		proband.createPdfFromRecord(facesContext);

		assertNotNull(document);
		verify(response).setContentType(eq("application/pdf"));
		
	}

	/**
	 * testet die Methode zum Erzeugen der PDF aus einer Liste von
	 * Archivdatensätzen
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreatePdfFromList() throws IOException {
		aktuellesArchivale.setId(1);
		aktuellesArchivale.setBetreff("TestBetreff");
		aktuellesArchivale.setVonJahr(2000);
		aktuellesArchivale.setBisJahr(2011);
		aktuellesArchivale.setInhalt("blabla");
		ArrayList<Archivale> alist = new ArrayList<Archivale>();
		for (int i = 0; i < 3; i++)
			alist.add(aktuellesArchivale);
		when(rechercheBean.getArchivalien()).thenReturn(alist);
		
		proband.createPdfFromList(facesContext);
		
		assertNotNull(document);
	}

}