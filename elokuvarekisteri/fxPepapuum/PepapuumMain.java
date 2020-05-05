package fxPepapuum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pepapuum.Elokuvarekisteri;

/**
 * @author Petra Puumala petra.p.puumala@student.jyu.fi
 * @version 10.6.2018
 * Pääohjelma elokuvarekisterin suorittamiseksi. Uutta elokuvalistausta luotaessa tulee herjaus, että tiedostoa ei voida
 * avata, mutta toimii silti!
 */
public class PepapuumMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(
                    getClass().getResource("PepapuumGUIView.fxml"));
            final Pane root = (Pane) ldr.load();
            final PepapuumGUIController pepapuumCtrl = (PepapuumGUIController) ldr
                    .getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("pepapuum.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Elokuvarekisteri");

            primaryStage.setOnCloseRequest((event) -> {
                if (!pepapuumCtrl.voikoSulkea())
                    event.consume();
            });

            Elokuvarekisteri elokuvarekisteri = new Elokuvarekisteri();
            pepapuumCtrl.setElokuvarekisteri(elokuvarekisteri);
            pepapuumCtrl.avaa();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
    * Käynnistetään käyttöliittymä 
    * @param args komentorivin parametrit
    */
    public static void main(String[] args) {
        launch(args);
    }
}