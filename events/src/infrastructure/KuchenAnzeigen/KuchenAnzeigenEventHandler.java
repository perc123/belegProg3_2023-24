package infrastructure.KuchenAnzeigen;


import java.util.LinkedList;
import java.util.List;

public class KuchenAnzeigenEventHandler {

    private final List<KuchenAnzeigenEventListener> listenerList = new LinkedList<>();

    public KuchenAnzeigenEventHandler() {
    }

    public void add(KuchenAnzeigenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(KuchenAnzeigenEvent event){
        for(KuchenAnzeigenEventListener listener: listenerList) listener.onKuchenAnzeigenEvent(event);
    }

}
