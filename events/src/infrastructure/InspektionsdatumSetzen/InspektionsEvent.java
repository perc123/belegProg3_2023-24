package infrastructure.InspektionsdatumSetzen;

import java.util.EventObject;

public class InspektionsEvent extends EventObject {


    private final String fachnummer;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public InspektionsEvent(Object source, String fachnummer) {
        super(source);
        this.fachnummer = fachnummer;
    }

    public String getFachnummer() {
        return fachnummer;
    }
}
