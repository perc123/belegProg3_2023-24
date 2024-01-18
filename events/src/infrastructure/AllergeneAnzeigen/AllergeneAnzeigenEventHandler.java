package infrastructure.AllergeneAnzeigen;


import java.util.LinkedList;
import java.util.List;

public class AllergeneAnzeigenEventHandler {

    private final List<infrastructure.AllergeneAnzeigen.AllergeneAnzeigenEventListener> listenerList = new LinkedList<>();

    public AllergeneAnzeigenEventHandler() {
    }

    public void add(AllergeneAnzeigenEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(AllergeneAnzeigenEvent event){
        for(AllergeneAnzeigenEventListener listener: listenerList) listener.onAllergeneAnzeigenEvent(event);
    }
}
