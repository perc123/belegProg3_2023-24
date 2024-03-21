package commands;

import infrastructure.PrintAllergies.PrintAllergiesEvent;
import infrastructure.PrintAllergies.PrintAllergiesEventHandler;
import infrastructure.PrintManufacturers.PrintHerstellerEvent;
import infrastructure.PrintManufacturers.PrintHerstellerEventHandler;
import infrastructure.PrintCakes.PrintCakeEvent;
import infrastructure.PrintCakes.PrintCakeEventHandler;

public class PrintMode implements Mode {

    private final PrintHerstellerEventHandler addHandlerHerstellerAnzeigen;
    private final PrintCakeEventHandler addHandlerKuchenAnzeigen;
    private final PrintAllergiesEventHandler addHandlerAllergeneAnzeigen;

    public PrintMode(PrintHerstellerEventHandler addHandlerHerstellerAnzeigen, PrintCakeEventHandler addHandlerKuchenAnzeigen, PrintAllergiesEventHandler addHandlerAllergeneAnzeigen) {
        this.addHandlerHerstellerAnzeigen = addHandlerHerstellerAnzeigen;
        this.addHandlerKuchenAnzeigen = addHandlerKuchenAnzeigen;
        this.addHandlerAllergeneAnzeigen = addHandlerAllergeneAnzeigen;
    }

    @Override
    public void handleInput(String input) {
        switch (input.toLowerCase()) {
            case "kremkuchen", "obstkuchen", "obsttorte", "kuchen" -> {
                // print Kremkuchen, Obstkuchen, Obsttorte or all kuchen
                PrintCakeEvent kuchenEvent = new PrintCakeEvent(this, input);
                if (null != this.addHandlerKuchenAnzeigen) {
                    this.addHandlerKuchenAnzeigen.handle(kuchenEvent);
                }
            }
            case "allergene i", "allergene e" -> {
                // Alle enthaltenen oder nicht enthaltene Allergene im Automaten anzeigen
                PrintAllergiesEvent allergeneEvent = new PrintAllergiesEvent(this, input);
                if (null != this.addHandlerAllergeneAnzeigen) {
                    this.addHandlerAllergeneAnzeigen.handle(allergeneEvent);
                }
            }
            case "hersteller" -> {
                // Print all manufacturers in the vending machine
                PrintHerstellerEvent herstellerEvent = new PrintHerstellerEvent(this, input);
                if (null != this.addHandlerHerstellerAnzeigen) {
                    this.addHandlerHerstellerAnzeigen.handle(herstellerEvent);
                }
            }
            default -> System.out.println("Unknown command, please try again");
        }
    }

}
