package pepapuum;

import java.io.*;
import java.util.Comparator;

import static kanta.JulkaisuvuosiTarkistus.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Elokuvarekisterin elokuva joka osaa mm. itse huolehtia tunnusnumerostaan
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 *
 */
public class Elokuva implements Cloneable {

    private int eid;
    private String elokuvanNimi = "";
    private String ohjaaja = "";
    private int julkaisuvuosi = 0;
    private String valmistusmaa = "";
    private String kieli = "";
    private String genre = "";
    private String kesto = "";
    private double imdbArvosana = 0;

    private static int seuraavaNro = 1;

    /**
     * @author petra
     * @version 25.7.2018
     * Vertailija-luokka, jonka avulla voidaan verrata kahta elokuvaa keskenään
     */
    public static class Vertailija implements Comparator<Elokuva> {
        private int k;


        /**
         * Alustetaan vertailija vertailemaan tietyn kentän perusteella
         * @param k vertailtavan kentän indeksi
         * 
         */
        public Vertailija(int k) {
            this.k = k;
        }


        /**
         * Verrataan kahta elokuvaa keskenään
         * @param elokuva1 1. verrattava elokuva
         * @param elokuva2 2. verrattava elokuva
         * @return <0 jos j1 < j2, == 0 jos j1 == j2 ja muuten >0
         */
        @Override
        public int compare(Elokuva elokuva1, Elokuva elokuva2) {
            return elokuva1.getAvain(k)
                    .compareToIgnoreCase(elokuva2.getAvain(k));
        }
    }


