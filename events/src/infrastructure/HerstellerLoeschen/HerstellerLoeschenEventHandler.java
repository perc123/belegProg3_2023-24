package infrastructure.HerstellerLoeschen;


import java.util.LinkedList;
import java.util.List;

public class HerstellerLoeschenEventHandler {

    private final List<HerstellerLoeschenEventListener> listenerList = new LinkedList<>();

    public HerstellerLoeschenEventHandler() {
    }

    public void add(HerstellerLoeschenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(HerstellerLoeschenEvent event){
        for(HerstellerLoeschenEventListener listener: listenerList) listener.onHerstellerLoeschenEvent(event);
    }
}
