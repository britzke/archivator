package de.archivator.beans;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import de.archivator.entities.Bilder;

@Named
@ApplicationScoped
public class FileUploadController {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println(event);
		entityManager = entityManagerFactory.createEntityManager();
		try {

			InputStream inputStream = event.getFile().getInputstream();
			int read = 0;
			byte[] bytes = new byte[1024];
			String fileName = event.getFile().getFileName();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] bildDaten;
			while ((read = inputStream.read(bytes)) != -1) {
				buffer.write(bytes, 0, read);
			}

			buffer.flush();
			bildDaten = buffer.toByteArray();
			inputStream.close();
			buffer.close();
			EntityTransaction uploadTransaction = entityManager
					.getTransaction();
			uploadTransaction.begin();
			Bilder bild = new Bilder();
			bild.setDatei(bildDaten);
			bild.setBeschreibung(fileName);
			entityManager.persist(bild);
			uploadTransaction.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed");
		} finally {
			entityManager.close();
		}

	}
}