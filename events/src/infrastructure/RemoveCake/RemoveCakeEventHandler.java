package infrastructure.RemoveCake;

import java.util.LinkedList;
import java.util.List;

public class RemoveCakeEventHandler {

    private final List<RemoveCakeEventListener> listenerList = new LinkedList<>();

    public RemoveCakeEventHandler() {
    }

    public void add(RemoveCakeEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(RemoveCakeEvent event){
        for(RemoveCakeEventListener listener: listenerList) listener.onRemoveCakeEvent(event);
    }
}
