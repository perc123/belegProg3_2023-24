package listener;



import UDP.ServerUDP;
import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.KuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import infrastructure.AddCake.AddCakeEvent;
import infrastructure.AddCake.AddCakeEventListener;
import infrastructure.AddManufacturer.AddHerstellerEvent;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import infrastructure.InspectionsDate.InspectionEvent;
import infrastructure.InspectionsDate.InspectionEventListener;
import infrastructure.PrintAllergies.PrintAllergiesEvent;
import infrastructure.PrintAllergies.PrintAllergiesEventListener;
import infrastructure.PrintCakes.PrintCakeEvent;
import infrastructure.PrintCakes.PrintCakeEventListener;
import infrastructure.PrintManufacturers.PrintHerstellerEvent;
import infrastructure.PrintManufacturers.PrintHerstellerEventListener;
import infrastructure.RemoveCake.RemoveCakeEvent;
import infrastructure.RemoveCake.RemoveCakeEventListener;
import infrastructure.RemoveManufacturer.RemoveHerstellerEvent;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventListener;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEvent;
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

public class NetListenerUDP implements AddHerstellerEventListener, AddCakeEventListener, RemoveHerstellerEventListener, RemoveCakeEventListener, InspectionEventListener, PrintAllergiesEventListener, PrintCakeEventListener, PrintHerstellerEventListener, SaveVendingMachineEventListener {


    private final ServerUDP server;

    public NetListenerUDP(VendingMachine vendingMachine, ServerUDP server) {
        SingletonVendingMachine.getInstance().setVendingMachine(vendingMachine);
        this.server = server;
    }

    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent addHerstellerEvent) {
        HerstellerImpl hersteller = new HerstellerImpl(addHerstellerEvent.getHersteller());
        SingletonVendingMachine.getInstance().getVendingMachine().addHersteller(hersteller);
        server.sendInfoToServer("One successful operation");
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

        String sorteZwei = addCakeEvent.getSorteZwei();


        switch (addCakeEvent.getKuchentyp()) {
            case "Kremkuchen" -> {
                KremkuchenImpl kremkuchen = new KremkuchenImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit,allergene, sorte);
                SingletonVendingMachine.getInstance().getVendingMachine().addItem(kremkuchen);
                server.sendInfoToServer("Kremkuchen added");
            }
            case "Obstkuchen" -> {
                ObstkuchenImpl Obstkuchen = new ObstkuchenImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit,allergene, sorte);
                SingletonVendingMachine.getInstance().getVendingMachine().addItem(Obstkuchen);
                server.sendInfoToServer("Obstkuchen added");            }
            case "Obsttorte" -> {
                ObsttorteImpl Obsttorte = new ObsttorteImpl(kuchenTyp, hersteller, preis, naehrwert, haltbarkeit,allergene, sorte, sorteZwei);
                SingletonVendingMachine.getInstance().getVendingMachine().addItem(Obsttorte);
                server.sendInfoToServer("Obsttorte added");            }
        }
    }

    @Override
    public void onRemoveHerstellerEvent(RemoveHerstellerEvent event) {
        SingletonVendingMachine.getInstance().getVendingMachine().removeHersteller(event.getHersteller());
        server.sendInfoToServer("One successful operation");    }

    @Override
    public void onRemoveCakeEvent(RemoveCakeEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        SingletonVendingMachine.getInstance().getVendingMachine().removeItem(fachnummer);
        server.sendInfoToServer("One successful operation");
    }

    @Override
    public void onInspectionEvent(InspectionEvent event) {
        int fachnummer = Integer.parseInt(event.getFachnummer());
        SingletonVendingMachine.getInstance().getVendingMachine().updateInspectionDate(fachnummer);
        server.sendInfoToServer("One successful operation");
    }

    @Override
    public void onPrintAllergiesEvent(PrintAllergiesEvent event) {
        if (event.getAllergene().equals("allergene i")) {
            List<Allergen> allergene = SingletonVendingMachine.getInstance().getVendingMachine().printAllergies(true);
            server.sendAllergenListToServer(allergene);
        } else if (event.getAllergene().equals("allergene e")) {
            List<Allergen> allergene = SingletonVendingMachine.getInstance().getVendingMachine().printAllergies(false);
            server.sendAllergenListToServer(allergene);
        }
    }

    @Override
    public void onPrintHerstellerEvent(PrintHerstellerEvent event) {
        List<HerstellerImpl> herstellerList = SingletonVendingMachine.getInstance().getVendingMachine().getHerstellerList();
        server.sendHerstellerListToServer(herstellerList); // Sends the list to client
    }

    @Override
    public void onPrintCakeEvent(PrintCakeEvent event) {
        List<KuchenImpl> cakeList = SingletonVendingMachine.getInstance().getVendingMachine().printCake(event.getKuchenTyp());
        server.sendKuchenListToServer(cakeList);
    }


    @Override
    public void onSaveVendingMachineEvent(SaveVendingMachineEvent event) {
        JOS jos = new JOS(SingletonVendingMachine.getInstance().getVendingMachine());
        JBP jbp = new JBP(SingletonVendingMachine.getInstance().getVendingMachine());
        if (event.getTypeOfSave().equals("saveJOS")) {
            jos.serialisierenJOS();
            server.sendInfoToServer("One successful operation");
        }
        if (event.getTypeOfSave().equals("loadJOS")) {
            VendingMachine loadedVendingMachine = jos.deserialisierenJOS();
            if (loadedVendingMachine != null) {
                SingletonVendingMachine.getInstance().setVendingMachine(loadedVendingMachine);
                server.sendInfoToServer("One successful operation");
            }
        }
        if (event.getTypeOfSave().equals("saveJBP")) {
            jbp.serialisierenJBP();
            server.sendInfoToServer("One successful operation");
        }
        if (event.getTypeOfSave().equals("loadJBP")) {
            VendingMachine deserializationVendingMachine = jbp.deserialisierenJBP();
            if (deserializationVendingMachine != null) {
                SingletonVendingMachine.getInstance().setVendingMachine(deserializationVendingMachine);
                server.sendInfoToServer("One successful operation");
            }
        }
    }
}


