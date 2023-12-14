package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obsttorte;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObsttorteImpl extends ObstkuchenImpl implements Obsttorte {
    private String kuchenTyp;
    private String kremsorte;

    public ObsttorteImpl(
            String kuchenTyp,
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            BigDecimal preis,
            String kremsorte,
            String obstsorte
    ) {
        super(kuchenTyp,hersteller, allergene, naehrwert, haltbarkeit, preis, obstsorte);
        this.kremsorte = kremsorte;
    }

    @Override
    public String getKuchenTyp() {
        kuchenTyp = "Obsttorte";
        return kuchenTyp;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }
}
