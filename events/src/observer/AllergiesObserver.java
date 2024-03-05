package observer;

import administration.VendingMachine;
import kuchen.Allergen;

import java.util.List;

public class AllergiesObserver implements Observer {

    private final VendingMachine vendingMachine;
    private List<Allergen> lastAllergy;

    public AllergiesObserver(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.lastAllergy = vendingMachine.printAllergies(true);
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof VendingMachine) {
            List<Allergen> aktuelleAllergene = this.vendingMachine.printAllergies(true);
            if (!aktuelleAllergene.equals(lastAllergy)) {
                System.out.println("Changes in allergies:");
                System.out.println("Old allergy: " + lastAllergy);
                System.out.println("New allergy: " + aktuelleAllergene);
                lastAllergy = aktuelleAllergene;
            }
        }
    }
}
