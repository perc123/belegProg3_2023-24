package administration;

import cakes.KuchenImpl;
import kuchen.Allergen;
import observer.Subject;
import verwaltung.Hersteller;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;



public class VendingMachine extends Subject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<KuchenImpl> inventory;
    private List<Hersteller> herstellerList;

    private int capacity;

  // Default constructor
    public VendingMachine() {
        this.inventory = new LinkedList<>();
        this.capacity = 0;
    }

    public VendingMachine(int capacity, List<KuchenImpl> inventory, List<Hersteller> herstellerList) {
        this.capacity = capacity;
        this.inventory = inventory;
        this.herstellerList = herstellerList;
    }

    public List<Hersteller> getAllHersteller() {
        return herstellerList;
    }

    // Getter method for the inventory property
    public List<KuchenImpl> getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    // Add manufacturer. Checks for name duplications
    public boolean addHersteller(Hersteller hersteller) {
        for (Hersteller h : herstellerList) {
            if (h.getName().equals(hersteller.getName()) || hersteller.getName().equals("")) {
                return false;
            }
        }
        herstellerList.add(hersteller);
        return true;
    }

    public boolean removeHersteller(String hersteller) {
        for (Hersteller h : herstellerList) {
            if (h.getName().equals(hersteller)) {
                herstellerList.remove(h);
                return true;
            }
        }
        return false;
    }
    // Call all manufacturers with their cakes
    public List<Hersteller> callHersteller() {
        int count = 0;
        List<KuchenImpl> cakeList = new LinkedList<>(inventory);
        for (Hersteller h : herstellerList) {
            for (int i = 0; i < inventory.size(); i++) {
                if (cakeList.get(i).getHersteller().equals(h)) {
                    count++;
                }
            }
            h.setCakeCount(count);
            count = 0;
        }
        return herstellerList;
    }

    public boolean addItem(KuchenImpl kuchen) {
        // In full capacity no cake will be added
        if (inventory.size() >= capacity) {
            return false;
        }


        for (Hersteller h : herstellerList) {
            if (h.equals(kuchen.getHersteller())) {
                // Give a tray number
                int fachnummer = inventory.size() + 1;
                kuchen.setFachnummer(fachnummer);

                inventory.add(kuchen);
                updateInspectionDate(fachnummer);
                notifyObservers();
                return true;
            }
        }
        return false;
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

    /*
   Gibt aus, welche Kuchen im Automaten sind. Wird ein Kuchentyp angegeben, werden nur Kuchen von diesen
   Kuchentypen aufgelistet
    */
    public List<KuchenImpl> printCake(String kuchentyp) {
        List<KuchenImpl> cakeList = new LinkedList<>();
        if (kuchentyp == null || kuchentyp.equals("Kuchen") || kuchentyp.equals("kuchen")) {
            for(KuchenImpl cake : inventory){
               cakeList.add(cake);
            }
            return cakeList;
        }
        for (KuchenImpl cake : inventory) {
            if (cake.getKuchenTyp().equals(kuchentyp.substring(0, 1).toUpperCase() + kuchentyp.substring(1).toLowerCase())) {
                LocalDateTime jetzt = LocalDateTime.now();
                cake.calculateRemainingShelfLife();
                cakeList.add(cake);
            }
        }
        return cakeList;
    }

    // Abruf aller vorhandenen oder nicht vorhandenen Allergene im Automaten
    public List<Allergen> allergeneAbrufen(boolean vorhanden) {
        Allergen[] alleAllergene = Allergen.values();

        List<Allergen> ergebnisListe = new LinkedList<>();
        for (Allergen allergen : alleAllergene) {
            boolean istVorhanden = false;
            for (KuchenImpl cake : inventory) {
                if (cake.getAllergene().contains(allergen)) {
                    istVorhanden = true;
                    break;
                }
            }
            if (istVorhanden == vorhanden) {
                ergebnisListe.add(allergen);
            }
        }
        return ergebnisListe;
    }
}