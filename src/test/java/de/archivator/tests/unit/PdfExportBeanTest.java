package de.archivator.tests.unit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.Document;

import de.archivator.beans.DetailBean;
import de.archivator.beans.EditBean;
import de.archivator.beans.PdfExportBean;
import de.archivator.beans.RechercheBean;
import de.archivator.beans.SearchBean;
import de.archivator.entities.Archivale;

public class PdfExportBeanTest {

	private PdfExportBean proband;
	private Archivale aktuellesArchivale;
	private DetailBean detailBean;
	private RechercheBean rechercheBean;
	private List<Archivale> archivalien;
	private FacesContext facesContext;
	private HttpServletResponse response;
	private ExternalContext externalContext;
	private Document document;

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
		
		externalContext = mock(ExternalContext.class);

		facesContext = mock(FacesContext.class);
	}

	/**
	 * testet ob die Liste der Archivalien nach der Erzeugung des Dokuments
	 * nicht null ist
	 * @throws IOException
	 */
	@Test
	public void testCreatePdfFromRecord() throws IOException {
		//testen ob archivalien Objekt das übergebene Objekt ist
		//
		aktuellesArchivale.setId(1);
		aktuellesArchivale.setBetreff("TestBetreff");
		aktuellesArchivale.setVonJahr(2000);
		aktuellesArchivale.setBisJahr(2011);
		aktuellesArchivale.setInhalt("blabla");
		when(detailBean.getAktuellesArchivale()).thenReturn(aktuellesArchivale);
		proband.createPdfFromRecord(facesContext);
		assertNotNull(archivalien);
		assertTrue(archivalien.size()>0);
		assertEquals(aktuellesArchivale, archivalien.get(0));
		assertNotNull(document);
	}
	

	/**
	 * testet die Methode zum Erzeugen der PDF aus einer Liste von Archivdatensätzen
	 * @throws IOException
	 */

	@Test
	public void testCreatePdfFromList() throws IOException {
		aktuellesArchivale.setId(1);
		aktuellesArchivale.setBetreff("TestBetreff");
		aktuellesArchivale.setVonJahr(2000);
		aktuellesArchivale.setBisJahr(2011);
		aktuellesArchivale.setInhalt("blabla");
		archivalien = new ArrayList<Archivale>();
		for (int i = 0; i < 3; i++)
			archivalien.add(aktuellesArchivale);
		when(rechercheBean.getArchivalien()).thenReturn(archivalien);
		proband.createPdfFromList(facesContext);
		assertNotNull(archivalien);
		assertNotNull(document);

	}

}
