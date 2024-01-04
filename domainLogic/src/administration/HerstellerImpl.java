package administration;

import verwaltung.Hersteller;
import eventSystem.EventListener;
import eventSystem.EventType;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
