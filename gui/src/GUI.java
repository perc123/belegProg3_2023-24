import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUI extends Application {

    public static void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startWindow.fxml"));
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}