package infrastructure.PrintCakes;


import java.util.LinkedList;
import java.util.List;

public class PrintCakeEventHandler {

    private final List<PrintCakeEventListener> listenerList = new LinkedList<>();

    public PrintCakeEventHandler() {
    }

    public void add(PrintCakeEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(PrintCakeEvent event){
        for(PrintCakeEventListener listener: listenerList) listener.onShowKuchenEvent(event);
    }

}
