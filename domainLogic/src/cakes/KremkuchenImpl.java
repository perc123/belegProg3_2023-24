package cakes;

import kuchen.Allergen;
import kuchen.Kremkuchen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, Verkaufsobjekt {
    private String kremsorte;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;

    public KremkuchenImpl(
            Hersteller hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            String kremsorte,
            BigDecimal preis,
            Date inspektionsdatum,
            int fachnummer
    ) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis, inspektionsdatum, fachnummer);
        this.kremsorte = kremsorte;
        this.preis = preis;
        this.inspektionsdatum = inspektionsdatum;
        this.fachnummer = fachnummer;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }

    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return fachnummer;
    }
}
