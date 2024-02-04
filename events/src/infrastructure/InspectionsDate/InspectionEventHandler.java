package infrastructure.InspectionsDate;

import java.util.LinkedList;
import java.util.List;

public class InspectionEventHandler {

    private final List<InspectionEventListener> listenerList = new LinkedList<>();

    public InspectionEventHandler() {
    }

    public void add(InspectionEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(InspectionEvent event){
        for(InspectionEventListener listener: listenerList) listener.onInspectionEvent(event);
    }
}
