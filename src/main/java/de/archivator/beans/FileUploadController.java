/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2014  e11_cheneaux
 *                     burghard.britzke bubi@charmides.in-berlin.de
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
package de.archivator.beans;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import de.archivator.entities.Bild;

/**
 * Der FileUpdloadController speichert die Bilddateien in die Datenbank.
 * 
 * @author e11_cheneaux
 * @author burghard.britzke bubi@charmides.in-berlin.de
 * 
 */
@Named
@ApplicationScoped
public class FileUploadController {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private Bild bild;

	/**
	 * Listener, der ein hochgeladenes Bild in die Datenbank speichert.
	 * 
	 * @param event
	 *            Informationen über den Upload
	 */
	public void handleFileUpload(FileUploadEvent event) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			int c;
			UploadedFile uploadedFile = event.getFile();
			InputStream inputStream = uploadedFile.getInputstream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			while ((c = inputStream.read()) != -1) {
				buffer.write(c);
			}
			buffer.flush();
			inputStream.close();
			bild = new Bild();
			bild.setDatei(buffer.toByteArray());
			buffer.close();
			bild.setBeschreibung(uploadedFile.getFileName());
			bild.setInhaltsTyp(uploadedFile.getContentType());

			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.persist(bild);
			transaction.commit();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage("Hochladefehler",
					e.getLocalizedMessage());
			context.addMessage(event.getComponent().getClientId(), message);
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}
}