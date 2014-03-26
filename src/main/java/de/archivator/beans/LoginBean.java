/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2012  e0_wiezorek, burghard.britzke
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

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 * Stellt die Funktionen für die An- und Abmeldung zur Verfügung.
 * 
 * @author e0_wiezorek
 * @author burghard.britzke
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// TODO Das kennwort muss variabel sein und nicht im Klartext gespeichert
	private static final String PASSWORD = "secret";

	private String password;
	private boolean angemeldet;

	/**
	 * Diese Methode wird als ActionListener der Schaltfläche “anmelden”
	 * angesprochen. Es wird die Eigenschaft “angemeldet” auf den Wert “true”
	 * gesetzt, wenn die Eigenschaft “password” dem konstanten Kennwort
	 * entspricht.
	 * 
	 * @param actionEvent
	 *            Wird nicht beachtet.
	 */
	public void login(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication()
				.getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
				messageBundleName, locale);
		if (password.contentEquals(PASSWORD)) {
			facesContext.addMessage(
					null,
					new FacesMessage(resourceBundle
							.getString("login.ErfolgreicheAnmeldung")));
			angemeldet = true;
		} else {
			facesContext.addMessage(
					null,
					new FacesMessage(resourceBundle
							.getString("login.ErfolgreicheAnmeldung")));
			angemeldet = false;
		}
	}

	/**
	 * Diese Methode wird als ActionListener der Schaltfläche “abmelden”
	 * angesprochen. Es wird die Eigenschaft “angemeldet" auf den Wert “false”
	 * gesetzt.
	 * 
	 * @param actionEvent
	 *            Wird nicht beachtet.
	 */
	public void logout(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication()
				.getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
				messageBundleName, locale);
		facesContext.addMessage(
				null,
				new FacesMessage(resourceBundle
						.getString("login.ErfolgreicheAbmeldung")));
		angemeldet = false;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the angemeldet
	 */
	public boolean isAngemeldet() {
		return angemeldet;
	}

	/**
	 * @param angemeldet
	 *            the angemeldet to set
	 */
	public void setAngemeldet(boolean angemeldet) {
		this.angemeldet = angemeldet;
	}
}