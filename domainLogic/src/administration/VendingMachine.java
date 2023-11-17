package administration;

import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import eventSystem.EventListener;
import eventSystem.EventType;
import kuchen.Kuchen;
import verwaltung.Hersteller;

import java.text.SimpleDateFormat;
import java.time.Duration;
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

            if (kuchenImpl.getHersteller() == hersteller) {
                if (kuchenImpl.getKuchenTyp().equals("Obstkuchen")) {
                    if (kuchenImpl instanceof ObstkuchenImpl) {
                        ObstkuchenImpl obstkuchen = (ObstkuchenImpl) kuchenImpl;
                        System.out.println("Obstkuchen added");

                        // Set the Inspektionsdatum
                        obstkuchen.setInspektionsdatum(new Date(System.currentTimeMillis()));

                        obstkuchen.setFachnummer(inventory.size() + 1);
                        inventory.add(obstkuchen);
                        return true;
                    } else {
                        System.out.println("Provided Kuchen does not exist!");
                    }
                } else {
                    System.out.println("Not supporting other Kuchen types yet");
                }
            } else {
                System.out.println("The provided Hersteller does not exist!");
            }
        } else {
            System.out.println("Vending machine is full, or provided Kuchen is not an instance of KuchenImpl");
        }
        return false;
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

    private String formatHaltbarkeit(Duration duration) {
        long hours = duration.toHours();
        long days = hours / 24;
        long remainingHours = hours % 24;
        return String.format("%dd %dh", days, remainingHours);
    }

    private String formatInspektionsdatum(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } else {
            return "Not inspected";
        }
    }

    @Override
    public void onEvent(EventType eventType, Object data) {
        if (eventType == EventType.INSERT_KUCHEN) {
            if (data instanceof ObstkuchenImpl) {
                KuchenImpl kuchen = (ObstkuchenImpl) data;
                addItem(kuchen, kuchen.getHersteller());
                System.out.println("Item added to VendingMachine");
            }
        }
        if (eventType == EventType.DISPLAY_KUCHEN) {
            listItems();
            for (KuchenImpl kuchen : inventory) {
                System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n"
                        + "Inspektionsdatum: " + formatInspektionsdatum(kuchen.getInspektionsdatum()) + "\n"
                        + "Verbleibender Haltbarkeit: " + formatHaltbarkeit(kuchen.getHaltbarkeit()));
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
