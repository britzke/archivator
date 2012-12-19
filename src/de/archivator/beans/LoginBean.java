package de.archivator.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author e0_wiezorek 
 */
@ManagedBean
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

		if (password.contentEquals(PASSWORD)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Sie haben sich erfolgreich angemeldet!"));
			angemeldet = true;
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
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Sie haben sich erfolgreich abgemeldet!"));
			angemeldet = false;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the angemeldet
	 */
	public Boolean getAngemeldet() {
		return angemeldet;
	}

	/**
	 * @param angemeldet
	 *            the angemeldet to set
	 */
	public void setAngemeldet(Boolean angemeldet) {
		this.angemeldet = angemeldet;
	}
}