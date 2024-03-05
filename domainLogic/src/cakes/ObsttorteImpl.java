package cakes;

import administration.HerstellerImpl;
import kuchen.Allergen;
import kuchen.Obsttorte;
import verwaltung.Hersteller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class ObsttorteImpl extends ObstkuchenImpl implements Obsttorte, Serializable {
    private String kremsorte;
    private String kuchenTyp;

    public ObsttorteImpl(
            String kuchenTyp,
            Hersteller hersteller,
            BigDecimal preis,
            int naehrwert,
            Duration haltbarkeit,
            Collection<Allergen> allergene,
            String sorteEins,
            String sorteZwei
    ) {
        super(kuchenTyp,hersteller, preis, naehrwert, haltbarkeit, allergene, sorteEins);
        this.kremsorte = sorteZwei;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
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
    public String getObstsorte() {
        return super.getObstsorte();
    }

    @Override
    public String getKuchenTyp() {
        kuchenTyp = "Obsttorte";
        return kuchenTyp;
    }
}
