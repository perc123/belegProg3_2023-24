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
import static org.junit.jupiter.api.Assertions.*;

class ObsttorteImplTest {


    String kuchenTyp;
    HerstellerImpl hersteller;
    BigDecimal preis;
    int naehrwert;
    Duration haltbarkeit;
    Collection<Allergen> allergene;
    String sorte;
    String sorteZwei;
    ObsttorteImpl testObsttorte;

    @BeforeEach
    void setUp() {
        kuchenTyp = "Kremkuchen";
        hersteller = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("3.20");
        naehrwert = 123;
        haltbarkeit = Duration.ofDays(3);
        allergene = List.of(Allergen.Gluten, Allergen.Sesamsamen);
        sorte = "Erdbeere";
        sorteZwei = "Sahne";
        testObsttorte = new ObsttorteImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit, allergene, sorte, sorteZwei);
    }

    @Test
    void getKremsorte() {
        assertEquals("Sahne",  testObsttorte.getKremsorte());
    }

    @Test
    void getHersteller() {
        assertEquals(hersteller, testObsttorte.getHersteller());
    }


    @Test
    void getAllergene() {
        assertEquals(List.of(Allergen.Gluten, Allergen.Sesamsamen), testObsttorte.getAllergene());
    }


    @Test
    void getNaehrwert() {
        assertEquals(naehrwert, testObsttorte.getNaehrwert());
    }


    @Test
    void getHaltbarkeit() {
        assertEquals(haltbarkeit, testObsttorte.getHaltbarkeit());
    }


    @Test
    void getPreis() {
        assertEquals(preis, testObsttorte.getPreis());
    }


    @Test
    void getSetInspektionsdatum() {
        LocalDate localDate = LocalDate.now();
        Date inspektion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testObsttorte.setInspektionsdatum(inspektion);
        assertEquals(inspektion, testObsttorte.getInspektionsdatum());
    }

    @Test
    void setFachnummer() {
        int fachnummer = 42;
        testObsttorte.setFachnummer(fachnummer);
        assertEquals(42, testObsttorte.getFachnummer());
    }

    @Test
    void getFachnummer() {
        LinkedList<HerstellerImpl> herstellerLinkedList = new LinkedList<>();
        LinkedList<KuchenImpl> kuchenLinkedList = new LinkedList<>();
        VendingMachine vendingMachine = new VendingMachine(3, kuchenLinkedList, herstellerLinkedList);
        vendingMachine.addHersteller(hersteller);
        vendingMachine.addItem(testObsttorte);
        assertEquals(1, testObsttorte.getFachnummer());
    }


    @Test
    void calculateRemainingShelfLife() {
        Date date = new Date();
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(haltbarkeit);
        testObsttorte.setInspektionsdatum(date);

        // Calculate the remaining shelf life
        long remainingShelfLife = testObsttorte.calculateRemainingShelfLife();

        // Expected shelf life is 3 days
        long expectedShelfLife = Duration.between(currentDate, expirationDate).toDays();

        // Assert that the remaining shelf life matches the expected value
        assertEquals(expectedShelfLife, remainingShelfLife);
    }

    @Test
    void getObstsorte() {
        assertEquals("Erdbeere", testObsttorte.getObstsorte());
    }

    @Test
    public void testGetKuchenTyp() {
        assertEquals("Obsttorte", testObsttorte.getKuchenTyp());
    }

}