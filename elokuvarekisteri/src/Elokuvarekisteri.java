/**
 * 
 */
package pepapuum;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Elokuvarekisteri-luokka. Huolehtii Elokuvat ja Nayttelijat luokkien v‰lisest‰ yhteistyˆst‰ ja
 * v‰litt‰‰ n‰it‰ tietoja pyydett‰ess‰. Lukee ja kirjoittaa Elokuvarekisterin tiedostoon pyyt‰m‰ll‰
 * apua avustajiltaan
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 */
public class Elokuvarekisteri {

    private Elokuvat elokuvat = new Elokuvat();
    private Nayttelijat nayttelijat = new Nayttelijat();


    /**
     * Lis‰‰ elokuvarekisteriin uuden elokuvan
     * @param elokuva liitett‰v‰ elokuva
     * @throws SailoException jos lis‰yst‰ ei voida tehd‰
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();
     * Elokuva logan1 = new Elokuva(), logan2 = new Elokuva();
     * logan1.rekisteroi(); logan2.rekisteroi();
     * elokuvarekisteri.getElokuvia() === 0;
     * elokuvarekisteri.lisaa(logan1); elokuvarekisteri.getElokuvia() === 1;
     * elokuvarekisteri.lisaa(logan2); elokuvarekisteri.getElokuvia() === 2;
     * elokuvarekisteri.lisaa(logan1); elokuvarekisteri.getElokuvia() === 3;
     * elokuvarekisteri.getElokuvia() === 3;
     * elokuvarekisteri.annaElokuva(0) === logan1;
     * elokuvarekisteri.annaElokuva(1) === logan2;
     * elokuvarekisteri.annaElokuva(2) === logan1;
     * elokuvarekisteri.annaElokuva(3) === logan1; #THROWS IndexOutOfBoundsException
     * elokuvarekisteri.lisaa(logan1); elokuvarekisteri.getElokuvia() === 4;
     * elokuvarekisteri.lisaa(logan1); elokuvarekisteri.getElokuvia() === 5;
     * elokuvarekisteri.lisaa(logan1);
     * </pre>
     */
    public void lisaa(Elokuva elokuva) throws SailoException {
        elokuvat.lisaa(elokuva);
    }


    /**
     * Lis‰‰ elokuvarekisteriin uuden n‰yttelij‰n
     * @param nayttelija liitett‰v‰ n‰yttelij‰
     */
    public void lisaa(Nayttelija nayttelija) {
        nayttelijat.lisaa(nayttelija);
    }


