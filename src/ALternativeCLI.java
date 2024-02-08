import administration.VendingMachine;
import cakes.KuchenImpl;
import commands.*;
import infrastructure.AddCake.AddCakeEventHandler;
import infrastructure.AddCake.AddCakeEventListener;
import infrastructure.AddManufacturer.AddHerstellerEventHandler;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import infrastructure.InspectionsDate.InspectionEventHandler;
import infrastructure.InspectionsDate.InspectionEventListener;
import infrastructure.PrintAllergies.PrintAllergiesEventHandler;
import infrastructure.PrintAllergies.PrintAllergiesEventListener;
import infrastructure.PrintCakes.PrintCakeEventHandler;
import infrastructure.PrintCakes.PrintCakeEventListener;
import infrastructure.PrintManufacturers.PrintHerstellerEventHandler;
import infrastructure.PrintManufacturers.PrintHerstellerEventListener;
import infrastructure.RemoveCake.RemoveCakeEventHandler;
import infrastructure.RemoveCake.RemoveCakeEventListener;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventHandler;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventListener;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventHandler;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventListener;
import listener.InfoListener;
import listener.Listener;
import observer.AllergiesObserver;
import observer.CapacityObserver;
import verwaltung.Hersteller;

import java.util.LinkedList;

public class ALternativeCLI {


    /*
       Command Line Interface indem die Funktionen Loeschen von Herstellern
       und das Auflisten der Allergene deaktiviert ist.
       Außerdem ist der Observer zur Ueberwachung der Kapaztaet nicht aktiv
        */
    public static void main(String[] args) {
        int capacity = 0;
        for (String arg : args) {
            try {
                capacity = Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                return;
            }
        }
        if(capacity >= 0 ){
            LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
            LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
            VendingMachine vendingMachine = new VendingMachine(capacity, kuchenLinkedList,herstellerLinkedList);
            System.out.println("Achtung! Löschen von Herstellern und das Auflisten der Allergene nicht möglich!");

            // Add Observers
            AllergiesObserver allergiesObserver = new AllergiesObserver(vendingMachine);
            vendingMachine.add(allergiesObserver);

            // Add cake event
            AddCakeEventHandler addCakeEventHandler = new AddCakeEventHandler();
            AddCakeEventListener addCakeEventListener = new Listener(vendingMachine);
            addCakeEventHandler.add(addCakeEventListener);
            AddCakeEventListener infoCakeListener = new Listener(vendingMachine);
            addCakeEventHandler.add(infoCakeListener);

            // Add manufacturer (Hersteller) event
            AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
            AddHerstellerEventListener addHerstellerEventListener = new Listener(vendingMachine);
            addHerstellerEventHandler.add(addHerstellerEventListener);
            AddHerstellerEventListener infoHerstellerListener = new InfoListener();
            addHerstellerEventHandler.add(infoHerstellerListener);

            AddMode addMode = new AddMode(addHerstellerEventHandler,addCakeEventHandler);

            // Remove cake event
            RemoveCakeEventHandler removeCakeEventHandler = new RemoveCakeEventHandler();
            RemoveCakeEventListener removeCakeEventListener = new Listener(vendingMachine);
            removeCakeEventHandler.add(removeCakeEventListener);
            RemoveCakeEventListener infoRemoveCakeListener = new Listener(vendingMachine);
            removeCakeEventHandler.add(infoRemoveCakeListener);

            // Remove manufacturer (Hersteller) event
            RemoveHerstellerEventHandler removeHerstellerEventHandler = new RemoveHerstellerEventHandler();

            RemoveMode removeMode = new RemoveMode(removeHerstellerEventHandler,removeCakeEventHandler);

            // Update inspection date event
            InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();
            InspectionEventListener inspectionEventListener = new Listener(vendingMachine);
            inspectionEventHandler.add(inspectionEventListener);
            InspectionEventListener infoInspectionEventListener = new InfoListener();
            inspectionEventHandler.add(infoInspectionEventListener);

            UpdateMode updateMode = new UpdateMode(inspectionEventHandler);

            // Print allergies event
            PrintAllergiesEventHandler printAllergiesEventHandler = new PrintAllergiesEventHandler();

            // Print cake event
            PrintCakeEventHandler printCakeEventHandler = new PrintCakeEventHandler();
            PrintCakeEventListener printCakeEventListener = new Listener(vendingMachine);
            printCakeEventHandler.add(printCakeEventListener);
            PrintCakeEventListener infoPrintCakeEventListener = new InfoListener();
            printCakeEventHandler.add(infoPrintCakeEventListener);

            // Print manufacturer event
            PrintHerstellerEventHandler printHerstellerEventHandler = new PrintHerstellerEventHandler();
            PrintHerstellerEventListener printHerstellerEventListener = new Listener(vendingMachine);
            printHerstellerEventHandler.add(printHerstellerEventListener);
            PrintHerstellerEventListener infoPrintHerstellerEventListener = new InfoListener();
            printHerstellerEventHandler.add(infoPrintHerstellerEventListener);

            PrintMode printMode = new PrintMode(printHerstellerEventHandler,printCakeEventHandler,printAllergiesEventHandler);

            // Save vending machine
            SaveVendingMachineEventHandler saveVendingMachineEventHandler = new SaveVendingMachineEventHandler();
            SaveVendingMachineEventListener saveVendingMachineEventListener = new Listener(vendingMachine);
            saveVendingMachineEventHandler.add(saveVendingMachineEventListener);
            SaveVendingMachineEventListener infoSaveVendingMachineEventListener = new InfoListener();
            saveVendingMachineEventHandler.add(infoSaveVendingMachineEventListener);

            SerializationMode serializationMode = new SerializationMode(saveVendingMachineEventHandler);

            CLIcontroller cliController = new CLIcontroller(addMode,removeMode,updateMode,printMode,serializationMode);
            cliController.start();

        }

    }
}
