package infrastructure.KuchenLoeschen;

import java.util.LinkedList;
import java.util.List;

public class KuchenLoeschenEventHandler {

    private final List<KuchenLoeschenEventListener> listenerList = new LinkedList<>();

    public KuchenLoeschenEventHandler() {
    }

    public void add(KuchenLoeschenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(KuchenLoeschenEvent event){
        for(KuchenLoeschenEventListener listener: listenerList) listener.onKuchenLoeschenEvent(event);
    }
}
