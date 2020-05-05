package fxPepapuum;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Tulostuksen hoitava luokka
 * 
 * @author pepapuum
 * @version 31.5.2018
 */
public class TulostusController implements ModalControllerInterface<String> {
    @FXML
    TextArea tulostusAlue;


    /**
     * OK-napin painamisen j‰lkeen tapahtuvat asiat
     */
    @FXML
    private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }


    /**
     * Tulosta-napin painamisen j‰lkeen tapahtuvat asiat
     */
    @FXML
    private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata viel‰ tulostaa");
    }


    /**
     * showModal kutsuu t‰t‰ kun dialogi on piilotettu ja tulos pit‰‰ palauttaa.
     */
    @Override
    public String getResult() {
        return null;
    }


    /**
     * Asetetaan aloitustieto dialogin sis‰lle
     */
    @Override
    public void setDefault(String oletus) {
        if (oletus == null)
            return;
        tulostusAlue.setText(oletus);
    }


    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        //
    }


    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }


    /**
     * N‰ytt‰‰ tulostusalueessa tekstin
     * @param tulostus tulostettava teksti
     * @return kontrolleri, jolta voidaan pyyt‰‰ lis‰‰ tietoa 
     */
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = (TulostusController) ModalController
                .showModeless(TulostusController.class.getResource(
                        "TulostusView.fxml"), "Tulostus", tulostus);
        return tulostusCtrl;
    }

}