package fxPepapuum;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import java.util.Optional;


@SuppressWarnings("javadoc")
public class PoistaNayttelijaView extends Application {
    @Override
    public void start(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Poistetaanko n�yttelij�?");
        alert.setHeaderText(null);
        alert.setContentText("Poistetaanko n�yttelij�?");

        ButtonType buttonTypeYes = new ButtonType("Kyll�", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.get() == buttonTypeYes ) System.out.println("Talletaan");    }

    public static void main(String[] args) { launch(args);  }
}