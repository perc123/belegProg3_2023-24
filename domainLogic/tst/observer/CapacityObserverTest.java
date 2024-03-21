package observer;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static kuchen.Allergen.Erdnuss;
import static org.junit.jupiter.api.Assertions.*;

class CapacityObserverTest {

    String kuchenTyp;
    HerstellerImpl hersteller1;
    BigDecimal preis;
    int naherwerte;
    Duration haltbarkeit;
    List<Allergen> allergens;
    String sorte;
    VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        kuchenTyp = "Kremkuchen";
        hersteller1 = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("3.20");
        naherwerte = 123;
        haltbarkeit = Duration.ofDays(3);
        allergens = List.of(Erdnuss);
        sorte = "Butter";

    }

    // Testet den Observer, ob er bei 90% auslastung eine Meldung von sich gibt
    @Test
    void testKapazitaetsObserver() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
        vendingMachine.add(capacityObserver);

        KuchenImpl kremkuchenTest = new KremkuchenImpl(kuchenTyp, hersteller1, preis, naherwerte, haltbarkeit, allergens, sorte);
        vendingMachine.getHerstellerList().add(hersteller1);

        // Umlenken der System.out-Ausgabe auf einen ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Fülle den Automat bis zur 90% Auslastung
        int i=0;
        while(i<9){
            vendingMachine.addItem(kremkuchenTest);
            i++;
        }

        // Überprüfen, ob der Observer eine Warnung ausgegeben hat
        String expectedOutput = "ACHTUNG: Kapazitaet zu 90% ausgelastet!\n";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    // Testet den Observer, ob er bei unter 90% auslastung keine Meldung von sich gibt
    @Test
    void testKapazitaetsObserverKeineAusgabe() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
        vendingMachine.add(capacityObserver);

        KuchenImpl kremkuchenTest = new KremkuchenImpl(kuchenTyp, hersteller1, preis, naherwerte, haltbarkeit, allergens, sorte);
        vendingMachine.getHerstellerList().add(hersteller1);

        // Umlenken der System.out-Ausgabe auf einen ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        vendingMachine.addItem(kremkuchenTest);

        // Überprüfen, ob der Observer eine Warnung ausgegeben hat
        String expectedOutput = "";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }


}