package controller;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import singletonPattern.SingletonController;
import singletonPattern.SingletonVendingMachine;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainScene implements Initializable {
    int capacity;
    @FXML
    private TextField startTextField;
    @FXML
    Text startText;

    @FXML
    public void startWindowCapacity(ActionEvent actionEvent) {
        if(SingletonVendingMachine.getInstance().getVendingMachine() != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/hauptfenster.fxml"));
                GUIcontroller guiController = new GUIcontroller(SingletonVendingMachine.getInstance().getVendingMachine());
                SingletonController.getInstance().setController(guiController);
                loader.setControllerFactory(e -> guiController);
                Parent root = loader.load();
                Scene hauptfenster_scene = new Scene(root);
                hauptfenster_scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
                Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(hauptfenster_scene);
                app_stage.show();
            } catch (Exception e) {
                startText.setText("The capacity should be a number!");
            }
        }
        else {
            System.out.println(capacity);

            try {
                capacity = Integer.parseInt(startTextField.getText());
                LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
                LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
                VendingMachine vendingMachine = new VendingMachine(capacity, kuchenLinkedList, herstellerLinkedList);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hauptfenster.fxml"));
                loader.setControllerFactory(e -> new GUIcontroller(vendingMachine));
                Parent root = loader.load();
                Scene hauptfenster_scene = new Scene(root);
                hauptfenster_scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
                Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(hauptfenster_scene);
                app_stage.show();
            } catch (Exception e) {
                startText.setText("The capacity should be a number!");
                System.out.println(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
