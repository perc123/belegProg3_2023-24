import TCP.ServerTCP;
import UDP.ServerUDP;
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
import listener.NetListenerTCP;
import listener.NetListenerUDP;
import verwaltung.Hersteller;

import java.io.IOException;

import java.util.LinkedList;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length < 2) {
            System.err.println("Usage: java Server <TCP/UDP> <capacity>");
            System.exit(1);
        }

        int capacity = Integer.parseInt(args[1]);
        LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(capacity, kuchenLinkedList, herstellerLinkedList);

        if (args[0].equalsIgnoreCase("TCP")) {
            ServerTCP serverTCP = new ServerTCP();

            // Add cake event
            AddCakeEventHandler addCakeEventHandler = new AddCakeEventHandler();
            AddCakeEventListener addCakeEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            addCakeEventHandler.add(addCakeEventListener);

            // Add manufacturer (Hersteller) event
            AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
            AddHerstellerEventListener addHerstellerEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            addHerstellerEventHandler.add(addHerstellerEventListener);

            AddMode addMode = new AddMode(addHerstellerEventHandler, addCakeEventHandler);
            serverTCP.setAddMode(addMode);

            // Remove cake event
            RemoveCakeEventHandler removeCakeEventHandler = new RemoveCakeEventHandler();
            RemoveCakeEventListener removeCakeEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            removeCakeEventHandler.add(removeCakeEventListener);

            // Remove manufacturer (Hersteller) event
            RemoveHerstellerEventHandler removeHerstellerEventHandler = new RemoveHerstellerEventHandler();
            RemoveHerstellerEventListener removeHerstellerEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            removeHerstellerEventHandler.add(removeHerstellerEventListener);

            RemoveMode removeMode = new RemoveMode(removeHerstellerEventHandler, removeCakeEventHandler);
            serverTCP.setRemoveMode(removeMode);

            // Update inspection date event
            InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();
            InspectionEventListener inspectionEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            inspectionEventHandler.add(inspectionEventListener);

            UpdateMode updateMode = new UpdateMode(inspectionEventHandler);
            serverTCP.setUpdateMode(updateMode);

            // Print allergies event
            PrintAllergiesEventHandler printAllergiesEventHandler = new PrintAllergiesEventHandler();
            PrintAllergiesEventListener printAllergiesEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            printAllergiesEventHandler.add(printAllergiesEventListener);

            // Print cake event
            PrintCakeEventHandler printCakeEventHandler = new PrintCakeEventHandler();
            PrintCakeEventListener printCakeEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            printCakeEventHandler.add(printCakeEventListener);

            // Print manufacturer event
            PrintHerstellerEventHandler printHerstellerEventHandler = new PrintHerstellerEventHandler();
            PrintHerstellerEventListener printHerstellerEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            printHerstellerEventHandler.add(printHerstellerEventListener);

            PrintMode printMode = new PrintMode(printHerstellerEventHandler, printCakeEventHandler, printAllergiesEventHandler);
            serverTCP.setPrintMode(printMode);

            // Save vending machine
            SaveVendingMachineEventHandler saveVendingMachineEventHandler = new SaveVendingMachineEventHandler();
            SaveVendingMachineEventListener saveVendingMachineEventListener = new NetListenerTCP(vendingMachine, serverTCP);
            saveVendingMachineEventHandler.add(saveVendingMachineEventListener);

            SerializationMode serializationMode = new SerializationMode(saveVendingMachineEventHandler);

            serverTCP.setSerializationMode(serializationMode);

            serverTCP.start();
        }
        else if (args[0].equalsIgnoreCase("UDP")) {
            ServerUDP serverUDP = new ServerUDP();

            // Add cake event
            AddCakeEventHandler addCakeEventHandler = new AddCakeEventHandler();
            AddCakeEventListener addCakeEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            addCakeEventHandler.add(addCakeEventListener);

            // Add manufacturer (Hersteller) event
            AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
            AddHerstellerEventListener addHerstellerEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            addHerstellerEventHandler.add(addHerstellerEventListener);

            AddMode addMode = new AddMode(addHerstellerEventHandler, addCakeEventHandler);
            serverUDP.setAddMode(addMode);

            // Remove cake event
            RemoveCakeEventHandler removeCakeEventHandler = new RemoveCakeEventHandler();
            RemoveCakeEventListener removeCakeEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            removeCakeEventHandler.add(removeCakeEventListener);

            // Remove manufacturer (Hersteller) event
            RemoveHerstellerEventHandler removeHerstellerEventHandler = new RemoveHerstellerEventHandler();
            RemoveHerstellerEventListener removeHerstellerEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            removeHerstellerEventHandler.add(removeHerstellerEventListener);

            RemoveMode removeMode = new RemoveMode(removeHerstellerEventHandler, removeCakeEventHandler);
            serverUDP.setRemoveMode(removeMode);

            // Update inspection date event
            InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();
            InspectionEventListener inspectionEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            inspectionEventHandler.add(inspectionEventListener);

            UpdateMode updateMode = new UpdateMode(inspectionEventHandler);
            serverUDP.setUpdateMode(updateMode);

            // Print allergies event
            PrintAllergiesEventHandler printAllergiesEventHandler = new PrintAllergiesEventHandler();
            PrintAllergiesEventListener printAllergiesEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            printAllergiesEventHandler.add(printAllergiesEventListener);

            // Print cake event
            PrintCakeEventHandler printCakeEventHandler = new PrintCakeEventHandler();
            PrintCakeEventListener printCakeEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            printCakeEventHandler.add(printCakeEventListener);

            // Print manufacturer event
            PrintHerstellerEventHandler printHerstellerEventHandler = new PrintHerstellerEventHandler();
            PrintHerstellerEventListener printHerstellerEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            printHerstellerEventHandler.add(printHerstellerEventListener);

            PrintMode printMode = new PrintMode(printHerstellerEventHandler, printCakeEventHandler, printAllergiesEventHandler);
            serverUDP.setPrintMode(printMode);

            // Save vending machine
            SaveVendingMachineEventHandler saveVendingMachineEventHandler = new SaveVendingMachineEventHandler();
            SaveVendingMachineEventListener saveVendingMachineEventListener = new NetListenerUDP(vendingMachine, serverUDP);
            saveVendingMachineEventHandler.add(saveVendingMachineEventListener);

            SerializationMode serializationMode = new SerializationMode(saveVendingMachineEventHandler);

            serverUDP.setSerializationMode(serializationMode);

            serverUDP.start();
        }
    }
}
