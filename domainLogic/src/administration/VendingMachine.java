package administration;

import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import eventSystem.EventListener;
import eventSystem.EventType;
import kuchen.Kuchen;
import verwaltung.Hersteller;

import java.util.*;

public class VendingMachine implements EventListener {
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
                if (kuchenImpl.getKuchenTyp().equals("Obstkuchen"))
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
        return inventory;
    }

    public void updateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());

        for (KuchenImpl kuchen : inventory) {
            kuchen.setInspektionsdatum(currentDate);
        }

        Collections.sort(inventory, Comparator.comparing(KuchenImpl::getInspektionsdatum));
    }

    @Override
    public void onEvent(EventType eventType, Object data) {
        if (eventType == EventType.INSERT_KUCHEN) {
            if (data instanceof KuchenImpl) {
                KuchenImpl kuchen = (KuchenImpl) data;
                addItem(kuchen, kuchen.getHersteller());
                System.out.println("Item added to VendingMachine");
            }
        }
        if (eventType == EventType.DISPLAY_KUCHEN) {
            if (data instanceof KuchenImpl) {
                listItems();
                for (KuchenImpl kuchen : inventory) {
                    System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n"
                            + "Inspektionsdatum" + kuchen.getInspektionsdatum() + "\n"
                            + "Verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
                }
            }
        }
    }
/*
    public void triggerAddItemEvent(Kuchen kuchen, Hersteller hersteller) {
        EventManager.triggerEvent(EventType.ADD_ITEM, new ItemEvent(kuchen, hersteller));
    }*/

    private static class ItemEvent {
        private final Kuchen kuchen;
        private final Hersteller hersteller;

        public ItemEvent(Kuchen kuchen, Hersteller hersteller) {
            this.kuchen = kuchen;
            this.hersteller = hersteller;
        }

        public Kuchen getKuchen() {
            return kuchen;
        }

        public Hersteller getHersteller() {
            return hersteller;
        }
    }

}
