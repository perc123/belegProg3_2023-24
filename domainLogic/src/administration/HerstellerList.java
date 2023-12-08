package administration;

import verwaltung.Hersteller;
import eventSystem.EventListener;
import eventSystem.EventType;

import java.util.ArrayList;
import java.util.List;

public class HerstellerList implements EventListener {
    private List<Hersteller> herstellerList;

    public HerstellerList() {
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

    @Override
    public void onEvent(EventType eventType, Object data) {
        if (eventType == EventType.INSERT_HERSTELLER) {
            if (data instanceof HerstellerImpl) {
                HerstellerImpl hersteller = (HerstellerImpl) data;
                addHersteller(hersteller);
                System.out.println("Hersteller added");
            }
        }
        if (eventType == EventType.DISPLAY_HERSTELLER) {
            for (Hersteller hersteller : herstellerList) {
                System.out.println("Hersteller Name: " + hersteller.getName());
            }
        }
    }
}
