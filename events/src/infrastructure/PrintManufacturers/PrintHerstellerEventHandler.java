package infrastructure.PrintManufacturers;

import java.util.LinkedList;
import java.util.List;

public class PrintHerstellerEventHandler {

    private final List<PrintHerstellerEventListener> listenerList = new LinkedList<>();

    public PrintHerstellerEventHandler() {
    }

    public void add(PrintHerstellerEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(PrintHerstellerEvent event){
        for(PrintHerstellerEventListener listener: listenerList) listener.onPrintHerstellerEvent(event);
    }

}
