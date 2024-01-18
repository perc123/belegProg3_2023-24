package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Kremkuchen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, Verkaufsobjekt, Serializable {
    private String kuchenTyp;
    private String kremsorte;

    public KremkuchenImpl(
            String kuchenTyp,
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            BigDecimal preis,
            String kremsorte
    ) {
        super(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremsorte;
    }
    @Override
    public String getKuchenTyp() {
        kuchenTyp = "Kremkuchen";
        return kuchenTyp;
    }
    @Override
    public String getKremsorte() {
        return kremsorte;
    }
}
