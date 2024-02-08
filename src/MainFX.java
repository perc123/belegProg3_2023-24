import administration.HerstellerStorage;
import administration.VendingMachine;
import controller.GUIcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    private VendingMachine vendingMachine;
    private HerstellerStorage herstellerStorage;
    @Override
    public void start(Stage primaryStage) {
        vendingMachine = new VendingMachine(10); // Initialize vending machine
        herstellerStorage = new HerstellerStorage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controller/sample.fxml"));
            Parent root = loader.load();

            GUIcontroller controller = loader.getController();
            controller.setVendingMachine(vendingMachine, herstellerStorage);


            primaryStage.setTitle("Vending Machine GUI");
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}