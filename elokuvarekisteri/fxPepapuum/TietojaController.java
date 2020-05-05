package fxPepapuum;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author Petra Puumala  petra.p.puumala@student.jyu.fi
 * @version 6.6.2018
 * Luokka, joka toimii tietoja-ikkunan ohjaimena
 */
public class TietojaController implements ModalControllerInterface<String> {

    /**
     * Tietoja-ikkunan avaaminen
     */
    public static void naytaTietoja() {
        ModalController.showModal(
                TietojaController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
    }


    /**
     * showModal kutsuu t‰t‰ kun dialogi on piilotettu ja tulos pit‰‰ palauttaa.
     */
    @Override
    public String getResult() {
        return null;
    }


    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        //

    }


    /**
     * Asetetaan mahdollinen aloitustieto dialogin sis‰lle
     */
    @Override
    public void setDefault(String oletus) {
        //

    }

}
