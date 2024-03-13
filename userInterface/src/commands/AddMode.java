package commands;

import infrastructure.AddManufacturer.AddHerstellerEvent;
import infrastructure.AddManufacturer.AddHerstellerEventHandler;
import infrastructure.AddCake.AddCakeEvent;
import infrastructure.AddCake.AddCakeEventHandler;

public class AddMode implements Mode {

    private final AddHerstellerEventHandler addHandlerHersteller;
    private final AddCakeEventHandler addHandlerKuchen;

    public AddMode(AddHerstellerEventHandler addHandlerHersteller, AddCakeEventHandler addHandlerKuchen) {
        this.addHandlerHersteller = addHandlerHersteller;
        this.addHandlerKuchen = addHandlerKuchen;
    }


    @Override
    public void handleInput(String input) {
        String[] commandParts = input.split(" ");
        System.out.println(commandParts.length); //Debug 1

        if (commandParts.length <= 4) {
            // Add manufacturer
            String herstellerName = String.join(" ", commandParts);
            AddHerstellerEvent event = new AddHerstellerEvent(this,herstellerName);
            if (null != this.addHandlerHersteller) {
                this.addHandlerHersteller.handle(event);
            }
        } else if(commandParts.length == 7 || commandParts.length == 8) {
            // Add cake
            AddCakeEvent event2 = new AddCakeEvent(this, commandParts[0], commandParts[1], commandParts[2], commandParts[3], commandParts[4], commandParts[5], commandParts[6]);
            if (null != this.addHandlerKuchen) {
                this.addHandlerKuchen.handle(event2);
            }
        }
    }
}
