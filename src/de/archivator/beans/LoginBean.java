/**
 * 
 */
package de.archivator.beans;

/**
 * Diese Bean stellt die Methoden für die Ansicht "login.xhtml" zur Verfügung.
 * 
 * @author bubi
 */
public class LoginBean {
	/**
	 * Der Wert des InputText-Elementes, in das der Benutzer das Kennwort
	 * eingeben kann.
	 */
	private String kennwort;
	/**
	 *  gibt an, ob der Benutzer angemeldet ist.
	 */
	private boolean angemeldet;

	/**
	 * @return the kennwort
	 */
	public String getKennwort() {
		return kennwort;
	}

	/**
	 * @param kennwort
	 *            the kennwort to set
	 */
	public void setKennwort(String kennwort) {
		this.kennwort = kennwort;
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

	/**
	 * Meldet den Benutzer an.
	 * 
	 * @return "" (leere Zeichenkette) immer.
	 */
	public String login() {
		return "";

	}

	/**
	 * Meldet den Benutzer ab.
	 * 
	 * @return "" (leere Zeichenkette) immer.
	 */
	public String logout() {
		return "";
	}
}
