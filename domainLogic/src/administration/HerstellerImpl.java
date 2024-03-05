package administration;

import verwaltung.Hersteller;

import java.io.Serial;
import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private int cakeCount;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCakeCount(int cakeCount) {
        this.cakeCount = cakeCount;
    }

    @Override
    public int getCakeCount() {
        return cakeCount;
    }

}
