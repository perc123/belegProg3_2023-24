package cli;

import administration.HerstellerImpl;
import administration.HerstellerList;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import commands.Command;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
            Command command = new Command(userInput);
            executeCommand(command);
        }
        scanner.close();
    }

    private void printCommands() {
        System.out.println("Available commands:");
        for (Command.Operator operator : Command.Operator.values()) {
            System.out.println(operator.getValue());
        }
        System.out.println("exit - Exit the application");
    }

    private void executeCommand(Command command) {
        switch (command.getOperator()) {
            case EXIT:
                isRunning = false;
                break;
            case ERROR:
                System.out.println("Invalid command. Type 'exit' to exit the application.");
                break;
            case INSERT_MODE:
                handleInsertMode(command.getArguments());
                break;
            case DELETE_MODE:
                handleDeleteMode(command.getArguments());
                break;
            case DISPLAY_MODE:
                handleDisplayMode(command.getArguments());
                break;
            case UPDATE_MODE:
                handleUpdateMode(command.getArguments());
                break;
            default:
                switchToMode(command.getOperator());
                break;
        }
    }
    private void switchToMode(Command.Operator operator) {
        System.out.println("Switching to " + operator);
    }

    private void handleInsertMode(List<String> arguments) {
        if (arguments.size() ==1 ){
            insertHersteller(arguments.get(0));
        }
        else if (arguments.size() == 8){
            String cakeType = arguments.get(0);
            HerstellerImpl manufacturerName = new HerstellerImpl(arguments.get(1));
            double price = Double.parseDouble(arguments.get(2));
            int nutritionalValue = Integer.parseInt(arguments.get(3));
            int shelfLife = Integer.parseInt(arguments.get(4));
            List<Allergen> allergens = convertToAllergenList(arguments.get(5));
            String fruitVariety = (arguments.size() > 6) ? arguments.get(6) : null;
            String creamType = (arguments.size() > 7) ? arguments.get(7) : null;
            KuchenImpl cake = new KremkuchenImpl("Kremkuchen",manufacturerName,allergens, nutritionalValue, Duration.ofDays(shelfLife), creamType, BigDecimal.valueOf(price));

            vendingMachine.addItem(cake, manufacturerName);
            System.out.println("Inserted a " + cakeType);
        }
        else
            System.out.println("Invalid arguments for insert mode.");
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
                if (arguments.size() > 1) {
                    String cakeType = arguments.get(1);
                    displayCakes(cakeType);
                } else {
                    displayCakes(null);
                }
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

    private void displayCakes(String cakeType) {
        System.out.println("Cake Display:");

        for (KuchenImpl cake : vendingMachine.listItems()) {
            if (cakeType == null || cake.getKuchenTyp().equalsIgnoreCase(cakeType)) {
                System.out.println("Cake Type: " + cake.getKuchenTyp());
                System.out.println("Tray Number: " + cake.getFachnummer());
                System.out.println("Inspection Date: " + cake.getInspektionsdatum());
                System.out.println("Remaining Shelf Life: " + calculateRemainingShelfLife(cake) + " days");
                System.out.println();
            }
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
            boolean matchesFilter = contains ? true : false;

            if (matchesFilter) {
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
                Allergen allergenEnum = Allergen.valueOf(allergen.trim().toUpperCase());

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

    private long calculateRemainingShelfLife(KuchenImpl cake) {
        Instant currentDate = Instant.now();
        Date inspectionDate = cake.getInspektionsdatum();

        if (inspectionDate != null) {
            Instant inspectionInstant = inspectionDate.toInstant();
            LocalDate inspectionLocalDate = inspectionInstant.atZone(ZoneId.systemDefault()).toLocalDate();
            long shelfLifeDays = cake.getHaltbarkeit().toDays();
            long daysElapsed = ChronoUnit.DAYS.between(inspectionLocalDate, currentDate);
            return Math.max(0, shelfLifeDays - daysElapsed);
        } else {
            // Handle the case where inspection date is null
            return 0;
        }
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