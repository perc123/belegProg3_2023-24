import administration.HerstellerImpl;
import administration.HerstellerList;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import administration.VendingMachine;
import cakes.ObsttorteImpl;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       VendingMachine vendingMachine = new VendingMachine(10);
        HerstellerList herstellerList = new HerstellerList();

        HerstellerImpl hersteller1 = new HerstellerImpl("Twix");
        HerstellerImpl hersteller2 = new HerstellerImpl("Sneakers");

        herstellerList.addHersteller(hersteller2);
        herstellerList.addHersteller(hersteller1);

        KuchenImpl cake1 = new KuchenImpl("Kremkuchen",hersteller1, Set.of(Allergen.Gluten, Allergen.Erdnuss), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0));
        KuchenImpl cake2 = new KremkuchenImpl("Kremkuchen", hersteller2, Set.of(Allergen.Haselnuss), 300, Duration.ofDays(10), BigDecimal.valueOf(7.0), "Schokolade");
        KuchenImpl cake3 = new KuchenImpl("Kremkuchen",hersteller1, Set.of(Allergen.Sesamsamen), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0));

        // Add the cakes to the vending machine
        vendingMachine.addItem(cake1, hersteller1);
        vendingMachine.addItem(cake2, hersteller2);
        vendingMachine.addItem(cake3, hersteller1);


        // List the items in the vending machine
        List<KuchenImpl> items = new ArrayList<>(vendingMachine.listItems());
        for (KuchenImpl item : items) {
            System.out.println("Hersteller: " + item.getHersteller().getName());
            System.out.println("Naehrwert: " + item.getNaehrwert());
            System.out.println("Haltbarkeit: " + item.getHaltbarkeit());
            System.out.println("Price: " + item.getPreis());
            System.out.println("Allergen: " + item.getAllergene());
            System.out.println("Inspektionsdatum: " + item.getInspektionsdatum());
            System.out.println("Fachnummer: " + item.getFachnummer());

            if (item instanceof KremkuchenImpl) {
                KremkuchenImpl kremkuchen = (KremkuchenImpl) item;
                System.out.println("Kremsorte: " + kremkuchen.getKremsorte());
            }
            System.out.println("---------------------------------------");
        }

        // Update inspection date for all items in the vending machine
        vendingMachine.updateInspectionDate(2);

        // Remove an item from the vending machine
        System.out.println("\nRemoving item with Fachnummer 2");
        vendingMachine.removeItem(1);
        vendingMachine.removeItem(2);


        KuchenImpl cake4 = new KuchenImpl("Kremkuchen",hersteller1, Set.of(Allergen.Gluten, Allergen.Erdnuss), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0));
        KuchenImpl cake5 = new KremkuchenImpl("Kremkuchen",hersteller2, Set.of(Allergen.Haselnuss), 300, Duration.ofDays(10), BigDecimal.valueOf(7.0), "Schokolade");
        KuchenImpl cake6 = new KuchenImpl("Kremkuchen",hersteller1, Set.of(Allergen.Sesamsamen), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0));
        KuchenImpl cake7 = new KuchenImpl("Kremkuchen",hersteller1, Set.of(Allergen.Sesamsamen), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0));


        // Add the cakes to the vending machine
        vendingMachine.addItem(cake4, hersteller1);
        vendingMachine.addItem(cake5, hersteller2);
        vendingMachine.addItem(cake6, hersteller1);
        vendingMachine.addItem(cake7, hersteller1);

        System.out.println("\nList of items after removal:");

        // List the items in the vending machine
        List<KuchenImpl> cakes = new ArrayList<>(vendingMachine.listItems());
        for (KuchenImpl item : cakes) {
            System.out.println("Hersteller: " + item.getHersteller().getName());
            System.out.println("Naehrwert: " + item.getNaehrwert());
            System.out.println("Haltbarkeit: " + item.getHaltbarkeit());
            System.out.println("Price: " + item.getPreis());
            System.out.println("Allergen: " + item.getAllergene());
            System.out.println("Inspektionsdatum: " + item.getInspektionsdatum());
            System.out.println("Fachnummer: " + item.getFachnummer());

            if (item instanceof KremkuchenImpl) {
                KremkuchenImpl kremkuchen = (KremkuchenImpl) item;
                System.out.println("Kremsorte: " + kremkuchen.getKremsorte());
            }
            System.out.println("---------------------------------------");
        }
    }
}
