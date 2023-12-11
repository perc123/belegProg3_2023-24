package cli;

import administration.HerstellerImpl;
import administration.HerstellerList;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import commands.Command;
import kuchen.Allergen;
import verwaltung.Hersteller;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;


import java.util.Scanner;

public class Console {
    HerstellerList herstellerList = new HerstellerList();
    private VendingMachine vendingMachine;
    private boolean isRunning;

    public Console(VendingMachine vendingMachine) {
        this.isRunning = true;
        this.vendingMachine = vendingMachine;  // Initialize the vendingMachine variable
    }

    public void start() {
        printCommands();
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            String userInput = scanner.nextLine();
            executeCommand(userInput);
        }
        scanner.close();
    }

    private void printCommands() {
        System.out.println("Available commands:");
        System.out.println(":c - Switch to insert mode");
        System.out.println(":d - Switch to delete mode");
        System.out.println(":r - Switch to display mode");
        System.out.println(":u - Switch to update mode");
        System.out.println(":p - Switch to persistence mode");
        System.out.println("exit - Exit the application");
    }

    private void executeCommand(String userInput) {
        if (userInput.equals("exit")) {
            isRunning = false;
        } else if (userInput.startsWith(":")) {
            // Handle built-in commands
            handleBuiltInCommand(userInput);
        } else {
            System.out.println("Please enter a correct command");
        }
    }
    private void switchToMode(Command.Operator commandType) {
        System.out.println("Switching to " + commandType + " mode.");
    }
    private void handleBuiltInCommand(String userInput) {
        // Handle built-in commands
        switch (userInput) {
            case ":c" -> {
                switchToMode(Command.Operator.SWITCH_INSERT_MODE);
                Scanner scanner = new Scanner(System.in);
                String inputLine = scanner.nextLine();
                String[] values = inputLine.split(" ");
                if (values.length == 1) {
                    insertHersteller(inputLine);
                } else if (values.length == 6 || values.length == 8) {
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
                                System.out.println("Inserted a " + cakeType);
                            }
                            case "Obstkuchen" -> {
                                KuchenImpl cake = new ObstkuchenImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType);
                                vendingMachine.addItem(cake, manufacturerName);
                                System.out.println("Inserted a " + cakeType);
                            }
                            case "Obsttorte" -> {
                                KuchenImpl cake = new ObsttorteImpl(cakeType, manufacturerName, allergens, nutritionalValue, Duration.ofDays(shelfLife), BigDecimal.valueOf(price), creamType, fruitVariety);
                                vendingMachine.addItem(cake, manufacturerName);
                                System.out.println("Inserted a " + cakeType);
                            }
                            default -> System.out.println("Not a valid cake type" + cakeType);
                        }
                    } else
                        System.out.println("Cake manufacturer not in the list.");
                } else
                    System.out.println("Invalid arguments for insert mode.");
            }
            case ":d" -> {
                switchToMode(Command.Operator.SWITCH_DELETE_MODE);
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the delete target (tray number or manufacturer name): ");
                String deleteTarget = scanner.nextLine();
                handleDeleteMode(List.of(deleteTarget));
            }
            case ":r" -> {
                switchToMode(Command.Operator.SWITCH_DISPLAY_MODE);
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the display type (manufacturer/cake/allergies): ");
                String displayType = scanner.nextLine().toLowerCase();
                handleDisplayMode(List.of(displayType));

            }
            case ":u" -> switchToMode(Command.Operator.SWITCH_UPDATE_MODE);
            case ":p" -> switchToMode(Command.Operator.SWITCH_PERSISTENCE_MODE);
            default -> System.out.println("Invalid command. Type 'exit' to exit the application.");
        }
    }
    private void handleDisplayMode(List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Invalid arguments for display mode.");
            return;
        }

        String displayType = arguments.get(0).toLowerCase();

        switch (displayType) {
            case "manufacturer":
                displayManufacturers();
                break;
            case "cake":
                displayCakes();
                break;
            case "allergies":
                if (arguments.size() > 1) {
                    String filter = arguments.get(1).toLowerCase();
                    displayAllergens(filter);
                } else {
                    displayAllergens(null);
                }
                break;
            default:
                System.out.println("Invalid arguments for display mode.");
        }
    }
    private void displayManufacturers() {
        System.out.println("Manufacturer Display:");
        herstellerList.displayHersteller();
    }

    private void displayCakes() {
        System.out.println("Cake Display:");

        for (KuchenImpl cake : vendingMachine.listItems()) {

                System.out.println("Cake Type: " + cake.getKuchenTyp());
                System.out.println("Tray Number: " + cake.getFachnummer());
                System.out.println("Inspection Date: " + cake.getInspektionsdatum());
                System.out.println("Remaining Shelf Life: " + cake.calculateRemainingShelfLife());
                System.out.println();

        }
    }

    private void displayAllergens(String filter) {
        System.out.println("Allergens Display:");

        for (KuchenImpl cake : vendingMachine.listItems()) {
            Collection<Allergen> allergens = cake.getAllergene();

            if (allergens != null && !allergens.isEmpty()) {
                if ("contain(i)".equalsIgnoreCase(filter)) {
                    displayAllergensMatchingFilter(allergens, true);
                } else if ("not contain(e)".equalsIgnoreCase(filter)) {
                    displayAllergensMatchingFilter(allergens, false);
                } else {
                    System.out.println("Invalid filter for allergens display.");
                }
            } else {
                System.out.println("No allergens for cake: " + cake.getKuchenTyp());
            }
        }
    }

    private void displayAllergensMatchingFilter(Collection<Allergen> allergens, boolean contains) {
        for (Allergen allergen : allergens) {

            if (contains) {
                System.out.println(allergen);
            }
        }
    }


    private void insertHersteller(String name) {
        HerstellerImpl hersteller = new HerstellerImpl(name);
        herstellerList.addHersteller(hersteller);
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


    private void handleDeleteMode(List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Invalid arguments for delete mode.");
            return;
        }

        String deleteTarget = arguments.get(0);

        try {
            int trayNumber = Integer.parseInt(deleteTarget);
            deleteCakeByTrayNumber(trayNumber);
        } catch (NumberFormatException e) {
            // Not a number, but a manufacturer name
            deleteManufacturer(deleteTarget);
        } catch (Exception e) {
            // Handle other unexpected input
            System.out.println("Invalid input for delete mode. Please enter a tray number or manufacturer name.");
        }
    }

    private void deleteManufacturer(String manufacturerName) {
        Hersteller hersteller = herstellerList.findHerstellerByName(manufacturerName);

        if (hersteller != null) {
            herstellerList.removeHersteller(hersteller);
            System.out.println("Manufacturer '" + manufacturerName + "' deleted.");
        } else {
            System.out.println("Manufacturer '" + manufacturerName + "' not found.");
        }
    }

    private void deleteCakeByTrayNumber(int trayNumber) {
        boolean success = vendingMachine.removeItem(trayNumber);

        if (success) {
            System.out.println("Cake at tray number " + trayNumber + " removed.");
        } else {
            System.out.println("No cake found at tray number " + trayNumber + ".");
        }
    }

    private void handleUpdateMode(List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Invalid arguments for inspect mode.");
            return;
        }
        try {
            int trayNumber = Integer.parseInt(arguments.get(0));
            inspectCake(trayNumber);
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument for inspect mode. Please provide a tray number.");
        }
    }

    private void inspectCake(int trayNumber) {
        vendingMachine.updateInspectionDate(trayNumber);
    }
}