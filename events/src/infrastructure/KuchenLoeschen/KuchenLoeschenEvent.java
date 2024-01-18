package infrastructure.KuchenLoeschen;

import java.util.EventObject;

public class KuchenLoeschenEvent extends EventObject {


    private final String fachnummer;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public KuchenLoeschenEvent(Object source, String fachnummer) {
        super(source);
        this.fachnummer = fachnummer;
    }

    public String getFachnummer() {
        return fachnummer;
    }
}
