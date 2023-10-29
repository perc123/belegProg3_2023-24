package mockito;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VendingMachineTestMockito {

    private VendingMachine vendingMachine;
    private HerstellerImpl hersteller1;

    @Mock
    private KuchenImpl mockKuchenWithLowNaehrwert;

    @Mock
    private KuchenImpl mockKuchenWithHighNaehrwert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vendingMachine = new VendingMachine(2);
    }

    @Test
    void testAddItem_WhenVendingMachineIsNotFull() {
        when(mockKuchenWithLowNaehrwert.getNaehrwert()).thenReturn(200);
        when(mockKuchenWithHighNaehrwert.getNaehrwert()).thenReturn(300);

        assertTrue(vendingMachine.addItem(mockKuchenWithLowNaehrwert, hersteller1));
        assertTrue(vendingMachine.addItem(mockKuchenWithHighNaehrwert, hersteller1));
    }

    @Test
    void testAddItem_WhenVendingMachineIsFull() {
        when(mockKuchenWithLowNaehrwert.getNaehrwert()).thenReturn(200);
        when(mockKuchenWithHighNaehrwert.getNaehrwert()).thenReturn(300);

        assertTrue(vendingMachine.addItem(mockKuchenWithLowNaehrwert,hersteller1));
        assertTrue(vendingMachine.addItem(mockKuchenWithHighNaehrwert,hersteller1));
        assertFalse(vendingMachine.addItem(mockKuchenWithLowNaehrwert,hersteller1)); // Vending machine is full
    }

    @Test
    void testRemoveItem() {
        when(mockKuchenWithLowNaehrwert.getNaehrwert()).thenReturn(200);

        vendingMachine.addItem(mockKuchenWithLowNaehrwert,hersteller1);
        assertTrue(vendingMachine.removeItem(mockKuchenWithLowNaehrwert));
        assertFalse(vendingMachine.removeItem(mockKuchenWithLowNaehrwert)); // Not in vending machine
    }

    @Test
    void testListItems() {
        when(mockKuchenWithLowNaehrwert.getNaehrwert()).thenReturn(200);
        when(mockKuchenWithHighNaehrwert.getNaehrwert()).thenReturn(300);

        vendingMachine.addItem(mockKuchenWithLowNaehrwert,hersteller1);
        vendingMachine.addItem(mockKuchenWithHighNaehrwert,hersteller1);

        List<KuchenImpl> items = vendingMachine.listItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(mockKuchenWithLowNaehrwert));
        assertTrue(items.contains(mockKuchenWithHighNaehrwert));
    }

    @Test
    void testUpdateInspectionDate() {
        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        when(mockKuchenWithLowNaehrwert.getInspektionsdatum()).thenReturn(currentDate);
        when(mockKuchenWithHighNaehrwert.getInspektionsdatum()).thenReturn(currentDate);

        vendingMachine.addItem(mockKuchenWithLowNaehrwert,hersteller1);
        vendingMachine.addItem(mockKuchenWithHighNaehrwert,hersteller1);
        vendingMachine.updateInspectionDate();

        for (KuchenImpl kuchen : vendingMachine.listItems()) {
            verify(kuchen).setInspektionsdatum(currentDate);
        }
    }
}
