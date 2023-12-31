package mockito;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KuchenImpl;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendingMachineTestMockito {

    private VendingMachine vendingMachine;

    @Mock
    private HerstellerImpl mockHersteller;

    @Mock
    private KuchenImpl mockObstkuchen1;

    @Mock
    private KuchenImpl mockObstkuchen2;

    @Captor
    private ArgumentCaptor<Date> dateCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendingMachine = new VendingMachine(2);

        when(mockHersteller.getName()).thenReturn("Hersteller1");

        when(mockObstkuchen1.getHersteller()).thenReturn(mockHersteller);
        when(mockObstkuchen1.getKuchenTyp()).thenReturn("Obstkuchen");
        when(mockObstkuchen1.getAllergene()).thenReturn(Set.of(Allergen.Sesamsamen));
        when(mockObstkuchen1.getNaehrwert()).thenReturn(200);
        when(mockObstkuchen1.getHaltbarkeit()).thenReturn(Duration.ofDays(6));
        when(mockObstkuchen1.getPreis()).thenReturn(BigDecimal.valueOf(20));

        when(mockObstkuchen2.getHersteller()).thenReturn(mockHersteller);
        when(mockObstkuchen2.getKuchenTyp()).thenReturn("Obstkuchen");
        when(mockObstkuchen2.getAllergene()).thenReturn(Set.of(Allergen.Haselnuss));
        when(mockObstkuchen2.getNaehrwert()).thenReturn(100);
        when(mockObstkuchen2.getHaltbarkeit()).thenReturn(Duration.ofDays(6));
        when(mockObstkuchen2.getPreis()).thenReturn(BigDecimal.valueOf(30));
    }

    @Test
    void testAddItem() {
        assertTrue(vendingMachine.addItem(mockObstkuchen1, mockHersteller));
        assertTrue(vendingMachine.addItem(mockObstkuchen2, mockHersteller));
        assertFalse(vendingMachine.addItem(mockObstkuchen1, mockHersteller)); // Vending machine is full
    }

    @Test
    void testRemoveItem() {
        vendingMachine.addItem(mockObstkuchen2, mockHersteller);
        assertTrue(vendingMachine.removeItem(mockObstkuchen2.getFachnummer()));
        assertFalse(vendingMachine.removeItem(mockObstkuchen1.getFachnummer()));
    }


    @Test
    void testListItems() {
        vendingMachine.addItem(mockObstkuchen1, mockHersteller);
        vendingMachine.addItem(mockObstkuchen2, mockHersteller);
        assertEquals(2, vendingMachine.listItems().size());
    }

    @Test
    void testUpdateInspectionDate() {
        vendingMachine.addItem(mockObstkuchen1, mockHersteller);
        vendingMachine.addItem(mockObstkuchen2, mockHersteller);
        vendingMachine.updateInspectionDate(1);

        Date currentDate = new Date(System.currentTimeMillis());
        for (KuchenImpl kuchen : vendingMachine.listItems()) {
            assertNotNull(kuchen.getInspektionsdatum());
            assertEquals(currentDate, kuchen.getInspektionsdatum());
        }
    }
}
