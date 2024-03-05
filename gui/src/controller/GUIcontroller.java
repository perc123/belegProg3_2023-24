package controller;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kuchen.Allergen;
import saveJBP.JBP;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class GUIcontroller implements Initializable {

    private final VendingMachine vendingMachine;

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
    private TextField userInputField;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem loadMenuItem;

    //Tablle für Hersteller
    @FXML
    private TableView<Hersteller> manufacturersListView;

    @FXML
    private TableColumn<Hersteller, Integer> cakeCount;

    @FXML
    private TableColumn<Hersteller, String> manufacturerName;

    //Tabeller für Kuchen
    @FXML
    private TableView<KuchenImpl> cakesListView;

    @FXML
    private TableColumn<KuchenImpl, String> kuchenTyp;

    @FXML
    private TableColumn<KuchenImpl, Hersteller> kuchenHersteller;

    @FXML
    private TableColumn<KuchenImpl, BigDecimal> kuchenPreis;

    @FXML
    private TableColumn<KuchenImpl, Date> inspektionsdatum;

    @FXML
    private TableColumn<KuchenImpl, Duration> haltbarkeit;

    @FXML
    private TableColumn<KuchenImpl, Integer> fachnummer;

    //Tabellen für Allergene
    @FXML
    private TableView<Allergen> allergenTableEnthalten;

    @FXML
    private TableView<Allergen> allergenTableNichtEnthalten;

    @FXML
    private TableColumn<KuchenImpl, Collection<Allergen>> allergeneVorhanden;

    @FXML
    private TableColumn<KuchenImpl, Collection<Allergen>> allergeneNichtVorhanden;


    public GUIcontroller(VendingMachine vendingMachine) {
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
        inspectCakeMenuItem.setOnAction(event -> handleInspectCake());
        saveMenuItem.setOnAction(event -> handleSave());
        //loadMenuItem.setOnAction(event -> handleLoad());
    }

    @FXML
    private void handleInsertManufacturer() {
        Platform.runLater(() -> {
            Stage newStage = new Stage();
            newStage.initOwner(manufacturersListView.getScene().getWindow());
            newStage.setTitle("Insert a manufacturer");
            newStage.setResizable(false);

            Text text = new Text("Give manufacturer name: ");
            TextField textField = new TextField();
            Button okButton = new Button("OK");
            Button abbrechenButton = new Button("Cancel");

            okButton.setOnAction(e -> {
                String manufacturerName = textField.getText();

                HerstellerImpl hersteller = new HerstellerImpl(manufacturerName);
                if (vendingMachine.addHersteller(hersteller)) {
                    manufacturersListView.getItems().clear();
                }
                tableUpdate();
                newStage.close();
            });

            buildStageOne(newStage, text, textField, okButton, abbrechenButton);
        });
    }


    private void handleInsertCake() {
        Platform.runLater(() -> {
            Stage newStage = new Stage();
            newStage.initOwner(cakesListView.getScene().getWindow());
            newStage.setTitle("Add cake");
            newStage.setResizable(false);

            Text text = new Text("Provide a cake to be added:");
            TextField textField = new TextField();
            Button okButton = new Button("OK");
            Button abbrechenButton = new Button("Cancel");

            okButton.setOnAction(e -> {
                String kuchenString = textField.getText();

                String[] values = kuchenString.split(" ");
                if (values.length == 6 || values.length == 8) {
                    String cakeType = values[0];
                    HerstellerImpl manufacturerName = new HerstellerImpl(values[1]);
                    List<Allergen> allergens = convertToAllergenList(values[2]);
                    int nutritionalValue = Integer.parseInt(values[3]);
                    int shelfLife = Integer.parseInt(values[4]);
                    double price = Double.parseDouble(values[5]);
                    String fruitVariety = (values.length > 6) ? values[6] : null;
                    String creamType = (values.length > 7) ? values[7] : null;
                    if (!vendingMachine.isFull()) {
                        try {
                            switch (cakeType) {
                                case "Kremkuchen" -> {
                                    KuchenImpl cake = new KremkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                                    vendingMachine.addItem(cake);
                                    outputTextArea.setText("Inserted a " + cakeType);
                                    cakesListView.getItems().add(cake);
                                    tableUpdate();
                                }
                                case "Obstkuchen" -> {
                                    KuchenImpl cake = new ObstkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                                    vendingMachine.addItem(cake);
                                    outputTextArea.setText("Inserted a " + cakeType);
                                    cakesListView.getItems().add(cake);
                                    tableUpdate();
                                }
                                case "Obsttorte" -> {
                                    KuchenImpl cake = new ObsttorteImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType, fruitVariety);
                                    vendingMachine.addItem(cake);
                                    outputTextArea.setText("Inserted a " + cakeType);
                                    cakesListView.getItems().add(cake);
                                    tableUpdate();
                                }
                                default -> outputTextArea.setText("Not a valid cake type" + cakeType);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    } else
                        outputTextArea.setText("Vending Machine is Full.");
                }
                 else {
                    outputTextArea.setText("Invalid arguments for insert mode.");
                }
                 newStage.close();
            });

            buildStageTwo(newStage, text, textField, okButton, abbrechenButton);
        });
    }



    private void handleDeleteCake() {

        Platform.runLater(() -> {
            Stage newStage = new Stage();
            newStage.initOwner(cakesListView.getScene().getWindow());
            newStage.setTitle("Delete cake");
            newStage.setResizable(false);

            Text text = new Text("Give a tray number:");
            TextField textField = new TextField();
            Button okButton = new Button("OK");
            Button abbrechenButton = new Button("Cancel");

            okButton.setOnAction(e -> {
                String fachnummer = textField.getText();

                try {
                    if (vendingMachine.removeItem(Integer.parseInt(fachnummer))) {
                        cakesListView.getItems().clear();
                    }
                } catch (Exception exc) {
                    return;
                }
                tableUpdate();
                newStage.close();
            });

            buildStageTwo(newStage, text, textField, okButton, abbrechenButton);
        });
    }

    private void handleDeleteManufacturer() {

        Platform.runLater(() -> {
            Stage newStage = new Stage();
            newStage.initOwner(manufacturersListView.getScene().getWindow());
            newStage.setTitle("Delete a manufacturer");
            newStage.setResizable(false);

            Text text = new Text("Give manufacturer name to be deleted:");
            TextField textField = new TextField();
            Button okButton = new Button("OK");
            Button abbrechenButton = new Button("Cancel");

            okButton.setOnAction(e -> {
                String herstellerName = textField.getText();

                if (vendingMachine.removeHersteller(herstellerName)) {
                    manufacturersListView.getItems().clear();
                }

                tableUpdate();
                newStage.close();
            });

            buildStageOne(newStage, text, textField, okButton, abbrechenButton);
        });
    }



    private void handleInspectCake() {
        outputTextArea.setText("Change inspections date. Give tray number:");
        userInputField.clear();

        userInputField.setOnAction(event -> {
            String inputLine = userInputField.getText();
            int trayNumber = Integer.parseInt(inputLine);

            Iterator<KuchenImpl> iterator = vendingMachine.getInventory().iterator();
            while (iterator.hasNext()) {
                KuchenImpl cake = iterator.next();
                if (cake.getFachnummer() == trayNumber) {
                    vendingMachine.updateInspectionDate(trayNumber);
                    outputTextArea.setText("Cake " + cake.getKuchenTyp() + " updated.");
                    tableUpdate();
                    return;
                }
            }

            outputTextArea.setText("Cake not found at tray number " + trayNumber);
            userInputField.clear();
        });
    }


    private List<Allergen> convertToAllergenList(String allergenInput) {
        List<Allergen> allergens = new ArrayList<>();

        String[] allergenArray = allergenInput.split(",");
        for (String allergen : allergenArray) {
            try {
                Allergen allergenEnum = Allergen.valueOf(allergen.trim());

                if (!allergens.contains(allergenEnum)) {
                    allergens.add(allergenEnum);
                } else {
                    outputTextArea.setText("Duplicate allergen: " + allergen.trim());
                }
            } catch (IllegalArgumentException e) {
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


    @FXML
    private void handleSave() {
        JBP jbpVending = new JBP(vendingMachine); // Save the vending machine data
        jbpVending.serialisierenJBP();
        outputTextArea.setText("Vending Machine and Manufacturer data saved");
    }

/*    @FXML
    private void handleLoad() {
        JBP jbpVending = new JBP(vendingMachine);// Load the vending machine data
        VendingMachine loadedVendingMachine = jbpVending.deserialisierenJBP();

        if (loadedVendingMachine != null) {
            vendingMachine.setModel(loadedVendingMachine);
            outputTextArea.setText("Vending Machine data loaded");
            tableUpdate();
        } else {
            outputTextArea.setText("Error loading Vending Machine data.");
        }
    }*/



    private void buildStageOne(Stage newStage, Text text, TextField textField, Button okButton, Button abbrechenButton) {
        abbrechenButton.setOnAction(e -> newStage.close());

        VBox vbox = new VBox(text, textField, new HBox(okButton, abbrechenButton));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        StackPane root = new StackPane(vbox);

        Scene scene = new Scene(root, 400, 100);
        newStage.setScene(scene);
        newStage.show();
    }

    private void buildStageTwo(Stage newStage, Text text, TextField textField, Button okButton, Button abbrechenButton) {
        abbrechenButton.setOnAction(e -> newStage.close());

        VBox vbox = new VBox(text, textField, new HBox(okButton, abbrechenButton));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        StackPane root = new StackPane(vbox);

        Scene scene = new Scene(root, 600, 100);
        newStage.setScene(scene);
        newStage.show();
    }

    public void dragAndDrop(){
        fachnummer.setCellFactory(column -> new TableCell<>() {
            {
                setOnDragDetected(event -> {
                    if (!isEmpty()) {
                        Dragboard db = startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent inhalt = new ClipboardContent();
                        inhalt.put(DataFormat.PLAIN_TEXT, getIndex());
                        db.setContent(inhalt);
                        event.consume();
                    }
                });
                setOnDragOver(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                        int draggedIndex = (int) db.getContent(DataFormat.PLAIN_TEXT);
                        if (draggedIndex != getIndex()) {
                            event.acceptTransferModes(TransferMode.MOVE);
                            event.consume();
                        }
                    }
                });
                setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                        int draggedIndex = (int) db.getContent(DataFormat.PLAIN_TEXT);
                        KuchenImpl draggedData = cakesListView.getItems().get(draggedIndex);
                        KuchenImpl dropData = getTableRow().getItem();
                        int dropIndex = getIndex();
                        if (draggedData != null && dropData != null) {
                            int temp = draggedData.getFachnummer();
                            draggedData.setFachnummer(dropData.getFachnummer());
                            dropData.setFachnummer(temp);
                            cakesListView.getItems().set(draggedIndex, dropData);
                            cakesListView.getItems().set(dropIndex, draggedData);
                            event.setDropCompleted(true);
                            cakesListView.getSelectionModel().select(dropIndex);
                        }
                        event.consume();
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : getString());
                setGraphic(null);
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
        });
    }
    private void tableUpdate() {
        cakesListView.getItems().clear();
        List<KuchenImpl> kuchen = vendingMachine.printCake("kuchen");
        ObservableList<KuchenImpl> listeKuchen = cakesListView.getItems();
        listeKuchen.addAll(kuchen);
        cakesListView.setItems(listeKuchen);
        manufacturersListView.getItems().clear();
        List<HerstellerImpl> resHersteller = vendingMachine.printHersteller();
        ObservableList<Hersteller> listHersteller = manufacturersListView.getItems();
        listHersteller.addAll(resHersteller);
        manufacturersListView.setItems(listHersteller);


        allergenTableEnthalten.getItems().clear();
        List<Allergen> allergeneVorhanden = vendingMachine.printAllergies(true);
        ObservableList<Allergen> listeAllergenVorhanden = allergenTableEnthalten.getItems();
        listeAllergenVorhanden.addAll(allergeneVorhanden);
        allergenTableEnthalten.setItems(listeAllergenVorhanden);



        allergenTableNichtEnthalten.getItems().clear();
        List<Allergen> allergeneNichtVorhanden = vendingMachine.printAllergies(false);
        ObservableList<Allergen> listeAllergenNichtVorhanden = allergenTableNichtEnthalten.getItems();
        listeAllergenNichtVorhanden.addAll(allergeneNichtVorhanden);
        allergenTableNichtEnthalten.setItems(listeAllergenNichtVorhanden);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manufacturerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cakeCount.setCellValueFactory(new PropertyValueFactory<>("anzahlKuchen"));
        kuchenTyp.setCellValueFactory(new PropertyValueFactory<>("kuchentyp"));
        kuchenHersteller.setCellValueFactory(new PropertyValueFactory<>("hersteller"));
        kuchenPreis.setCellValueFactory(new PropertyValueFactory<>("preis"));
        inspektionsdatum.setCellValueFactory(new PropertyValueFactory<>("inspektionsdatum"));
        haltbarkeit.setCellValueFactory(new PropertyValueFactory<>("haltbarkeit"));
        fachnummer.setCellValueFactory(new PropertyValueFactory<>("fachnummer"));
        allergeneVorhanden.setCellValueFactory(new PropertyValueFactory<>("text"));
        allergeneNichtVorhanden.setCellValueFactory(new PropertyValueFactory<>("text"));
        tableUpdate();
        dragAndDrop();
    }
}