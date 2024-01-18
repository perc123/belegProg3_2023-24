package infrastructure.HerstellerEinfuegen;

import java.util.LinkedList;
import java.util.List;

public class HerstellerEinfuegenEventHandler {

    private final List<HerstellerEinfuegenEventListener> listenerList = new LinkedList<>();

    public HerstellerEinfuegenEventHandler() {
    }

    public void add(HerstellerEinfuegenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(HerstellerEinfuegenEvent event){
        for(HerstellerEinfuegenEventListener listener: listenerList) listener.onHerstellerEinfuegenEvent(event);
    }

}
