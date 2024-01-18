package infrastructure.KuchenEinfuegen;

import java.util.LinkedList;
import java.util.List;

public class KuchenEinfuegenEventHandler {

    private final List<KuchenEinfuegenEventListener> listenerList = new LinkedList<>();

    public KuchenEinfuegenEventHandler() {
    }

    public void add(KuchenEinfuegenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(KuchenEinfuegenEvent event){
        for(KuchenEinfuegenEventListener listener: listenerList) listener.onEinfuegenEvent(event);
    }

}
