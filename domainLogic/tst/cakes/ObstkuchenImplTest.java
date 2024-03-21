package cakes;

import administration.HerstellerImpl;
import administration.VendingMachine;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class ObstkuchenImplTest {
    String kuchenTyp;
    HerstellerImpl hersteller;
    BigDecimal preis;
    int naehrwert;
    Duration haltbarkeit;
    Collection<Allergen> allergene;
    String sorte;
    ObstkuchenImpl testObstkuchen;

    @BeforeEach
    void setUp() {
        kuchenTyp = "Obstkuchen";
        hersteller = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("3.20");
        naehrwert = 123;
        haltbarkeit = Duration.ofDays(3);
        allergene = List.of(Allergen.Gluten, Allergen.Sesamsamen);
        sorte = "Apfel";
        testObstkuchen = new ObstkuchenImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit, allergene, sorte);
    }


    @Test
    void getHersteller() {
        assertEquals(hersteller, testObstkuchen.getHersteller());
    }

    @Test
    void getAllergene() {
        assertEquals(List.of(Allergen.Gluten, Allergen.Sesamsamen), testObstkuchen.getAllergene());
    }


    @Test
    void getNaehrwert() {
        assertEquals(naehrwert, testObstkuchen.getNaehrwert());
    }


    @Test
    void getHaltbarkeit() {
        assertEquals(haltbarkeit, testObstkuchen.getHaltbarkeit());
    }


    @Test
    void getPreis() {
        assertEquals(preis, testObstkuchen.getPreis());
    }


    @Test
    void getSetInspektionsdatum() {
        LocalDate localDate = LocalDate.now();
        Date inspektion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testObstkuchen.setInspektionsdatum(inspektion);
        assertEquals(inspektion, testObstkuchen.getInspektionsdatum());
    }

    @Test
    void setFachnummer() {
        int fachnummer = 42;
        testObstkuchen.setFachnummer(fachnummer);
        assertEquals(42, testObstkuchen.getFachnummer());
    }

    @Test
    void getFachnummer() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(3, kuchenLinkedList, herstellerLinkedList);
        vendingMachine.addHersteller(hersteller);
        vendingMachine.addItem(testObstkuchen);
        assertEquals(1, testObstkuchen.getFachnummer());
    }


    @Test
    void calculateRemainingShelfLife() {
        Date date = new Date();
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(haltbarkeit);
        testObstkuchen.setInspektionsdatum(date);

        // Calculate the remaining shelf life
        long remainingShelfLife = testObstkuchen.calculateRemainingShelfLife();

        // Expected shelf life is 3 days
        long expectedShelfLife = Duration.between(currentDate, expirationDate).toDays();

        // Assert that the remaining shelf life matches the expected value
        assertEquals(expectedShelfLife, remainingShelfLife);
    }

    @Test
    void getKuchenTyp() {
        assertEquals(kuchenTyp, testObstkuchen.getKuchenTyp());
    }

    @Test
    void getObstsorte()  {
        assertEquals("Apfel", testObstkuchen.getObstsorte());
    }
}