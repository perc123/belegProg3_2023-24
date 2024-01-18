package infrastructure.HerstellerAnzeigen;

import java.util.LinkedList;
import java.util.List;

public class HerstellerAnzeigenEventHandler {

    private final List<HerstellerAnzeigenEventListener> listenerList = new LinkedList<>();

    public HerstellerAnzeigenEventHandler() {
    }

    public void add(HerstellerAnzeigenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(HerstellerAnzeigenEvent event){
        for(HerstellerAnzeigenEventListener listener: listenerList) listener.onHerstellerAnzeigenEvent(event);
    }

}
