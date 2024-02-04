package administration;

import verwaltung.Hersteller;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class HerstellerStorage implements Serializable {
    private List<Hersteller> herstellerList;

    public HerstellerStorage() {
        this.herstellerList = new LinkedList<>();
    }

    public void addHersteller(Hersteller hersteller) {
        if (!herstellerList.contains(hersteller))
            herstellerList.add(hersteller);
        System.out.println(hersteller.getName());
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

    public List<Hersteller> getAllHersteller() {
        return herstellerList;
    }

    public void displayHersteller() {
        for (Hersteller hersteller : herstellerList) {
            System.out.println("Hersteller Name: " + hersteller.getName());
        }
    }

    public Hersteller findHerstellerByName(String name) {
        for (Hersteller hersteller : herstellerList) {
            if (hersteller.getName().equalsIgnoreCase(name)) {
                return hersteller;
            }
        }
        return null;  // Return null if no matching Hersteller is found
    }

    // Standard JavaBeans getter and setter for herstellerList
   public List<Hersteller> getHerstellerList() {
        return herstellerList;
    }

    public void setHerstellerList(List<Hersteller> herstellerList) {
        this.herstellerList = herstellerList;
    }
    public void addAllHersteller(List<Hersteller> herstellerList) {
        this.herstellerList.addAll(herstellerList);
    }


    public void setManufacturerList(HerstellerStorage other) {
        // Clear existing
        this.herstellerList.clear();

        // Copy from other, avoiding duplicates
        for (Hersteller hersteller : other.herstellerList) {
            if (!this.herstellerList.contains(hersteller)) {
                this.herstellerList.add(hersteller);
            }
        }

    }
}
