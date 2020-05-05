/**
 * 
 */
package pepapuum;

import java.io.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Nayttelija luokka, joka mm: tiet‰‰ n‰yttelij‰n kent‰t, osaa tarkistaa tietyn kent‰n oikeellisuuden, osaa muuttaa
 * merkkijonon n‰yttelij‰n tiedoiksi, osaa antaa merkkijonona i:nnen kent‰n tiedot ja muuttaa merkkijonon i:nneksi
 * kent‰ksi
 * @author petra puumala petra.p.puumala@student.jyu.fi
 * @version 16.6.2018
 *
 */
public class Nayttelija implements Cloneable {

    private int nid;
    private int eid;
    private String nayttelijanNimi = "";
    private String rooli = "";

    private static int seuraavaNro = 1;


    /**
     * Alustetaan n‰yttelij‰. tyhj‰
     */
    public Nayttelija() {
    }


    /**
     * Palauttaa n‰yttelij‰n kenttien lukum‰‰r‰n
     * @return n‰yttelij‰n kenttien lukum‰‰r‰
     */
    public int getKenttia() {
        return 4;
    }


    /**
     * Palauttaa n‰yttelij‰n nimen
     * @return n‰yttelij‰n nimi
     */
    public String getNimi() {
        return nayttelijanNimi;
    }


    /**
     * Palauttaa n‰yttelij‰n roolin
     * @return n‰yttelij‰n rooli
     */
    public String getRooli() {
        return rooli;
    }


    /**
     * Palautetaan kysymysteksti
     * @param k mink‰ kent‰n kysymys halutaan
     * @return valitun kent‰n kysymysteksti
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0:
            return "id";
        case 1:
            return "elokuvanId";
        case 2:
            return "Nimi";
        case 3:
            return "Rooli";
        default:
            return "???";
        }
    }


    /**
     * @param k Mink‰ kent‰n sis‰ltˆ halutaan
     * @return valitun kent‰n sis‰ltˆ
     * @example
     * <pre name="test">
     *   Nayttelija nayt = new Nayttelija();
     *   nayt.parse("   2   |  10  |   Iina Kuustonen  | Iiris ");
     *   nayt.anna(0) === "2";   
     *   nayt.anna(1) === "10";   
     *   nayt.anna(2) === "Iina Kuustonen";   
     *   nayt.anna(3) === "Iiris";
     * </pre>
     */
    public String anna(int k) {
        switch (k) {
        case 0:
            return "" + nid;
        case 1:
            return "" + eid;
        case 2:
            return nayttelijanNimi;
        case 3:
            return rooli;
        default:
            return "???";
        }
    }


    /**
     * Palauttaa nrona ekan indeksin kentt‰‰n joka on mielek‰st‰ n‰ytt‰‰ k‰ytt‰j‰lle
     * @return ensimm‰inen k‰ytt‰j‰n syˆtett‰v‰n kent‰n indeksi
     */
    public int ekaKentta() {
        return 2;
    }


    /**
     * Alustetaan tietyn elokuvan n‰yttelij‰
     * @param nro elokuvan id nro
     */
    public Nayttelija(int nro) {
        this.eid = nro;
    }


    /**
     * Antaa n‰yttelij‰lle seuraavan Id-numeron
     * @return n‰yttelij‰n id nro
     * @example
     * <pre name="test">
     * Nayttelija hugh1 = new Nayttelija();
     * hugh1.getNid() === 0;
     * hugh1.rekisteroi();
     * Nayttelija hugh2 = new Nayttelija();
     * hugh2.rekisteroi();
     * int n1 = hugh1.getNid();
     * int n2 = hugh2.getNid();
     * n1 === n2-1;
     * hugh1.getNid() === n1;
     * </pre>
     */
    public int rekisteroi() {
        nid = seuraavaNro;
        seuraavaNro++;
        return nid;
    }


    /**
     * Palauttaa n‰yttelij‰n tunnusnumeron (Nid)
     * @return n‰yttelij‰n nid eli tunnusnumero
     */
    public int getNid() {
        return nid;
    }


    /**
     * Palauttaa elokuvan tunnusnumeron (Eid)
     * @return elokuvan eid eli tunnusnumero
     */
    public int getEid() {
        return eid;
    }


    /**
     * Arvotaan satunnainen kokonaisluku v‰lille [ala, yla] testin‰yttelij‰‰ varten
     * @param ala alaraja
     * @param yla yl‰raja
     * @return satunnainen luku v‰lilt‰ [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala;
        return (int) Math.round(n);
    }


    /**
     * Apumetodi, jolla saadaan t‰ytetty‰ testiarvot n‰yttelij‰lle. Numero nimen per‰‰n arvotaan, jotta kahdella ei olisi
     * samoja tietoja
     * @param nro elokuvan tunnusnumero
     */
    public void taytaHughTiedot(int nro) {
        eid = nro;
        nayttelijanNimi = "Logan " + rand(1000, 9999);
        rooli = "Logan / Wolverine, X-24";
    }


