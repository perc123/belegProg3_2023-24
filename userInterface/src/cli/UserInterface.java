package cli;

import commands.CommandType;
import eventSystem.EventSystem;
import eventSystem.EventType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Scanner;

public class UserInterface {
    private EventSystem eventSystem;
    private boolean isRunning;

    public UserInterface(EventSystem eventSystem) {
        this.eventSystem = eventSystem;
        this.isRunning = true;
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
            // Handle other user-defined commands
            // You can implement logic for user-defined commands here
        }
    }

    private void handleBuiltInCommand(String userInput) {
        // Handle built-in commands
        switch (userInput) {
            case ":c":
                switchToMode(CommandType.SWITCH_INSERT_MODE);

                insertHersteller(userInput);
                break;
            case ":d":
                switchToMode(CommandType.SWITCH_DELETE_MODE);
                break;
            case ":r":
                switchToMode(CommandType.SWITCH_DISPLAY_MODE);
                break;
            case ":u":
                switchToMode(CommandType.SWITCH_UPDATE_MODE);
                break;
            case ":p":
                switchToMode(CommandType.SWITCH_PERSISTENCE_MODE);
                break;
            default:
                System.out.println("Invalid command. Type 'exit' to exit the application.");
        }
    }

    private void switchToMode(CommandType commandType) {
        // Implement the logic to switch to the specified mode
        System.out.println("Switching to " + commandType + " mode.");
    }


    // Command to insert a Hersteller
    private void insertHersteller(String name) {
        ;
    }

    // Command to insert a Kuchen
    private void insertKuchen(String type, String herstellerName, BigDecimal price, int nutritionValue, Duration durability, String allergens, String obstsorte, String kremsorte) {
        // Implement the logic to insert a Kuchen
    }

    // Command to display Hersteller with the count of Kuchen
    private void displayHersteller() {
        // Implement the logic to display Hersteller
    }

    // Command to display Kuchen filtered by type
    private void displayKuchen(String type) {
        // Implement the logic to display Kuchen
    }

    // Command to display allergene
    private void displayAllergene(String filter) {
        // Implement the logic to display allergene (filtered)
    }

    // Command to delete a Hersteller
    private void deleteHersteller(String name) {
        // Implement the logic to delete a Hersteller
    }

    // Command to remove Kuchen from a specific Fachnummer
    private void removeKuchen(int fachnummer) {
        // Implement the logic to remove Kuchen
    }

    // Command to update the inspection date of a Kuchen
    private void updateInspectionDate(int fachnummer) {
        // Implement the logic to update the inspection date
    }

    // Command to save data using JOS
    private void saveDataJOS() {
        // Implement the logic to save data using JOS
    }

    // Command to load data using JOS
    private void loadDataJOS() {
        // Implement the logic to load data using JOS
    }

    // Command to save data using JBP
    private void saveDataJBP() {
        // Implement the logic to save data using JBP
    }

    // Command to load data using JBP
    private void loadDataJBP() {
        // Implement the logic to load data using JBP
    }
}

