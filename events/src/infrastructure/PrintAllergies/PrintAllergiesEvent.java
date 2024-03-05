package infrastructure.PrintAllergies;

import java.util.EventObject;

public class PrintAllergiesEvent extends EventObject {

    private final String allergene;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PrintAllergiesEvent(Object source, String allergene) {
        super(source);
        this.allergene = allergene;
    }

    public String getAllergene() {
        return allergene;
    }

}
