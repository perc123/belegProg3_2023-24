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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        this.herstellerList = new HerstellerList();
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;

    }

    @FXML
    private void initialize() {
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
                if (herstellerList.getAllHersteller().contains(manufacturerName) && !vendingMachine.isFull()) {
                    switch (cakeType) {
                        case "Kremkuchen" -> {
                            KuchenImpl cake = new KremkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                            updateCakesListViewTrayNumber();
                        }
                        case "Obstkuchen" -> {
                            KuchenImpl cake = new ObstkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                            updateCakesListViewTrayNumber();

                        }
                        case "Obsttorte" -> {
                            KuchenImpl cake = new ObsttorteImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType, fruitVariety);
                            vendingMachine.addItem(cake, manufacturerName);
                            outputTextArea.setText("Inserted a " + cakeType);
                            cakesListView.getItems().add(printCake(cake));
                            updateCakesListViewTrayNumber();
                        }
                        default -> outputTextArea.setText("Not a valid cake type" + cakeType);
                    }
                } else if(vendingMachine.isFull()){
                    outputTextArea.setText("Vending Machine is Full.");
                }
                else
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
                updateCakesListViewTrayNumber();
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
            Hersteller hersteller = herstellerList.findHerstellerByName(inputLine);

                if (hersteller != null)  {
                    herstellerList.removeHersteller(hersteller);
                    outputTextArea.setText("Manufacturer '" + inputLine + "' deleted.");
                    updateManufacturersListView();
                    deleteCakeByManufacturer(hersteller.getName());
                    updateCakesListViewTrayNumber();
                } else {
                    outputTextArea.setText("Manufacturer '" + inputLine + "' not found.");
                }
            userInputField.clear();

            //TODO: Handle other unexpected input, but probably not needed
                //outputTextArea.setText("Invalid input for delete mode. Please enter a manufacturer name.");

        });
    }
    private void updateManufacturersListView() {
        manufacturersListView.getItems().clear();
        for (Hersteller hersteller : herstellerList.getAllHersteller()){
            manufacturersListView.getItems().add(hersteller.getName());
        }
    }

    private void updateCakesListViewTrayNumber() {
        cakesListView.getItems().clear();

        List<KuchenImpl> cakes = vendingMachine.listItems();

        cakes.sort(Comparator.comparingInt(KuchenImpl::getFachnummer));

        for (KuchenImpl cake : cakes) {
            cakesListView.getItems().add(printCake(cake));
        }
    }


    private void handleDisplayCake() {
        updateCakesListViewTrayNumber();
        outputTextArea.setText("Displaying cakes by tray number");

       /* userInputField.setOnAction(event -> {
            String displayType = userInputField.getText().toLowerCase();

            switch (displayType) {
                case "cake by tray number":
                    updateCakesListViewTrayNumber();
                    outputTextArea.setText("Displaying cakes by tray number");
                    break;
                case "manufacturer":
                    updateCakesListViewByManufacturer();
                    outputTextArea.setText("Displaying cakes by manufacturer");
                    break;
                case "allergies":
                    updateCakesListViewByAllergies();
                    break;
                default:
                    outputTextArea.setText("Invalid display type. Supported types: 'cake by tray number', 'manufacturer', 'allergies'");
            }

        });*/
        userInputField.clear();
    }

    private void handleDisplayManufacturer() {
        updateCakesListViewByManufacturer();
        outputTextArea.setText("Displaying cakes by manufacturer");
        userInputField.clear();
    }

    private void updateCakesListViewByManufacturer() {
        cakesListView.getItems().clear();

        List<KuchenImpl> cakes = vendingMachine.listItems();

        // Sort the cakes by manufacturer's name
        cakes.sort(Comparator.comparing(cake -> cake.getHersteller().getName()));
        outputTextArea.setText("It's called");

        for (KuchenImpl cake : cakes) {
            cakesListView.getItems().add(printCake(cake));
        }
    }

    private void handleDisplayAllergies() {
        // Handle display allergies logic here
    }

    private void handleInspectCake() {
        outputTextArea.setText("Change inspections date. Give tray number:");
        userInputField.clear();

        userInputField.setOnAction(event -> {
            String inputLine = userInputField.getText();
            int trayNumber = Integer.parseInt(inputLine);

            Iterator<KuchenImpl> iterator = vendingMachine.listItems().iterator();
            while (iterator.hasNext()) {
                KuchenImpl cake = iterator.next();
                if (cake.getFachnummer() == trayNumber) {
                    vendingMachine.updateInspectionDate(trayNumber);
                    outputTextArea.setText("Cake " + cake.getKuchenTyp() + " updated.");
                    updateCakesListViewTrayNumber();
                    return;
                }
            }

            outputTextArea.setText("Cake not found at tray number " + trayNumber);
            userInputField.clear();
        });
    }


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
                    outputTextArea.setText("Duplicate allergen: " + allergen.trim());
                }
            } catch (IllegalArgumentException e) {
                // Handle invalid allergen input
                outputTextArea.setText("Invalid allergen: " + allergen.trim());
                return null;
            }
        }

        return allergens;
    }

    private String printCake(KuchenImpl cake) {
        return (cake.getFachnummer() +
                " " + cake.getKuchenTyp() +
                ", Inspection Date: " + cake.getFormattedInspectionDate() +
                ", Remaining Shelf Life: " + cake.calculateRemainingShelfLife());
    }


    private void deleteCakeByManufacturer(String manufacturer) {
        ListIterator<KuchenImpl> iterator = vendingMachine.listItems().listIterator();
        while (iterator.hasNext()) {
            KuchenImpl cake = iterator.next();
            if (cake.getHersteller().getName().equalsIgnoreCase(manufacturer)) {
                iterator.remove();
            }
        }
        updateCakesListViewTrayNumber();
        outputTextArea.setText("All cakes from " + manufacturer + " removed.");
    }

/*
    private void deleteCakeByManufacturer(String manufacturer){
        for (KuchenImpl cake : vendingMachine.listItems()){
            if (cake.getHersteller().getName() == manufacturer) {
                boolean success = vendingMachine.removeItem(cake.getFachnummer());
                if (success) {
                    outputTextArea.setText("All cakes from " + manufacturer + " removed.");
                } else {
                    outputTextArea.setText("No cake  from " + manufacturer + " found.");
                }
            } else{
                outputTextArea.setText("Invalid input");
            }
        }
        updateCakesListViewTrayNumber();
    }

    private void updateCakesListViewByAllergies() {
        cakesListView.getItems().clear();

        List<KuchenImpl> cakes = vendingMachine.listItems();

        cakes.sort(Comparator.comparing(cake -> cake.getAllergene().stream().map(Allergen::name).collect(Collectors.joining(", "))));

        for (KuchenImpl cake : cakes) {
            cakesListView.getItems().add(printCake(cake));
        }
    }*/
}
