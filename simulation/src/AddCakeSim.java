import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.Set;

public class AddCakeSim {
    private final VendingMachine vendingMachine;
    private final Random random = new Random();

    public AddCakeSim(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void addRandomCake() {
        // Synchronize critical section for accessing and modifying the vending machine
        synchronized (vendingMachine) {
            String[] randomCakeInfo = createRandomCakeInfo();
            KuchenImpl randomCake = createRandomCake(randomCakeInfo);

            vendingMachine.addItem(randomCake, randomCake.getHersteller());

            System.out.println("Thread " + Thread.currentThread().getId() +
                    ", Cake added, cake" + randomCake.getFachnummer());
        }
    }

    private String[] createRandomCakeInfo() {
        String kuchenTyp = "Kremkuchen";
        String herstellerName = "Hersteller" + (random.nextInt(10) + 1);
        int naehwert = random.nextInt(10) + 1;
        int duration = random.nextInt(10) + 1;
        String kremsorte = "Schokolade";
        double preis = (random.nextInt(10) + 1) * 2.5;

        return new String[]{kuchenTyp, herstellerName, String.valueOf(naehwert), String.valueOf(duration), kremsorte, String.valueOf(preis)};
    }

    private KuchenImpl createRandomCake(String[] randomCakeInfo) {
        String kuchenTyp = randomCakeInfo[0];
        HerstellerImpl hersteller = new HerstellerImpl(randomCakeInfo[1]);
        Set<Allergen> allergy = Set.of(Allergen.Haselnuss);
        int naehwert = Integer.parseInt(randomCakeInfo[2]);
        Duration duration = Duration.ofDays(Integer.parseInt(randomCakeInfo[3]));
        String kremsorte = randomCakeInfo[4];
        BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(randomCakeInfo[5]));

        return new KremkuchenImpl(kuchenTyp, hersteller, allergy, naehwert, duration, preis, kremsorte);
    }
}
