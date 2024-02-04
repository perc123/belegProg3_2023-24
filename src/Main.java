import administration.HerstellerStorage;
import administration.VendingMachine;
import cli.Console;
import infrastructure.AddManufacturer.AddHerstellerEventHandler;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import listener.InfoListener;
import listener.Listener;


public class Main {
    public static void main(String[] args) {
        int capacity = 0;
        VendingMachine vendingMachine = new VendingMachine();
        HerstellerStorage herstellerStorage = new HerstellerStorage();
        Console console = new Console(vendingMachine, herstellerStorage);
        AddHerstellerEventHandler addHandlerHersteller = new AddHerstellerEventHandler();
        AddHerstellerEventListener addListenerHersteller = new Listener(vendingMachine);
        addHandlerHersteller.add(addListenerHersteller);
        AddHerstellerEventListener infoListenerHersteller = new InfoListener();
        addHandlerHersteller.add(infoListenerHersteller);
        console.start();
    }
}
