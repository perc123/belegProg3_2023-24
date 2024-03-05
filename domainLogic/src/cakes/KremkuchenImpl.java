package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Kremkuchen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, Verkaufsobjekt, Serializable {
    private String kuchenTyp;
    private String kremsorte;


    public KremkuchenImpl(
            String kuchenTyp,
            Hersteller hersteller,
            BigDecimal preis,
            int naehrwert,
            Duration haltbarkeit,
            Collection<Allergen> allergene,
            String sorte
    ) {
        super(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.kremsorte = sorte;
    }


    @Override
    public HerstellerImpl getHersteller() {
        return super.getHersteller();
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return super.getAllergene();
    }

    @Override
    public int getNaehrwert() {
        return super.getNaehrwert();
    }

    @Override
    public Duration getHaltbarkeit() {
        return super.getHaltbarkeit();
    }

    @Override
    public BigDecimal getPreis() {
        return super.getPreis();
    }

    @Override
    public Date getInspektionsdatum() {
        return super.getInspektionsdatum();
    }

    @Override
    public void setFachnummer(int fachnummer) {
        super.setFachnummer(fachnummer);
    }

    @Override
    public int getFachnummer() {
        return super.getFachnummer();
    }

    @Override
    public void setInspektionsdatum(Date currentDate) {
        super.setInspektionsdatum(currentDate);
    }

    @Override
    public String getFormattedInspectionDate() {
        return super.getFormattedInspectionDate();
    }

    @Override
    public long calculateRemainingShelfLife() {
        return super.calculateRemainingShelfLife();
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
