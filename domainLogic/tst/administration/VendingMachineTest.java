package administration;

import static kuchen.Allergen.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import kuchen.Allergen;
import kuchen.Kremkuchen;
import kuchen.Obstkuchen;
import kuchen.Obsttorte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineTest {

    HerstellerImpl hersteller1;
    BigDecimal preis;
    int naherwerte;
    Duration haltbarkeit;
    List<Allergen> allergens;
    String sorte;
    String sorteZwei;
    VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        hersteller1 = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("2.20");
        naherwerte = 243;
        haltbarkeit = Duration.ofDays(3);
        allergens = List.of(Erdnuss);
        sorte = "Butter";
        sorteZwei = "Erdbeere";
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        vendingMachine = new VendingMachine(10, kuchenLinkedList, herstellerLinkedList);
    }

    // Test 1 verifies that a Hersteller can be added successfully
    @Test
    void testAddHersteller() {
        HerstellerImpl h = new HerstellerImpl("hersteller1");
        assertTrue(vendingMachine.addHersteller(h));
    }

    // Test 2 verifies that is it impossible to add the same Hersteller name more than one time
    @Test
    void testAddSameHersteller() {
        HerstellerImpl h = new HerstellerImpl("hersteller1");
        vendingMachine.addHersteller(h);
        assertFalse(vendingMachine.addHersteller(h));
    }

    // Test 3 **Mock** for adding a Hersteller
    @Test
    public void testAddHerstellerMock() {
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        assertTrue(vendingMachine.addHersteller(mockHersteller));
    }

    // Test 4 **Mock / Verify** for adding a Kremkuchen
    @Test
    public void testAddKremkuchenMock() {
        // Create Mock objects
        KremkuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Configure mock objects
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        assertTrue(vendingMachine.addItem(mockKremkuchen));

        // Verify that the getHersteller method was called
        verify(mockKremkuchen).getHersteller();
    }

    // Test 5 returns true if a Kremkuchen is successfully added
    @Test
    public void testAddKremkuchen() {
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        assertTrue(vendingMachine.addItem(kremkuchen));
    }

    // Test 6 returns false if a cake is added when the capacity is full
    @Test
    public void testFullCapacity() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();

        VendingMachine vendingMachine = new VendingMachine(2, kuchenLinkedList, herstellerLinkedList);

        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl kremkuchen2 = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl kremkuchen3 = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);

        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem(kremkuchen);
        vendingMachine.addItem(kremkuchen2);


        assertFalse(vendingMachine.addItem(kremkuchen3));
    }

    // Test 7 **Mock / Verify** for adding an Obstkuchen
    @Test
    public void testAddObstkuchenMock() {
        // Create Mock objects
        Obstkuchen mockObstkuchen = mock(ObstkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Configure Mock objects
        when(mockObstkuchen.getHersteller()).thenReturn(mockHersteller);

        assertTrue(vendingMachine.addItem((KuchenImpl) mockObstkuchen));
        // Verify that the getHersteller method was called
        verify(mockObstkuchen).getHersteller();
    }

    // Test 8 returns true if an Obstkuchen is successfully added
    @Test
    public void testAddObstkuchen() {
        Obstkuchen obstkuchen = new ObstkuchenImpl("Obstkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.getHerstellerList().add(hersteller1);
        assertTrue(vendingMachine.addItem((KuchenImpl) obstkuchen));
    }

    // Test 9 **Mock / Verify** for adding an Obsttorte
    @Test
    public void testAddObsttorteMock() {
        // Create Mock objects
        Obsttorte mockObsttorte = mock(ObsttorteImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Configure Mock objects
        when(mockObsttorte.getHersteller()).thenReturn(mockHersteller);

        assertTrue(vendingMachine.addItem((KuchenImpl) mockObsttorte));

        // Verify that the getHersteller method was called
        verify(mockObsttorte).getHersteller();
    }

    // Test 10 returns true if an Obsttorte is successfully added
    @Test
    public void testAddObsttorte() {
        Obsttorte obsttorte = new ObsttorteImpl("Obsttorte", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte, sorteZwei);
        vendingMachine.getHerstellerList().add(hersteller1);
        assertTrue(vendingMachine.addItem((KuchenImpl) obsttorte));
    }

    // Test 11 adding a cake with no manufacturer in the list should not be possible
    @Test
    public void testAddCakeNotPossible() {
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        assertFalse(vendingMachine.addItem(kremkuchen));
    }


    // Test 12 **Mock** for Inspection Date verification
    @Test
    public void testInspectionsDateMock() {
        // Create Mock objects
        KuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Configure Mock objects
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        // Add cake
        vendingMachine.addItem(mockKremkuchen);

        verify(mockKremkuchen).getInspektionsdatum();
    }

    // Test 13 **Mock** for correct tray number (Fachnummer) verification
    @Test
    public void testFachnummerMock() {
        KuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        vendingMachine.addItem(mockKremkuchen);

        // Verify if the tray number is given
        verify(mockKremkuchen).setFachnummer(1);
    }

    // Test 14 **Mock** for adding random cake (KuchenImpl) verification
    @Test
    public void testAddItemMock() {
        KuchenImpl mockCake = mock(KuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        when(mockCake.getHersteller()).thenReturn(mockHersteller);

        assertTrue(vendingMachine.addItem(mockCake));
    }

    // Test 15 the size of the cake list in vending machine
    @Test
    public void testIncrementInventory() {
        Kremkuchen kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem((KuchenImpl) kremkuchen);
        assertEquals(1, vendingMachine.getInventory().size());
    }

    // Test 16 verifies if the cake list contains the expected cakes
    @Test
    public void testInventoryContains() {
        Kremkuchen kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem((KuchenImpl) kremkuchen);
        List<KuchenImpl> kuchenList = vendingMachine.getInventory();
        assertTrue(kuchenList.contains(kremkuchen));
    }

    // Test 17 verifies the expected size of the manufacturer's (Hersteller) list
    @Test
    public void testIncrementHerstellerList() {
        vendingMachine.addHersteller(hersteller1);
        assertEquals(1, vendingMachine.getHerstellerList().size());
    }

    // Test 18 verifies the manufacturer's list for the expected manufacturer name
    @Test
    public void testHerstellerListContains() {
        HerstellerImpl hersteller2 = new HerstellerImpl("Hersteller 1");
        vendingMachine.addHersteller(hersteller2);
        List<HerstellerImpl> herstellerList = vendingMachine.getHerstellerList();
        assertTrue(herstellerList.contains(hersteller2));
    }

    // Test 19 **Mock** verifies the print Hersteller method
    @Test
    public void testPrintHersteller(){
        HerstellerImpl testHersteller = mock(HerstellerImpl.class);
        vendingMachine.getHerstellerList().add(testHersteller);
        List<HerstellerImpl> res = vendingMachine.printHersteller();
        // Should return true, since only one Hersteller is added
        assertEquals(1, res.size());
    }

    //Test 20 **Mock** to check the cake count of each manufacturer
    @Test
    public void testHerstellerCakeCount(){
        HerstellerImpl testHersteller = new HerstellerImpl("testHersteller");

        // Create Mock objects
        KuchenImpl testKremkuchen = mock(KremkuchenImpl.class);
        KuchenImpl testObstkuchen = mock(ObstkuchenImpl.class);
        KuchenImpl testObsttorte = mock(ObsttorteImpl.class);

        // Configure Mock objects
        when(testKremkuchen.getHersteller()).thenReturn(testHersteller);
        when(testObstkuchen.getHersteller()).thenReturn(testHersteller);
        when(testObsttorte.getHersteller()).thenReturn(testHersteller);

        // Add cakes and manufacturer
        vendingMachine.getHerstellerList().add(testHersteller);
        vendingMachine.getInventory().add(testKremkuchen);
        vendingMachine.getInventory().add(testObstkuchen);
        vendingMachine.getInventory().add(testObsttorte);

        List<HerstellerImpl> res = vendingMachine.printHersteller();
        assertEquals(3, res.get(0).getCakeCount());
    }

    // Test 21 verifies that the printCake method returns a list
    @Test
    void testPrintCake(){
        List<KuchenImpl> res = vendingMachine.printCake(null);
        assertEquals(0, res.size());
    }

    // Test 22 **Mock** for the print cake method. Should return the correct cake list
    @Test
    void testPrintCakeNotNull(){
        KuchenImpl testKremkuchen = mock(KremkuchenImpl.class);
        KuchenImpl testObstkuchen = mock(ObstkuchenImpl.class);
        KuchenImpl testObsttorte = mock(ObsttorteImpl.class);

        when(testKremkuchen.getInspektionsdatum()).thenReturn(Date.from(Instant.ofEpochSecond(System.currentTimeMillis())));
        when(testObstkuchen.getInspektionsdatum()).thenReturn(Date.from(Instant.ofEpochSecond(System.currentTimeMillis())));
        when(testObsttorte.getInspektionsdatum()).thenReturn(Date.from(Instant.ofEpochSecond(System.currentTimeMillis())));

        vendingMachine.getInventory().add(testKremkuchen);
        vendingMachine.getInventory().add(testObstkuchen);
        vendingMachine.getInventory().add(testObsttorte);

        List<KuchenImpl> res = vendingMachine.printCake(null);
        assertEquals(3, res.size());
    }

    // Test 23 verifies the print cake method only for Kremkuchen
    @Test
    void testKremkuchenPrint(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obstkuchen = new ObstkuchenImpl("Obstkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obsttorte = new ObsttorteImpl("Obsttorte", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte, sorteZwei);

        kremkuchen.setInspektionsdatum(new Date());
        obstkuchen.setInspektionsdatum(new Date());
        obsttorte.setInspektionsdatum(new Date());

        // Adding 3 different cakes but only 1 Kremkuchen
        vendingMachine.getInventory().add( kremkuchen);
        vendingMachine.getInventory().add(obstkuchen);
        vendingMachine.getInventory().add(obsttorte);

        List<KuchenImpl> res = vendingMachine.printCake("Kremkuchen");
        assertEquals(1, res.size());
    }

    // Test 24 verifies the printCake method for Obstkuchen
    @Test
    void testObstkuchenPrint(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obstkuchen = new ObstkuchenImpl("Obstkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obsttorte = new ObsttorteImpl("Obsttorte", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte, sorteZwei);

        kremkuchen.setInspektionsdatum(new Date());
        obstkuchen.setInspektionsdatum(new Date());
        obsttorte.setInspektionsdatum(new Date());

        // Adding different cakes but only 2 Obstkuchen
        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(obstkuchen);
        vendingMachine.getInventory().add(obstkuchen);
        vendingMachine.getInventory().add(obsttorte);

        List<KuchenImpl> res = vendingMachine.printCake("Obstkuchen");
        assertEquals(2, res.size());
    }

    // Test 25 the printCake method for Obsttorte
    @Test
    void testObsttortePrint(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obstkuchen = new ObstkuchenImpl("Obstkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl obsttorte = new ObsttorteImpl("Obsttorte", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte, sorteZwei);

        kremkuchen.setInspektionsdatum(new Date());
        obstkuchen.setInspektionsdatum(new Date());
        obsttorte.setInspektionsdatum(new Date());

        // Adding different cakes but only 3 Obsttorte
        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(obstkuchen);
        vendingMachine.getInventory().add(obstkuchen);
        vendingMachine.getInventory().add(obsttorte);
        vendingMachine.getInventory().add(obsttorte);
        vendingMachine.getInventory().add(obsttorte);

        List<KuchenImpl> res = vendingMachine.printCake("Obsttorte");
        assertEquals(3, res.size());
    }

    // Test 26 verifies that the updateInspectionDate method corresponds to the getInspektionsdatum of a Kremkuchen
    @Test
    void testGetInspektionsdatum(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.getInventory().add(kremkuchen);
        kremkuchen.setFachnummer(1);
        Date inspektion = new Date();
        vendingMachine.updateInspectionDate(1);
        assertEquals(inspektion, kremkuchen.getInspektionsdatum());
    }


    // Test 27 **Mock / Spy** verifies the removal of a Hersteller
    @Test
    void testRemoveHerstellerSpy() {
        // Create a spy of HerstellerImpl
        HerstellerImpl herstellerSpy = spy(new HerstellerImpl("herstellerSpy"));

        // Add the spy to the vending machine
        vendingMachine.addHersteller(herstellerSpy);

        // Remove the Hersteller from the vending machine
        assertTrue(vendingMachine.removeHersteller("herstellerSpy"));

        // Verify that the remove method of the spy was called with the expected argument
        verify(herstellerSpy).equals(false); //remove();

        // Verify that the Hersteller is no longer in the vending machine
        assertFalse(vendingMachine.getHerstellerList().contains(herstellerSpy));
    }

    // Test 28 **Mock** verifies that the remove method is not possible,
    // since the manufacturer is not in the list
    @Test
    void testRemoveHerstellerFalse(){
        HerstellerImpl herstellerMock = mock(HerstellerImpl.class);
        when(herstellerMock.getName()).thenReturn("herstellerMock");
        vendingMachine.getHerstellerList().add(herstellerMock);
        assertFalse(vendingMachine.removeHersteller("hersteller"));
    }

    // TODO: fix Test 29 remove a cake from the vending machine by tray number
    @Test
    void testRemoveItem(){
        // Create mock instances of KuchenImpl
        KuchenImpl mockKremkuchen1 = mock(KremkuchenImpl.class);
        KuchenImpl mockKremkuchen2 = mock(KremkuchenImpl.class);

        // Add the mock instances to the vending machine
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem(mockKremkuchen1);
        vendingMachine.addItem(mockKremkuchen2);

        when(mockKremkuchen1.getHersteller()).thenReturn(hersteller1);
        when(mockKremkuchen2.getHersteller()).thenReturn(hersteller1);

        // Spy on the vending machine
        VendingMachine spyVendingMachine = spy(vendingMachine);

        // Remove item from the spy vending machine
        spyVendingMachine.removeItem(1);

        // Verify that removeItem was called with index 1 on the spy vending machine
        verify(spyVendingMachine).removeItem(1);

        assertEquals(1, hersteller1.getCakeCount());
        // Check the size of the inventory after removal
        //assertEquals(1, spyVendingMachine.getInventory().size());
    }

    // Test 30 remove cake with wrong tray number not possible
    @Test
    void testRemoveCakeNoTrayNumber(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl kremkuchen2 = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        kremkuchen.setFachnummer(1);
        kremkuchen2.setFachnummer(2);
        vendingMachine.getInventory().add( kremkuchen);
        vendingMachine.getInventory().add( kremkuchen2);
        assertFalse(vendingMachine.removeItem(3));
    }

    // Test 31 **Mock** print allergies of cakes in the vending machine
    @Test
    void testPrintAllergies(){
        KuchenImpl testKremkuchen = mock(KremkuchenImpl.class);
        KuchenImpl testObstkuchen = mock(ObstkuchenImpl.class);
        KuchenImpl testObsttorte = mock(ObsttorteImpl.class);

        when(testKremkuchen.getAllergene()).thenReturn(Arrays.asList(Erdnuss, Gluten));
        when(testObstkuchen.getAllergene()).thenReturn(Collections.singleton(Gluten));
        when(testObsttorte.getAllergene()).thenReturn(Collections.singleton(Sesamsamen));

        vendingMachine.getInventory().add( testKremkuchen);
        vendingMachine.getInventory().add( testObstkuchen);
        vendingMachine.getInventory().add( testObsttorte);

        List<Allergen> res = vendingMachine.printAllergies(true);
        assertEquals(3, res.size());
    }

    // Test 32 **Mock** for allergies not in the vending machine
    @Test
    void testAllergiesNotInVendingMachine(){
        KuchenImpl testKremkuchen = mock(KremkuchenImpl.class);
        KuchenImpl testObstkuchen = mock(ObstkuchenImpl.class);
        KuchenImpl testObsttorte = mock(ObsttorteImpl.class);

        when(testKremkuchen.getAllergene()).thenReturn(Arrays.asList(Erdnuss, Gluten));
        when(testObstkuchen.getAllergene()).thenReturn(Collections.singleton(Gluten));
        when(testObsttorte.getAllergene()).thenReturn(Collections.singleton(Sesamsamen));

        vendingMachine.getInventory().add(testKremkuchen);
        vendingMachine.getInventory().add(testObstkuchen);
        vendingMachine.getInventory().add(testObsttorte);

        List<Allergen> res = vendingMachine.printAllergies(false);
        assertEquals(1, res.size());
    }

    // Test 33 verifies the getCapacity method
    @Test
    void testGetCapacity() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(2, kuchenLinkedList, herstellerLinkedList);

        //int capacity = vendingMachine.getCapacity();
        assertEquals(2, vendingMachine.getCapacity(), "Capacity should match the number");
    }

    // Test 34 encapsulation
    @Test
    void testEncapsulation() {
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem(kremkuchen);
        vendingMachine.addItem(kremkuchen);
        List<KuchenImpl> resList = vendingMachine.printCake("kuchen");
        resList.clear();
        List<KuchenImpl> resList2 = vendingMachine.printCake("kuchen");
        assertEquals(2, resList2.size());
    }

    // Test 35 verifies the isFull method returns false, when no cake is in the vending machine
    @Test
    void isFullWhenEmpty() {
        List<KuchenImpl> inventory = new LinkedList<>();
        List<HerstellerImpl> herstellerList = new LinkedList<>();
        vendingMachine = new VendingMachine(3, inventory, herstellerList);
        assertFalse(vendingMachine.isFull(), "The vending machine should not be full when empty.");
    }

    // Test 36 verifies the isFull method returns false,
    // when the full capacity of the vending machine is not reached
    @Test
    void isFullWhenNotFull() {
        List<KuchenImpl> inventory = new LinkedList<>();
        List<HerstellerImpl> herstellerList = new LinkedList<>();
        vendingMachine = new VendingMachine(3, inventory, herstellerList);
        // Add two items to the vending machine
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);

        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(kremkuchen);

        assertFalse(vendingMachine.isFull(), "The vending machine should not be full when not at capacity.");
    }

    // Test 37 verifies the isFull method returns true, when the capacity is reached
    @Test
    void isFullWhenFull() {
        List<KuchenImpl> inventory = new LinkedList<>();
        List<HerstellerImpl> herstellerList = new LinkedList<>();
        vendingMachine = new VendingMachine(3, inventory, herstellerList);
        // Add three items to the vending machine
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);

        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(kremkuchen);

        assertTrue(vendingMachine.isFull(), "The vending machine should be full when capacity is reached.");
    }

    @Test
    public void testAddCakeFullCapacityMock() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> inventory = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(2, inventory, herstellerLinkedList);
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);

        vendingMachine.addItem(kremkuchen);
        vendingMachine.addItem(kremkuchen);


        assertTrue(vendingMachine.isFull());
        //assertFalse(vendingMachine.addItem(kremkuchen));

        // Erstellen von Mock-Objekten
        KuchenImpl mockObstkuchen = mock(KremkuchenImpl.class);
        KuchenImpl mockObsttorte = mock(ObstkuchenImpl.class);
        KuchenImpl mockKremkuchen = mock(ObsttorteImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);

        //Hersteller einfuegen
        //vendingMachine.addHersteller(mockHersteller);

        // Konfiguration der Mock-Objekte
        when(mockObsttorte.getHersteller()).thenReturn(mockHersteller);
        when(mockObstkuchen.getHersteller()).thenReturn(mockHersteller);
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        //Einfuegen der Kuchen
        vendingMachine.addItem(mockKremkuchen);
        //assertTrue(vendingMachine.addItem(mockKremkuchen));
        vendingMachine.addItem(mockObsttorte);
        vendingMachine.addItem(mockObsttorte);

        //vendingMachine.addItem(mockKremkuchen);
        //assertTrue(vendingMachine.addItem(mockObstkuchen));
        //assertTrue(vendingMachine.addItem(mockObsttorte));
        //assertTrue(vendingMachine.isFull());

        //assertEquals(2, vendingMachine.getCapacity());
        //assertEquals(2, vendingMachine.getInventory().size());
        // Einfuegen des Kuchens des Kremkuchen, der nicht hinzugefuegt werden kann, da Kapazitaet erreicht
        //assertFalse(vendingMachine.addItem(mockKremkuchen));

    }
}
