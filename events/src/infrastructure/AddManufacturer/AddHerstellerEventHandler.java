package infrastructure.AddManufacturer;

import java.util.LinkedList;
import java.util.List;

public class AddHerstellerEventHandler {

    private final List<AddHerstellerEventListener> listenerList = new LinkedList<>();

    public AddHerstellerEventHandler() {
    }

    public void add(AddHerstellerEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(AddHerstellerEvent event){
        for(AddHerstellerEventListener listener: listenerList) listener.onAddHerstellerEvent(event);
    }

}
