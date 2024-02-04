package saveJBP;

import administration.VendingMachine;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JBPTest {

    @Test
    void serialisierenJBP() {
        VendingMachine vendingMachine = new VendingMachine();
        JBP jbp = new JBP(vendingMachine);

        // Test serialization
        assertDoesNotThrow(() -> {
            jbp.serialisierenJBP();
            File file = new File("src/saveModeJBP/saveModelJBP.xml");
            assertTrue(file.exists());
        }, "Serialization should not throw any exception");
    }

    @Test
    void deserialisierenJBP() {
        //JBP jbp = new JBP(null); // Pass null for vending machine since it will be loaded

        // Test deserialization
        VendingMachine vendingMachine = new VendingMachine();
        JBP jbp = new JBP(vendingMachine);
        jbp.serialisierenJBP();
        VendingMachine newvending = jbp.deserialisierenJBP();
        assertTrue(Objects.nonNull(newvending));
        /*VendingMachine loadedVendingMachine = assertDoesNotThrow(() -> {
            return jbp.deserialisierenJBP();
        }, "Deserialization should not throw any exception");

        assertNotNull(loadedVendingMachine, "Deserialized vending machine should not be null");*/
    }

    private VendingMachine createSampleVendingMachine() {
        // Implement this method to create a sample vending machine with some data
        // You can add items to the vending machine for testing
        VendingMachine vendingMachine = new VendingMachine();
        return vendingMachine;
    }
}
