package infrastructure.PrintManufacturers;

import java.util.EventObject;

public class PrintHerstellerEvent extends EventObject {

    private final String hersteller;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PrintHerstellerEvent(Object source, String hersteller) {
        super(source);
        this.hersteller = hersteller;
    }

    public String getHersteller() {
        return hersteller;
    }
}
