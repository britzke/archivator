/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2013  burghard.britzke, bubi@charmides.in-berlin.de
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

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import de.archivator.altdaten.AltdatenKonverter;
import de.archivator.entities.Archivale;
import de.archivator.entities.Dokumentart;
import de.archivator.entities.Name;
import de.archivator.entities.Koerperschaft;
import de.archivator.entities.Schlagwort;

/**
 * Eine ImportBean verwaltet den Upload und den Import der Altdaten.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
@Named
public class ImportBean {
	@Inject
	EntityManagerFactory entityManagerFactory;
    /**
     * Importiert die Altdaten aus der geladenen XML-Datei.
     *  
     * @param event Ermöglicht den Zugriff auf die geladene Datei.
     */
    public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        UploadedFile file = event.getFile();
        try {
    		AltdatenKonverter me = new AltdatenKonverter(file.getInputstream());

    		me.extractArchivale();

    		CompassConfiguration conf = new CompassConfiguration().configure();
    		conf.addClass(Archivale.class);
    		conf.addClass(Dokumentart.class);
    		conf.addClass(Name.class);
    		conf.addClass(Koerperschaft.class);
    		conf.addClass(Schlagwort.class);
    		Compass compass = conf.buildCompass();

    		CompassGps gps = new SingleCompassGps(compass);
    		CompassGpsDevice jpaDevice = new JpaGpsDevice("jpa", entityManagerFactory);
    		gps.addGpsDevice(jpaDevice);
    		gps.start();
    		gps.index();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}