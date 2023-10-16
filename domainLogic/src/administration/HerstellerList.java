package administration;

import verwaltung.Hersteller;

import java.util.ArrayList;
import java.util.List;

public class HerstellerList {
    private List<Hersteller> herstellerList;

    public HerstellerList() {
        this.herstellerList = new ArrayList<>();
    }

    public void addHersteller(HerstellerImpl hersteller) {
        herstellerList.add(hersteller);
    }

    public void removeHersteller(Hersteller hersteller) {
        herstellerList.remove(hersteller);
    }

    public List<Hersteller> getAllHersteller() {
        return herstellerList;
    }
}