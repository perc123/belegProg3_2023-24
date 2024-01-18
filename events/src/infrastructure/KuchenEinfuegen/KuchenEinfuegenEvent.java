package infrastructure.KuchenEinfuegen;

import java.util.EventObject;

public class KuchenEinfuegenEvent extends EventObject {

    private final String kuchentyp;
    private final String hersteller;
    private final String preis;
    private final String naehrwert;
    private final String haltbarkeit;
    private final String allergene;
    private final String sorte;
    private final String[] sorteZwei;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public KuchenEinfuegenEvent(Object source, String kuchentyp, String hersteller, String preis, String naehrwert, String haltbarkeit, String allergene, String sorte, String... sorteZwei) {
        super(source);
        this.kuchentyp = kuchentyp;
        this.hersteller = hersteller;
        this.preis = preis;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.allergene = allergene;
        this.sorte = sorte;
        if (sorteZwei == null || sorteZwei.length == 0) {
            this.sorteZwei = new String[] { "error" };
        } else {
            this.sorteZwei = sorteZwei;
        }
    }

    public String getKuchentyp() {
        return kuchentyp;
    }

    public String getHersteller() {
        return hersteller;
    }

    public String getPreis() {
        return preis;
    }

    public String getNaehrwert() {
        return naehrwert;
    }

    public String getHaltbarkeit() {
        return haltbarkeit;
    }

    public String getAllergene() {
        return allergene;
    }

    public String getSorte() {
        return sorte;
    }

    public String[] getSorteZwei() {
        return sorteZwei;
    }

}
