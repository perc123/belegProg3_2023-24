package serialization;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;

import org.junit.jupiter.api.Test;
import saveJBP.JBP;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JBPTest {



    // Test um die Erstellung der Datei zu pruefen
    @Test
    public void testSerializationJBPCreateFile() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);
        jbp.serialisierenJBP();
        File file = new File("/serialization/src/saveModeJBP/");
        assertTrue(file.exists());
    }



    // Test überprueft, ob das gelesene Objekt eine Instanz von Model ist
    @Test
    void serializationJBPInstance() throws IOException, ClassNotFoundException {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);

        jbp.serialisierenJBP();

        File file = new File("/serialization/src/saveModeJBP/saveMode.ser");
        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
             VendingMachine serializedVendingMachine = (VendingMachine) objectInputStream.readObject();

             assertNotNull(serializedVendingMachine);
        }
    }

    // Test prueft ob die Eigenschaften des gelesenen Modells übereinstimmen
    @Test
    public void testSerialisierenJBPDateiInhalt() throws IOException, ClassNotFoundException {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);
        jbp.serialisierenJBP();

        File file = new File("/serialization/src/saveModeJBP/saveMode.ser");
        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            VendingMachine serializedVendingMachine = (VendingMachine) objectInputStream.readObject();

            assertEquals(vendingMachine.getCapacity(), serializedVendingMachine.getCapacity());
        }
    }

    // Test ueberprueft ob eine Vorhandene Datei deserialsiert wird
    @Test
    public void testDeserialisierenJBPDateiVorhanden() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);
        jbp.serialisierenJBP();
        VendingMachine deserializedVendingMachine = jbp.deserialisierenJBP();
        assertTrue(Objects.nonNull(deserializedVendingMachine));
    }

    @Test
    public void testDeserialisierenJBPRichtigeEigenschaften() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);

        jbp.serialisierenJBP();

        VendingMachine deserializedVendingMachine = jbp.deserialisierenJBP();

        assertEquals(vendingMachine.getCapacity(), deserializedVendingMachine.getCapacity());
    }


}