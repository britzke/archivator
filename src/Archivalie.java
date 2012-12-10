import java.sql.Blob;

/**
 * @author e0_junghans
 *
 */
public class Archivalie {
	private int schubfach;
	private int mappe;
	private String betreff;
	private String inhalt;
	private int von;
	private int bis;
	private Blob datei;
	/**
	 * @return the schubfach
	 */
	public int getSchubfach() {
		return schubfach;
	}
	/**
	 * @param schubfach the schubfach to set
	 */
	public void setSchubfach(int schubfach) {
		this.schubfach = schubfach;
	}
	/**
	 * @return the mappe
	 */
	public int getMappe() {
		return mappe;
	}
	/**
	 * @param mappe the mappe to set
	 */
	public void setMappe(int mappe) {
		this.mappe = mappe;
	}
	/**
	 * @return the betreff
	 */
	public String getBetreff() {
		return betreff;
	}
	/**
	 * @param betreff the betreff to set
	 */
	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}
	/**
	 * @return the inhalt
	 */
	public String getInhalt() {
		return inhalt;
	}
	/**
	 * @param inhalt the inhalt to set
	 */
	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	/**
	 * @return the von
	 */
	public int getVon() {
		return von;
	}
	/**
	 * @param von the von to set
	 */
	public void setVon(int von) {
		this.von = von;
	}
	/**
	 * @return the bis
	 */
	public int getBis() {
		return bis;
	}
	/**
	 * @param bis the bis to set
	 */
	public void setBis(int bis) {
		this.bis = bis;
	}
	/**
	 * @return the datei
	 */
	public Blob getDatei() {
		return datei;
	}
	/**
	 * @param datei the datei to set
	 */
	public void setDatei(Blob datei) {
		this.datei = datei;
	}
	public Archivalie() {
		
	}
}
