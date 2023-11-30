import administration.VendingMachine;

import java.util.Scanner;

public class SimulationOne {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = 0;  // Initialize to a default value

        // Loop until valid input is provided
        while (true) {
            try {
                // Get user input for vending machine capacity
                System.out.print("Enter the capacity of the vending machine (0 is permissible): ");
                capacity = scanner.nextInt();

                // Check if the capacity is a non-negative integer
                if (capacity >= 0) {
                    break;  // Exit the loop if input is valid
                } else {
                    System.out.println("Please enter a non-negative integer.");
                }
            } catch (java.util.InputMismatchException e) {
                // Handle non-integer input
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();  // Consume the invalid input to avoid an infinite loop
            }
        }

        // Create VendingMachine with user-defined capacity
        VendingMachine vendingMachine = new VendingMachine(capacity);

        // Use CakeSimulation for the main simulation
        CakeSimulation cakeSimulation = new CakeSimulation(vendingMachine);

        // Thread for continuously trying to insert a randomly generated cake
        Runnable insertCakeTask = cakeSimulation;

        // Thread for retrieving and deleting cakes
        Runnable retrieveAndDeleteTask = cakeSimulation::retrieveAndDeleteCakes;

        // Create threads for each CakeSimulation instance
        Thread insertCakeThread = new Thread(insertCakeTask);
        Thread retrieveAndDeleteThread = new Thread(retrieveAndDeleteTask);

        // Start the threads
        insertCakeThread.start();
        retrieveAndDeleteThread.start();

        // Close the scanner (optional)
        scanner.close();
    }
}
