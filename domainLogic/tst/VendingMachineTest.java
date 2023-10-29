import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {

    private VendingMachine vendingMachine;
    private KuchenImpl kuchen1;
    private KuchenImpl kuchen2;
    private HerstellerImpl hersteller1;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine(2); // Set capacity to 2
        kuchen1 = new KuchenImpl(hersteller1, Set.of(Allergen.Gluten),200, Duration.ofDays(7), BigDecimal.valueOf(5.0), new Date());
        kuchen2 = new KuchenImpl(hersteller1, Set.of(Allergen.Erdnuss), 300, Duration.ofDays(10), BigDecimal.valueOf(7.0), new Date());
    }

    @Test
    void testAddItem() {
        assertTrue(vendingMachine.addItem(kuchen1, hersteller1));
        assertTrue(vendingMachine.addItem(kuchen2, hersteller1));
        assertFalse(vendingMachine.addItem(kuchen1, hersteller1)); // Vending machine is full
    }

    @Test
    void testRemoveItem() {
        vendingMachine.addItem(kuchen1, hersteller1);
        assertTrue(vendingMachine.removeItem(kuchen1));
        assertFalse(vendingMachine.removeItem(kuchen1)); // Not in vending machine
    }

    @Test
    void testListItems() {
        vendingMachine.addItem(kuchen1, hersteller1);
        vendingMachine.addItem(kuchen2, hersteller1);
        List<KuchenImpl> items = vendingMachine.listItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(kuchen1));
        assertTrue(items.contains(kuchen2));
    }

    @Test
    void testUpdateInspectionDate() {
        vendingMachine.addItem(kuchen1, hersteller1);
        vendingMachine.addItem(kuchen2, hersteller1);
        vendingMachine.updateInspectionDate();

        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        for (KuchenImpl kuchen : vendingMachine.listItems()) {
            assertEquals(currentDate, kuchen.getInspektionsdatum());
        }
    }
}





