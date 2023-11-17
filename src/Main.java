import administration.HerstellerList;

import administration.VendingMachine;
import cli.UserInterface;
import eventSystem.EventSystem;


public class Main {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        VendingMachine vendingMachine = new VendingMachine(10);
        HerstellerList herstellerList = new HerstellerList();

        eventSystem.subscribe(herstellerList);
        eventSystem.subscribe(vendingMachine);

        UserInterface userInterface = new UserInterface(eventSystem);
        userInterface.start();
    }
}
