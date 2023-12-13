package controller;

import administration.HerstellerImpl;
import administration.HerstellerList;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import administration.VendingMachine;
import javafx.scene.control.TextField;
import kuchen.Allergen;
import verwaltung.Hersteller;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private VendingMachine vendingMachine;

    @FXML
    private MenuItem insertManufacturerMenuItem;
    @FXML
    private MenuItem insertCakeMenuItem;
    @FXML
    private MenuItem deleteCakeMenuItem;
    @FXML
    private MenuItem deleteManufacturerMenuItem;
    @FXML
    private MenuItem displayCakeMenuItem;
    @FXML
    private MenuItem displayManufacturerMenuItem;
    @FXML
    private MenuItem displayAllergiesMenuItem;
    @FXML
    private MenuItem inspectCakeMenuItem;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField userInputField;

    @FXML
    private ListView<String> manufacturersListView;

    private HerstellerList herstellerList;

    @FXML
    private TextArea outputTextArea;
    @FXML
    private ListView<String> cakesListView;

    public Controller() {
        // Initialize herstellerList and other necessary components
        this.herstellerList = new HerstellerList();
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        // Set the manufacturers in the ListView
       // manufacturersListView.getItems().addAll(vendingMachine.getManufacturers());
    }

    @FXML
    private void initialize() {
        // Perform any initialization logic here
        setMenuBarActions();
    }

    private void setMenuBarActions() {
        insertManufacturerMenuItem.setOnAction(event -> handleInsertManufacturer());
        insertCakeMenuItem.setOnAction(event -> handleInsertCake());
        deleteCakeMenuItem.setOnAction(event -> handleDeleteCake());
        deleteManufacturerMenuItem.setOnAction(event -> handleDeleteManufacturer());
        displayCakeMenuItem.setOnAction(event -> handleDisplayCake());
        displayManufacturerMenuItem.setOnAction(event -> handleDisplayManufacturer());
        displayAllergiesMenuItem.setOnAction(event -> handleDisplayAllergies());
        inspectCakeMenuItem.setOnAction(event -> handleInspectCake());
    }

    @FXML
    private void handleInsertManufacturer() {
        outputTextArea.setText("Insert a manufacturer:");

        userInputField.clear();

        userInputField.setOnAction(event -> {
            String manufacturerName = userInputField.getText();

            if (manufacturerName != null && !manufacturerName.isEmpty()) {
                // Check if the manufacturer already exists
                if (herstellerList.findHerstellerByName(manufacturerName) != null) {
                    outputTextArea.setText("Manufacturer already in the list.");
                    userInputField.clear();
                } else {
                    HerstellerImpl hersteller = new HerstellerImpl(manufacturerName);
                    herstellerList.addHersteller(hersteller);
                    manufacturersListView.getItems().add(manufacturerName);

                    userInputField.clear();

                    outputTextArea.setText("Manufacturer added: " + manufacturerName);
                }
            } else {
                outputTextArea.setText("Please enter a valid manufacturer name.");
            }
        });
    }


    private void handleInsertCake() {
        outputTextArea.setText("Insert a cake:");

        userInputField.clear();

        userInputField.setOnAction(event -> {
            String inputLine = userInputField.getText();
            String[] values = inputLine.split(" ");

            if (values.length == 6 || values.length == 8) {
                String cakeType = values[0];
                HerstellerImpl manufacturerName = (HerstellerImpl) herstellerList.findHerstellerByName(values[1]);
                List<Allergen> allergens = convertToAllergenList(values[2]);
                int nutritionalValue = Integer.parseInt(values[3]);
                int shelfLife = Integer.parseInt(values[4]);
                double price = Double.parseDouble(values[5]);
                String fruitVariety = (values.length > 6) ? values[6] : null;
                String creamType = (values.length > 7) ? values[7] : null;
                if (herstellerList.getAllHersteller().contains(manufacturerName)) {
                    switch (cakeType) {
                        case "Kremkuchen" -> {
                            KuchenImpl cake = new KremkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                        }
                        case "Obstkuchen" -> {
                            KuchenImpl cake = new ObstkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                        }
                        case "Obsttorte" -> {
                            KuchenImpl cake = new ObsttorteImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType, fruitVariety);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                        }
                        default -> outputTextArea.setText("Not a valid cake type" + cakeType);
                    }
                } else
                    outputTextArea.setText("Cake manufacturer not in the list.");
            } else
                outputTextArea.setText("Invalid arguments for insert mode.");

        });
    }

    private void handleDeleteCake() {
        outputTextArea.setText("Give a tray number to delete a cake: ");
        userInputField.clear();
        userInputField.setOnAction(event -> {

            String inputLine = userInputField.getText();
            try {
                int trayNumber = Integer.parseInt(inputLine);
                deleteCakeByTrayNumber(trayNumber);
            } catch (Exception e) {
                // Handle other unexpected input
                outputTextArea.setText("Invalid input for delete mode. Please enter a tray number.");
            }
            userInputField.clear();
        });
    }
    private void deleteCakeByTrayNumber(int trayNumber) {
        boolean success = vendingMachine.removeItem(trayNumber);

        if (success) {
            outputTextArea.setText("Cake at tray number " + trayNumber + " removed.");
        } else {
            outputTextArea.setText("No cake found at tray number " + trayNumber + ".");
        }
    }

    private void handleDeleteManufacturer() {
        outputTextArea.setText("Delete a manufacturer:");
        userInputField.clear();
        userInputField.setOnAction(event -> {

                String inputLine = userInputField.getText();
            try {
                Hersteller hersteller = herstellerList.findHerstellerByName(inputLine);
                if (hersteller != null) {
                    herstellerList.removeHersteller(hersteller);
                    outputTextArea.setText("Manufacturer '" + inputLine + "' deleted.");
                    updateManufacturersListView();

                } else {
                    outputTextArea.setText("Manufacturer '" + inputLine + "' not found.");
                }
            } catch (Exception e) {
                // Handle other unexpected input
                outputTextArea.setText("Invalid input for delete mode. Please enter a manufacturer name.");
            }
            userInputField.clear();
        });
    }
    private void updateManufacturersListView() {
        // Clear the existing items and update with the current manufacturers
        manufacturersListView.getItems().clear();

        for (Hersteller hersteller : herstellerList.getAllHersteller()){
            manufacturersListView.getItems().add(hersteller.getName());
        }
        }

    private void handleDisplayCake() {
        outputTextArea.setText("Display cakes by:");

        userInputField.clear();    }

    private void handleDisplayManufacturer() {
        // Handle display manufacturer logic here
    }

    private void handleDisplayAllergies() {
        // Handle display allergies logic here
    }

    private void handleInspectCake() {
        outputTextArea.setText("Change inspections date");

        userInputField.clear();    }

    private List<Allergen> convertToAllergenList(String allergenInput) {
        List<Allergen> allergens = new ArrayList<>();

        // Split the allergenInput by commas and trim each allergen
        String[] allergenArray = allergenInput.split(",");
        for (String allergen : allergenArray) {
            try {
                // Convert the trimmed allergen to Allergen enum
                Allergen allergenEnum = Allergen.valueOf(allergen.trim());

                // Check if the allergen is already in the list
                if (!allergens.contains(allergenEnum)) {
                    allergens.add(allergenEnum);
                } else {
                    System.out.println("Duplicate allergen: " + allergen.trim());
                }
            } catch (IllegalArgumentException e) {
                // Handle invalid allergen input
                System.out.println("Invalid allergen: " + allergen.trim());
                return null;
            }
        }

        return allergens;
    }

    private String printCake(KuchenImpl cake){
        return ("Cake Type: " + cake.getKuchenTyp() +
                ", Tray Number: " + cake.getFachnummer() +
                ", Inspection Date: " + cake.getInspektionsdatum() +
                ", Remaining Shelf Life: " + cake.calculateRemainingShelfLife());

    }
}
