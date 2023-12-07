import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import kuchen.Allergen;

import java.util.List;
import java.util.Random;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

public class CakeSimulation implements Runnable {
    private final VendingMachine vendingMachine;
    private final Random random = new Random();

    public CakeSimulation(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void run() {
        while (true) {
            // Synchronize critical section for accessing and modifying the vending machine
            synchronized (vendingMachine) {
                // Wait if the vending machine is full
                while (vendingMachine.isFull()) {
                    try {
                        System.out.println("Thread " + Thread.currentThread().getId() +
                                " is waiting to insert cakes.");
                        vendingMachine.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String[] randomCakeInfo = createRandomCakeInfo();
                KuchenImpl randomCake = createRandomCake(randomCakeInfo);

                vendingMachine.addItem(randomCake, randomCake.getHersteller());

                System.out.println("Thread " + Thread.currentThread().getId() +
                        ", Cake added, cake" + randomCake.getFachnummer());

                // Notify waiting threads that the vending machine has been modified
                vendingMachine.notifyAll();
            }
        }
    }

    private String[] createRandomCakeInfo() {
        String kuchenTyp = "Kremkuchen";
        String herstellerName = "Hersteller" + (random.nextInt(10) + 1);
        int naehwert = random.nextInt(10) + 1;
        int duration = random.nextInt(10) + 1;
        String kremsorte = "Schokolade";
        double preis = (random.nextInt(10) + 1) * 2.5;

        return new String[]{kuchenTyp, herstellerName, String.valueOf(naehwert), String.valueOf(duration), kremsorte, String.valueOf(preis)};
    }

    private KuchenImpl createRandomCake(String[] randomCakeInfo) {
        String kuchenTyp = randomCakeInfo[0];
        HerstellerImpl hersteller = new HerstellerImpl(randomCakeInfo[1]);
        Set<Allergen> allergy = Set.of(Allergen.Haselnuss);
        int naehwert = Integer.parseInt(randomCakeInfo[2]);
        Duration duration = Duration.ofDays(Integer.parseInt(randomCakeInfo[3]));
        String kremsorte = randomCakeInfo[4];
        BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(randomCakeInfo[5]));

        return new KremkuchenImpl(kuchenTyp, hersteller, allergy, naehwert, duration, preis, kremsorte);
    }

    public void retrieveAndDeleteCakes() {
        while (true) {
            // Synchronize critical section for accessing and modifying the vending machine
            synchronized (vendingMachine) {
                // Wait if there are less than 3 cakes in the vending machine
                while (vendingMachine.listItems().size() < 3) {
                    try {
                        System.out.println("Thread " + Thread.currentThread().getId() +
                                " is waiting to retrieve and delete cakes.");
                        vendingMachine.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                List<KuchenImpl> items = vendingMachine.listItems();
                if (!items.isEmpty()) {
                    int randomIndex = random.nextInt(items.size());
                    KuchenImpl cakeToDelete = items.get(randomIndex);

                    vendingMachine.removeItem(cakeToDelete.getFachnummer());

                    System.out.println("Thread " + Thread.currentThread().getId() +
                            ", Cake deleted, cake" + cakeToDelete.getFachnummer());

                    // Notify waiting threads that the vending machine has been modified
                    vendingMachine.notifyAll();
                }
            }
        }
    }
}

