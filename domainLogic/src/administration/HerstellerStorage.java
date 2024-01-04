package administration;

import verwaltung.Hersteller;
import eventSystem.EventListener;
import eventSystem.EventType;

import java.util.ArrayList;
import java.util.List;

public class HerstellerStorage {
    private List<Hersteller> herstellerList;

    public HerstellerStorage() {
        this.herstellerList = new ArrayList<>();
    }

    public void addHersteller(HerstellerImpl hersteller) {
        herstellerList.add(hersteller);
    }

    public void removeHersteller(Hersteller hersteller) {
        herstellerList.remove(hersteller);
    }

    public List<Hersteller> getAllHersteller() {
        return herstellerList;
    }
    public void displayHersteller(){
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

}
