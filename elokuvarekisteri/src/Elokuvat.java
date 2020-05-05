package pepapuum;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author petra petra.p.puumala@student.jyu.fi
 * @version 15.6.2018
 *
 * Elokuvat-luokka joka osaa mm. lis‰t‰ uuden elokuvan
 */
public class Elokuvat {
    private static final int MAX_ELOKUVIA = 8;
    private int lkm = 0;
    private Elokuva[] alkiot = new Elokuva[MAX_ELOKUVIA];
    private String tiedostonPerusNimi = "nimet";
    private boolean muokattu = false;
    private int kestot;


    /**
     * Oletusmuodostaja
     */
    public Elokuvat() {
        //
    }


    /**
     * Lis‰‰ uuden elokuvan tietorakenteeseen. Ottaa elokuvan omistukseensa. Kasvattaa tarvittaessa taulukon kokoa.
     * Kasvattaa elokuvalistan elokuvien yhteiskestoa ko elokuvan keston verran.
     * @param elokuva lis‰tt‰v‰‰n elokuvaan viite. Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva logan1 = new Elokuva(), logan2 = new Elokuva();
     * elokuvat.getLkm() === 0;
     * elokuvat.lisaa(logan1); elokuvat.getLkm() === 1;
     * elokuvat.lisaa(logan2); elokuvat.getLkm() === 2;
     * elokuvat.lisaa(logan1); elokuvat.getLkm() === 3;
     * elokuvat.anna(0) === logan1;
     * elokuvat.anna(1) === logan2;
     * elokuvat.anna(2) === logan1;
     * elokuvat.anna(1) == logan1 === false;
     * elokuvat.anna(1) == logan2 === true;
     * elokuvat.anna(3) === logan1; #THROWS IndexOutOfBoundsException
     * elokuvat.lisaa(logan1); elokuvat.getLkm() === 4;
     * elokuvat.lisaa(logan1); elokuvat.getLkm() === 5;
     * elokuvat.lisaa(logan1);
     * </pre>
     */
    public void lisaa(Elokuva elokuva) {
        if (lkm >= alkiot.length) {
            Elokuva[] t = new Elokuva[lkm + 8];
            for (int i = 0; i < lkm; i++) {
                t[i] = alkiot[i];
            }
            alkiot = t;
        }

        alkiot[lkm] = elokuva;

        lkm++;
        muokattu = true;

    }


    /**
     * @param hloganehto s
     * @param k s
     * @return s
     */
    public Collection<Elokuva> etsi(String hloganehto, int k) {
        String ehto = "*";
        if (hloganehto != null && hloganehto.length() > 0)
            ehto = hloganehto;
        int hk = k;
        List<Elokuva> loytyneet = new ArrayList<Elokuva>();
        for (int i = 0; i < lkm; i++) {
            Elokuva elokuva = alkiot[i];
            if (WildChars.onkoSamat(elokuva.annaTieto(hk), ehto))
                loytyneet.add(elokuva);
        }
        Collections.sort(loytyneet, new Elokuva.Vertailija(hk));
        return loytyneet;
    }


    /**
     * Lukee elokuvat tiedostosta
     * @param tied tiedoston nimi
     * @throws SailoException jos lukeminen ep‰onnistuu
    * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Elokuvat elokuvat = new Elokuvat();
     *  Elokuva logan1 = new Elokuva(), logan2 = new Elokuva();
     *  logan1.taytaLoganTiedot();
     *  logan2.taytaLoganTiedot();
     *  String hakemisto = "testielokuvat";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  elokuvat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  elokuvat.lisaa(logan1);
     *  elokuvat.lisaa(logan2);
     *  elokuvat.talleta();
     *  elokuvat.getLkm() === 2;
     *  elokuvat = new Elokuvat();            // Poistetaan vanhat luomalla uusi
     *  elokuvat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  elokuvat.lisaa(logan2);
     *  elokuvat.getLkm() === 3;
     *  elokuvat.talleta();
     *  ftied.delete();
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete();
     *  dir.delete();
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (Scanner fi = new Scanner(
                new FileInputStream(new File(getTiedostonNimi())))) {
            String rivi;
            while (fi.hasNextLine()) {
                rivi = fi.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';')
                    continue; // rivi on tyhj‰ tai sis puolipisteen eli
                              // kommenttirivin
                Elokuva elokuva = new Elokuva();
                elokuva.parse(rivi);
                lisaa(elokuva);
            }
            muokattu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea tai kyseess‰ on uusi elokuvalista");
        } catch (Exception e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa perusnimen ilman tarkenninta
     * @param tiedNimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tiedNimi) {
        tiedostonPerusNimi = tiedNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Tallentaa elokuvat tiedostoon.
     * Tiedoston muoto:
     * <pre>
     *  1|Logan|James Mangold|2017|Yhdysvallat|englanti|Scifi|137min|8,1
     *  2|Kummiset‰|Francis Ford Coppola|1972|Yhdysvallat|englanti|Draama|175min|9,2
     * </pre>
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void talleta() throws SailoException {
        if (!muokattu)
            return;

        File fbak = new File(getBakNimi());         // tehd‰‰n vanhasta tiedostosta
                                                    // varakopio
        File ftied = new File(getTiedostonNimi());  // tehd‰‰n uusi tiedosto
        fbak.delete();                              // tuhotaan vanha varakopio
        ftied.renameTo(fbak);                       // nimet‰‰n nykyinen tiedosto varakopioksi

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) { // nimess‰ polut
                                                             // yms paikallaan
            for (int i = 0; i < lkm; i++) { 
                fo.println(alkiot[i].toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException(
                    "Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia");
        }

        muokattu = false;
    }


    /**
     * palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    private String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
     * Palauttaa elokuvien lukum‰‰r‰n
     * @return elokuvien lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * palauttaa elokuvien yhteiskestot
     * @return palauttaa yhteiskestot
     */
    public int getYhteisKesto() {
        return kestot;
    }


