package observer;

import administration.VendingMachine;

public class AddCakeObserver implements Observer {

    private final VendingMachine vendingMachine;
    private int anzahlKuchen;

    public AddCakeObserver(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
        this.anzahlKuchen = vendingMachine.getCapacity();
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof VendingMachine) {
            int aktuelleAnzahlKuchen = vendingMachine.getCapacity();
            if (aktuelleAnzahlKuchen > anzahlKuchen) {
                System.out.println("Cake added");
            }
            anzahlKuchen = aktuelleAnzahlKuchen;
        }
    }
}
