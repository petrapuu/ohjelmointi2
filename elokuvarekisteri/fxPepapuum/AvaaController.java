package fxPepapuum;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kysyt‰‰n elokuvalistan nimi ja luodaan t‰t‰ varten dialogi.
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 25.7.2018
 */
public class AvaaController implements ModalControllerInterface<String> {

    @FXML
    private TextField textElokuvalista;
    private String vastaus = null;


    /**
     * OK-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleOK() {
        vastaus = textElokuvalista.getText();
        ModalController.closeStage(textElokuvalista);
    }


    /**
     * Peruuta-nappia painettaessa tapahtuvat asiat
     */
    @FXML
    private void handleCancel() {
        ModalController.closeStage(textElokuvalista);
    }


    /**
     * showModal kutsuu t‰t‰ kun dialogi on piilotettu ja tulos pit‰‰ palauttaa.
     */
    @Override
    public String getResult() {
        return vastaus;
    }


    /**
     * Asetetaan mahdollinen aloitustieto dialogin sis‰lle
     */
    @Override
    public void setDefault(String oletus) {
        textElokuvalista.setText(oletus);
    }


    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        textElokuvalista.requestFocus();
    }


    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ nime‰ n‰ytet‰‰n oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AvaaController.class.getResource("AvaaView.fxml"),
                "Elokuvarekisteri", modalityStage, oletus);
    }
}