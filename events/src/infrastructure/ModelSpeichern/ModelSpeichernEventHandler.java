package infrastructure.ModelSpeichern;

import java.util.LinkedList;
import java.util.List;

public class ModelSpeichernEventHandler {


    private final List<ModelSpeichernEventListener> listenerList = new LinkedList<>();

    public ModelSpeichernEventHandler() {
    }

    public void add(ModelSpeichernEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(ModelSpeichernLadenEvent event){
        for(ModelSpeichernEventListener listener: listenerList) listener.onModelSpeichernEvent(event);
    }
}
