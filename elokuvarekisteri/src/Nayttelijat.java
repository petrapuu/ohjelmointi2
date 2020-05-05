/**
 * 
 */
package pepapuum;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * N�yttelij�t luokka, joka yll�pit�� varsinaista rekisteri� n�yttelij�ist�, eli osaa lis�t� ja 
 * poistaa n�yttelij�n. Lukee ja kirjoittaa n�yttelij�iden tiedostoon. Osaa etsi� ja lajitella.
 * @author petra petra.p.puumala@student.jyu.fi
 * @version 26.7.2018
 *
 */
public class Nayttelijat implements Iterable<Nayttelija> {
    private boolean muokattu = false;
    private String tiedostonPerusNimi = "nayttelijat";
    private final Collection<Nayttelija> alkiot = new LinkedList<Nayttelija>();


    /**
     * N�yttelij�iden alustus
     */
    public Nayttelijat() {
        // 
    }


    /**
     * Iteraattori kaikkien n�yttelij�iden l�pik�ymiseen
     * @return n�yttelij�iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Nayttelijat nayttelijat = new Nayttelijat();
     *  Nayttelija hugh21 = new Nayttelija(2); nayttelijat.lisaa(hugh21);
     *  Nayttelija hugh11 = new Nayttelija(1); nayttelijat.lisaa(hugh11);
     *  Nayttelija hugh22 = new Nayttelija(2); nayttelijat.lisaa(hugh22);
     *  Nayttelija hugh12 = new Nayttelija(1); nayttelijat.lisaa(hugh12);
     *  Nayttelija hugh23 = new Nayttelija(2); nayttelijat.lisaa(hugh23);
     * 
     *  Iterator<Nayttelija> i2=nayttelijat.iterator();
     *  i2.next() === hugh21;
     *  i2.next() === hugh11;
     *  i2.next() === hugh22;
     *  i2.next() === hugh12;
     *  i2.next() === hugh23;
     *  i2.next() === hugh12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int nids[] = {2,1,2,1,2};
     *  
     *  for ( Nayttelija nayt:nayttelijat ) { 
     *    nayt.getEid() === nids[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Nayttelija> iterator() {
        return alkiot.iterator();
    }


    /**
     * Lis�� uuden n�yttelij�n tietorakenteeseen. Ottaa n�yttelij�n omistukseensa.
     * @param nayt lis�tt�v��n n�yttelij��n viite. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Nayttelija nayt) {
        alkiot.add(nayt);
        muokattu = true;
    }


    /**
     * Palauttaa n�yttelij�iden lukum��r�n
     * @return n�yttelij�iden lukum��r�
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Antaa tiedot tietyn elokuvan n�yttelij�ist�
     * @param nro elokuvan id numero
     * @return lista elokuvalle l�ydetyist� n�yttelij�ist�
     */
    public List<Nayttelija> annaNayttelijat(int nro) {
        List<Nayttelija> loydetyt = new ArrayList<Nayttelija>();
        for (Nayttelija nayt : alkiot)
            if (nayt.getEid() == nro)
                loydetyt.add(nayt);
        return loydetyt;
    }


