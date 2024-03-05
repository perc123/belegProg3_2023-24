package infrastructure.SaveAndLoadVendingMachine;

import java.util.LinkedList;
import java.util.List;

public class SaveVendingMachineEventHandler {


    private final List<SaveVendingMachineEventListener> listenerList = new LinkedList<>();

    public SaveVendingMachineEventHandler() {
    }

    public void add(SaveVendingMachineEventListener listener){
        this.listenerList.add(listener);
    }

    public void handle(SaveVendingMachineEvent event){
        for(SaveVendingMachineEventListener listener: listenerList) listener.onSaveVendingMachineEvent(event);
    }
}
