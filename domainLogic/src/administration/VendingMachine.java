package administration;

import cakes.KremkuchenImpl;
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

    public boolean addItem(Kuchen kuchen, HerstellerImpl hersteller) {
        System.out.println("cake added");

        if (inventory.size() < capacity && kuchen instanceof KremkuchenImpl) {
            KuchenImpl kuchenImpl = (KuchenImpl) kuchen;
            int newFachnummer = findFirstAvailableFachnummer();
            kuchenImpl.setFachnummer(newFachnummer);
            inventory.add(kuchenImpl);
            return true;
        }
        return false; // Vending machine is full or kuchen is not an instance of KuchenImpl
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

    public boolean removeItem(int trayNumber) {
        KuchenImpl cakeToRemove = null;
        boolean removed = false;
        for (KuchenImpl kuchen : inventory) {
            if (kuchen.getFachnummer() == trayNumber) {
                cakeToRemove = kuchen;
                break;
            }
        }
        if (cakeToRemove != null) {
            removed = inventory.remove(cakeToRemove);

            // Reassign correct Fachnummer values after removal
            if (removed) {
                reassignFachnummer();
            }
            return removed;
        }

        return removed;
    }

    public boolean isFull() {
        return inventory.size() >= capacity;
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

    public void updateInspectionDate(int fachnummer) {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());

        for (KuchenImpl kuchen : inventory) {
            if (kuchen.getFachnummer() == fachnummer) {
                kuchen.setInspektionsdatum(currentDate);
                break;  // cake is found and updated
            }
        }
        inventory.removeIf(kuchen -> kuchen.getInspektionsdatum() == null);
        inventory.sort(Comparator.comparing(KuchenImpl::getInspektionsdatum, Comparator.nullsLast(Comparator.naturalOrder())));
    }
}