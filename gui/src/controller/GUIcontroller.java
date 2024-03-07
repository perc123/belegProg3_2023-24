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
import verwaltung.Verkaufsobjekt;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class GUIcontroller implements Initializable {



        //Tablle für Hersteller
        @FXML
        private TableView<Hersteller> herstellerTable;

        @FXML
        private TableColumn<Hersteller, Integer> anzahlKuchen;

        @FXML
        private TableColumn<Hersteller, String> herstellerName;

        //Tabeller für Kuchen
        @FXML
        private TableView<KuchenImpl> kuchenTable;

        @FXML
        private TableColumn<KuchenImpl, String> kuchenTyp;

        @FXML
        private TableColumn<KuchenImpl, Hersteller> kuchenHersteller;

        @FXML
        private TableColumn<KuchenImpl, BigDecimal> kuchenPreis;

        @FXML
        private TableColumn<KuchenImpl, Date> inspektionsdatum;

        @FXML
        private TableColumn<KuchenImpl, javafx.util.Duration> haltbarkeit;

        @FXML
        private TableColumn<KuchenImpl, Integer> fachnummer;

        //Tabellen für Allergene
        @FXML
        private TableView<Allergen> allergenTableEnthalten;

        @FXML
        private TableView<Allergen> allergenTableNichtEnthalten;

        @FXML
        private TableColumn<Verkaufsobjekt, Collection<Allergen>> allergeneVorhanden;

        @FXML
        private TableColumn<Verkaufsobjekt, Collection<Allergen>> allergeneNichtVorhanden;


        private final VendingMachine vendingMachine;

    public GUIcontroller(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }


        @FXML
        public void HerstellerEinfuegen() {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.initOwner(herstellerTable.getScene().getWindow());
                newStage.setTitle("Hersteller hinzufuegen");
                newStage.setResizable(false);

                Text text = new Text("Geben Sie den Namen des Herstellers ein, den Sie hinzufuegen moechten:");
                TextField textField = new TextField();
                Button okButton = new Button("OK");
                Button abbrechenButton = new Button("Abbrechen");

                okButton.setOnAction(e -> {
                    String herstellerName = textField.getText();

                    HerstellerImpl hersteller = new HerstellerImpl(herstellerName);
                    if (vendingMachine.addHersteller(hersteller)) {
                        herstellerTable.getItems().clear();
                    }

                    tableUpdate();

                    newStage.close();
                });

                buildStageOne(newStage, text, textField, okButton, abbrechenButton);
            });
        }


        @FXML
        public void HerstellerLoeschen() {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.initOwner(herstellerTable.getScene().getWindow());
                newStage.setTitle("Hersteller loeschen");
                newStage.setResizable(false);

                Text text = new Text("Geben Sie den Namen des Herstellers ein, den Sie loeschen moechten:");
                TextField textField = new TextField();
                Button okButton = new Button("OK");
                Button abbrechenButton = new Button("Abbrechen");

                okButton.setOnAction(e -> {
                    String herstellerName = textField.getText();

                    if (vendingMachine.removeHersteller(herstellerName)) {
                        herstellerTable.getItems().clear();
                    }

                    tableUpdate();

                    newStage.close();
                });

                buildStageOne(newStage, text, textField, okButton, abbrechenButton);
            });
        }

        @FXML
        public void KuchenEinfuegen() {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.initOwner(kuchenTable.getScene().getWindow());
                newStage.setTitle("Kuchen hinzufuegen");
                newStage.setResizable(false);

                Text text = new Text("Legen Sie einen Kuchen mit allen noetigen Daten an:");
                TextField textField = new TextField();
                Button okButton = new Button("OK");
                Button abbrechenButton = new Button("Abbrechen");

                okButton.setOnAction(e -> {
                    String kuchenString = textField.getText();

                    String[] res = kuchenString.split(" ");

                    try {
                        HerstellerImpl hersteller = new HerstellerImpl(res[1]);

                        BigDecimal preis;
                        try {
                            preis = new BigDecimal(res[2].replace(",", "."));
                        } catch (IllegalArgumentException | ArithmeticException e1) {
                            return;
                        }

                        int naehrwert;
                        try {
                            naehrwert = Integer.parseInt(res[3]);
                        } catch (NumberFormatException e2) {
                            return;
                        }

                        java.time.Duration haltbarkeit;
                        try {
                            haltbarkeit = java.time.Duration.ofDays(Integer.parseInt(res[4]));
                        } catch (NumberFormatException e3) {
                            return;
                        }

                        String[] allergenStrings = res[5].split(",");
                        Collection<Allergen> allergene = new ArrayList<>();
                        for (String allergenString : allergenStrings) {
                            allergene.add(Allergen.fromString(allergenString));
                        }
                        String sorte = res[6];


                        switch (res[0]) {
                            case "Kremkuchen": {
                                KuchenImpl kremkuchen = new KremkuchenImpl(res[0],hersteller, preis, naehrwert, haltbarkeit, allergene, sorte);
                                if (vendingMachine.addItem(kremkuchen)) {
                                    kuchenTable.getItems().clear();
                                }
                            }
                            case "Obstkuchen": {
                                KuchenImpl Obstkuchen = new ObstkuchenImpl(res[0],hersteller, preis, naehrwert, haltbarkeit, allergene, sorte);
                                if (vendingMachine.addItem(Obstkuchen)) {
                                    kuchenTable.getItems().clear();
                                }
                            }
                            case "Obsttorte": {
                                KuchenImpl Obsttorte = new ObsttorteImpl(res[0],hersteller, preis, naehrwert, haltbarkeit, allergene, sorte, res[7]);
                                if (vendingMachine.addItem(Obsttorte)) {
                                    kuchenTable.getItems().clear();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        return;
                    }

                    tableUpdate();
                    newStage.close();
                });

                buildStageTwo(newStage, text, textField, okButton, abbrechenButton);
            });
        }


        @FXML
        public void KuchenLoeschen() {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.initOwner(kuchenTable.getScene().getWindow());
                newStage.setTitle("Kuchen loeschen");
                newStage.setResizable(false);

                Text text = new Text("Geben Sie die Fachnummer des Kuchen ein, welchen Sie loeschen moechten:");
                TextField textField = new TextField();
                Button okButton = new Button("OK");
                Button abbrechenButton = new Button("Abbrechen");

                okButton.setOnAction(e -> {
                    String fachnummer = textField.getText();

                    try {
                        if (vendingMachine.removeItem(Integer.parseInt(fachnummer))) {
                            kuchenTable.getItems().clear();
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

        public void tableUpdateausloesen(){
            tableUpdate();
        }

        private void tableUpdate() {
            kuchenTable.getItems().clear();
            List<KuchenImpl> kuchen = vendingMachine.printCake("kuchen");
            ObservableList<KuchenImpl> listeKuchen = kuchenTable.getItems();
            listeKuchen.addAll(kuchen);
            kuchenTable.setItems(listeKuchen);
            herstellerTable.getItems().clear();
            List<HerstellerImpl> resHersteller = vendingMachine.getHerstellerList();
            ObservableList<Hersteller> listHersteller = herstellerTable.getItems();
            listHersteller.addAll(resHersteller);
            herstellerTable.setItems(listHersteller);


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

        @FXML
        public void inspektionSetzen() {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.initOwner(kuchenTable.getScene().getWindow());
                newStage.setTitle("Inspektionsdatum setzen");
                newStage.setResizable(false);

                Text text = new Text("Geben Sie die Fachnummer des Kuchen ein, bei welchen Sie ein das Inspektionsdatum setzen möchten:");
                TextField textField = new TextField();
                Button okButton = new Button("OK");
                Button abbrechenButton = new Button("Abbrechen");

                okButton.setOnAction(e -> {
                    String fachnummer = textField.getText();

                    try {
                        vendingMachine.updateInspectionDate(Integer.parseInt(fachnummer));
                    } catch (Exception exce) {
                        return;
                    }


                    tableUpdate();

                    newStage.close();
                });

                abbrechenButton.setOnAction(e -> newStage.close());

                VBox vbox = new VBox(text, textField, new HBox(okButton, abbrechenButton));
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                StackPane root = new StackPane(vbox);

                Scene scene = new Scene(root, 700, 100);
                newStage.setScene(scene);
                newStage.show();
            });
        }


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
            fachnummer.setCellFactory(column -> new TableCell<KuchenImpl, Integer>() {
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
                            KuchenImpl draggedData = kuchenTable.getItems().get(draggedIndex);
                            KuchenImpl dropData = getTableRow().getItem();
                            int dropIndex = getIndex();
                            if (draggedData != null && dropData != null) {
                                int temp = draggedData.getFachnummer();
                                draggedData.setFachnummer(dropData.getFachnummer());
                                dropData.setFachnummer(temp);
                                kuchenTable.getItems().set(draggedIndex, dropData);
                                kuchenTable.getItems().set(dropIndex, draggedData);
                                event.setDropCompleted(true);
                                kuchenTable.getSelectionModel().select(dropIndex);
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


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            herstellerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            anzahlKuchen.setCellValueFactory(new PropertyValueFactory<>("anzahlKuchen"));
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
