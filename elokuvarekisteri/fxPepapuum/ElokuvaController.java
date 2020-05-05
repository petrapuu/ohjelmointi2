package fxPepapuum;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pepapuum.Elokuva;

/**
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 * Luokka, joka toimii elokuvan muokkaus- ja lis‰ysikkunan ohjaimena
 */
public class ElokuvaController
        implements ModalControllerInterface<Elokuva>, Initializable {
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
    private Label labelVirhe;
    private TextField edits[];
    private Elokuva elokuvaKohdalla;

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }


    /**
     * Tallenna-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleTallenna() {
        if (elokuvaKohdalla != null
                && elokuvaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("nimi ei saa olla tyhj‰");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }


    /**
     * Peruuta-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleCancel() {
        elokuvaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }


    /**
     * Asetetaan mahdollinen aloitustieto dialogin sis‰lle
     * @param oletus oletuselokuva
     */
    @Override
    public void setDefault(Elokuva oletus) {
        elokuvaKohdalla = oletus;
        naytaElokuva(elokuvaKohdalla, edits);
    }


    /**
     * showModal kutsuu t‰t‰ kun dialogi on piilotettu ja tulos pit‰‰ palauttaa.
     */
    @Override
    public Elokuva getResult() {
        return elokuvaKohdalla;
    }

    /**
     * Tekee tarvittavat alustukset
     */
    private void alusta() {
        edits = new TextField[] { editNimi, editOhjaaja, editVuosi, editMaa,
                editKieli, editGenre, editKesto, editImdb };
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosElokuvaan(k,
                    (TextField) (e.getSource())));
        }
    }

    
    /**
     * @param elokuva elokuva, jonka tiedot halutaan n‰ytt‰‰
     * @param edits taulukko TextField-kentist‰, joihin halutaan elokuvan tietoja
     */
    public static void naytaElokuva(Elokuva elokuva, TextField[] edits) {
        if (elokuva == null)
            return;

        edits[0].setText(elokuva.getNimi());
        edits[1].setText(elokuva.getOhjaaja());
        edits[2].setText(elokuva.getVuosi());
        edits[3].setText(elokuva.getMaa());
        edits[4].setText(elokuva.getKieli());
        edits[5].setText(elokuva.getGenre());
        edits[6].setText(elokuva.getKesto());
        edits[7].setText(elokuva.getImdb());
    }

    
/**
 * K‰sitell‰‰n tullut muutos elokuvaan.    
 * @param k mit‰ kentt‰‰ muokataan
 * @param edit muuttunut kentt‰
 */
    private void kasitteleMuutosElokuvaan(int k, TextField edit) {
        if (elokuvaKohdalla == null)
            return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
        case 1:
            virhe = elokuvaKohdalla.setNimi(s);
            break;
        case 2:
            virhe = elokuvaKohdalla.setOhjaaja(s);
            break;
        case 3:
            virhe = elokuvaKohdalla.setVuosi(s);
            break;
        case 4:
            virhe = elokuvaKohdalla.setMaa(s);
            break;
        case 5:
            virhe = elokuvaKohdalla.setKieli(s);
            break;
        case 6:
            virhe = elokuvaKohdalla.setGenre(s);
            break;
        case 7:
            virhe = elokuvaKohdalla.setKesto(s);
            break;
        case 8:
            virhe = elokuvaKohdalla.setImdb(s);
            break;
        default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }

    }


    /**
     * N‰ytet‰‰n mahdollinen virhe. Jos ei ole virhett‰, ei n‰ytet‰.
     * @param virhe mahdollinen virheteksti
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
     * Tyhjent‰‰n tekstikent‰t 
     * @param edits taulukko jossa tyhjennett‰vi‰ tektsikentti‰
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus oletuselokuva
     * @return elokuva
     */
    public static Elokuva kysyElokuva(Stage modalityStage, Elokuva oletus) {
        return ModalController.<Elokuva, ElokuvaController> showModal(
                ElokuvaController.class.getResource("ElokuvaView.fxml"),
                "Elokuvarekisteri", modalityStage, oletus, null);

    }

}