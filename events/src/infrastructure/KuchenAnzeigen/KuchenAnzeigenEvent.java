package infrastructure.KuchenAnzeigen;

import java.util.EventObject;

public class KuchenAnzeigenEvent extends EventObject {

    private final String kuchenTyp;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public KuchenAnzeigenEvent(Object source, String kuchenTyp) {
        super(source);
        this.kuchenTyp = kuchenTyp;
    }

    public String getKuchenTyp() {
        return kuchenTyp;
    }
}
