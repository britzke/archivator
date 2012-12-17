/**
 * 
 */
package de.archivator.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 */

/**
 * @author e0_wiezorek
 * 
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String user;
	List<String> userList;
	String correct;
	boolean angemeldet;

	public LoginBean() {

		userList = new ArrayList<String>();
		userList.add("admin");
		userList.add("root");

		correct = "";
	}

	public void anmeldung(ActionEvent actionEvent) {

		for (int i = 0; i < userList.size(); i++) {
			if (user.contentEquals(userList.get(i))) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Sie haben sich erfolgreich angemeldet!"));
				correct = userList.get(i);
				angemeldet = true;
				// break; bricht die for ab
				return;// bricht die ganze methode ab
			} else {
				correct = "";
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Falsche Angabe. Bitte erneut versuchen!"));
		angemeldet = false;
	}

	public void abmeldung(ActionEvent actionEvent) {

		if (!(correct.isEmpty())) {
			correct = "";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Sie haben sich erfolgreich abgemeldet!"));
			angemeldet = false;
		}
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the correct
	 */
	public String getCorrect() {
		return correct;
	}

	/**
	 * @param correct
	 *            the correct to set
	 */
	public void setCorrect(String correct) {
		this.correct = correct;
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