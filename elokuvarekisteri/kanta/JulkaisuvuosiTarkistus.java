package kanta;

/**
 * @author petra
 * @version 15.6.2018
 *
 */
public class JulkaisuvuosiTarkistus {

    /**
     * Arvotaan satunnainen valmistumisvuosi, jotta elokuvien tiedot eroaisivat toisistaan
     * @return arvottu vuosiluku
     */
    public static int arvoVuosi() {
        int vuosiluku = (int) (Math.random() * (2018 - 1900) + 1900);
        return Math.round(vuosiluku);
    }

}
