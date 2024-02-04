package listener;

import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import infrastructure.AddCake.AddCakeEvent;
import infrastructure.RemoveCake.RemoveCakeEvent;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEvent;
import infrastructure.PrintAllergies.PrintAllergiesEvent;
import infrastructure.PrintAllergies.PrintAllergiesEventListener;
import infrastructure.PrintCakes.PrintCakeEvent;
import infrastructure.PrintManufacturers.PrintHerstellerEvent;
import infrastructure.PrintManufacturers.PrintHerstellerEventListener;
import infrastructure.AddManufacturer.AddHerstellerEvent;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import infrastructure.RemoveManufacturer.RemoveHerstellerEvent;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventListener;
import infrastructure.InspectionsDate.InspectionEvent;
import infrastructure.InspectionsDate.InspectionEventListener;
import infrastructure.PrintCakes.PrintCakeEventListener;
import infrastructure.AddCake.AddCakeEventListener;
import infrastructure.RemoveCake.RemoveCakeEventListener;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventListener;
import kuchen.Allergen;
import saveJBP.JBP;
import saveJOS.JOS;
import singletonPattern.SingletonVendingMachine;
import verwaltung.Hersteller;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Listener implements AddHerstellerEventListener, AddCakeEventListener, RemoveHerstellerEventListener, RemoveCakeEventListener, InspectionEventListener, PrintAllergiesEventListener, PrintCakeEventListener, PrintHerstellerEventListener, SaveVendingMachineEventListener {

    //private HerstellerStorage herstellerStorage;
    //HerstellerStorage herstellerStorage = new HerstellerStorage();

    private final VendingMachine vendingMachine;

    public Listener(VendingMachine vendingMachine) {
        SingletonVendingMachine.getInstance().setVendingMachine(vendingMachine);
        this.vendingMachine= SingletonVendingMachine.getInstance().getVendingMachine();
    }

    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent addHerstellerEvent) {
        HerstellerImpl hersteller = new HerstellerImpl(addHerstellerEvent.getHersteller());
        SingletonVendingMachine.getInstance().getVendingMachine().addHersteller(hersteller);;
    }

    @Override
    public void onAddEvent(AddCakeEvent addCakeEvent) {

        String kuchenTyp = null;

        HerstellerImpl hersteller = new HerstellerImpl(addCakeEvent.getHersteller());

        BigDecimal preis;
        try {
            preis = new BigDecimal(addCakeEvent.getPreis().replace(",", "."));
        } catch (IllegalArgumentException | ArithmeticException e) {
            return;
        }

        int naehrwert;
        try {
            naehrwert = Integer.parseInt(addCakeEvent.getNaehrwert());
        } catch (NumberFormatException e) {
            return;
        }

        Duration haltbarkeit;
        try {
            haltbarkeit = Duration.ofDays(Integer.parseInt(addCakeEvent.getHaltbarkeit()));
        } catch (NumberFormatException e) {
            return;
        }

        String[] allergenStrings = addCakeEvent.getAllergene().split(",");
        Collection<Allergen> allergene = new ArrayList<>();
        for (String allergenString : allergenStrings) {
            allergene.add(Allergen.valueOf(allergenString));
        }
        String sorte = addCakeEvent.getSorte();
        String obstsorte = addCakeEvent.getSorte();

        String[] sorteZwei = addCakeEvent.getSorteZwei();


        switch (addCakeEvent.getKuchentyp()) {
            case "Kremkuchen" -> {
                KremkuchenImpl kremkuchen = new KremkuchenImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte);
                vendingMachine.addItem(kremkuchen);
            }
            case "Obstkuchen" -> {
                ObstkuchenImpl Obstkuchen = new ObstkuchenImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte);
                vendingMachine.addItem(Obstkuchen);
            }
            case "Obsttorte" -> {
                ObsttorteImpl Obsttorte = new ObsttorteImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte, obstsorte);
                vendingMachine.addItem(Obsttorte);
            }
        }
    }

    @Override
    public void onRemoveHerstellerEvent(RemoveHerstellerEvent event) {
        SingletonVendingMachine.getInstance().getVendingMachine().removeHersteller(event.getHersteller());
    }

    @Override
    public void onRemoveCakeEvent(RemoveCakeEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        SingletonVendingMachine.getInstance().getVendingMachine().removeItem(fachnummer);
        //vendingMachine.removeItem(fachnummer);
    }

    @Override
    public void onInspectionEvent(InspectionEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        SingletonVendingMachine.getInstance().getVendingMachine().updateInspectionDate(fachnummer);
    }

    @Override
    public void onPrintAllergiesEvent(PrintAllergiesEvent event) {
        if (event.getAllergene().equals("allergene i")) {
            List<Allergen> allergene = SingletonVendingMachine.getInstance().getVendingMachine().allergeneAbrufen(true);
            for (Allergen a : allergene) {
                System.out.println(a.toString());
            }
        } else if (event.getAllergene().equals("allergene e")) {
            List<Allergen> allergene = SingletonVendingMachine.getInstance().getVendingMachine().allergeneAbrufen(false);
            for (Allergen a : allergene) {
                System.out.println(a.toString());
            }
        }
    }

    @Override
    public void onPrintHerstellerEvent(PrintHerstellerEvent event) {
        List<Hersteller> herstellerList = SingletonVendingMachine.getInstance().getVendingMachine().callHersteller();
        for (Hersteller hersteller : herstellerList) {
            System.out.println("Manufacturer name: " + hersteller + " | Number of cakes: " + hersteller.getCakeCount());
        }
    }

    @Override
    public void onPrintCakeEvent(PrintCakeEvent event) {
        List<KuchenImpl> cakeList = SingletonVendingMachine.getInstance().getVendingMachine().printCake(event.getKuchenTyp());
        for (KuchenImpl cake : cakeList)
            System.out.println(cake);
    }

    @Override
    public void onSaveVendingMachineEvent(SaveVendingMachineEvent event) {
        JOS jos = new JOS(vendingMachine);
        JBP jbp = new JBP(vendingMachine);
        if (event.getTypeOfSave().equals("saveJOS")) {
            jos.serialisierenJOS();
        }
        if (event.getTypeOfSave().equals("loadJOS")) {
            VendingMachine loadedVendingMachine = jos.deserialisierenJOS();
            if (loadedVendingMachine != null) {
                SingletonVendingMachine.getInstance().setVendingMachine(loadedVendingMachine);
            }
        }
        if (event.getTypeOfSave().equals("saveJBP")) {
            jbp.serialisierenJBP();
        }
        if (event.getTypeOfSave().equals("loadJBP")) {
            VendingMachine deserializationVendingMachine = jbp.deserialisierenJBP();
            if (deserializationVendingMachine != null) {
                SingletonVendingMachine.getInstance().setVendingMachine(deserializationVendingMachine);
            }
        }
    }
}
