package administration;

import cakes.KuchenImpl;
import kuchen.Kuchen;
import java.util.*;

public class VendingMachine {
    private List<KuchenImpl> inventory;
    private int capacity;

    public VendingMachine(int capacity) {
        this.capacity = capacity;
        this.inventory = new ArrayList<>();
    }

    public boolean addItem(Kuchen kuchen) {
        if (inventory.size() < capacity && kuchen instanceof KuchenImpl) {
            inventory.add((KuchenImpl) kuchen);
            return true;
        } else {
            return false; // Vending machine is full, or kuchen is not an instance of KuchenImpl
        }
    }

    public boolean removeItem(Kuchen kuchen) {
        boolean removed = inventory.remove(kuchen);
        return removed;
    }

    public List<KuchenImpl> listItems() {
        return Collections.unmodifiableList(inventory);
    }

    public void updateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());

        for (KuchenImpl kuchen : inventory) {
            if (kuchen instanceof KuchenImpl) {
                ((KuchenImpl) kuchen).setInspektionsdatum(currentDate);
            }
        }

        Collections.sort(inventory, Comparator.comparing(KuchenImpl::getInspektionsdatum));
    }
}
