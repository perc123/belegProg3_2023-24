import administration.VendingMachine;

import java.util.Scanner;

public class SimulationOne {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = 0;

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
                scanner.nextLine();
            }
        }

        VendingMachine vendingMachine = new VendingMachine(capacity);

        CakeSimulation cakeSimulation = new CakeSimulation(vendingMachine);

        Runnable insertCakeTask = cakeSimulation;

        Runnable retrieveAndDeleteTask = cakeSimulation::retrieveAndDeleteCakes;

        Thread insertCakeThread = new Thread(insertCakeTask);
        Thread retrieveAndDeleteThread = new Thread(retrieveAndDeleteTask);

        // Start the threads
        insertCakeThread.start();
        retrieveAndDeleteThread.start();

        scanner.close();
    }
}
