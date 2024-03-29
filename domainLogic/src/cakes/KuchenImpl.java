package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Kuchen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

public abstract class KuchenImpl implements Kuchen, Verkaufsobjekt, Serializable {
    private String kuchenTyp;
    private HerstellerImpl hersteller;
    private Collection<Allergen> allergene;
    private int naehrwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;

    public KuchenImpl(
            String kuchenTyp,
            Hersteller hersteller,
            BigDecimal preis,
            int naehrwert,
            Duration haltbarkeit,
            Collection<Allergen> allergene
            ) {
        this.hersteller = (HerstellerImpl) hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
    }

    public String getKuchenTyp() {
        return kuchenTyp;
    }
    @Override
    public HerstellerImpl getHersteller() {
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

    public void setInspektionsdatum(Date inspektionsdatum) {
        this.inspektionsdatum = inspektionsdatum;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getFormattedInspectionDate() {
        return DATE_FORMAT.format(inspektionsdatum);
    }

    public long calculateRemainingShelfLife() {
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(getHaltbarkeit());

        long remainingDays = Duration.between(currentDate, expirationDate).toDays();


        if (remainingDays >= 0) {
            return remainingDays;
        } else {
            return -1;
        }
    }
}