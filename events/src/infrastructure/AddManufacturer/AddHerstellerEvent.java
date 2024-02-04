package infrastructure.AddManufacturer;

import java.util.EventObject;

public class AddHerstellerEvent extends EventObject {

    private final String hersteller;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public AddHerstellerEvent(Object source, String hersteller) {
        super(source);
        this.hersteller = hersteller;
    }

    public String getHersteller() {
        return hersteller;
    }
}
