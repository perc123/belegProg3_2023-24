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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HerstellerImpl herstellerImpl) {
            return name.equals(herstellerImpl.name);
        } else {
            return false;
        }
    }

}
