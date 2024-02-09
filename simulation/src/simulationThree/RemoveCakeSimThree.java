package simulationThree;

import administration.VendingMachine;
import cakes.KuchenImpl;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RemoveCakeSimThree extends Thread{
    private final VendingMachine vendingMachine;
    private final Random random;
    private final Lock lock;
    private final Condition condition;


    public RemoveCakeSimThree(VendingMachine vendingMachine, Lock lock, Condition condition, long seed) {
        this.vendingMachine = vendingMachine;
        this.lock = lock;
        this.condition = condition;
        this.random = new Random(seed);
    }
    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (vendingMachine.printCake("kuchen").isEmpty()) {
                    System.out.println("Kuchenliste ist leer " + this.getName() + " kann nichts loeschen");
                    condition.await();
                }
                KuchenImpl cake = vendingMachine.printCake("kuchen").stream()
                        .min(Comparator.comparing(KuchenImpl::getInspektionsdatum))
                        .orElse(null);
                if (cake != null) {
                    System.out.println(this.getName() + " Probiert Kuchen zu loeschen: " + cake);
                    vendingMachine.removeItem(cake.getFachnummer());
                    condition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
