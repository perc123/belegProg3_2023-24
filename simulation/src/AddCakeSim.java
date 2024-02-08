import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class AddCakeSim extends Thread{
    private final VendingMachine vendingMachine;
    private final Random random;
    private final Lock lock;

    public AddCakeSim(VendingMachine vendingMachine, Lock lock, long seed) {
        this.vendingMachine = vendingMachine;
        this.lock = lock;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                String[] randomCakeInfo = createRandomCakeInfo();
                KuchenImpl randomCake = createRandomCake(randomCakeInfo);
                System.out.println(randomCake.getKuchenTyp() + " Probiert Kuchen hinzufuegen");
                vendingMachine.addItem(randomCake);
            } finally {
                lock.unlock();
            }
        }
    }

    private String[] createRandomCakeInfo() {
        String kuchenTyp = random.nextBoolean() ? "Kremkuchen" : "Obstkuchen";
        String herstellerName = "Hersteller" + (random.nextInt(10) + 1);
        int naehwert = random.nextInt(10) + 1;
        int duration = random.nextInt(10) + 1;
        String kremsorte = random.nextBoolean() ? "Schokolade" : "Honig";
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
