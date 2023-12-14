package cakes;

import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen {
    private String kuchenTyp;
    private String obstsorte;

    public ObstkuchenImpl(
            String kuchenTyp,
            Hersteller hersteller,
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
