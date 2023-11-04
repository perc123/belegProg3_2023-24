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

    @Override
    public void onEvent(EventType eventType, Object data) {
        // Handle events related to HerstellerList
        if (eventType == EventType.INSERT_HERSTELLER) {
            if (data instanceof HerstellerImpl) {
                HerstellerImpl hersteller = (HerstellerImpl) data;
                addHersteller(hersteller);
            }
        }
        if (eventType == EventType.DISPLAY_HERSTELLER) {
            // Display all Hersteller from herstellerList
            getAllHersteller();
            for (Hersteller hersteller : herstellerList) {
                System.out.println("Hersteller Name: " + hersteller.getName());
            }
        }
        // Other event handling logic can be added here
    }
}
