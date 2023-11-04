package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obstkuchen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen {
    private String obstsorte;

    public ObstkuchenImpl(
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            String obstsorte,
            BigDecimal preis,
            Date inspektionsdatum
    ) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis, inspektionsdatum);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }
}