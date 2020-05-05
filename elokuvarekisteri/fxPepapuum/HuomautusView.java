package fxPepapuum;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



/**
 * @author petra
 * @version 6.6.2018
 * Huomautusikkuna, tulee esiin jos tiedosto ei aukea
 */
public class HuomautusView extends Application {
    @Override
    public void start(Stage stage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Huomautus");
        alert.setHeaderText(null);
        alert.setContentText("Tiedosto ei aukea, tarkista oikeinkirjoitus!");
        alert.showAndWait(); 
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) { launch(args);  }
}