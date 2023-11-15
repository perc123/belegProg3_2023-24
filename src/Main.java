import administration.HerstellerImpl;
import administration.HerstellerList;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import administration.VendingMachine;
import cli.UserInterface;
import eventSystem.EventSystem;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

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
