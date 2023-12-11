package controller;

import administration.VendingMachine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button switchInsertModeButton;

    @FXML
    private Button switchDeleteModeButton;

    @FXML
    private Button switchDisplayModeButton;

    @FXML
    private Button switchUpdateModeButton;

    @FXML
    private Button switchPersistenceModeButton;

    @FXML
    private TextField userInputField;

    @FXML
    private TextArea outputTextArea;

    private VendingMachine vendingMachine;

    public Controller() {
    }
    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @FXML
    private void initialize() {
        // Perform any initialization logic here
        outputTextArea.setText("Welcome to the Vending Machine GUI!\n");
    }

    @FXML
    private void handleSwitchInsertMode() {
        // Implement logic for switching to insert mode
        outputTextArea.appendText("Switching to insert mode.\n");
        // Add more logic as needed
    }

    @FXML
    private void handleSwitchDeleteMode() {
        // Implement logic for switching to delete mode
        outputTextArea.appendText("Switching to delete mode.\n");
        // Add more logic as needed
    }

    @FXML
    private void handleSwitchDisplayMode() {
        // Implement logic for switching to display mode
        outputTextArea.appendText("Switching to display mode.\n");
        // Add more logic as needed
    }

    @FXML
    private void handleSwitchUpdateMode() {
        // Implement logic for switching to update mode
        outputTextArea.appendText("Switching to update mode.\n");
        // Add more logic as needed
    }

    @FXML
    private void handleSwitchPersistenceMode() {
        // Implement logic for switching to persistence mode
        outputTextArea.appendText("Switching to persistence mode.\n");
        // Add more logic as needed
    }

    @FXML
    private void handleUserInput() {
        // Implement logic for handling user input
        String userInput = userInputField.getText();
        // Add more logic as needed
    }
}
