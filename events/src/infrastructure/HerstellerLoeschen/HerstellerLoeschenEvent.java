package infrastructure.HerstellerLoeschen;

import administration.HerstellerImpl;
import administration.HerstellerStorage;
import verwaltung.Hersteller;

import java.util.EventObject;

public class HerstellerLoeschenEvent extends EventObject {

    private final HerstellerImpl hersteller;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public HerstellerLoeschenEvent(Object source, HerstellerImpl hersteller) {
        super(source);
        this.hersteller = hersteller;
    }

    public HerstellerImpl getHersteller() {
        return hersteller;
    }
}
