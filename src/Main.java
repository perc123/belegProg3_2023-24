
import administration.HerstellerImpl;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import administration.VendingMachine;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a vending machine with a capacity of 10
        VendingMachine vendingMachine = new VendingMachine(10);

        // Create some Hersteller
        HerstellerImpl hersteller1 = new HerstellerImpl("Hersteller 1");
        HerstellerImpl hersteller2 = new HerstellerImpl("Hersteller 2");

        // Create some Kuchen (cakes)
        KuchenImpl cake1 = new KuchenImpl(hersteller1, new ArrayList<>(), 200, Duration.ofDays(7), BigDecimal.valueOf(5.0), new Date(), 1);
        KremkuchenImpl cake2 = new KremkuchenImpl(hersteller2, new ArrayList<>(), 300, Duration.ofDays(10), "Schokolade", BigDecimal.valueOf(7.0), new Date(), 2);

        // Add the cakes to the vending machine
        vendingMachine.addItem(cake1);
        vendingMachine.addItem(cake2);

        // List the items in the vending machine
        List<KuchenImpl> items = new ArrayList<>(vendingMachine.listItems());
        for (KuchenImpl item : items) {
            System.out.println("Hersteller: " + item.getHersteller().getName());
            System.out.println("Naehrwert: " + item.getNaehrwert());
            System.out.println("Haltbarkeit: " + item.getHaltbarkeit());
            System.out.println("Price: " + item.getPreis());
            System.out.println("Inspektionsdatum: " + item.getInspektionsdatum());
            System.out.println("Fachnummer: " + item.getFachnummer());

            if (item instanceof KremkuchenImpl) {
                KremkuchenImpl kremkuchen = (KremkuchenImpl) item;
                System.out.println("Kremsorte: " + kremkuchen.getKremsorte());
            }
        }

        // Update inspection date for all items in the vending machine
        vendingMachine.updateInspectionDate();
    }
}
