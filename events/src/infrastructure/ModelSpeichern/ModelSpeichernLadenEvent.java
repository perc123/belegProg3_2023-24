package infrastructure.ModelSpeichern;

import java.util.EventObject;

public class ModelSpeichernLadenEvent extends EventObject {

    private final String speicherArt;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ModelSpeichernLadenEvent(Object source, String speicherArt) {
        super(source);
        this.speicherArt = speicherArt;
    }

    public String getSpeicherArt() {
        return speicherArt;
    }
}
