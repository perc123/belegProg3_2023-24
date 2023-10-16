package mockito;

import administration.VendingMachine;
import cakes.KuchenImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class VendingMachineTestMockito {

    private VendingMachine vendingMachine;

    @Mock
    private KuchenImpl kuchen1;

    @Mock
    private KuchenImpl kuchen2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        vendingMachine = new VendingMachine(2);
    }

    @Test
    void testAddItem() {
        when(kuchen1.getNaehrwert()).thenReturn(200);
        when(kuchen2.getNaehrwert()).thenReturn(300);

        assertTrue(vendingMachine.addItem(kuchen1));
        assertTrue(vendingMachine.addItem(kuchen2));
        assertFalse(vendingMachine.addItem(kuchen1)); // Vending machine is full
    }

    @Test
    void testRemoveItem() {
        when(kuchen1.getNaehrwert()).thenReturn(200);

        vendingMachine.addItem(kuchen1);
        assertTrue(vendingMachine.removeItem(kuchen1));
        assertFalse(vendingMachine.removeItem(kuchen1)); // Not in vending machine
    }

    @Test
    void testListItems() {
        when(kuchen1.getNaehrwert()).thenReturn(200);
        when(kuchen2.getNaehrwert()).thenReturn(300);

        vendingMachine.addItem(kuchen1);
        vendingMachine.addItem(kuchen2);

        List<KuchenImpl> items = vendingMachine.listItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(kuchen1));
        assertTrue(items.contains(kuchen2));
    }

    @Test
    void testUpdateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        when(kuchen1.getInspektionsdatum()).thenReturn(currentDate);
        when(kuchen2.getInspektionsdatum()).thenReturn(currentDate);

        vendingMachine.addItem(kuchen1);
        vendingMachine.addItem(kuchen2);
        vendingMachine.updateInspectionDate();

        for (KuchenImpl kuchen : vendingMachine.listItems()) {
            verify(kuchen).setInspektionsdatum(currentDate);
        }
    }
}
