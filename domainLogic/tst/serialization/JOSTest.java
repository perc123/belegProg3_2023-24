package serialization;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;

import org.junit.jupiter.api.Test;
import saveJOS.JOS;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JOSTest {

    // Test um die Erstellung der Datei zu pruefen
    @Test
    public void testSerialisierenJOSDateiErstellung() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JOS jos = new JOS(vendingMachine);
        jos.serialisierenJOS();
        File file = new File("/serialization/src/saveModeJOS/saveMode.ser");
        assertTrue(file.exists());
    }



    // Test überprueft, ob das gelesene Objekt eine Instanz von Model ist
    @Test
    void serialisierenJOSInstanz() throws IOException, ClassNotFoundException {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);

        jos.serialisierenJOS();

        File file = new File("/serialization/src/saveModeJOS/saveMode.ser");
        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            VendingMachine serializedVendingMachine = (VendingMachine) objectInputStream.readObject();

            assertNotNull(serializedVendingMachine);
        }
    }

    // Test prueft ob die Eigenschaften des gelesenen Modells übereinstimmen
    @Test
    public void testSerialisierenJOSDateiInhalt() throws IOException, ClassNotFoundException {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);
        jos.serialisierenJOS();

        File file = new File("/serialization/src/saveModeJOS/saveMode.ser");
        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            VendingMachine serializedVendingMachine = (VendingMachine) objectInputStream.readObject();

            assertEquals(vendingMachine.getCapacity(), serializedVendingMachine.getCapacity());
        }
    }

    // Test ueberprueft ob eine Vorhandene Datei deserialsiert wird
    @Test
    public void testDeserialisierenJOSDateiVorhanden() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);
        jos.serialisierenJOS();
        VendingMachine deserializedVendingMachine = jos.deserialisierenJOS();
        assertTrue(Objects.nonNull(deserializedVendingMachine));
    }

    @Test
    public void testDeserialisierenJOSRichtigeEigenschaften() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);

        jos.serialisierenJOS();

        VendingMachine deserializedVendingMachine = jos.deserialisierenJOS();

        assertEquals(vendingMachine.getCapacity(), deserializedVendingMachine.getCapacity());
    }





}