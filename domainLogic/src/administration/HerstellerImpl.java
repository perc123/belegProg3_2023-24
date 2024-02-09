package administration;

import verwaltung.Hersteller;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private String name;

    public HerstellerImpl() {
    }
    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCakeCount(int cakeCount) {

    }

    @Override
    public int getCakeCount() {
        return 0;
    }

}