    /**
     * Lukee n�yttelij�t tiedostosta
     * @param tied tiedoston nimi
     * @throws SailoException jos lukeminen ep�onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Nayttelijat nayttelijat = new Nayttelijat();
     *  Nayttelija hugh1 = new Nayttelija(), hugh2 = new Nayttelija();
     *  hugh1.taytaHughTiedot(1);
     *  hugh2.taytaHughTiedot(2);
     *  String hakemisto = "testinayttelijat";
     *  String tiedNimi = hakemisto+"/nayttelijat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  nayttelijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  nayttelijat.lisaa(hugh1);
     *  nayttelijat.lisaa(hugh2);
     *  nayttelijat.talleta();
     *  nayttelijat.getLkm() === 2;
     *  nayttelijat = new Nayttelijat();            // Poistetaan vanhat luomalla uusi
     *  nayttelijat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  nayttelijat.lisaa(hugh2);
     *  nayttelijat.talleta();
     *  nayttelijat.getLkm() === 3;
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
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
                    continue; // rivi on tyhj� tai sis puolipisteen eli
                              // kommenttirivin
                Nayttelija nayttelija = new Nayttelija();
                nayttelija.parse(rivi);
                lisaa(nayttelija);
            }
            muokattu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (Exception e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
     * Luetaan aikaisemmin annetun nimisest� tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Palauttaa tiedoston nimen, jota k�ytet��n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asetetaan perusnimi ilman tarkenninta
     * @param tied tallennustiedoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;

    }


    /**
     * Palauttaa tallennukseen k�ytett�v�n tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
     * Tallentaa n�yttelij�t tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * 3|1|Boyd Holbrook|Donald Pierce
     * 4|1|Stephen Merchant|Caliban
     * </pre>
     * @throws SailoException jos talletus ep�onnistuu
     */
    public void talleta() throws SailoException {
        if (!muokattu)
            return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();         // tuhotaan vanha varakopio
        ftied.renameTo(fbak);  // nimet��n nykyinen tiedosto uudelleen
                               // varakopioksi

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) {
            for (Nayttelija nayt : this) {
                fo.println(nayt.toString());
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
     * Korvaa n�yttelij�n tietorakenteessa.  Ottaa n�yttelij�n omistukseensa.
     * Etsit��n samalla tunnusnumerolla oleva n�yttelij�.  Jos ei l�ydy,
     * niin lis�t��n uutena n�yttelij�n�.
     * @param nayttelija n�yttelij� viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t�ynn�
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
    public void korvaaTaiLisaa(Nayttelija nayttelija) throws SailoException {
        int id = nayttelija.getNid();

        for (int i = 0; i < getLkm(); i++) {
            if (((LinkedList<Nayttelija>) alkiot).get(i).getNid() == id) {
                ((LinkedList<Nayttelija>) alkiot).set(i, nayttelija);
                muokattu = true;
                return;
            }
        }
        lisaa(nayttelija);
    }


    /**
     * Poistaa kaikki tietyn tietyn elokuvan n�yttelij�t
     * @param nro viite siihen, mihin liittyv�t tietueet poistetaan
     * @return montako poistettiin
     * @example
     * <pre name="test">
     *  Nayttelijat nayttelijat = new Nayttelijat();
     *  Nayttelija hugh21 = new Nayttelija(); hugh21.taytaHughTiedot(2);
     *  Nayttelija hugh11 = new Nayttelija(); hugh11.taytaHughTiedot(1);
     *  Nayttelija hugh22 = new Nayttelija(); hugh22.taytaHughTiedot(2);
     *  Nayttelija hugh12 = new Nayttelija(); hugh12.taytaHughTiedot(1);
     *  Nayttelija hugh23 = new Nayttelija(); hugh23.taytaHughTiedot(2);
     *  nayttelijat.lisaa(hugh21);
     *  nayttelijat.lisaa(hugh11);
     *  nayttelijat.lisaa(hugh22);
     *  nayttelijat.lisaa(hugh12);
     *  nayttelijat.lisaa(hugh23);
     *  nayttelijat.poistaElokuvanNayttelijat(2) === 3;  nayttelijat.getLkm() === 2;
     *  nayttelijat.poistaElokuvanNayttelijat(3) === 0;  nayttelijat.getLkm() === 2;
     *  List<Nayttelija> n = nayttelijat.annaNayttelijat(2);
     *  n.size() === 0;
     *  n = nayttelijat.annaNayttelijat(1);
     *  n.get(0) === hugh11;
     *  n.get(1) === hugh12;
     * </pre>
     */
    public int poistaElokuvanNayttelijat(int nro) {
        int n = 0;
        for (Iterator<Nayttelija> it = alkiot.iterator(); it.hasNext();) {
            Nayttelija nayttelija = it.next();
            if (nayttelija.getEid() == nro) {
                it.remove();
                n++;
            }
        }
        if (n > 0) {
            muokattu = true;
        }
        return n;
    }


    /**
     * Poistaa valitun n�yttelij�n
     * @param nayttelija poistettava n�yttelija
     * @return tosi jos l�ytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Nayttelijat nayttelijat = new Nayttelijat();
     *  Nayttelija hugh21 = new Nayttelija(); hugh21.taytaHughTiedot(2);
     *  Nayttelija hugh11 = new Nayttelija(); hugh11.taytaHughTiedot(1);
     *  Nayttelija hugh22 = new Nayttelija(); hugh22.taytaHughTiedot(2); 
     *  Nayttelija hugh12 = new Nayttelija(); hugh12.taytaHughTiedot(1); 
     *  Nayttelija hugh23 = new Nayttelija(); hugh23.taytaHughTiedot(2); 
     *  nayttelijat.lisaa(hugh21);
     *  nayttelijat.lisaa(hugh11);
     *  nayttelijat.lisaa(hugh22);
     *  nayttelijat.lisaa(hugh12);
     *  nayttelijat.poista(hugh23) === false ; nayttelijat.getLkm() === 4;
     *  nayttelijat.poista(hugh11) === true;   nayttelijat.getLkm() === 3;
     *  List<Nayttelija> h = nayttelijat.annaNayttelijat(1);
     *  h.size() === 1; 
     *  h.get(0) === hugh12;
     * </pre>
     */
    public boolean poista(Nayttelija nayttelija) {
        boolean ret = alkiot.remove(nayttelija);
        if (ret)
            muokattu = true;
        return ret;
    }


    /**
     * Testiohjelma n�yttelij�ille
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Nayttelijat nayttelijat = new Nayttelijat();
        Nayttelija hugh = new Nayttelija();
        hugh.taytaHughTiedot(2);
        Nayttelija hugh2 = new Nayttelija();
        hugh2.taytaHughTiedot(1);
        Nayttelija hugh3 = new Nayttelija();
        hugh3.taytaHughTiedot(2);
        Nayttelija hugh4 = new Nayttelija();
        hugh4.taytaHughTiedot(2);

        try {
            nayttelijat.lueTiedostosta();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        nayttelijat.lisaa(hugh);
        nayttelijat.lisaa(hugh2);
        nayttelijat.lisaa(hugh3);
        nayttelijat.lisaa(hugh2);
        nayttelijat.lisaa(hugh4);

        try {
            nayttelijat.talleta();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(
                "================== Nayttelijat testi ==================");

        List<Nayttelija> nayttelijat2 = nayttelijat.annaNayttelijat(2);
        for (Nayttelija nayt : nayttelijat2) {
            System.out.println(nayt.getEid() + " ");
            nayt.tulosta(System.out);
        }

    }

}