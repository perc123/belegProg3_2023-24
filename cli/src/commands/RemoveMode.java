package commands;


import infrastructure.RemoveManufacturer.RemoveHerstellerEvent;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventHandler;
import infrastructure.RemoveCake.RemoveCakeEvent;
import infrastructure.RemoveCake.RemoveCakeEventHandler;

public class RemoveMode implements Mode {

    private final RemoveHerstellerEventHandler addHandlerHerstellerLoeschen;
    private final RemoveCakeEventHandler addHandlerKuchenLoeschen;

    public RemoveMode(RemoveHerstellerEventHandler addHandlerHerstellerLoeschen, RemoveCakeEventHandler addHandlerKuchenLoeschen) {
        this.addHandlerHerstellerLoeschen = addHandlerHerstellerLoeschen;
        this.addHandlerKuchenLoeschen = addHandlerKuchenLoeschen;
    }

    @Override
    public void handleInput(String input) {
        if (nummerPruefen(input)) {
            // Kuchen nach Fachnummer loeschen
            RemoveCakeEvent event = new RemoveCakeEvent(this, input);
            if (null != this.addHandlerKuchenLoeschen) {
                this.addHandlerKuchenLoeschen.handle(event);
            }
        } else {
            // Hersteller nach Namen loeschen
            RemoveHerstellerEvent event2 = new RemoveHerstellerEvent(this, input);
            if (null != this.addHandlerHerstellerLoeschen) {
                this.addHandlerHerstellerLoeschen.handle(event2);
            }
        }
    }

    // Methode prueft ob eine Zahl eingegeben wurde
    public static boolean nummerPruefen(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
