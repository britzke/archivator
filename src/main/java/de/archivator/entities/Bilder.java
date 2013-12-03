package de.archivator.entities;
import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Entity zum hochladen und speichern von Bildern
 *
 */
@Entity
@Table(name = "Bilder", schema = "ARCHIVATOR")
public class Bilder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    
    private String beschreibung;
    
    @Lob
    private byte[] datei;
    
    // bi-directional many-to-many association to Archivalien
    @ManyToMany
    @JoinTable(name = "BILDER_ARCHIVALIEN", joinColumns = { @JoinColumn(name = "BILDER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ARCHIVALIEN_ID") }, schema = "ARCHIVATOR")
    private List<Archivale> archivalien;
    public Bilder() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public byte[] getDatei() {
        return datei;
    }
    public void setDatei(byte[] datei) {
        this.datei = datei;
    }
   
}