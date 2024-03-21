package commands;


import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventHandler;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEvent;

public class SerializationMode implements Mode {

    private final SaveVendingMachineEventHandler addHandlerSpeichern;

    public SerializationMode(SaveVendingMachineEventHandler addHandlerSpeichern){
        this.addHandlerSpeichern = addHandlerSpeichern;
    }

    @Override
    public void handleInput(String input) {
        switch (input){
            case "saveJOS", "loadJOS", "saveJBP", "loadJBP" -> {
                SaveVendingMachineEvent event = new SaveVendingMachineEvent(this, input);
                if (null != this.addHandlerSpeichern) {
                    this.addHandlerSpeichern.handle(event);
                }
            }
        }
    }
}
