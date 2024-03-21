package cakes;

import administration.HerstellerImpl;
import administration.VendingMachine;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class KremkuchenImplTest {


    String kuchenTyp;
    HerstellerImpl hersteller;
    BigDecimal preis;
    int naehrwert;
    Duration haltbarkeit;
    Collection<Allergen> allergene;
    String sorte;
    KremkuchenImpl testKremkuchen;

    @BeforeEach
    void setUp() {
        kuchenTyp = "Kremkuchen";
        hersteller = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("3.20");
        naehrwert = 123;
        haltbarkeit = Duration.ofDays(3);
        allergene = List.of(Allergen.Gluten, Allergen.Sesamsamen);
        sorte = "Butter";
        testKremkuchen = new KremkuchenImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit, allergene, sorte);
    }

    // Test 1 for getKremsorte method
    @Test
    void getKremsorte() {
        assertEquals("Butter", testKremkuchen.getKremsorte());
    }

    // Test 2 for getHersteller method
    @Test
    void getHersteller() {
        assertEquals(hersteller, testKremkuchen.getHersteller());
    }

    // Test 3 for getAllergene method
    @Test
    void getAllergene() {
        assertEquals(List.of(Allergen.Gluten, Allergen.Sesamsamen), testKremkuchen.getAllergene());
    }

    // Test 4 for getNaehrwert method
    @Test
    void getNaehrwert() {
        assertEquals(naehrwert, testKremkuchen.getNaehrwert());
    }

    // Test 5 for getHaltbarkeit method
    @Test
    void getHaltbarkeit() {
        assertEquals(haltbarkeit, testKremkuchen.getHaltbarkeit());
    }

    // Test 6 for getPreis method
    @Test
    void getPreis() {
        assertEquals(preis, testKremkuchen.getPreis());
    }

    // Test 7 for getInspektionsdatum and setIspektionsdatum Methode
    @Test
    void getSetInspektionsdatum() {
        LocalDate localDate = LocalDate.now();
        Date inspektion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testKremkuchen.setInspektionsdatum(inspektion);
        assertEquals(inspektion, testKremkuchen.getInspektionsdatum());
    }

    // Test 8 for getEinfuegedatum
    @Test
    void  testGetEinfuegedatum() {
        Date date = new Date();
        testKremkuchen.setInspektionsdatum(date);
        assertEquals(date, testKremkuchen.getInspektionsdatum());
    }

    // Test 9 for the getFachnummer method
    @Test
    void getFachnummer() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(3, kuchenLinkedList, herstellerLinkedList);
        vendingMachine.addHersteller(hersteller);
        vendingMachine.addItem(testKremkuchen);
        assertEquals(1, testKremkuchen.getFachnummer());
    }

    // Test 10 for the setFachnummer method
    @Test
    void setFachnummer() {
        int fachnummer = 42;
        testKremkuchen.setFachnummer(fachnummer);
        assertEquals(42, testKremkuchen.getFachnummer());
    }

    // Test 11 for the setEinfuegedatum method
    @Test
    void setEinfuegedatum() {
        Date date = new Date();
        testKremkuchen.setInspektionsdatum(date);
        assertEquals(date, testKremkuchen.getInspektionsdatum());
    }

    // Test 12 for the calculateRemainingShelfLife method
    @Test
    void calculateRemainingShelfLife() {
        Date date = new Date();
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(haltbarkeit);
        testKremkuchen.setInspektionsdatum(date);

        // Calculate the remaining shelf life
        long remainingShelfLife = testKremkuchen.calculateRemainingShelfLife();

        // Expected shelf life is 3 days
        long expectedShelfLife = Duration.between(currentDate, expirationDate).toDays();

        // Assert that the remaining shelf life matches the expected value
        assertEquals(expectedShelfLife, remainingShelfLife);
    }

    // Test 13 for the getKuchenTyp method
    @Test
    void getKuchenTyp() {
        assertEquals(kuchenTyp, testKremkuchen.getKuchenTyp());
    }


}