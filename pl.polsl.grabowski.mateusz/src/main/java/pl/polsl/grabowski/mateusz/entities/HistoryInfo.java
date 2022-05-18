package pl.polsl.grabowski.mateusz.entities;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class that is used to pass data to the database
 *
 * @author Mateusz Grabowski
 * @version 1.0
 */
@Entity
public class HistoryInfo implements Serializable {

    /**
     * serial Id
     */
    private static final long serialVersionUID = 1L;
    /**
     * Id of the database record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * user's choice
     */
    private int choice;



    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets choice.
     *
     * @return the choice
     */
    public int getChoice() {
        return choice;
    }

    /**
     * Sets choice.
     *
     * @param choice the choice
     */
    public void setChoice(int choice) {
        this.choice = choice;
    }

    /**
     * Gets seed.
     *
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }

    /**
     * Sets seed.
     *
     * @param seed the seed
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /**
     * Key seed provided by the user
     */
    private int seed;

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * text provided by the user
     */
    private String text;

    /**
     * function returning the hash code of the id
     *
     * @return the hash code of the id
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Function returning whether given object is equal to this HistoryInfo object
     *
     * @param object given object
     * @return whether given object is equal to this HistoryInfo object
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoryInfo)) {
            return false;
        }
        HistoryInfo other = (HistoryInfo) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    /**
     * converting id to string
     *
     * @return id converted to string
     */
    @Override
    public String toString() {
        return "entities.HistoryInfo[ id=" + id + " ]";
    }

}
