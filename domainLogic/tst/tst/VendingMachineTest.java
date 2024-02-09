package tst;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineTest {

    private VendingMachine vendingMachine;
    private KuchenImpl kuchen1;
    private KuchenImpl kuchen2;
    private HerstellerImpl hersteller1;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine(2); // Set capacity to 2
        hersteller1 = new HerstellerImpl("Hersteller1");
        kuchen1 = new ObstkuchenImpl("Obstkuchen", hersteller1, Set.of(Allergen.Sesamsamen), 200, Duration.ofDays(6),
                BigDecimal.valueOf(20), "Apfel");
        kuchen2 = new ObstkuchenImpl("Obstkuchen", hersteller1, Set.of(Allergen.Haselnuss), 100, Duration.ofDays(6),
                BigDecimal.valueOf(30), "Birne");
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
        assertTrue(vendingMachine.removeItem(1));
        assertFalse(vendingMachine.removeItem(2)); // Not in vending machine
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
        vendingMachine.updateInspectionDate(2);

        Date currentDate = new Date(System.currentTimeMillis());
        for (KuchenImpl kuchen : vendingMachine.listItems()) {
            assertNotNull(kuchen.getInspektionsdatum());
            assertEquals(currentDate, kuchen.getInspektionsdatum());
        }
    }
}
