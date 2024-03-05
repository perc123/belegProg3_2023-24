import TCP.ClientTCP;
import UDP.ClientUDP;
import administration.HerstellerImpl;
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

import java.io.IOException;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int capacity = 0;
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        boolean TCP = false;
        boolean UDP = false;
        for (String arg : args) {
            try {
                capacity = Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                if (arg.equalsIgnoreCase("TCP")) {
                    TCP = true;
                }
                else if (arg.equalsIgnoreCase("UDP")) {
                    UDP = true;
                }
            }
        }
        if (TCP) {
            ClientTCP client = new ClientTCP();
            client.start();
            System.out.println("Here TCP");
        }
        if (UDP) {
            ClientUDP clientUDP = new ClientUDP();
            clientUDP.start();
            System.out.println("Here UDP");

        }
        if (capacity >= 0) {

            VendingMachine vendingMachine = new VendingMachine(capacity, kuchenLinkedList, herstellerLinkedList);
            // Add Observers
            CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
            vendingMachine.add(capacityObserver);
            AllergiesObserver allergiesObserver = new AllergiesObserver(vendingMachine);
            vendingMachine.add(allergiesObserver);

            // Add cake event
            AddCakeEventHandler addCakeEventHandler = new AddCakeEventHandler();
            AddCakeEventListener addCakeEventListener = new Listener(vendingMachine);
            addCakeEventHandler.add(addCakeEventListener);
            AddCakeEventListener infoCakeListener = new InfoListener();
            addCakeEventHandler.add(infoCakeListener);

            // Add manufacturer (Hersteller) event
            AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
            AddHerstellerEventListener addHerstellerEventListener = new Listener(vendingMachine);
            addHerstellerEventHandler.add(addHerstellerEventListener);
            AddHerstellerEventListener infoHerstellerListener = new InfoListener();
            addHerstellerEventHandler.add(infoHerstellerListener);

            AddMode addMode = new AddMode(addHerstellerEventHandler, addCakeEventHandler);

            // Remove cake event
            RemoveCakeEventHandler removeCakeEventHandler = new RemoveCakeEventHandler();
            RemoveCakeEventListener removeCakeEventListener = new Listener(vendingMachine);
            removeCakeEventHandler.add(removeCakeEventListener);
            RemoveCakeEventListener infoRemoveCakeListener = new InfoListener();
            removeCakeEventHandler.add(infoRemoveCakeListener);

            // Remove manufacturer (Hersteller) event
            RemoveHerstellerEventHandler removeHerstellerEventHandler = new RemoveHerstellerEventHandler();
            RemoveHerstellerEventListener removeHerstellerEventListener = new Listener(vendingMachine);
            removeHerstellerEventHandler.add(removeHerstellerEventListener);
            RemoveHerstellerEventListener infoRemoveHerstellerListener = new InfoListener();
            removeHerstellerEventHandler.add(infoRemoveHerstellerListener);

            RemoveMode removeMode = new RemoveMode(removeHerstellerEventHandler, removeCakeEventHandler);

            // Update inspection date event
            InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();
            InspectionEventListener inspectionEventListener = new Listener(vendingMachine);
            inspectionEventHandler.add(inspectionEventListener);
            InspectionEventListener infoInspectionEventListener = new InfoListener();
            inspectionEventHandler.add(infoInspectionEventListener);

            UpdateMode updateMode = new UpdateMode(inspectionEventHandler);

            // Print allergies event
            PrintAllergiesEventHandler printAllergiesEventHandler = new PrintAllergiesEventHandler();
            PrintAllergiesEventListener printAllergiesEventListener = new Listener(vendingMachine);
            printAllergiesEventHandler.add(printAllergiesEventListener);
            PrintAllergiesEventListener infoPrintAllergiesEventListener = new InfoListener();
            printAllergiesEventHandler.add(infoPrintAllergiesEventListener);

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

            PrintMode printMode = new PrintMode(printHerstellerEventHandler, printCakeEventHandler, printAllergiesEventHandler);

            // Save vending machine
            SaveVendingMachineEventHandler saveVendingMachineEventHandler = new SaveVendingMachineEventHandler();
            SaveVendingMachineEventListener saveVendingMachineEventListener = new Listener(vendingMachine);
            saveVendingMachineEventHandler.add(saveVendingMachineEventListener);
            SaveVendingMachineEventListener infoSaveVendingMachineEventListener = new InfoListener();
            saveVendingMachineEventHandler.add(infoSaveVendingMachineEventListener);

            SerializationMode serializationMode = new SerializationMode(saveVendingMachineEventHandler);

            CLIcontroller cliController = new CLIcontroller(addMode, removeMode, updateMode, printMode, serializationMode);
            cliController.start();
        }
    }
}

