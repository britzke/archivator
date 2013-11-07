package de.archivator.beans;

/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  müller, dreher,
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.archivator.entities.Archivale;
import de.archivator.entities.Name;

/**
 * Die PdfExportBean dient zum generieren einer PDF-Datei, die die Informationen
 * zu einem Archivale enthält
 * 
 * @author Jan Müller
 */

@Named(value = "pdfExportBean")
@SessionScoped
public class PdfExportBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private DetailBean detailBean;

	Document document;

	/**
	 * Die Methode createPdf dient zum Erzeugen einer PDF-Datei, die mit den
	 * Inhalten des aktuellen Archivales gefüllt wird.
	 */
	public void createPdf(FacesContext context) {
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		// den Browser informieren, dass er eine neue Datei erhält und sie
		// herunterladen soll, anstatt sie auf der Seite darzustellen
		response.setHeader("Content-disposition",
				"attachment; filename=output.pdf");
		try {
			document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			addContent();
			document.close();
			// the contentlength
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		} catch (DocumentException e) {
			System.out.println("Document Exception");
		} catch (IOException e) {
			System.out.println("IOException");
		} catch (Exception ex) {
			System.out.println("Exception");
		}
		context.responseComplete();
	}

	/**
	 * die Methode addDetails dient zum Hinzufügen von Details aus dem aktuellen
	 * Archivale in das Dokument
	 */
	private void addContent() throws DocumentException {
		Archivale aktuellesArchivale = detailBean.getAktuellesArchivale();
		document.add(new Paragraph("Betreff: "
				+ aktuellesArchivale.getBetreff()));
		document.add(new Paragraph("Inhalt: " + aktuellesArchivale.getInhalt()));
		document.add(new Paragraph("Datum (Jahr): "
				+ aktuellesArchivale.getVonJahr() + " - "
				+ aktuellesArchivale.getBisJahr()));
		List <Name> names = aktuellesArchivale.getNamen();
		for (Name n : names) {
			document.add(new Paragraph("Name:" + n.getVorname() + " " + n.getNachname()));
		}
	}

}
