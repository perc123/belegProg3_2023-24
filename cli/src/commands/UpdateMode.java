package commands;

import infrastructure.InspectionsDate.InspectionEvent;
import infrastructure.InspectionsDate.InspectionEventHandler;

public class UpdateMode implements Mode {

    private final InspectionEventHandler addHandlerInspektion;

    public UpdateMode(InspectionEventHandler addHandlerInspektion) {
        this.addHandlerInspektion = addHandlerInspektion;
    }


    @Override
    public void handleInput(String input) {
        if (RemoveMode.nummerPruefen(input)) {
            // Inspektionsdatum nach Fachnummer loeschen
            InspectionEvent event = new InspectionEvent(this, input);
            if (null != this.addHandlerInspektion) {
                this.addHandlerInspektion.handle(event);
            }
        } else {
            System.out.println("Ungueltiger Befehl. Bitte versuchen Sie es erneut");
        }
    }
    }



