package infrastructure.PrintCakes;

import java.util.EventObject;

public class PrintCakeEvent extends EventObject {

    private final String kuchenTyp;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PrintCakeEvent(Object source, String kuchenTyp) {
        super(source);
        this.kuchenTyp = kuchenTyp;
    }

    public String getKuchenTyp() {
        return kuchenTyp;
    }
}
