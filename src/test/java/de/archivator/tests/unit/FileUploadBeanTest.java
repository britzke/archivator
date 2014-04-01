package de.archivator.tests.unit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import de.archivator.beans.FileUploadController;
import de.archivator.entities.Bild;

public class FileUploadBeanTest {

	private FileUploadController proband;
	private Bild bild;
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction et;
	private Query q;
	private FileUploadEvent event;
	private UploadedFile file;
	private FileUpload upload;
	private InputStream stream;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		proband = new FileUploadController();
		
		//Eigenschaft initalisiert
		Field f = proband.getClass().getDeclaredField("bild");
		f.setAccessible(true);
		f.set(proband, bild);

		emf = mock(EntityManagerFactory.class);
		em = mock(EntityManager.class);
		when(emf.createEntityManager()).thenReturn(em);
		et = mock(EntityTransaction.class);
		when(em.getTransaction()).thenReturn(et);
		q = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(q);
		when(em.merge(bild)).thenReturn(bild);
		file = mock(UploadedFile.class);
		upload = mock(FileUpload.class);
		event = new FileUploadEvent(upload,file);
		stream = IOUtils.toInputStream("some test data for my input stream");
		when(event.getFile().getInputstream()).thenReturn(stream);
		bild = mock(Bild.class);
		// entityManager injizieren
		f = proband.getClass().getDeclaredField("entityManagerFactory");
		f.setAccessible(true);
		f.set(proband, emf);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandleFileUpload() {
		
		//System.out.println(event);
		proband.handleFileUpload(event);
		assertNotNull(bild);
		
	}

}
