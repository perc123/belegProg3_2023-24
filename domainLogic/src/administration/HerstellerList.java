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
        if (eventType == EventType.INSERT_HERSTELLER) {
            // Handle deletion of Hersteller

            System.out.println("Hersteller added");
            // Example: Remove this Hersteller if its name matches the data received
            if (data instanceof String) {
                String herstellerName = (String) data;
                if (herstellerName.equals(name)) {
                    // Remove this Hersteller
                    // Example: HerstellerList.removeHersteller(this);
                }
            }
        }
    }
}
