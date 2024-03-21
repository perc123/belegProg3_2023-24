package cli;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import commands.*;
import infrastructure.AddCake.AddCakeEventHandler;
import infrastructure.AddManufacturer.AddHerstellerEventHandler;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import infrastructure.InspectionsDate.InspectionEventHandler;
import infrastructure.PrintAllergies.PrintAllergiesEventHandler;
import infrastructure.PrintCakes.PrintCakeEventHandler;
import infrastructure.PrintManufacturers.PrintHerstellerEventHandler;
import infrastructure.PrintManufacturers.PrintHerstellerEventListener;
import infrastructure.RemoveCake.RemoveCakeEventHandler;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventHandler;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventHandler;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventListener;
import listener.InfoListener;
import listener.Listener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.SequenceInputStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CLIcontrollerTest {

    private CLIcontroller clIcontroller;
    private VendingMachine vendingMachine;


    @BeforeEach
    void setUp() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        //Kuchen Einfuegen Event ohne einhaengen der Handler und Listener
        AddCakeEventHandler addCakeEventHandler = new AddCakeEventHandler();
        //Hersteller Einfuegen Event
        AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
        AddHerstellerEventListener addHerstellerEventListener = new Listener(vendingMachine);
        addHerstellerEventHandler.add(addHerstellerEventListener);
        AddHerstellerEventListener infoListenerHersteller = new InfoListener();
        addHerstellerEventHandler.add(infoListenerHersteller);

        AddMode addMode = new AddMode(addHerstellerEventHandler, addCakeEventHandler);

        //Kuchen Loeschen Event
        RemoveCakeEventHandler removeCakeEventHandler = new RemoveCakeEventHandler();
        //Hersteller Loeschen Event
        RemoveHerstellerEventHandler removeHerstellerEventHandler = new RemoveHerstellerEventHandler();

        RemoveMode removeMode = new RemoveMode(removeHerstellerEventHandler, removeCakeEventHandler);

        //Inspektionsdatum setzen Event
        InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();

        UpdateMode updateMode = new UpdateMode(inspectionEventHandler);

        //Allergene anzeigen Event
        PrintAllergiesEventHandler printAllergiesEventHandler = new PrintAllergiesEventHandler();
        //Kuchen anzeigen Event
        PrintCakeEventHandler printCakeEventHandler = new PrintCakeEventHandler();
        //Hersteller anzeigen Event
        PrintHerstellerEventHandler printHerstellerEventHandler = new PrintHerstellerEventHandler();
        PrintHerstellerEventListener printHerstellerEventListener = new Listener(vendingMachine);
        printHerstellerEventHandler.add(printHerstellerEventListener);


        PrintMode printMode = new PrintMode(printHerstellerEventHandler, printCakeEventHandler, printAllergiesEventHandler);

        //Mode speichern
        SaveVendingMachineEventHandler saveVendingMachineEventHandler = new SaveVendingMachineEventHandler();
        SaveVendingMachineEventListener saveVendingMachineEventListener = new Listener(vendingMachine);
        saveVendingMachineEventHandler.add(saveVendingMachineEventListener);

        SerializationMode serializationMode = new SerializationMode(saveVendingMachineEventHandler);

        clIcontroller = new CLIcontroller(addMode, removeMode, updateMode, printMode, serializationMode);
    }


    /*
    CLI-Test der überprueft ob ein Hersteller hinzugefuegt werden kann mit der Eingabe ":c"
    und der Eingabe "Hersteller"
     */
    @Test
    void testHerstellerEinfuegen() {
        // Hersteller über das CLI einfuegen
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(":c\n".getBytes());
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream("Hersteller\n".getBytes());
        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream1, inputStream2);

        // Setzt die beiden Eingaben ins System (:c und anschließend Hersteller)
        System.setIn(sequenceInputStream);

        try {
            clIcontroller.start();
        } catch (Exception e) {
            /*
            beim zweiten Durchlaufen der hauptCLI.start()
            Methode wird eine Exception geworfen da
            keine weitere Eingabe erfolgt. Damit der Test nicht
            Abbricht wird die Exception abgefangen
             */
        }

        // Herstellerliste abrufen
        HerstellerImpl hersteller = vendingMachine.getHerstellerList().get(0);

        // Überprüfen, ob der Hersteller korrekt eingefuegt wurde
        assertEquals("Hersteller", hersteller.getName());
    }


    /*
    CLI-Test der ueberprueft ob ein Hersteller hinzugefuegt werden kann mit der Eingabe ":c"
    und der Eingabe "Hersteller Test"
     */
    @Test
    void testHerstellerEinfuegenMitLeertaste() {
        // Hersteller über das CLI einfuegen
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(":c\n".getBytes());
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream("Hersteller Test\n".getBytes());
        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream1, inputStream2);

        System.setIn(sequenceInputStream);

        try {
            clIcontroller.start();
        } catch (Exception e) {
            /*
            beim zweiten Durchlaufen der hauptCLI.start()
            Methode wird eine Exception geworfen da
            keine weitere Eingabe erfolgt. Damit der Test nicht
            Abbricht wird die Exception abgefangen
             */
        }

        // Herstellerliste abrufen
        HerstellerImpl hersteller = vendingMachine.getHerstellerList().get(0);

        // Ueberpruefen, ob der Hersteller korrekt eingefügt wurde
        assertEquals("Hersteller Test", hersteller.getName());
    }

    /*
    CLI-Test der das Anzeigen von Herstellern ueberprueft
    */
    @Test
    void testHerstellerAnzeigen() {
        HerstellerImpl hersteller = new HerstellerImpl("Hersteller1");
        vendingMachine.getHerstellerList().add(hersteller);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(":r\n".getBytes());
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream("Hersteller\n".getBytes());
        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream1, inputStream2);
        System.setIn(sequenceInputStream);

        try {
            clIcontroller.start();
        } catch (Exception e) {
            /*
            beim zweiten Durchlaufen der hauptCLI.start()
            Methode wird eine Exception geworfen da
            keine weitere Eingabe erfolgt. Damit der Test nicht
            Abbricht wird die Exception abgefangen
             */
        }

        System.setOut(System.out);

        String expectedOutput = "Manufacturer name: Hersteller1 | Number of cakes: 0";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

}