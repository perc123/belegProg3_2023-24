package administration;

import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import kuchen.Kuchen;
import verwaltung.Hersteller;

import java.util.*;

public class VendingMachine {
    private List<KuchenImpl> inventory;
    private int capacity;

    public VendingMachine(int capacity) {
        this.capacity = capacity;
        this.inventory = new ArrayList<>();
    }

    public boolean addItem(Kuchen kuchen, Hersteller hersteller) {
        if (inventory.size() < capacity && kuchen instanceof KuchenImpl) {
            //KuchenImpl kuchenImpl = (KuchenImpl) kuchen;
            KuchenImpl kuchenImpl = (KuchenImpl) kuchen;
            // Check if Hersteller exists
            if (kuchenImpl.getHersteller() == hersteller) {
                if (kuchenImpl.getKuchenTyp() == "Obstkuchen")
                    kuchenImpl = (ObstkuchenImpl) kuchen;
                    System.out.println("Obstkuchen added");
                kuchenImpl.setFachnummer(inventory.size() + 1);
                inventory.add(kuchenImpl);
                return true;
            }
            else {
                System.out.println("The provided Hersteller does not exist!");
                return false;
            }
        }
        return false; // Vending machine is full, kuchen is not an instance of KuchenImpl, or wrong Hersteller
    }

    public boolean removeItem(Kuchen kuchen) {
        boolean removed = inventory.remove(kuchen);
        return removed;
    }

    public List<KuchenImpl> listItems() {
        // The list is made unmodifiable to prevent external code from modifying the vending machine's inventory directly
        return Collections.unmodifiableList(inventory);
    }

    public void updateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());

        for (KuchenImpl kuchen : inventory) {
            kuchen.setInspektionsdatum(currentDate);
        }

        Collections.sort(inventory, Comparator.comparing(KuchenImpl::getInspektionsdatum));
    }
}
