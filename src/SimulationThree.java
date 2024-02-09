import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import simulationThree.AddCakeSimThree;
import simulationThree.InspectionThread;
import simulationThree.RemoveCakeSimThree;
import verwaltung.Hersteller;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationThree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the capacity of the vending machine (0 is permissible): ");
        int capacity = scanner.nextInt();
        System.out.println("Enter the number of Threads");
        int numThreads = scanner.nextInt();
        System.out.println("Enter timeslot in Milliseconds");
        int interval = scanner.nextInt();

        // Initialize vending machine and lock
        LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> cakeLinkedList = new LinkedList<>();
        VendingMachine vendingMachine= new VendingMachine(capacity, cakeLinkedList, herstellerLinkedList);
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        vendingMachine.addHersteller(new HerstellerImpl("ThreadHersteller"));

        for (int i = 0; i < numThreads; i++) {
            Thread addCakeSimThree = new AddCakeSimThree(vendingMachine, lock, condition, 50);
            addCakeSimThree.start();

            Thread removeCakeSimThree = new RemoveCakeSimThree(vendingMachine, lock, condition, 50);
            removeCakeSimThree.start();

            Thread inspectionThread = new InspectionThread(vendingMachine, condition, lock);
            inspectionThread.start();

            addCakeSimThree.setName("AddThread-" + (i+1));
            removeCakeSimThree.setName("RemoveThread-" + (i+1));
            inspectionThread.setName("InspectionThread-" + (i+1));
        }


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            lock.lock();
            try {
                System.out.println("Capacity:  " + vendingMachine.getCapacity() + ", Cake count " + vendingMachine.getInventory().size());
            } finally {
                lock.unlock();
            }
        }, 0, interval, TimeUnit.MILLISECONDS);

    }
}
