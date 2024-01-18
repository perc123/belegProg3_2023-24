package listener;

import administration.HerstellerImpl;
import administration.HerstellerStorage;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import infrastructure.AllergeneAnzeigen.AllergeneAnzeigenEvent;
import infrastructure.AllergeneAnzeigen.AllergeneAnzeigenEventListener;
import infrastructure.HerstellerAnzeigen.HerstellerAnzeigenEvent;
import infrastructure.HerstellerAnzeigen.HerstellerAnzeigenEventListener;
import infrastructure.HerstellerEinfuegen.HerstellerEinfuegenEvent;
import infrastructure.HerstellerEinfuegen.HerstellerEinfuegenEventListener;
import infrastructure.HerstellerLoeschen.HerstellerLoeschenEvent;
import infrastructure.HerstellerLoeschen.HerstellerLoeschenEventListener;
import infrastructure.InspektionsdatumSetzen.InspektionsEvent;
import infrastructure.InspektionsdatumSetzen.InspektionsEventListener;
import infrastructure.KuchenAnzeigen.KuchenAnzeigenEvent;
import infrastructure.KuchenAnzeigen.KuchenAnzeigenEventListener;
import infrastructure.KuchenEinfuegen.KuchenEinfuegenEvent;
import infrastructure.KuchenEinfuegen.KuchenEinfuegenEventListener;
import infrastructure.KuchenLoeschen.KuchenLoeschenEvent;
import infrastructure.KuchenLoeschen.KuchenLoeschenEventListener;
import infrastructure.ModelSpeichern.ModelSpeichernLadenEvent;
import infrastructure.ModelSpeichern.ModelSpeichernEventListener;
import kuchen.Allergen;
import kuchen.Kremkuchen;
import saveJBP.JBP;
import verwaltung.Hersteller;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Listener implements HerstellerEinfuegenEventListener, KuchenEinfuegenEventListener, HerstellerLoeschenEventListener, KuchenLoeschenEventListener, InspektionsEventListener, AllergeneAnzeigenEventListener, KuchenAnzeigenEventListener, HerstellerAnzeigenEventListener, ModelSpeichernEventListener {

    HerstellerStorage herstellerStorage = new HerstellerStorage();

    private VendingMachine vendingMachine;

    public Listener(VendingMachine vendingMachine) {
        this.vendingMachine= vendingMachine;
    }

    @Override
    public void onHerstellerEinfuegenEvent(HerstellerEinfuegenEvent herstellerEinfuegenEvent) {
        HerstellerImpl hersteller = new HerstellerImpl(herstellerEinfuegenEvent.getHersteller());
        herstellerStorage.addHersteller(hersteller);
    }

    @Override
    public void onEinfuegenEvent(KuchenEinfuegenEvent kuchenEinfuegenEvent) {

        String kuchenTyp = null;

        HerstellerImpl hersteller = new HerstellerImpl(kuchenEinfuegenEvent.getHersteller());

        BigDecimal preis;
        try {
            preis = new BigDecimal(kuchenEinfuegenEvent.getPreis().replace(",", "."));
        } catch (IllegalArgumentException | ArithmeticException e) {
            return;
        }

        int naehrwert;
        try {
            naehrwert = Integer.parseInt(kuchenEinfuegenEvent.getNaehrwert());
        } catch (NumberFormatException e) {
            return;
        }

        Duration haltbarkeit;
        try {
            haltbarkeit = Duration.ofDays(Integer.parseInt(kuchenEinfuegenEvent.getHaltbarkeit()));
        } catch (NumberFormatException e) {
            return;
        }

        String[] allergenStrings = kuchenEinfuegenEvent.getAllergene().split(",");
        Collection<Allergen> allergene = new ArrayList<>();
        for (String allergenString : allergenStrings) {
            allergene.add(Allergen.valueOf(allergenString));
        }
        String sorte = kuchenEinfuegenEvent.getSorte();
        String obstsorte = kuchenEinfuegenEvent.getSorte();

        String[] sorteZwei = kuchenEinfuegenEvent.getSorteZwei();


        switch (kuchenEinfuegenEvent.getKuchentyp()) {
            case "Kremkuchen" -> {
                KremkuchenImpl kremkuchen = new KremkuchenImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte);
                vendingMachine.addItem(kremkuchen, hersteller);
            }
            case "Obstkuchen" -> {
                ObstkuchenImpl Obstkuchen = new ObstkuchenImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte);
                vendingMachine.addItem(Obstkuchen, hersteller);
            }
            case "Obsttorte" -> {
                ObsttorteImpl Obsttorte = new ObsttorteImpl(kuchenTyp, hersteller, allergene, naehrwert, haltbarkeit, preis, sorte, obstsorte);
                vendingMachine.addItem(Obsttorte, hersteller);
            }
        }
    }

    @Override
    public void onHerstellerLoeschenEvent(HerstellerLoeschenEvent event) {
        herstellerStorage.removeHersteller(event.getHersteller());
    }

    @Override
    public void onKuchenLoeschenEvent(KuchenLoeschenEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        vendingMachine.removeItem(fachnummer);
    }

    @Override
    public void onInspektionsEvent(InspektionsEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        vendingMachine.updateInspectionDate(fachnummer);
    }

    @Override
    public void onAllergeneAnzeigenEvent(AllergeneAnzeigenEvent event) {
/*        if (event.getAllergene().equals("allergene i")) {
            for (Allergen a : allergene) {
                System.out.println(a.toString());
            }
        } else if (event.getAllergene().equals("allergene e")) {
            for (Allergen a : allergene) {
                System.out.println(a.toString());
            }
        }*/
    }

    @Override
    public void onHerstellerAnzeigenEvent(HerstellerAnzeigenEvent event) {
        herstellerStorage.displayHersteller();
    }

    @Override
    public void onKuchenAnzeigenEvent(KuchenAnzeigenEvent event) {
        for (KuchenImpl cake : vendingMachine.listItems())
            System.out.println(cake);
    }

    @Override
    public void onModelSpeichernEvent(ModelSpeichernLadenEvent event) {
        JBP jbpVending = new JBP(vendingMachine); // Save the vending machine data
        JBP jbpHersteller = new JBP(herstellerStorage); // Save the manufacturer data

        if (event.getSpeicherArt().equals("saveJBP")) {
            jbpVending.serialisierenJBP();
            jbpHersteller.serialHerstellerJBP();        }
        if (event.getSpeicherArt().equals("loadJBP")) {
            VendingMachine loadedVendingMachine = jbpVending.deserialisierenJBP();
            HerstellerStorage loadedherstellerStorage = jbpHersteller.desirialHerstellerJBP();
            if (loadedVendingMachine != null) {
                vendingMachine.setModel(loadedVendingMachine);
            }
        }
        if (event.getSpeicherArt().equals("saveJOS")) {
            System.out.println("Not yet");        }
        if (event.getSpeicherArt().equals("loadJOS")) {
            System.out.println("Not yet");        }

    }
}
