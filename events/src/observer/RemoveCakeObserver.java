package observer;


import administration.VendingMachine;

public class RemoveCakeObserver implements Observer {

    private final VendingMachine vendingMachine;
    private int anzahlKuchen;

    public RemoveCakeObserver(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
        this.anzahlKuchen = vendingMachine.getCapacity();
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof VendingMachine) {
            int aktuelleAnzahlKuchen = vendingMachine.getCapacity();
            if (aktuelleAnzahlKuchen < anzahlKuchen) {
                System.out.println("Cake removed");
            }
            anzahlKuchen = aktuelleAnzahlKuchen;
        }
    }
}
