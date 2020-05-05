package fxPepapuum;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import pepapuum.Elokuva;
import pepapuum.Elokuvarekisteri;
import pepapuum.Nayttelija;
import pepapuum.SailoException;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.util.*;

import fi.jyu.mit.fxgui.*;

/**
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 *
 */
public class PepapuumGUIController implements Initializable {
    @FXML
    private TextField hakuehto;
    @FXML
    private Label labelVirhe;
    @FXML
    private ComboBoxChooser<String> cbKentat; // mink‰ mukaan haetaan
    @FXML
    private ListChooser<Elokuva> chooserElokuvat;
    @FXML
    private ScrollPane panelElokuva;
    @FXML
    private StringGrid<Nayttelija> tableNayttelijat;
    @FXML
    private TextField editNimi;
    @FXML
    private TextField editOhjaaja;
    @FXML
    private TextField editVuosi;
    @FXML
    private TextField editMaa;
    @FXML
    private TextField editKieli;
    @FXML
    private TextField editGenre;
    @FXML
    private TextField editKesto;
    @FXML
    private TextField editImdb;
    @FXML
    private Label labelKestot;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    /**
     * Hakuehdon k‰sittely
     */
    @FXML
    private void handleHakuehto() {
        if (elokuvaKohdalla != null)
            hae(elokuvaKohdalla.getEid());
    }


    /**
     * Elokuvalistan tallennuksen k‰sittely
     */
    @FXML
    private void handleTallenna() {
        tallenna();
    }


    /**
     * Uuden elokuvalistan avaamisen k‰sittely
     */
    @FXML
    private void handleAvaa() {
        avaa();
    }


