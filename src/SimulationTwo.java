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

public class SimulationTwo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the capacity of the vending machine (0 is permissible): ");
        int capacity = scanner.nextInt();
        System.out.println("Enter the number of Threads");
        int numThreads = scanner.nextInt();
        Lock lock = new ReentrantLock();
        LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> cakeLinkedList = new LinkedList<>();
        VendingMachine vendingMachine= new VendingMachine(capacity, cakeLinkedList, herstellerLinkedList);
        vendingMachine.addHersteller(new HerstellerImpl("ThreadHersteller"));
        AddCakeObserver addCakeObserver = new AddCakeObserver(vendingMachine);
        vendingMachine.add(addCakeObserver);
        RemoveCakeObserver removeCakeObserver = new RemoveCakeObserver(vendingMachine);
        vendingMachine.add(removeCakeObserver);
        CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
        vendingMachine.add(capacityObserver);
        for (int i = 0; i < numThreads; i++) {
            AddCakeSimOne addCakeSimOne = new AddCakeSimOne(vendingMachine, lock, 42);
            RemoveCakeSimOne removeCakeSimOne = new RemoveCakeSimOne(vendingMachine, lock, 42);
            addCakeSimOne.setName("AddThread-" + (i+1));
            removeCakeSimOne.setName("RemoveThread-" + (i+1));
            addCakeSimOne.start();
            removeCakeSimOne.start();
        }
    }

}
