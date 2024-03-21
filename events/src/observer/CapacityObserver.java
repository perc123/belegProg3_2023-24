package observer;


import administration.VendingMachine;

public class CapacityObserver implements Observer {

   private final VendingMachine vendingMachine;

    public CapacityObserver(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void update(observer.Subject subject) {
        if (subject instanceof VendingMachine) {
            int usedCapacity = this.vendingMachine.getInventory().size();
            double percentage = (double) usedCapacity / this.vendingMachine.getCapacity()* 100;
            if (percentage >= 90) {
                System.out.println("ACHTUNG: Kapazitaet zu " + (int) percentage + "% ausgelastet!");
            }
        }
    }
}