    /**
     * Palauttaa viitteen i:teen elokuvaan
     * @param i monennenko elokuvan viite halutaan
     * @return viite elokuvaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Elokuva anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return alkiot[i];
    }


    /**
     * Korvaa elokuvan tietorakenteessa.  Ottaa elokuvan omistukseensa.
     * Etsit‰‰n samalla tunnusnumerolla oleva elokuva.  Jos ei lˆydy,
     * niin lis‰t‰‰n uutena elokuvana.
     * @param elokuva elokuvan viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva logan1 = new Elokuva(), logan2 = new Elokuva();
     * logan1.rekisteroi(); logan2.rekisteroi();
     * elokuvat.getLkm() === 0;
     * elokuvat.korvaaTaiLisaa(logan1); elokuvat.getLkm() === 1;
     * elokuvat.korvaaTaiLisaa(logan2); elokuvat.getLkm() === 2;
     * Elokuva logan3 = logan1.clone();
     * elokuvat.korvaaTaiLisaa(logan3); elokuvat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Elokuva elokuva) throws SailoException {
        int id = elokuva.getEid();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getEid() == id) {
                alkiot[i] = elokuva;
                muokattu = true;
                return;
            }
        }
        lisaa(elokuva);
    }


    /**
     * Poistaa elokuvan jolla on valittu tunnusnumero 
     * @param id poistettavan elokuvan tunnusnumero
     * @return 1 jos poistettiin, 0 jos ei lˆydy
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva elokuva1 = new Elokuva(), elokuva2 = new Elokuva(), elokuva3 = new Elokuva();
     * elokuva1.rekisteroi(); elokuva2.rekisteroi(); elokuva3.rekisteroi();
     * int id1 = elokuva1.getEid();
     * elokuvat.lisaa(elokuva1); elokuvat.lisaa(elokuva2); elokuvat.lisaa(elokuva3);
     * elokuvat.poista(id1+1) === 1;
     * elokuvat.poista(id1) === 1; elokuvat.getLkm() === 1;
     * elokuvat.poista(id1+3) === 0; elokuvat.getLkm() === 1;
     * </pre>
     * 
     */
    public int poista(int id) {
        int nro = etsiEid(id);
        if (nro < 0) {
            return 0;
        }
        lkm--;
        for (int i = nro; i < lkm; i++) {
            alkiot[i] = alkiot[i + 1];
        }
        alkiot[lkm] = null;
        muokattu = true;
        return 1;
    }


    /**
     * Etsii elokuvan id:n perusteella
     * @param id tunnusnumero, jonka mukaan etsit‰‰n
     * @return lˆytyneen elokuvan indeksi tai -1 jos ei lˆydy
     * <pre name="test">
     * #THROWS SailoException 
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva elokuva1 = new Elokuva(), elokuva2 = new Elokuva(), elokuva3 = new Elokuva();
     * elokuva1.rekisteroi(); elokuva2.rekisteroi(); elokuva3.rekisteroi();
     * int id1 = elokuva1.getEid();
     * elokuvat.lisaa(elokuva1); elokuvat.lisaa(elokuva2); elokuvat.lisaa(elokuva3);
     * elokuvat.etsiEid(id1+1) === 1;
     * elokuvat.etsiEid(id1+2) === 2;
     * </pre>
     */
    public int etsiEid(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getEid()) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Testiohjelma elokuville
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Elokuvat elokuvat = new Elokuvat();
        Elokuva logan = new Elokuva();
        Elokuva logan2 = new Elokuva();
        logan.rekisteroi();
        logan.taytaLoganTiedot();
        logan2.rekisteroi();
        logan2.taytaLoganTiedot();

        try {
            elokuvat.lueTiedostosta();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        elokuvat.lisaa(logan);
        elokuvat.lisaa(logan2);
        elokuvat.lisaa(logan2);
        elokuvat.lisaa(logan2);
        elokuvat.lisaa(logan2);
        elokuvat.lisaa(logan2);

        System.out.println(
                "================== Elokuvat testi ==================");

        for (int i = 0; i < elokuvat.getLkm(); i++) {
            Elokuva elokuva = elokuvat.anna(i); // t‰m‰ poistetaan aikanaan,
                                                // samoin getLkm
            System.out.println("Elokuva nro:" + i);
            elokuva.tulosta(System.out);
        }

    }

}