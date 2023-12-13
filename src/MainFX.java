import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import administration.VendingMachine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


public class MainFX extends Application {

    private VendingMachine vendingMachine;
    @Override
    public void start(Stage primaryStage) {
        vendingMachine = new VendingMachine(10); // Initialize your vending machine

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controller/sample.fxml"));
            Parent root = loader.load();

            // Set the controller with the vending machine instance
            //Controller controller = new Controller(vendingMachine);
            Controller controller = loader.getController();
            controller.setVendingMachine(vendingMachine);


            primaryStage.setTitle("Vending Machine GUI");
            primaryStage.setScene(new Scene(root, 752, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
