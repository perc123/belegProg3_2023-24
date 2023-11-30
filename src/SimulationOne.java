import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationOne {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(10);

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
    }
}


