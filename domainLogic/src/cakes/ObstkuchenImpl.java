package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltung.Hersteller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen, Serializable {
    private String kuchenTyp;
    private String obstsorte;

    public ObstkuchenImpl(
            String kuchenTyp,
            Hersteller hersteller,
            BigDecimal preis,
            int naehrwert,
            Duration haltbarkeit,
            Collection<Allergen> allergene,
            String sorte
    ) {
        super(kuchenTyp,hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.obstsorte = sorte;
        this.getInspektionsdatum();
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
        kuchenTyp = "Obstkuchen";
        return kuchenTyp;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }
}
