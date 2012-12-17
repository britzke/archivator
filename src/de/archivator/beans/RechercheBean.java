package de.archivator.beans;

import java.util.List;

import de.archivator.entities.Archivale;

/**
 * Stellt Eigenschaften und Funktionen für den View search.xthml sowie
 * results.xhtml zur Verfügung.
 * 
 * @author bubi
 */
public class RechercheBean {
	/**
	 * Das Suchkriterium, dass der Benutzer in das Formular search.xhtml
	 * eingetragen hat.
	 */
	private String suchKriterium;
	/**
	 * Die Liste der Archivalien, die durch die zuletzt ausgeführte Recherche
	 * gefunden wurden.
	 */
	private List<Archivale> archivalien;

	/**
	 * @return the suchKriterium
	 */
	public String getSuchKriterium() {
		return suchKriterium;
	}

	/**
	 * @param suchKriterium the suchKriterium to set
	 */
	public void setSuchKriterium(String suchKriterium) {
		this.suchKriterium = suchKriterium;
	}

	/**
	 * @return the archivalien
	 */
	public List<Archivale> getArchivalien() {
		return archivalien;
	}

	/**
	 * @param archivalien the archivalien to set
	 */
	public void setArchivalien(List<Archivale> archivalien) {
		this.archivalien = archivalien;
	}

	/**
	 * Sucht nach den Archivalien, die durch die Eigenschaft "suchKriterium"
	 * beschrieben werden.
	 * 
	 * @return "" konstant.
	 */
	public String search() {
		return "";
	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Betreff". Der Text
	 * "[betreff] = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void betreffClicked() {

	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Name". Der Text "[name] = "
	 * wird in die Eigenschaft suchKriterium gespeichert. Ist bereits ein Text
	 * im suchKriterium, so wird der text " and " vorangestellt.
	 */
	public void nameClicked() {

	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Schlagwort". Der Text
	 * "[schlagwort] = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void schlagwortClicked() {

	}

	/**
	 * ActionListener-Methode für die Schaltfläche "Titel". Der Text
	 * "[titel] = " wird in die Eigenschaft suchKriterium gespeichert. Ist
	 * bereits ein Text im suchKriterium, so wird der text " and "
	 * vorangestellt.
	 */
	public void titelClicked() {

	}

	/**
	 * ActionListener-Methode für die Schaltfläche "and". Der Text
	 * "[schlagwort] = " wird in die Eigenschaft suchKriterium gespeichert.
	 */
	public void andClicked() {

	}

	/**
	 * ActionListener-Methode für die Schaltfläche "or". Der Text
	 * " or " wird in die Eigenschaft suchKriterium gespeichert.
	 */
	public void orClicked() {

	}

}
