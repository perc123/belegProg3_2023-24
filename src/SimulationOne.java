import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import observer.AddCakeObserver;
import observer.CapacityObserver;
import observer.RemoveCakeObserver;
import simulationOne.AddCakeSimOne;
import simulationOne.RemoveCakeSimOne;
import verwaltung.Hersteller;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationOne {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = 0;

        // Loop until valid input is provided
        while (true) {
            try {
                System.out.print("Enter the capacity of the vending machine (0 is permissible): ");
                capacity = scanner.nextInt();

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
        LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(capacity, kuchenLinkedList,herstellerLinkedList);
        Lock lock = new ReentrantLock();

        AddCakeObserver addCakeObserver = new AddCakeObserver(vendingMachine);
        vendingMachine.add(addCakeObserver);
        RemoveCakeObserver removeCakeObserver = new RemoveCakeObserver(vendingMachine);
        vendingMachine.add(removeCakeObserver);
        CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
        vendingMachine.add(capacityObserver);

        vendingMachine.addHersteller(new HerstellerImpl("Manufacturer Thread"));
        AddCakeSimOne addCakeSim = new AddCakeSimOne(vendingMachine, lock, 50);
        RemoveCakeSimOne removeCakeSim = new RemoveCakeSimOne(vendingMachine, lock, 50);
        addCakeSim.start();
        removeCakeSim.start();

        scanner.close();
    }
}
