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

    // Test 1 checks if the file is created
    @Test
    public void testSerializationJOSCreateFile() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
        JOS jos = new JOS(vendingMachine);
        jos.serialisierenJOS();
        File file = new File("/serialization/src/saveModeJOS/saveMode.ser");
        assertTrue(file.exists());
    }

    // Test 2 verifies if the saved object is an instance of the vending machine
    @Test
    void testSerializationJOSInstance() throws IOException, ClassNotFoundException {
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

    // Test 3 verifies that the capacity is the same between the vending machine and the serialized object
    // reassuring that the vending machine is correctly saved
    @Test
    public void testSerializationJOSCapacity() throws IOException, ClassNotFoundException {
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

    // Test 4 verifies that a file is deserialized
    @Test
    public void testDeserializationJOSFile() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);
        jos.serialisierenJOS();
        VendingMachine deserializedVendingMachine = jos.deserialisierenJOS();
        assertTrue(Objects.nonNull(deserializedVendingMachine));
    }

    // Test 5 verifies that the capacity is the same between the vending machine and the deserialized object
    // reassuring that the vending machine is correctly loaded
    @Test
    public void testDeserializationJOSCapacity() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);

        JOS jos = new JOS(vendingMachine);

        jos.serialisierenJOS();

        VendingMachine deserializedVendingMachine = jos.deserialisierenJOS();

        assertEquals(vendingMachine.getCapacity(), deserializedVendingMachine.getCapacity());
    }
}