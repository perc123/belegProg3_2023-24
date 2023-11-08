package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obstkuchen;
import kuchen.Obsttorte;
import verwaltung.Verkaufsobjekt;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObsttorteImpl extends ObstkuchenImpl implements Obsttorte {
    private String kremsorte;

    public ObsttorteImpl(
            String kuchenTyp,
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            String obstsorte,
            String kremsorte,
            BigDecimal preis
            //Date inspektionsdatum
    ) {
        super(kuchenTyp,hersteller, allergene, naehrwert, haltbarkeit, preis, obstsorte);
        this.kremsorte = kremsorte;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }
}
