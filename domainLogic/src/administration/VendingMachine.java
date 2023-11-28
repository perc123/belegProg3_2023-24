package administration;

import cakes.KuchenImpl;
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
            KuchenImpl kuchenImpl = (KuchenImpl) kuchen;
            // Check if Hersteller exists
            if (kuchenImpl.getHersteller() == hersteller) {
                int newFachnummer = findFirstAvailableFachnummer();
                kuchenImpl.setFachnummer(newFachnummer);
                inventory.add(kuchenImpl);
                return true;
            }
        }
        return false; // Vending machine is full, kuchen is not an instance of KuchenImpl, or wrong Hersteller
    }

    private int findFirstAvailableFachnummer() {
        int newFachnummer = 1;
        for (KuchenImpl kuchen : inventory) {
            if (kuchen.getFachnummer() == newFachnummer) {
                newFachnummer++;
            } else {
                break;
            }
        }
        return newFachnummer;
    }

    public boolean removeItem(Kuchen kuchen) {
        boolean removed = inventory.remove(kuchen);
        if (removed) {
            // Reassign correct Fachnummer values after removal
            reassignFachnummer();
        }
        return removed;
    }

    private void reassignFachnummer() {
        int newFachnummer = 1;
        for (KuchenImpl kuchen : inventory) {
            kuchen.setFachnummer(newFachnummer);
            newFachnummer++;
        }
    }

    public List<KuchenImpl> listItems() {
        return inventory;
    }

    public void updateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());

        for (KuchenImpl kuchen : inventory) {
            kuchen.setInspektionsdatum(currentDate);
        }

        Collections.sort(inventory, Comparator.comparing(KuchenImpl::getInspektionsdatum));
    }
}
