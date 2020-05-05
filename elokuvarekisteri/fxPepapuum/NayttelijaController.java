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
import pepapuum.Nayttelija;

/**
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 * N‰yttelij‰n muokkausta / lis‰yst‰ varten olevan ikkunan avaaminen
 */
public class NayttelijaController
        implements ModalControllerInterface<Nayttelija>, Initializable {
    @FXML
    private TextField editNimi;
    @FXML
    private TextField editRooli;
    @FXML
    private Label lb;
    private TextField edits[];
    private Nayttelija nayttelija;


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    /**
     * showModal kutsuu t‰t‰ kun dialogi on piilotettu ja tulos pit‰‰ palauttaa.
     */
    @Override
    public Nayttelija getResult() {
        return nayttelija;
    }


    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }


    /**
     * Asetetaan mahdollinen aloitustieto dialogin sis‰lle
     */
    @Override
    public void setDefault(Nayttelija oletus) {
        nayttelija = oletus;
        naytaNayttelija(nayttelija, edits);
    }



    /**
     * Tallenna-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleTallenna() {
        if (nayttelija != null && nayttelija.getNimi().trim().equals("")) {
            naytaVirhe("nimi ei saa olla tyhj‰");
            return;
        }
        ModalController.closeStage(lb);
    }


    /**
     * Peruuta-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleCancel() {
        nayttelija = null;
        ModalController.closeStage(lb);
    }

    
    /**
     * Tehd‰‰n tarvittavat alustukset
     */
    private void alusta() {
        edits = new TextField[] { editNimi, editRooli };
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosNayttelijaan(k,
                    (TextField) (e.getSource())));
        }
    }


    /**
     * @param nayt n‰yttelij‰, jonka tietoja ollaan muuttamassa
     * @param edits taulukko tekstikentist‰
     */
    public static void naytaNayttelija(Nayttelija nayt, TextField[] edits) {
        if (nayt == null)
            return;

        edits[0].setText(nayt.getNimi());
        edits[1].setText(nayt.getRooli());

    }

    
    /**
     * K‰sitell‰‰n n‰yttelij‰n tietoihin tullut muutos
     * @param k kentt‰ mit‰ muutos koskee
     * @param edit muuttunut kentt‰
     */
    private void kasitteleMuutosNayttelijaan(int k, TextField edit) {
        if (nayttelija == null)
            return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
        case 1:
            virhe = nayttelija.setNimi(s);
            break;
        case 2:
            virhe = nayttelija.setRooli(s);
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
     * N‰ytet‰‰n mahdollinen virhe labelissa
     * @param virhe mahdollinen virheteksti
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            lb.setText("");
            lb.getStyleClass().removeAll("virhe");
            return;
        }
        lb.setText(virhe);
        lb.getStyleClass().add("virhe");
    }


    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus oletuselokuva
     * @return elokuva
     */
    public static Nayttelija kysyNayttelija(Stage modalityStage,
            Nayttelija oletus) {
        return ModalController.<Nayttelija, NayttelijaController> showModal(
                NayttelijaController.class.getResource("NayttelijaView.fxml"),
                "N‰yttelij‰", modalityStage, oletus, null);

    }


}
