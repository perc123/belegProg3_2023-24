package infrastructure.AddCake;

import java.util.LinkedList;
import java.util.List;

public class AddCakeEventHandler {

    private final List<AddCakeEventListener> listenerList = new LinkedList<>();

    public AddCakeEventHandler() {
    }

    public void add(AddCakeEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(AddCakeEvent event){
        for(AddCakeEventListener listener: listenerList) listener.onAddEvent(event);
        System.out.println("Added2");
    }

}