    /**
     * Lukee elokuvarekisterin tiedot tiedostosta
     * @param nimi jota k‰yte‰‰n lukemisessa
     * @throws SailoException jos lukeminen ep‰onnistuu
    * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * #import java.io.*; 
     * #import java.util.*; 
     *  
     *  Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri(); 
     *   
     *  Elokuva elokuva1 = new Elokuva(); elokuva1.taytaLoganTiedot(); elokuva1.rekisteroi(); 
     *  Elokuva elokuva2 = new Elokuva(); elokuva2.taytaLoganTiedot(); elokuva2.rekisteroi(); 
     *  Nayttelija nayttelija21 = new Nayttelija(); nayttelija21.taytaHughTiedot(elokuva2.getEid()); 
     *  Nayttelija nayttelija11 = new Nayttelija(); nayttelija11.taytaHughTiedot(elokuva1.getEid()); 
     *  Nayttelija nayttelija22 = new Nayttelija(); nayttelija22.taytaHughTiedot(elokuva2.getEid());  
     *  Nayttelija nayttelija12 = new Nayttelija(); nayttelija12.taytaHughTiedot(elokuva1.getEid());  
     *  Nayttelija nayttelija23 = new Nayttelija(); nayttelija23.taytaHughTiedot(elokuva2.getEid()); 
     *    
     *  String hakemisto = "testielokuvat"; 
     *  File dir = new File(hakemisto); 
     *  File ftied  = new File(hakemisto+"/nimet.dat"); 
     *  File fhtied = new File(hakemisto+"/nayttelijat.dat"); 
     *  dir.mkdir();   
     *  ftied.delete(); 
     *  fhtied.delete(); 
     *  elokuvarekisteri.lueTiedostosta(hakemisto); #THROWS SailoException 
     *  elokuvarekisteri.lisaa(elokuva1); 
     *  elokuvarekisteri.lisaa(elokuva2); 
     *  elokuvarekisteri.lisaa(nayttelija21); 
     *  elokuvarekisteri.lisaa(nayttelija11); 
     *  elokuvarekisteri.lisaa(nayttelija22); 
     *  elokuvarekisteri.lisaa(nayttelija12); 
     *  elokuvarekisteri.lisaa(nayttelija23); 
     *  elokuvarekisteri.talleta(); 
     *  elokuvarekisteri.getElokuvia() === 2;
     *  elokuvarekisteri = new Elokuvarekisteri(); 
     *  elokuvarekisteri.lueTiedostosta(hakemisto); 
     *  elokuvarekisteri.lisaa(elokuva2); 
     *  elokuvarekisteri.lisaa(nayttelija23); 
     *  elokuvarekisteri.talleta(); 
     *  elokuvarekisteri.getElokuvia() === 3;
     *  ftied.delete()  === true; 
     *  fhtied.delete() === true; 
     *  File fbak = new File(hakemisto+"/nimet.bak"); 
     *  File fhbak = new File(hakemisto+"/nayttelijat.bak"); 
     *  fbak.delete() === true; 
     *  fhbak.delete() === true; 
     *  dir.delete() === true; 
     * </pre> 
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        elokuvat = new Elokuvat();
        nayttelijat = new Nayttelijat();

        setTiedosto(nimi);
        elokuvat.lueTiedostosta();
        nayttelijat.lueTiedostosta();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien elkokuvien viitteet 
     * @param hakuehto hakuehto  
     * @param k etsitt‰v‰n kent‰n indeksi  
     * @return tietorakenteen lˆytyneist‰ elokuvista
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *  
     *  Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri(); 
     *   
     *  Elokuva elokuva1 = new Elokuva(); elokuva1.taytaLoganTiedot(); elokuva1.rekisteroi(); 
     *  Elokuva elokuva2 = new Elokuva(); elokuva2.taytaLoganTiedot(); elokuva2.rekisteroi(); 
     *  Nayttelija nayttelija21 = new Nayttelija(); nayttelija21.taytaHughTiedot(elokuva2.getEid()); 
     *  Nayttelija nayttelija11 = new Nayttelija(); nayttelija11.taytaHughTiedot(elokuva1.getEid()); 
     *  Nayttelija nayttelija22 = new Nayttelija(); nayttelija22.taytaHughTiedot(elokuva2.getEid());  
     *  Nayttelija nayttelija12 = new Nayttelija(); nayttelija12.taytaHughTiedot(elokuva1.getEid());  
     *  Nayttelija nayttelija23 = new Nayttelija(); nayttelija23.taytaHughTiedot(elokuva2.getEid()); 
     *  elokuvarekisteri.lisaa(elokuva1);  
     *  elokuvarekisteri.lisaa(elokuva2);
     *   Elokuva elokuva3 = new Elokuva(); elokuva3.rekisteroi();
     *   elokuva3.setNimi("Muumit");
     *   elokuvarekisteri.lisaa(elokuva3);
     *   Collection<Elokuva> loytyneet = elokuvarekisteri.etsi("*Muumi*",0);
     *   loytyneet.size() === 1;
     *   Iterator<Elokuva> it = loytyneet.iterator();
     *   it.next() == elokuva3 === true; 
     * </pre>
     */ 
    public Collection<Elokuva> etsi(String hakuehto, int k) {
        return elokuvat.etsi(hakuehto, k);
    }


    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty())
            hakemistonNimi = nimi + "/";
        elokuvat.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        nayttelijat.setTiedostonPerusNimi(hakemistonNimi + "nayttelijat");
    }


    /**
     * Poistaa elokuvista ja n‰yttelijˆist‰ ne joilla on nro. Kesken.
     * @param elokuva , jonka mukaan poistetaan
     * @return montako leffaa poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *  
     * @example
     * <pre name="test">
     * #THROWS Exception
     *  Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();
     *   
     *  Elokuva elokuva1 = new Elokuva(); elokuva1.taytaLoganTiedot(); elokuva1.rekisteroi(); 
     *  Elokuva elokuva2 = new Elokuva(); elokuva2.taytaLoganTiedot(); elokuva2.rekisteroi();
     *  Elokuva elokuva3 = new Elokuva(); elokuva3.taytaLoganTiedot(); elokuva3.rekisteroi(); 
     *  Elokuva elokuva4 = new Elokuva(); elokuva4.taytaLoganTiedot(); elokuva4.rekisteroi(); 
     *  Elokuva elokuva5 = new Elokuva(); elokuva5.taytaLoganTiedot(); elokuva5.rekisteroi(); 
     *   
     *  Nayttelija nayttelija21 = new Nayttelija(); nayttelija21.taytaHughTiedot(elokuva2.getEid()); 
     *  Nayttelija nayttelija11 = new Nayttelija(); nayttelija11.taytaHughTiedot(elokuva1.getEid()); 
     *  Nayttelija nayttelija22 = new Nayttelija(); nayttelija22.taytaHughTiedot(elokuva2.getEid());  
     *  Nayttelija nayttelija12 = new Nayttelija(); nayttelija12.taytaHughTiedot(elokuva1.getEid());  
     *  Nayttelija nayttelija23 = new Nayttelija(); nayttelija23.taytaHughTiedot(elokuva2.getEid());
     *  
     *  elokuvarekisteri.lisaa(elokuva1); 
     *  elokuvarekisteri.lisaa(elokuva2);
     *  elokuvarekisteri.lisaa(elokuva3); 
     *  elokuvarekisteri.lisaa(elokuva4);
     *  elokuvarekisteri.lisaa(elokuva5); 
     *  
     *  elokuvarekisteri.lisaa(nayttelija21); 
     *  elokuvarekisteri.lisaa(nayttelija11); 
     *  elokuvarekisteri.lisaa(nayttelija22); 
     *  elokuvarekisteri.lisaa(nayttelija12); 
     *  elokuvarekisteri.lisaa(nayttelija23);
     *   
     *  elokuvarekisteri.etsi("*",0).size() === 5;
     *  elokuvarekisteri.annaNayttelijat(elokuva1).size() === 2;
     *  elokuvarekisteri.poista(elokuva1) === 1;
     *  elokuvarekisteri.etsi("*",0).size() === 4;
     *  elokuvarekisteri.annaNayttelijat(elokuva1).size() === 0;
     *  elokuvarekisteri.annaNayttelijat(elokuva2).size() === 3;
     * </pre>
     */
    public int poista(Elokuva elokuva) {
        if (elokuva == null) {
            return 0;
        }
        int ret = elokuvat.poista(elokuva.getEid());
        nayttelijat.poistaElokuvanNayttelijat(elokuva.getEid());
        return ret;
    }


    /** 
     * Poistaa t‰m‰n nayttelijan
     * @param nayttelija poistettava nayttelija
     * @example
     * <pre name="test">
     * #THROWS Exception
     *  Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();
     *   
     *   
     *  Elokuva elokuva1 = new Elokuva(); elokuva1.taytaLoganTiedot(); elokuva1.rekisteroi(); 
     *  Elokuva elokuva2 = new Elokuva(); elokuva2.taytaLoganTiedot(); elokuva2.rekisteroi(); 
     *  Nayttelija nayttelija21 = new Nayttelija();    
     *  nayttelija21.taytaHughTiedot(elokuva2.getEid()); 
     *  Nayttelija nayttelija11 = new Nayttelija(); 
     *  nayttelija11.taytaHughTiedot(elokuva1.getEid()); 
     *  Nayttelija nayttelija22 = new Nayttelija(); 
     *  nayttelija22.taytaHughTiedot(elokuva2.getEid());  
     *  Nayttelija nayttelija12 = new Nayttelija(); 
     *  nayttelija12.taytaHughTiedot(elokuva1.getEid());  
     *  Nayttelija nayttelija23 = new Nayttelija(); 
     *  nayttelija23.taytaHughTiedot(elokuva2.getEid()); 
     *  
     *  elokuvarekisteri.lisaa(elokuva1); 
     *  elokuvarekisteri.lisaa(elokuva2); 
     *  elokuvarekisteri.lisaa(nayttelija21); 
     *  elokuvarekisteri.lisaa(nayttelija11); 
     *  elokuvarekisteri.lisaa(nayttelija22); 
     *  elokuvarekisteri.lisaa(nayttelija12); 
     *  elokuvarekisteri.lisaa(nayttelija23);
     *   
     *  elokuvarekisteri.annaNayttelijat(elokuva1).size() === 2;
     *  elokuvarekisteri.poistaNayttelija(nayttelija11);
     *  elokuvarekisteri.annaNayttelijat(elokuva1).size() === 1;
     *  </pre>
     */
    public void poistaNayttelija(Nayttelija nayttelija) {
        nayttelijat.poista(nayttelija);
    }

    
    /**
     * Palauttaa i:nnen elokuvan
     * @param i  monesko elokuva palautetaan
     * @return  viite i:nteen elokuvaan
     * @throws IndexOutOfBoundsException    jos i v‰‰rin
     */
    public Elokuva annaElokuva(int i) throws IndexOutOfBoundsException {
        return elokuvat.anna(i);
    }


    /**
     * Haetaan elokuvan kaikki n‰yttelij‰t
     * @param elokuva elokuva jonka n‰yttelij‰tietoja haetaan
     * @return  tietorakenne, jossa viitteet elokuvan n‰yttelij‰tietoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     *  Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();
     *  Elokuva logan1 = new Elokuva(), logan2 = new Elokuva(), logan3 = new Elokuva();
     *  logan1.rekisteroi(); logan2.rekisteroi(); logan3.rekisteroi();
     *  int eid1 = logan1.getEid();
     *  int eid2 = logan2.getEid();
     *  Nayttelija hugh11 = new Nayttelija(eid1); elokuvarekisteri.lisaa(hugh11);
     *  Nayttelija hugh12 = new Nayttelija(eid1); elokuvarekisteri.lisaa(hugh12);
     *  Nayttelija hugh21 = new Nayttelija(eid2); elokuvarekisteri.lisaa(hugh21);
     *  Nayttelija hugh22 = new Nayttelija(eid2); elokuvarekisteri.lisaa(hugh22);
     *  Nayttelija hugh23 = new Nayttelija(eid2); elokuvarekisteri.lisaa(hugh23);
     *  
     *  List<Nayttelija> loytyneet;
     *  loytyneet = elokuvarekisteri.annaNayttelijat(logan3);
     *  loytyneet.size() === 0; 
     *  loytyneet = elokuvarekisteri.annaNayttelijat(logan1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == hugh11 === true;
     *  loytyneet.get(1) == hugh12 === true;
     *  loytyneet = elokuvarekisteri.annaNayttelijat(logan2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == hugh21 === true;
     * </pre> 
     */
    public List<Nayttelija> annaNayttelijat(Elokuva elokuva) {
        return nayttelijat.annaNayttelijat(elokuva.getEid());
    }


    /**
     * Palauttaa elokuvarekisterin elokuvam‰‰r‰n
     * @return      elokuvam‰‰r‰
     */
    public int getElokuvia() {
        return elokuvat.getLkm();
    }


    /**
     * palauttaa elokuvien yhteiskestot
     * @return palauttaa yhteiskestot
     */
    public int getYhteisKesto() {
        return elokuvat.getYhteisKesto();
    }


    /**
     * Tallettaa elokuvarekisterin tiedot tiedostoon
     * vaikka elokuvien tallentaminen ei onnistuisi, yritet‰‰n silti tallettaa n‰yttelij‰t
     * ennen kuin heitet‰‰n poikkeus
     * @throws SailoException jos talletuksessa ongelmia
     */
    public void talleta() throws SailoException {
        String virhe = "";
        try {
            elokuvat.talleta();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        try {
            nayttelijat.talleta();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe))
            throw new SailoException(virhe);
    }
    
    
    /** 
     * Korvaa elokuvan tietorakenteessa.  Ottaa elokuvan omistukseensa. 
     * Etsit‰‰n samalla tunnusnumerolla oleva elokuva.  Jos ei lˆydy, 
     * niin lis‰t‰‰n uutena elokuvana. 
     * @param elokuva lis‰tt‰v‰n elokuvan viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo t‰ynn‰ 
     */
    public void korvaaTaiLisaa(Elokuva elokuva) throws SailoException {
        elokuvat.korvaaTaiLisaa(elokuva);
    }


    /** 
     * Korvaa n‰yttelij‰n tietorakenteessa.  Ottaa n‰yttelij‰n omistukseensa. 
     * Etsit‰‰n samalla tunnusnumerolla oleva n‰yttelij‰.  Jos ei lˆydy, 
     * niin lis‰t‰‰n uutena n‰yttelij‰n‰. 
     * @param nayttelija lis‰tt‰v‰n n‰yttelij‰n viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo t‰ynn‰ 
     */
    public void korvaaTaiLisaa(Nayttelija nayttelija) throws SailoException {
        nayttelijat.korvaaTaiLisaa(nayttelija);
    }


    /**
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();

        try {
            // elokuvarekisteri.lueTiedostosta("elokuvat");

            Elokuva logan1 = new Elokuva(), logan2 = new Elokuva();
            logan1.rekisteroi();
            logan1.taytaLoganTiedot();
            logan2.rekisteroi();
            logan2.taytaLoganTiedot();

            elokuvarekisteri.lisaa(logan1);
            elokuvarekisteri.lisaa(logan2);
            int eid1 = logan1.getEid();
            int eid2 = logan2.getEid();

            elokuvarekisteri.lueTiedostosta("elokuvat");
            Nayttelija hugh11 = new Nayttelija(eid1);
            hugh11.taytaHughTiedot(eid1);
            elokuvarekisteri.lisaa(hugh11);
            Nayttelija hugh12 = new Nayttelija(eid1);
            hugh12.taytaHughTiedot(eid1);
            elokuvarekisteri.lisaa(hugh12);
            Nayttelija hugh21 = new Nayttelija(eid2);
            hugh21.taytaHughTiedot(eid2);
            elokuvarekisteri.lisaa(hugh21);
            Nayttelija hugh22 = new Nayttelija(eid2);
            hugh22.taytaHughTiedot(eid2);
            elokuvarekisteri.lisaa(hugh22);
            Nayttelija hugh23 = new Nayttelija(eid2);
            hugh23.taytaHughTiedot(eid2);
            elokuvarekisteri.lisaa(hugh23);

            System.out.println(
                    "============= elokuvarekisterin testi =================");

            for (int i = 0; i < elokuvarekisteri.getElokuvia(); i++) {
                Elokuva Elokuva = elokuvarekisteri.annaElokuva(i);
                System.out.println("Elokuva paikassa: " + i);
                Elokuva.tulosta(System.out);
                List<Nayttelija> loytyneet = elokuvarekisteri
                        .annaNayttelijat(Elokuva);
                for (Nayttelija Nayttelija : loytyneet)
                    Nayttelija.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}