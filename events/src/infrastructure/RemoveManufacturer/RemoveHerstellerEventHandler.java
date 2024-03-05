package infrastructure.RemoveManufacturer;


import java.util.LinkedList;
import java.util.List;

public class RemoveHerstellerEventHandler {

    private final List<RemoveHerstellerEventListener> listenerList = new LinkedList<>();

    public RemoveHerstellerEventHandler() {
    }

    public void add(RemoveHerstellerEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(RemoveHerstellerEvent event){
        for(RemoveHerstellerEventListener listener: listenerList) listener.onRemoveHerstellerEvent(event);
    }
}
