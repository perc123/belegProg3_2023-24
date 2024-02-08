import administration.VendingMachine;
import cakes.KuchenImpl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class RemoveCakeSim extends Thread{
    private final VendingMachine vendingMachine;
    private final Random random;
    private final Lock lock;


    public RemoveCakeSim(VendingMachine vendingMachine, Lock lock, long seed) {
        this.vendingMachine = vendingMachine;
        this.lock = lock;
        this.random = new Random(seed);
    }
    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                List<KuchenImpl> cakeList = vendingMachine.printCake("kuchen");
                if (!cakeList.isEmpty()) {
                    int index = random.nextInt(cakeList.size());
                    KuchenImpl cake = cakeList.get(index);
                    int fachnummer = cake.getFachnummer();
                    System.out.println(this.getName() + "Remove cake: " + cake);
                    vendingMachine.removeItem(fachnummer);
                } else {
                    System.out.println("The list of cakes is empty, " + this.getName() + " cannot be removed");
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
