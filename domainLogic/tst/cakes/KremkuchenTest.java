package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Kremkuchen;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class KremkuchenTest {

    String kuchenTyp;
    HerstellerImpl hersteller;
    BigDecimal preis;
    int naehrwert;
    Duration haltbarkeit;
    Collection<Allergen> allergene;
    String sorte;
    Kremkuchen testKremkuchen;

    @BeforeEach
    void setUp() {
        kuchenTyp = "Kremkuchen";
        hersteller = new HerstellerImpl("hersteller1");
        preis = new BigDecimal("3.20");
        naehrwert = 123;
        haltbarkeit = Duration.ofDays(3);
        allergene = List.of(Allergen.Gluten, Allergen.Sesamsamen);
        sorte = "Butter";
        KuchenImpl testKremkuchen = new KremkuchenImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit, allergene, sorte);
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

    // Testet die getEinfuegedatum Methode
    @Test
    void  getEinfuegedatum() {
        LocalDateTime einfuegedatum = LocalDateTime.of(2022, 3, 1, 12, 0, 0);
        testKremkuchen.setEinfuegedatum(einfuegedatum);
        assertEquals(einfuegedatum, testKremkuchen.getEinfuegedatum());
    }

    // Testet die getFachnummer Methode
    @Test
    void getFachnummer() {
        LinkedList<Hersteller> herstellerLinkedList = new LinkedList<>();
        LinkedList<Verkaufsobjekt> verkaufsobjektLinkedList = new LinkedList<>();
        Model model = new Model(3, verkaufsobjektLinkedList, herstellerLinkedList);
        model.herstellerEinfuegen(hersteller);
        model.verkaufsObjektEinfuegen(testKremkuchen);
        assertEquals(1, testKremkuchen.getFachnummer());
    }

    // Testet die setFachnummer Methode
    @Test
    void setFachnummer() {
        int fachnummer = 42;
        testKremkuchen.setFachnummer(fachnummer);
        assertEquals(42, testKremkuchen.getFachnummer());
    }

    // Testet die setEinfuegedatum Methode
    @Test
    void setEinfuegedatum() {
        LocalDateTime erwartetesDatum = LocalDateTime.of(2022, 1, 1, 12, 0, 0);
        testKremkuchen.setEinfuegedatum(erwartetesDatum);
        assertEquals(erwartetesDatum, testKremkuchen.getEinfuegedatum());
    }


}