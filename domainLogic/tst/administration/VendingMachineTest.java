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

    // Test ueberprueft, ob ein Hersteller erfolgreich hinzugefuegt werden kann
    @Test
    void herstellerEinfuegen() {
        HerstellerImpl h = new HerstellerImpl("hersteller1");
        assertTrue(vendingMachine.addHersteller(h));
    }

    // Test ueberprueft, ob es möglich ist, zweimal den gleich Hersteller einzufuegen. Es darf dabei nicht moeglich sein
    @Test
    void herstellerEinfuegenNichtMoeglich() {
        HerstellerImpl h = new HerstellerImpl("hersteller1");
        vendingMachine.addHersteller(h);
        assertFalse(vendingMachine.addHersteller(h));
    }

    // Hersteller Einfuegen als MockTest
    @Test
    public void testHerstellerEinfuegenMitMock() {
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        assertTrue(vendingMachine.addHersteller(mockHersteller));
    }

    // Add Kremkuchen Mocktest
    @Test
    public void testKremkuchenEinfuegenMitMock() {
        // Create Mock objects
        KremkuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Configure mock objects
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);


        assertTrue(vendingMachine.addItem(mockKremkuchen));
    }

    // Add Kremkuchen
    @Test
    public void testAddKremkuchen() {
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.getHerstellerList().add(hersteller1);
        assertTrue(vendingMachine.addItem((KuchenImpl) kremkuchen));
    }

    // Returns false if a cake is added when the capacity is full
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

    // Obstkuchen Einfuegen MockTest
    @Test
    public void testObstkuchenEinfuegenMitMock() {
        // Erstellen von Mock-Objekten
        Obstkuchen mockObstkuchen = mock(ObstkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Konfiguration der Mock-Objekte
        when(mockObstkuchen.getHersteller()).thenReturn(mockHersteller);

        // Test des Einfuegens des Obstkuchens
        assertTrue(vendingMachine.addItem((KuchenImpl) mockObstkuchen));
    }

    // Obstkuchen Einfuegen Test
    @Test
    public void testObstkuchenEinfuegen() {
        Obstkuchen obstkuchen = new ObstkuchenImpl("Obstkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.getHerstellerList().add(hersteller1);
        assertTrue(vendingMachine.addItem((KuchenImpl) obstkuchen));
    }

    // Obsttorte Einfuegen MockTest
    @Test
    public void testObsttorteEinfuegenMitMock() {
        // Erstellen von Mock-Objekten
        Obsttorte mockObsttorte = mock(ObsttorteImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Konfiguration der Mock-Objekte
        when(mockObsttorte.getHersteller()).thenReturn(mockHersteller);

        // Test des Einfuegens der Obsttorte
        assertTrue(vendingMachine.addItem((KuchenImpl) mockObsttorte));
    }

    // Obsttorte Einfuegen Test
    @Test
    public void testObsttorteEinfuegen() {
        Obsttorte obsttorte = new ObsttorteImpl("Obsttorte", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte, sorteZwei);
        vendingMachine.getHerstellerList().add(hersteller1);
        assertTrue(vendingMachine.addItem((KuchenImpl) obsttorte));
    }

    // Test adding a cake with no manufacturer in the list should not be possible
    @Test
    public void testAddCakeNotPossible() {
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        assertFalse(vendingMachine.addItem(kremkuchen));
    }




    @Test
    public void EinfuegedatumTestMitMock() {
        // Erstellen von Mock-Objekten
        KuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        // Konfiguration der Mock-Objekte
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        // Einfuegen des Kuchens
        vendingMachine.addItem(mockKremkuchen);

        verify(mockKremkuchen).getInspektionsdatum();
    }

    // Tray number (Mockito-Test)
    @Test
    public void FachnummerTestMock() {
        KuchenImpl mockKremkuchen = mock(KremkuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        vendingMachine.addItem(mockKremkuchen);

        // Verify if the tray number is given
        verify(mockKremkuchen).setFachnummer(1);
    }

    // Add Item (Mockito-Test)
    @Test
    public void AddItemTestMock() {
        KuchenImpl mockCake = mock(KuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);
        vendingMachine.addHersteller(mockHersteller);

        when(mockCake.getHersteller()).thenReturn(mockHersteller);

        assertTrue(vendingMachine.addItem(mockCake));
    }

    // Test the size of a cake list
    @Test
    public void testIncrementInventory() {
        Kremkuchen kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem((KuchenImpl) kremkuchen);
        assertEquals(1, vendingMachine.getInventory().size());
    }

    // Test the if the cake list contains the expected cakes
    @Test
    public void testGetInventory() {
        Kremkuchen kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.addHersteller(hersteller1);
        vendingMachine.addItem((KuchenImpl) kremkuchen);
        List<KuchenImpl> kuchenList = vendingMachine.getInventory();
        assertTrue(kuchenList.contains(kremkuchen));
    }

    // Test the expected size of the manufacturer's list
    @Test
    public void testIncrementHerstellerList() {
        vendingMachine.addHersteller(hersteller1);
        assertEquals(1, vendingMachine.getHerstellerList().size());
    }

    // Tests the manufacturer's list for the expected manufacturer name
    @Test
    public void testGetHerstellerListe() {
        HerstellerImpl hersteller2 = new HerstellerImpl("Hersteller 1");
        vendingMachine.addHersteller(hersteller2);
        List<HerstellerImpl> herstellerList = vendingMachine.getHerstellerList();
        assertTrue(herstellerList.contains(hersteller2));
    }

    // Tests the content of manufacturer's list
    @Test
    public void testCallHersteller(){
        HerstellerImpl testHersteller = mock(HerstellerImpl.class);
        vendingMachine.getHerstellerList().add(testHersteller);
        List<HerstellerImpl> res = vendingMachine.printHersteller();
        assertEquals(1, res.size());
    }


    //Test to check the cake count of each manufacturer

    @Test
    public void testCallHerstellerCakeCount(){
        HerstellerImpl testHersteller = new HerstellerImpl("testHersteller");

        // Erstellen von Mock-Objekten
        KuchenImpl testKremkuchen = mock(KremkuchenImpl.class);
        KuchenImpl testObstkuchen = mock(ObstkuchenImpl.class);
        KuchenImpl testObsttorte = mock(ObsttorteImpl.class);

        // Konfiguration der Mock-Objekte
        when(testKremkuchen.getHersteller()).thenReturn(testHersteller);
        when(testObstkuchen.getHersteller()).thenReturn(testHersteller);
        when(testObsttorte.getHersteller()).thenReturn(testHersteller);

        // Einfuegen der Kuchen und des Herstellers
        vendingMachine.getHerstellerList().add(testHersteller);
        vendingMachine.getInventory().add(testKremkuchen);
        vendingMachine.getInventory().add(testObstkuchen);
        vendingMachine.getInventory().add(testObsttorte);

        List<HerstellerImpl> res = vendingMachine.printHersteller();
        assertEquals(3, res.get(0).getCakeCount());
    }

    // Testet die kuchenAbrufen() Methode, ob sie eine Liste zurueck gibt
    @Test
    void testPrintCake(){
        List<KuchenImpl> res = vendingMachine.printCake(null);
        assertEquals(0, res.size());
    }

    // Test for the print cake method. Should return the correct cake list (Mock)
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

    // Tests the print cake method only for Kremkuchen (Mock)
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

    // Tests the printCake method for Obstkuchen (Mock)
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

    // Tests the printCake method for Obsttorte (Mock)
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

    // Testet die IspektionsDatumSetzen() Methode, ob das Inspektionsdatum gesetzt wird
    @Test
    void testIspektionsDatumSetzen(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        vendingMachine.getInventory().add(kremkuchen);
        kremkuchen.setFachnummer(1);
        Date inspektion = new Date();
        vendingMachine.updateInspectionDate(1);
        assertEquals(inspektion, kremkuchen.getInspektionsdatum());
    }

    // Test zum loeschen eines Herstellers
    @Test
    void testloeschenHersteller(){
        HerstellerImpl herstellerMock = mock(HerstellerImpl.class);
        when(herstellerMock.getName()).thenReturn("herstellerMock");
        vendingMachine.getHerstellerList().add(herstellerMock);
        assertTrue(vendingMachine.removeHersteller("herstellerMock"));
    }

    //Test remove method not possible, since manufacturer not in the list
    @Test
    void testloeschenHerstellerFalse(){
        HerstellerImpl herstellerMock = mock(HerstellerImpl.class);
        when(herstellerMock.getName()).thenReturn("herstellerMock");
        vendingMachine.getHerstellerList().add(herstellerMock);
        assertFalse(vendingMachine.removeHersteller("hersteller"));
    }

    // Test remove a cake from the vending machine
    @Test
    void testloeschenEinesKuchens(){
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        KuchenImpl kremkuchen2 = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);
        kremkuchen.setFachnummer(1);
        kremkuchen2.setFachnummer(2);
        vendingMachine.getInventory().add(kremkuchen);
        vendingMachine.getInventory().add(kremkuchen2);
        vendingMachine.removeItem(1);
        assertEquals(1,vendingMachine.getInventory().size());
    }

    // Test remove cake with wrong tray number not possible
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

    // Test print allergies of cakes in the vending machine (Mock)
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

    // Test for allergies not in the vending machine
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

    // Test getCapacity method
    @Test
    void testGetCapacity() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(2, kuchenLinkedList, herstellerLinkedList);

        int capacity = vendingMachine.getCapacity();
        assertEquals(2, vendingMachine.getCapacity(), "Capacity should match the number");
    }

    // Encapsulation test
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




    @Test
    void isFullWhenEmpty() {
        List<KuchenImpl> inventory = new LinkedList<>();
        List<HerstellerImpl> herstellerList = new LinkedList<>();
        vendingMachine = new VendingMachine(3, inventory, herstellerList);
        assertFalse(vendingMachine.isFull(), "The vending machine should not be full when empty.");
    }

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

        assertTrue(vendingMachine.isFull(), "The vending machine should be full when at capacity.");
    }

/*
    @Test
    public void testIsFull() {
        // Mock HerstellerImpl objects
        HerstellerImpl hersteller1 = mock(HerstellerImpl.class);
        HerstellerImpl hersteller2 = mock(HerstellerImpl.class);

        // Mock inventory list
        List<KuchenImpl> inventory = new ArrayList<>();

        // Mock Hersteller list
        List<HerstellerImpl> herstellerList = new ArrayList<>();
        herstellerList.add(hersteller1);
        herstellerList.add(hersteller2);

        // Create a vending machine with capacity 2
        VendingMachine vendingMachine = new VendingMachine(2, inventory, herstellerList);

        // Mock KuchenImpl objects
        KuchenImpl cake1 = mock(KuchenImpl.class);
        KuchenImpl cake2 = mock(KuchenImpl.class);

        // Mock addItem() behavior
        when(cake1.getHersteller()).thenReturn(hersteller1);
        when(cake2.getHersteller()).thenReturn(hersteller2);

        // Add two cakes to the vending machine
        assertTrue(vendingMachine.addItem(cake1));
        assertTrue(vendingMachine.addItem(cake2));

        // Attempt to add a third cake
        KuchenImpl cake3 = mock(KuchenImpl.class);
        when(cake3.getHersteller()).thenReturn(hersteller1);

        assertFalse(vendingMachine.addItem(cake3));
    }

    // TODO: Test add cake when capacity is full
    @Test
    public void testAddCakeFullCapacityMock() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> inventory = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(2, inventory, herstellerLinkedList);
        KuchenImpl kremkuchen = new KremkuchenImpl("Kremkuchen", hersteller1, preis, naherwerte,haltbarkeit, allergens, sorte);

        // Erstellen von Mock-Objekten
        KuchenImpl mockObstkuchen = mock(KuchenImpl.class);
        KuchenImpl mockObsttorte = mock(KuchenImpl.class);
        KuchenImpl mockKremkuchen = mock(KuchenImpl.class);
        HerstellerImpl mockHersteller = mock(HerstellerImpl.class);

        //Hersteller einfuegen
        vendingMachine.addHersteller(mockHersteller);

        // Konfiguration der Mock-Objekte
        when(mockObsttorte.getHersteller()).thenReturn(mockHersteller);
        when(mockObstkuchen.getHersteller()).thenReturn(mockHersteller);
        when(mockKremkuchen.getHersteller()).thenReturn(mockHersteller);

        //Einfuegen der Kuchen
        vendingMachine.addItem(mockKremkuchen);
        //assertTrue(vendingMachine.addItem(mockKremkuchen));
        vendingMachine.addItem(mockKremkuchen);
        //vendingMachine.addItem(mockKremkuchen);
        assertTrue(vendingMachine.addItem(mockObstkuchen));
        assertTrue(vendingMachine.addItem(mockObsttorte));

        //assertEquals(2, vendingMachine.getCapacity());
        //assertEquals(2, vendingMachine.getInventory().size());
        // Einfuegen des Kuchens des Kremkuchen, der nicht hinzugefuegt werden kann, da Kapazitaet erreicht
        assertFalse(vendingMachine.addItem(mockKremkuchen));

    }*/
}
