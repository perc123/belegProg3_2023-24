import administration.VendingMachine;
import cakes.KuchenImpl;

import java.util.List;
import java.util.Random;

public class RemoveCakeSim {
/*    private final VendingMachine vendingMachine;
    private final Random random = new Random();

    public RemoveCakeSim(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void retrieveAndDeleteCakes() {
        // Synchronize critical section for accessing and modifying the vending machine
        synchronized (vendingMachine) {
            List<KuchenImpl> items = vendingMachine.listItems();
            if (!items.isEmpty()) {
                int randomIndex = random.nextInt(items.size());
                KuchenImpl cakeToDelete = items.get(randomIndex);

                vendingMachine.removeItem(cakeToDelete.getFachnummer());

                System.out.println("Thread " + Thread.currentThread().getId() +
                        ", Cake deleted, cake" + cakeToDelete.getFachnummer());
            }
        }
    }*/
}
