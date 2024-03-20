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

    // Test 1 checks if the file is created
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

    // Test 2 verifies if the saved object is an instance of the vending machine
    @Test
    void testSerializationJBPInstance() throws IOException, ClassNotFoundException {
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

    // Test 3 verifies that the capacity is the same between the vending machine and the serialized object
    // reassuring that the vending machine is correctly saved
    @Test
    public void testSerializationJBPCapacity() throws IOException, ClassNotFoundException {
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

    // Test 4 verifies that a file is deserialized
    @Test
    public void testDeserializationJBPFile() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);
        jbp.serialisierenJBP();
        VendingMachine deserializedVendingMachine = jbp.deserialisierenJBP();
        assertTrue(Objects.nonNull(deserializedVendingMachine));
    }

    // Test 5 verifies that the capacity is the same between the vending machine and the deserialized object
    // reassuring that the vending machine is correctly loaded
    @Test
    public void testDeserializationJBPCapacity() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JBP jbp = new JBP(vendingMachine);

        jbp.serialisierenJBP();

        VendingMachine deserializedVendingMachine = jbp.deserialisierenJBP();

        assertEquals(vendingMachine.getCapacity(), deserializedVendingMachine.getCapacity());
    }
}