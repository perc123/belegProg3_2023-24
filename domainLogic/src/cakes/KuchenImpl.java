package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Kuchen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KuchenImpl implements Kuchen, Verkaufsobjekt {
    private HerstellerImpl hersteller;
    private Collection<Allergen> allergene;
    private int naehrwert;
    private Duration haltbarkeit;
    private BigDecimal preis;          // Added property
    private Date inspektionsdatum;
    private int fachnummer;             // Added property

    public KuchenImpl(
            HerstellerImpl hersteller,
            Collection<Allergen> allergene,
            int naehrwert,
            Duration haltbarkeit,
            BigDecimal preis,
            Date inspektionsdatum
    ) {
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;             // Initialize the preis property
        this.inspektionsdatum = inspektionsdatum;
    }

    @Override
    public Hersteller getHersteller() {
        return hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return inspektionsdatum;
    }

    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    @Override
    public int getFachnummer() {
        return fachnummer;
    }

    public void setInspektionsdatum(Date currentDate) {
        this.inspektionsdatum = currentDate;
    }
}
