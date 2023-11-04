package administration;

import verwaltung.Hersteller;
import eventSystem.EventListener;
import eventSystem.EventType;

public class HerstellerImpl implements Hersteller, EventListener {
    private String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onEvent(EventType eventType, Object data) {
        // Handle events specific to Hersteller
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
        else if (eventType == EventType.DELETE_HERSTELLER) {
            // Handle deletion of Hersteller
            System.out.println("Hersteller removed");
            // Example: Remove this Hersteller if its name matches the data received
            if (data instanceof String) {
                String herstellerName = (String) data;
                if (herstellerName.equals(name)) {
                    // Remove this Hersteller
                    // Example: HerstellerList.removeHersteller(this);
                }
            }
        }
        if (eventType == EventType.DELETE_HERSTELLER) {
            // Handle deletion of Hersteller
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
