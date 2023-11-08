package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obstkuchen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen {
    private String kuchenTyp;
    private String obstsorte;

    public ObstkuchenImpl(
            String kuchenTyp,
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            BigDecimal preis,
            String obstsorte
            //Date inspektionsdatum
    ) {
        super(kuchenTyp,hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getKuchenTyp() {
        kuchenTyp = "Obstkuchen";
        return kuchenTyp;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }
}
