/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  Jan Müller
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import de.archivator.entities.Archivale;
import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Körperschaft;

/**
 * Die PdfExportBean dient zum generieren einer PDF-Datei, die die Informationen
 * zu einem Archivale enthält
 * 
 * @author Jan Müller
 */
@Named(value = "pdfExportBean")
@RequestScoped
public class PdfExportBean {

	@Inject
	private DetailBean detailBean;
	@Inject
	private RechercheBean rechercheBean;
	private List<Archivale> archivalien;
	private Document document;
	private FacesContext context;
	private final String FILENAME = "document.pdf";
	private ByteArrayOutputStream byteArrayOutputStream;

	private boolean printPersonen;
	private boolean printDatum;
	private boolean printKörperschaften;
	private boolean printDokumentarten;

	/**
	 * Die Methode createPdfFromRecord dient zum Erzeugen einer PDF-Datei aus
	 * einem einzelnen Archiv-Datensatz
	 * 
	 * @param context
	 *            Der FacesContext
	 * @throws IOException
	 */
	public void createPdfFromRecord(FacesContext context) throws IOException {
		this.context = context;
		archivalien = new ArrayList<Archivale>();
		archivalien.add(detailBean.getAktuellesArchivale());
		if (archivalien.size() > 0) {
			createDocument();
			createResponse();
		}
	}

	/**
	 * Die Methode createPdfFromList dient zum Erzeugen einer PDF-Datei aus
	 * einem Recherche-Ergebnis
	 * 
	 * @throws IOException
	 */
	public void createPdfFromList(FacesContext context) throws IOException {
		this.context = context;
		archivalien = rechercheBean.getArchivalien();
		if (archivalien.size() > 0) {
			createDocument();
			createResponse();
		}
	}

	/**
	 * Die Methode createDocument erzeugt ein Pdf-Dokument und füllt sie mit den
	 * Inhalten aus der Liste "archivalien"
	 */
	private void createDocument() {
		try {
			document = new Document();
			byteArrayOutputStream = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();
			for (int i = 0; i < archivalien.size(); i++) {
				addContentFrom(archivalien.get(i));
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode addDetails dient zum Hinzufügen von Details aus einem
	 * Archivale in ein Dokument.
	 * 
	 * @param aktuellesArchivale
	 *            Das aktuelle Archivale, dessen Details zum PDF-Dokument
	 *            hinzugefügt werden soll.
	 */
	private void addContentFrom(Archivale aktuellesArchivale)
			throws DocumentException {
		LineSeparator UNDERLINE = new LineSeparator(1, 100, null,
				Element.ALIGN_CENTER, -2);

		// beim ersten Archivdatensatz einen Absatz hinzufügen,
		// da sonst Leerzeilen nicht eingefügt werden
		if (archivalien.indexOf(aktuellesArchivale) == 0) {
			document.add(new Paragraph(""));
		}

		document.add(Chunk.NEWLINE);
		document.add(UNDERLINE);
		document.add(Chunk.NEWLINE);

		document.add(new Paragraph("Betreff: "
				+ aktuellesArchivale.getBetreff()));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Inhalt: " + aktuellesArchivale.getInhalt()));
		document.add(Chunk.NEWLINE);

		document.add(new Paragraph("Mappe: " + aktuellesArchivale.getMappe()
				+ " Schubfach: " + aktuellesArchivale.getSchubfach()));
		document.add(Chunk.NEWLINE);
		if (printDatum) {
			document.add(new Paragraph("Datum (Jahr): "
					+ aktuellesArchivale.getVonJahr() + " - "
					+ aktuellesArchivale.getBisJahr()));
			document.add(Chunk.NEWLINE);
		}

		if (printPersonen) {
			List<Name> names = aktuellesArchivale.getNamen();
			if (names.size() > 0) {
				document.add(new Paragraph("Personen: "));
				for (Name n : names) {
					document.add(new Paragraph("  - " + n.getVorname() + " "
							+ n.getNachname()));
				}
				document.add(Chunk.NEWLINE);
			}
		}

		if (printKörperschaften) {
			List<Körperschaft> körperschaften = aktuellesArchivale
					.getKörperschaften();
			if (körperschaften.size() > 0) {
				document.add(new Paragraph("Körperschaften: "));
				for (Körperschaft o : körperschaften) {
					document.add(new Paragraph("Körperschaften:" + o.getName()));
				}
				document.add(Chunk.NEWLINE);
			}

		}

		if (printDokumentarten) {
			List<Dokumentart> dokumentarten = aktuellesArchivale
					.getDokumentarten();
			if (dokumentarten.size() > 0) {
				document.add(new Paragraph("Dokumentarten: "));
				for (Dokumentart d : dokumentarten) {
					document.add(new Paragraph("Dokumentarten:" + d.getName()));
				}
				document.add(Chunk.NEWLINE);
			}
		}

		if (archivalien.indexOf(aktuellesArchivale) == archivalien.size() - 1) {
			document.add(UNDERLINE);
			document.add(Chunk.NEWLINE);
		}
	}

	/**
	 * Schreibt den Inhalt des vorbereiteten byteArrayOutputStreams in den
	 * Response, stellt sicher, dass der Inhalt an den Klienten versendet wird
	 * und schließt den Response dann JSF-seitig ab.
	 */
	private void createResponse() {
		try {
			HttpServletResponse response = (HttpServletResponse) this.context
					.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			// den Browser informieren, dass er eine neue Datei erhält und sie
			// herunterladen soll, anstatt sie auf der Seite darzustellen
			response.setHeader("Content-disposition", "attachment; filename="
					+ FILENAME);
			// the contentlength
			response.setContentLength(byteArrayOutputStream.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			ServletOutputStream os = response.getOutputStream();
			byteArrayOutputStream.writeTo(os);
			os.flush();
			os.close();
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPrintPersonen() {
		return printPersonen;
	}

	public void setPrintPersonen(boolean printPersonen) {
		this.printPersonen = printPersonen;
	}

	public boolean isPrintDatum() {
		return printDatum;
	}

	public void setPrintDatum(boolean printDatum) {
		this.printDatum = printDatum;
	}

	public boolean isPrintKörperschaften() {
		return printKörperschaften;
	}

	public void setPrintKörperschaften(boolean printKörperschaften) {
		this.printKörperschaften = printKörperschaften;
	}

	public boolean isPrintDokumentarten() {
		return printDokumentarten;
	}

	public void setPrintDokumentarten(boolean printDokumentarten) {
		this.printDokumentarten = printDokumentarten;
	}
}