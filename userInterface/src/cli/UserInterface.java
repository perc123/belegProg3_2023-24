package cli;

import administration.HerstellerImpl;
import cakes.KuchenImpl;
import commands.CommandType;
import eventSystem.EventSystem;
import eventSystem.EventType;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


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
            System.out.println("Please enter a correct command");
        }
    }

    private void handleBuiltInCommand(String userInput) {
        // Handle built-in commands
        switch (userInput) {
            case ":c" -> {
                switchToMode(CommandType.SWITCH_INSERT_MODE);
                Scanner scanner = new Scanner(System.in);
                String inputLine = scanner.nextLine();
                String[] values = inputLine.split(" ");
                if (values.length == 1) {
                    insertHersteller(inputLine);
                }
                else if (values.length == 6) {
                    System.out.println("Kuchen:");
                    //insert Kuchen
                }
                else if (values.length > 6) {
                    //insert Obstkuchen Kremkuchen
                    String kuchenType = values[0];
                    String herstellerName = values[1];
                    BigDecimal price = new BigDecimal(values[2]);
                    int nutritionValue = Integer.parseInt(values[3]);
                    Duration durability = Duration.ofDays(Long.parseLong(values[4]));
                    String allergens = values[5];
                    String obstsorte = values[6];
                    String kremsorte = values[7];
                    insertKuchen(kuchenType,herstellerName,price,nutritionValue,durability,allergens,obstsorte,kremsorte);
                }
                /*else if (values.length == 8) {
                    //insert Obsttorte
                }*/
                else {
                    System.out.println("Invalid input. Please provide all required values.");
                }
            }
            case ":d" -> switchToMode(CommandType.SWITCH_DELETE_MODE);
            case ":r" -> {
                switchToMode(CommandType.SWITCH_DISPLAY_MODE);
                eventSystem.triggerEvent(EventType.DISPLAY_HERSTELLER, userInput);
                /*
                noch zu implementieren
                switchToMode(CommandType.SWITCH_DISPLAY_MODE);
                eventSystem.triggerEvent(EventType.DISPLAY_KUCHEN, userInput);
                */
            }
            case ":u" -> switchToMode(CommandType.SWITCH_UPDATE_MODE);
            case ":p" -> switchToMode(CommandType.SWITCH_PERSISTENCE_MODE);
            default -> System.out.println("Invalid command. Type 'exit' to exit the application.");
        }
    }

    private void switchToMode(CommandType commandType) {
        System.out.println("Switching to " + commandType + " mode.");
    }


    private void insertHersteller(String name) {
        HerstellerImpl hersteller = new HerstellerImpl(name);
        eventSystem.triggerEvent(EventType.INSERT_HERSTELLER, hersteller);
    }

    private void insertKuchen(String type, String herstellerName, BigDecimal price, int nutritionValue, Duration durability, String allergens, String obstsorte, String kremsorte) {
        HerstellerImpl hersteller = new HerstellerImpl(herstellerName);

        Set<Allergen> allergenSet = Arrays.stream(allergens.split(","))
                .map(Allergen::valueOf)
                .collect(Collectors.toSet());
        
        KuchenImpl kuchen = new KuchenImpl(type, hersteller, allergenSet, nutritionValue, durability, price);
        eventSystem.triggerEvent(EventType.INSERT_KUCHEN, kuchen);
    }


    private void displayHersteller() {
        // noch zu implementieren
    }

    private void displayKuchen(String type) {
        // noch zu implementieren
    }

    private void displayAllergene(String filter) {
        // noch zu implementieren
    }

    private void deleteHersteller(String name) {
        // noch zu implementieren
    }

    private void removeKuchen(int fachnummer) {
        // noch zu implementieren
    }

    private void updateInspectionDate(int fachnummer) {
        // noch zu implementieren
    }

    private void saveDataJOS() {
        // noch zu implementieren
    }

    private void loadDataJOS() {
        // noch zu implementieren
    }

    private void saveDataJBP() {
        // noch zu implementieren
    }

    private void loadDataJBP() {
        // noch zu implementieren
    }
}

