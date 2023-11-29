package cli;

import administration.HerstellerImpl;
import administration.HerstellerList;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import commands.Command;
import eventSystem.EventSystem;
import eventSystem.EventType;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


import java.util.Scanner;

public class Console {
    private boolean isRunning;

    public Console() {
        this.isRunning = true;
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
            default:
                switchToMode(command.getOperator());
                // Perform operations based on the command type
                // You can call other methods or perform operations specific to each command type here
                break;
        }
    }

    private void switchToMode(Command.Operator operator) {
        System.out.println("Switching to " + operator);
    }

}