    /**
     * Arvotaan satunnainen kokonaisluku välille [ala, yla], testiarvoja varten
     * @param ala alaraja
     * @param yla yläraja
     * @return satunnainen luku väliltä [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala;
        return (int) Math.round(n);
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot elokuvalle. Julkaisuvuosi arvotaan, jotta kahdella
     * elokuvalla ei olisi samoja tietoja.
     * @param apuvuosi vuosiluku joka annetaan elokuvalle valmistumisvuodeksi
     */
    public void taytaLoganTiedot(int apuvuosi) {
        elokuvanNimi = "Logan " + rand(1000, 9999);
        ohjaaja = "James Mangold";
        julkaisuvuosi = apuvuosi;
        valmistusmaa = "Yhdysvallat";
        kieli = "englanti";
        genre = "Scifi";
        kesto = "137min";
        imdbArvosana = 8.1;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot elokuvalle. Valmistumisvuosi arvotaan,
     * jotta kahdella elokuvalla ei olisi samoja tietoja
     */
    public void taytaLoganTiedot() {
        int apuvuosi = arvoVuosi();
        taytaLoganTiedot(apuvuosi);
    }

    
    /**
     * Palauttaa elokuvan nimen
     * @return elokuvan nimi merkkijonona
     */
    public String getNimi() {
        return this.elokuvanNimi;
    }


    /**
     * @param s Asettaa elokuvan nimen
     * @return s palauttaa nullin
     */
    public String setNimi(String s) {
        elokuvanNimi = s;
        return null;

    }


    /**
     * Palauttaa elokuvan tunnusnumeron (Eid)
     * @return elokuvan Eid eli tunnusnumero
     */
    public int getEid() {
        return eid;
    }


    /**
     * Asetetaan elokuvalle id-numero ja varmistetaan, että seuraava numero on suurempi, kuin tähän mennessä suurin
     * @param nro asetettava eid
     */
    private void setEid(int nro) {
        eid = nro;
        if (eid >= seuraavaNro)
            seuraavaNro = eid + 1;
    }


    /**
     * Palautetaan elokuvan ohjaaja
     * @return elokuvan ohjaajan nimi merkkijonona
     */
    public String getOhjaaja() {
        return this.ohjaaja;
    }


    /**
     * Asetetaan ohjaaja
     * @param s ohjaaja
     * @return null
     */
    public String setOhjaaja(String s) {
        ohjaaja = s;
        return null;
    }


    /**
     * Palautetaan elokuvan julkaisuvuosi
     * @return julkaisuvuosi
     */
    public String getVuosi() {
        String vuosi = Integer.toString(this.julkaisuvuosi);
        if (vuosi.isEmpty())
            vuosi = "";
        return vuosi;
    }


    /**
     * Asetetaan julkaisuvuosi
     * @param s julkaisuvuosi merkkijonona
     * @return null jos kaikki ok, muuten virheilmoitus
     */
    public String setVuosi(String s) {
        try {
            julkaisuvuosi = Integer.parseInt(s);
            return null;
        } catch (NumberFormatException ex) {
            String virhe = "Antamasi vuosiluku ei kelpaa";
            return virhe;
        }
    }


    /**
     * Palautetaan elokuvan valmistusmaa
     * @return elokuvan valmistusmaa merkkijonona
     */
    public String getMaa() {
        return this.valmistusmaa;
    }


    /**
     * Asetetaan valmistusmaa
     * @param s valmistusmaa
     * @return null
     */
    public String setMaa(String s) {
        valmistusmaa = s;
        return null;
    }


    /**
     * Palautetaan elokuvan kieli
     * @return elokuvan kieli merkkijonona
     */
    public String getKieli() {
        return this.kieli;
    }


    /**
     * Asetetaan elokuvan kieli
     * @param s kieli
     * @return null
     */
    public String setKieli(String s) {
        kieli = s;
        return null;
    }


    /**
     * Palautetaan elokjuvan genre merkkijonona
     * @return elokuvan genre merkkijonona
     */
    public String getGenre() {
        return this.genre;
    }


    /**
     * asetetaan genre
     * @param s genre
     * @return null
     */
    public String setGenre(String s) {
        genre = s;
        return null;
    }
    
    
    /**
     * Palauttaa elokuvan kestoajan
     * @return elokuvan pituus minuutteina
     */
    public String getKesto() {
        return kesto;
    }
    
    
    /**
     * Palauttaa elokuvan kestoajan inttinä
     * @return elokuvan pituus inttinä
     */
    public int getKestoInt() {
        String k = kesto;
        String kestoNrot = k.replaceAll("[^0-9.]", "");
        if (kestoNrot == "" || kestoNrot.isEmpty())
            return 0;
        int kestoInt = Integer.parseInt(kestoNrot);
        return kestoInt;
    }


    /**
     * asetetaan kesto
     * @param s kesto
     * @return null jos kaikki ok, muuten virheilmoitus
     */
    public String setKesto(String s) {
        String virhe = null;
        try {
            if (s.contains("min") && s.matches("\\d+.*")) {
                kesto = s;
                return null;
            }
            virhe = "Kesto on ilmoitettava minuutteina muodossa (123 min)";
        } catch (Exception e) {
            virhe = "Kesto on ilmoitettava minuutteina muodossa (123 min)";
            return virhe;
        }
        return virhe;
    }


    /**
     * palautetaan imdb-arvosana
     * @return elokuvan imdb-arvosana merkkijonona
     */
    public String getImdb() {
        String imdb = Double.toString(this.imdbArvosana);
        if (imdb.isEmpty())
            imdb = "";
        return imdb;
    }


    /**
     * asetetaan imdb-arvosana
     * @param s imdb-arvosana
     * @return null jos kaikki ok, muuten virheilmoitus
     */
    public String setImdb(String s) {
        String virhe = null;
        try {
            if (s.matches("\\d+.*") && (0 < Double.parseDouble(s))
                    && (Double.parseDouble(s) <= 10)) {
                imdbArvosana = Double.parseDouble(s);
                return null;
            }
            virhe = "ImDb-arvosanan on oltava väliltä 0-10";
        } catch (Exception e) {
            virhe = "ImDb-arvosanan on oltava väliltä 0-10";
            return virhe;
        }
        return virhe;
    }


    /**
     * Antaa elokuvalle seuraavan Id-numeron
     * @return elokuvan id
     * @example
     * <pre name="test">
     * Elokuva logan1 = new Elokuva();
     * logan1.getEid() === 0;
     * logan1.rekisteroi();
     * Elokuva logan2 = new Elokuva();
     * logan2.rekisteroi();
     * int n1 = logan1.getEid();
     * int n2 = logan2.getEid();
     * n1 === n2-1;
     * logan1.getEid() === n1;
     * </pre>
     */
    public int rekisteroi() {
        eid = seuraavaNro;
        seuraavaNro++;
        return eid;
    }

    
    /**
     * Tulostetaan elokuvan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", eid) + "  " + elokuvanNimi + "  "
                + ohjaaja);
        out.println("  " + julkaisuvuosi + "  " + valmistusmaa + "  " + kieli);
        out.println("  " + genre + "  " + kesto + "  " + imdbArvosana);
    }
    
    
    /**
     * Tulostetaan elokuvan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Palauttaa elokuvan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return elokuva tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Elokuva elokuva = new Elokuva();
     *   elokuva.parse("   1  |  Logan   | 2017");
     *   elokuva.toString().startsWith("1|Logan|2017|") === true; // on enemmän kuin 3 kenttää, siksi lopussa |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getEid() + "|" + elokuvanNimi + "|" + ohjaaja + "|"
                + julkaisuvuosi + "|" + valmistusmaa + "|" + kieli + "|" + genre
                + "|" + kesto + "|" + imdbArvosana;
    }


    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String annaTieto(int k) {
        switch (k) {
        case 0:
            return "" + elokuvanNimi;
        case 1:
            return "" + ohjaaja;
        case 2:
            return "" + julkaisuvuosi;
        case 3:
            return "" + valmistusmaa;
        case 4:
            return "" + kieli;
        case 5:
            return "" + genre;
        case 6:
            return "" + kesto;
        case 7:
            return "" + imdbArvosana;
        default:
            return "Äääliö";
        }
    }


    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch (k) {
        case 0:
            return "" + elokuvanNimi.toUpperCase();
        case 1:
            return "" + ohjaaja.toUpperCase();
        case 2:
            return "" + String.format("%2d", julkaisuvuosi);
        case 3:
            return "" + valmistusmaa.toUpperCase();
        case 4:
            return "" + kieli.toUpperCase();
        case 5:
            return "" + genre.toUpperCase();
        case 6:
            return "" + String.format("%4d", getKestoInt());
        case 7:
            return "" + imdbArvosana;
        default:
            return "Äääliö";
        }
    }


    /**
     * Selvitetään näyttelijän tiedot merkkijonosta, joka on eroteltu | -merkeillä.
     * Pitää huolta, että seuraavaNro on aina suurempi kuin asetettava Eid.
     * @param rivi merkkijono, josta tiedot erotellaan
     * @example
     * <pre name="test">
     * Elokuva elokuva = new Elokuva();
     * elokuva.parse("  1  |  Logan |    James Mangold");
     * elokuva.getEid() === 1;
     * elokuva.toString().startsWith("1|Logan|James Mangold|") === true;
     * 
     * elokuva.rekisteroi();
     * int n = elokuva.getEid();
     * elokuva.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     * elokuva.rekisteroi();
     * elokuva.getEid() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setEid(Mjonot.erota(sb, '|', getEid()));
        elokuvanNimi = Mjonot.erota(sb, '|', elokuvanNimi);
        ohjaaja = Mjonot.erota(sb, '|', ohjaaja);
        julkaisuvuosi = Mjonot.erota(sb, '|', julkaisuvuosi);
        valmistusmaa = Mjonot.erota(sb, '|', valmistusmaa);
        kieli = Mjonot.erota(sb, '|', kieli);
        genre = Mjonot.erota(sb, '|', genre);
        kesto = Mjonot.erota(sb, '|', kesto);
        imdbArvosana = Mjonot.erota(sb, '|', imdbArvosana);
    }


    @Override
    public int hashCode() {
        return eid;
    }


    /**
     * Tehdään identtinen klooni elokuvasta
     * @return Object kloonattu elokuva
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Elokuva elokuva = new Elokuva();
     *   elokuva.parse("   1  |  Logan   | James Magnigold");
     *   Elokuva kopio = elokuva.clone();
     *   kopio.toString() === elokuva.toString();
     *   elokuva.parse("   2  |  Kummisetä   | Francis Ford Coppola");
     *   kopio.toString().equals(elokuva.toString()) === false;
     * </pre>
     */
    @Override
    public Elokuva clone() throws CloneNotSupportedException {
        Elokuva uusi;
        uusi = (Elokuva) super.clone();
        return uusi;
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Elokuva logan = new Elokuva();
        Elokuva logan2 = new Elokuva();

        logan.tulosta(System.out);
        logan2.tulosta(System.out);

        logan.rekisteroi();
        logan2.rekisteroi();

        logan.taytaLoganTiedot();
        logan2.taytaLoganTiedot();

        logan.tulosta(System.out);
        logan2.tulosta(System.out);
    }

}