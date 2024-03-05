package infrastructure.PrintAllergies;


import java.util.LinkedList;
import java.util.List;

public class PrintAllergiesEventHandler {

    private final List<PrintAllergiesEventListener> listenerList = new LinkedList<>();

    public PrintAllergiesEventHandler() {
    }

    public void add(PrintAllergiesEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(PrintAllergiesEvent event){
        for(PrintAllergiesEventListener listener: listenerList) listener.onPrintAllergiesEvent(event);
    }
}
