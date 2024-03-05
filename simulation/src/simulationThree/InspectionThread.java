package simulationThree;

import administration.VendingMachine;
import cakes.KuchenImpl;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class InspectionThread extends Thread{
    private final VendingMachine vendingMachine;
    private final Random random = new Random();
    private final Condition condition;
    private final Lock lock;

    public InspectionThread(VendingMachine vendingMachine, Condition condition, Lock lock) {
        this.vendingMachine = vendingMachine;
        this.condition = condition;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                List<KuchenImpl> cakeList = vendingMachine.printCake("kuchen");
                if (!cakeList.isEmpty()) {
                    int index = random.nextInt(cakeList.size());
                    System.out.println(this.getName() + " New inspection date");
                    vendingMachine.updateInspectionDate(index);
                } else {
                    System.out.println(this.getName() + " cake list is empty");
                    condition.await();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