    /**
     * N‰yttelij‰n tiedot merkkijonona, joka voidaan tallentaa tiedostoon
     * @return n‰yttelij‰n tiedot |- merkein eroteltuna
     * @example
     * <pre name="test">
     * Nayttelija nayttelija = new Nayttelija();
     * nayttelija.parse("  1  |  2 |   Hugh Jackman |   Logan / Wolverine, X-24  ");
     * nayttelija.toString() === "1|2|Hugh Jackman|Logan / Wolverine, X-24";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getNid() + "|" + eid + "|" + nayttelijanNimi + "|" + rooli;
    }


    /**
     * Tulostetaan n‰yttelij‰n tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(nayttelijanNimi + "  " + rooli);
    }


    /**
     * Tulostetaan elokuvan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Selvitet‰‰n/pilkotaan n‰yttelij‰n tiedot merkkijonosta, joka eroteltu | -merkeill‰.
     * Huolehtii, ett‰ seuraavaNro on aina suurempi, kuin tuleva n‰yttelij‰n tunnusnumero (nid)
     * @param rivi josta n‰yttelij‰n tiedot haetaan
     * @example
     * <pre name="test">
     * Nayttelija nayttelija = new Nayttelija();
     * nayttelija.parse("  1  |  2 |   Hugh Jackman |   Logan / Wolverine, X-24  ");
     * nayttelija.getEid() === 2;
     * nayttelija.toString() === "1|2|Hugh Jackman|Logan / Wolverine, X-24";
     * 
     * nayttelija.rekisteroi();
     * int n = nayttelija.getNid();
     * nayttelija.parse(""+(n+20));
     * nayttelija.rekisteroi();
     * nayttelija.getNid() === n+20+1;
     * nayttelija.toString() === "" + (n+20+1) + "|2|Hugh Jackman|Logan / Wolverine, X-24";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setNid(Mjonot.erota(sb, '|', getNid()));
        eid = Mjonot.erota(sb, '|', eid);
        nayttelijanNimi = Mjonot.erota(sb, '|', nayttelijanNimi);
        rooli = Mjonot.erota(sb, '|', rooli);
    }


    /**
     * asetetaan n‰yttelij‰lle id-numero ja huolehditaan, ett‰ seuraavaNro kasvaa yhdell‰
     * @param nro nro joka asetetaan nidiksi
     */
    private void setNid(int nro) {
        nid = nro;
        if (nid >= seuraavaNro)
            seuraavaNro = nid + 1;

    }


    /**
     * Asetetaan nimi
     * @param nimi asetettava nimi
     * @return null jos kaikki ok, muutoin virheteksti
     */
    public String setNimi(String nimi) {
        nayttelijanNimi = nimi;
        if (nayttelijanNimi.isEmpty())
            return "T‰yt‰ nimi";
        return null;
    }


    /**
     * Asetetaan rooli
     * @param role asetettava rooli
     * @return null jos kaikki ok, muutoin virheteksti
     */
    public String setRooli(String role) {
        rooli = role;
        if (rooli.isEmpty())
            return "T‰yt‰ rooli";
        return null;
    }


    @Override
    public int hashCode() {
        return nid;
    }


    /**
     * Tehd‰‰n identtinen klooni n‰yttelij‰st‰
     * @return Object kloonattu n‰yttelij‰
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Nayttelija nayttelija = new Nayttelija();
     *   nayttelija.parse("   1  |  2   | Iina Kuustonen");
     *   Nayttelija kopio = nayttelija.clone();
     *   kopio.toString() === nayttelija.toString();
     *   nayttelija.parse("   2  |  3   | Minka Kuustonen");
     *   kopio.toString().equals(nayttelija.toString()) === false;
     * </pre>
     */
    @Override
    public Nayttelija clone() throws CloneNotSupportedException {
        Nayttelija uusi;
        uusi = (Nayttelija) super.clone();
        return uusi;
    }


    /**
     * Testiohjelma Nayttelija luokalle
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Elokuva logan = new Elokuva(), logan2 = new Elokuva();
        logan.rekisteroi();
        logan2.rekisteroi();
        logan.tulosta(System.out);
        logan.taytaLoganTiedot();
        logan.tulosta(System.out);

        logan2.taytaLoganTiedot();
        logan2.tulosta(System.out);

        logan2.taytaLoganTiedot();
        logan2.tulosta(System.out);

    }

}