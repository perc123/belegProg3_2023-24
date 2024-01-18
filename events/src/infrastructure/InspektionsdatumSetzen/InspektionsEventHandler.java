package infrastructure.InspektionsdatumSetzen;

import java.util.LinkedList;
import java.util.List;

public class InspektionsEventHandler {

    private final List<InspektionsEventListener> listenerList = new LinkedList<>();

    public InspektionsEventHandler() {
    }

    public void add(InspektionsEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(InspektionsEvent event){
        for(InspektionsEventListener listener: listenerList) listener.onInspektionsEvent(event);
    }
}
