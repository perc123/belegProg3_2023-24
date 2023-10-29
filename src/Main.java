import administration.HerstellerImpl;
import administration.HerstellerList;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import administration.VendingMachine;
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

        KuchenImpl cake1 = new KuchenImpl(hersteller1, Set.of(Allergen.Gluten, Allergen.Erdnuss), 400, Duration.ofDays(8), BigDecimal.valueOf(5.0), new Date());
        KuchenImpl cake2 = new KremkuchenImpl(hersteller2, Set.of(Allergen.Haselnuss), 300, Duration.ofDays(10), "Schokolade", BigDecimal.valueOf(7.0), new Date());
        KuchenImpl cake3 = new KuchenImpl(hersteller1, Set.of(Allergen.Sesamsamen), 400, Duration.ofDays(8),BigDecimal.valueOf(5.0),new Date());

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
        vendingMachine.updateInspectionDate();
    }
}
