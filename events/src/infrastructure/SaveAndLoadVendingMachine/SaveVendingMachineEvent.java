package infrastructure.SaveAndLoadVendingMachine;

import java.util.EventObject;

public class SaveVendingMachineEvent extends EventObject {

    private final String speicherArt;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SaveVendingMachineEvent(Object source, String speicherArt) {
        super(source);
        this.speicherArt = speicherArt;
    }

    public String getTypeOfSave() {
        return speicherArt;
    }
}
