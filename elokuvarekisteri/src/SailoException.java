package pepapuum;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Petra Puumala petra.p.puumala@student.jyu.fi, Vesa Lappalainen
 * @version 15.6.2018
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa k�ytett�v� viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }

}