    /**
     * tulostusikkunan avaamisen k‰sittely
     */
    @FXML
    private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
    }


    /**
     * Ohjelman lopettamisen k‰sittely
     */
    @FXML
    private void handleLopeta() {
        tallenna();
        Platform.exit();
    }


    /**
     * elokuvan lis‰yksen k‰sittely
     */
    @FXML
    private void handleLisaaElokuva() {
        uusiElokuva();
        // ElokuvaController.muokkaaElokuvaa();
    }


    /**
     * elokuvan muokkauksen k‰sittely
     */
    @FXML
    private void handleMuokkaaElokuvaa() {
        muokkaaElokuvaa();
    }


    /**
     * elokuvan poistamisen k‰sittely
     */
    @FXML
    private void handlePoistaElokuva() {
        poistaElokuva();
    }


    /**
     * n‰yttelij‰n lis‰‰misen k‰sittely
     */
    @FXML
    private void handleLisaaNayttelija() {
        uusiNayttelija();
    }


    /**
     * n‰yttelij‰n tietojen muokkaamisen k‰sittely
     */
    @FXML
    private void handleMuokkaaNayttelijaa() {
        muokkaaNayttelijaa();
    }


    /**
     * n‰yttelij‰n poistaminen
     */
    @FXML
    private void handlePoistaNayttelija() {
        poistaNayttelija();
    }


    /**
     * K‰yttˆohjeiden avaaminen selaimeen
     */
    @FXML
    private void handleApua() {
        apua();
    }


    /**
     * Tietoja-ikkunan avaaminen
     */
    @FXML
    private void handleTietoja() {
        TietojaController.naytaTietoja();
    }

    // ===========================================================================================
    // T‰st‰ eteenp‰in ei k‰yttˆliittym‰‰n suoraan liittyv‰‰ koodia

    private Elokuvarekisteri elokuvarekisteri;
    private Elokuva elokuvaKohdalla;
    private TextField edits[];
    private String kysymykset[];
    private int yhteisKesto;
    private static Nayttelija apunayttelija = new Nayttelija();
    private String listannimi = "elokuvat";


    /**
     * Tekee tarvittavat muut alustukset, Luodaan tekstikent‰t ja asetetaan niihin kuuntelija.
     * Alustetaan n‰yttelij‰taulukon otsikot
     */
    protected void alusta() {
        chooserElokuvat.clear();
        chooserElokuvat.addSelectionListener(e -> naytaElokuva());
        edits = new TextField[] { editNimi, editOhjaaja, editVuosi, editMaa,
                editKieli, editGenre, editKesto, editImdb };
        kysymykset = new String[] { "Elokuvan nimi", "Ohjaaja", "Julkaisuvuosi",
                "Valmistusmaa", "Kieli", "Genre", "Kesto", "ImDb-arvosana" };

        cbKentat.clear();
        for (int i = 0; i < kysymykset.length; i++) {
            cbKentat.add(kysymykset[i]);
        }
        cbKentat.setSelectedIndex(0);

        int eka = apunayttelija.ekaKentta();
        int lkm = apunayttelija.getKenttia();
        String[] headings = new String[lkm - eka];
        for (int i = 0, k = eka; k < lkm; i++, k++)
            headings[i] = apunayttelija.getKysymys(k);
        tableNayttelijat.initTable(headings);
        tableNayttelijat
                .setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableNayttelijat.setEditable(false);
        tableNayttelijat.setPlaceholder(new Label("Ei viel‰ n‰yttelijˆit‰"));

        tableNayttelijat.setColumnSortOrderNumber(1);
        tableNayttelijat.setColumnSortOrderNumber(2);
        tableNayttelijat.setAlignment(1, Pos.TOP_LEFT);
    }


    /**
     * N‰ytet‰‰n mahdollinen virheteksti
     * @param virhe virheteksti
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


    /**
     * Asetetaan otsikko
     * @param title otsikko
     */
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }


    /**
     * Alustaa elokuvalistan lukemalla sen valitun nimisest‰ tiedostosta
     * @param nimi tiedosto josta elokuvalistan tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstin‰
     */
    protected String lueTiedosto(String nimi) {
        listannimi = nimi;
        setTitle("Elokuvalista - " + listannimi);
        try {
            elokuvarekisteri.lueTiedostosta(nimi);
            hae(0); // hakee listboxin n‰ytt‰m‰‰n sis‰ltˆj‰
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null)
                Dialogs.showMessageDialog(virhe, dlg -> dlg.getDialogPane().setPrefWidth(400));
            return virhe;
        }
    }


    /**
    * Kysyt‰‰n tiedoston nimi ja luetaan se
    * @return true jos onnistui, false jos ei
    */
    public boolean avaa() {
        String uusinimi = AvaaController.kysyNimi(null, listannimi);
        if (uusinimi == null)
            return false;
        lueTiedosto(uusinimi);
        return true;
    }


    /**
    * Tietojen tallennus
    */
    private String tallenna() {
        try {
            elokuvarekisteri.talleta();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog(
                    "Talletuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }


    /**
     * Elokuvan muokkausikkunan avaus, tietojen tallennus ja elokuvien hakeminen listaan
     */
    private void muokkaaElokuvaa() {
        try {
            Elokuva elokuva;
            elokuva = ElokuvaController.kysyElokuva(null,
                    elokuvaKohdalla.clone());
            if (elokuva == null)
                return;
            elokuvarekisteri.korvaaTaiLisaa(elokuva);
            hae(elokuva.getEid());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }


    /**
     * N‰yttelij‰n tietojen muokkaus, tietojen tallennus ja elokuvien hakeminen listaan
     */
    private void muokkaaNayttelijaa() {
        int r = tableNayttelijat.getRowNr();
        if (r < 0) {
            return;
        }
        Nayttelija nayttelija = tableNayttelijat.getObject();
        if (nayttelija == null)
            return;
        try {
            nayttelija = NayttelijaController.kysyNayttelija(null,
                    nayttelija.clone());
            if (nayttelija == null) {
                return;
            }
            elokuvarekisteri.korvaaTaiLisaa(nayttelija);
            naytaNayttelijat(elokuvaKohdalla);
            tableNayttelijat.selectRow(r); // j‰rjestet‰‰n sama rivi takaisin
                                           // valituksi
        } catch (CloneNotSupportedException e) { /* clone on tehty */
        } catch (SailoException e) {
            Dialogs.showMessageDialog(
                    "Ongelmia lis‰‰misess‰: " + e.getMessage());
        }
    }


    /**
     * Dialogi elokuvan poistamiseksi
     */
    private void poistaElokuva() {
        if (elokuvaKohdalla == null) {
            return;
        }
        if (!Dialogs.showQuestionDialog("Poistetaanko elokuva?",
                "Poistetaanko elokuva: " + elokuvaKohdalla.getNimi() + "?",
                "Kyll‰", "Ei")) {
            return;
        }
        elokuvarekisteri.poista(elokuvaKohdalla);
        int index = chooserElokuvat.getSelectedIndex();
        hae(0);
        chooserElokuvat.setSelectedIndex(index);
    }


    /**
     * Dialogi n‰yttelij‰n poistamiseksi
     */
    private void poistaNayttelija() {
        Nayttelija nayt = tableNayttelijat.getObject();
        if (nayt == null)
            return;
        if (!Dialogs.showQuestionDialog("Poistetaanko n‰yttelij‰?",
                "Poistetaanko n‰yttelij‰: " + nayt.getNimi() + "?", "Kyll‰",
                "Ei")) {
            return;
        }
        int rivi = tableNayttelijat.getRowNr();
        elokuvarekisteri.poistaNayttelija(nayt);
        naytaNayttelijat(elokuvaKohdalla);
        tableNayttelijat.selectRow(rivi);
    }


    /** tarkistus, onko tallennettu
     * @return true, jos saa sulkea
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }


    /**
     * Avaa k‰yttˆohjeet selaimeen
     */
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI(
                    "https://tim.jyu.fi/view/kurssit/tie/ohj2/2018k/ht/pepapuum");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }

    }


    /**
     * Luo uuden elokuvan jota aletaan editoimaan
     */
    public void uusiElokuva() {
        try {
            Elokuva uusi = new Elokuva();
            uusi = ElokuvaController.kysyElokuva(null, uusi);
            if (uusi == null)
                return;
            uusi.rekisteroi();
            elokuvarekisteri.lisaa(uusi);
            hae(uusi.getEid());
        } catch (SailoException e) {
            Dialogs.showMessageDialog(
                    "Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }


    /**
     * Luo uuden n‰yttelij‰n jota aletaan editoimaan
     */
    public void uusiNayttelija() {
        if (elokuvaKohdalla == null)
            return;
        Nayttelija nayt = new Nayttelija(elokuvaKohdalla.getEid());
        nayt = NayttelijaController.kysyNayttelija(null, nayt);
        if (nayt == null)
            return;
        nayt.rekisteroi();
        elokuvarekisteri.lisaa(nayt);
        naytaNayttelijat(elokuvaKohdalla);
        tableNayttelijat.selectRow(500); // valitsee viimeisen rivin
        // nayt.taytaHughTiedot(elokuvaKohdalla.getEid());
        // elokuvarekisteri.lisaa(nayt);;
        // hae(elokuvaKohdalla.getEid());
    }


    /**
     * Hakee elokuvien tiedot listaan
     * @param elokuvanro elokuvan numero, joka aktivoidaan haun j‰lkeen
     */
    private void hae(int elokuvanro) {
        int k = cbKentat.getSelectedIndex();
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0)
            ehto = "*" + ehto + "*";
        naytaVirhe(null);

        chooserElokuvat.clear();

        Collection<Elokuva> elokuvat;
        int index = 0;
        int i = 0;
        elokuvat = elokuvarekisteri.etsi(ehto, k);
        yhteisKesto = 0;
        for (Elokuva elokuva : elokuvat) {
            if (elokuva.getEid() == elokuvanro)
                index = i;
            chooserElokuvat.add(elokuva.getNimi(), elokuva);
            yhteisKesto += elokuva.getKestoInt();
            i++;
        }
        if (i == 0)
            ElokuvaController.tyhjenna(edits); // jos ei yht‰‰n j‰sent‰

        labelKestot.setText("Elokuvien yhteiskesto: " + yhteisKesto / 60 + " h "
                + yhteisKesto % 60 + " min");
        chooserElokuvat.setSelectedIndex(index); // t‰st‰ tulee muutosviesti
                                                 // joka n‰ytt‰‰ j‰senen
    }


    /**
     * n‰ytt‰‰ listalta valitun elokuvan tiedot, tilap‰isesti isossa edit-kent‰ss‰
     */
    private void naytaElokuva() {
        elokuvaKohdalla = chooserElokuvat.getSelectedObject();

        if (elokuvaKohdalla == null)
            return;

        ElokuvaController.naytaElokuva(elokuvaKohdalla, edits);
        naytaNayttelijat(elokuvaKohdalla);
    }


    /**
     * N‰ytet‰‰n n‰yttelij‰t taulukkoon.  Tyhjennet‰‰n ensin taulukko ja sitten
     * lis‰t‰‰n siihen kaikki n‰yttelij‰t
     * @param elokuva elokuva, jonka n‰yttelij‰t n‰ytet‰‰n
     */
    private void naytaNayttelijat(Elokuva elokuva) {
        tableNayttelijat.clear();

        if (elokuva == null)
            return;

        List<Nayttelija> nayttelijat = elokuvarekisteri
                .annaNayttelijat(elokuva);
        if (nayttelijat.size() == 0)
            return;
        for (Nayttelija nayt : nayttelijat)
            naytaNayttelija(nayt);
    }


    /**
     * Lis‰t‰‰n yhden n‰yttelij‰n tiedot taulukkoon.  
     * @param nayt n‰yttelij‰ joka n‰ytet‰‰n
     */
    private void naytaNayttelija(Nayttelija nayt) {

        int kenttia = nayt.getKenttia();
        String[] rivi = new String[kenttia - nayt.ekaKentta()];
        for (int i = 0, k = nayt.ekaKentta(); k < kenttia; i++, k++)
            rivi[i] = nayt.anna(k);
        tableNayttelijat.add(nayt, rivi);
    }


    /**
     * Tulostetaan elokuvan ja siin‰ olevien n‰yttelijˆiden tiedot
     * @param os tulostusvirta
     * @param elokuva elokuva, jonka tiedot halutaan tulostaa
     */
    public void tulosta(PrintStream os, final Elokuva elokuva) {
        os.println("----------------------------------------------");
        elokuva.tulosta(os);
        os.println("----------------------------------------------");
        List<Nayttelija> nayttelijat = elokuvarekisteri
                .annaNayttelijat(elokuva);
        for (Nayttelija nayt : nayttelijat)
            nayt.tulosta(os);
        os.println("----------------------------------------------");
    }


    /**
     * Tulostaa listassa olevat elokuvat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki elokuvat");
            Collection<Elokuva> elokuvat = elokuvarekisteri.etsi("", -1);
            for (Elokuva elokuva : elokuvat) {
                tulosta(os, elokuva);
                os.println("\n\n");
            }
            os.println("Elokuvien yhteiskesto: " + yhteisKesto + " min");
        }
    }

    
    /**
     * Asetetaan k‰ytett‰v‰ elokuvarekisteri t‰h‰n
     * @param elokuvarekisteri Elokuvarekisteri jota k‰ytet‰‰n t‰st‰ k‰yttˆliittym‰st‰
     */
    public void setElokuvarekisteri(Elokuvarekisteri elokuvarekisteri) {
        this.elokuvarekisteri = elokuvarekisteri;

    }
}