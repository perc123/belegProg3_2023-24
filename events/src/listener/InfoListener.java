package listener;


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

public class InfoListener implements HerstellerEinfuegenEventListener, KuchenEinfuegenEventListener, HerstellerLoeschenEventListener, KuchenLoeschenEventListener, InspektionsEventListener, AllergeneAnzeigenEventListener, KuchenAnzeigenEventListener, HerstellerAnzeigenEventListener, ModelSpeichernEventListener {
    @Override
    public void onHerstellerEinfuegenEvent(HerstellerEinfuegenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onEinfuegenEvent(KuchenEinfuegenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onHerstellerLoeschenEvent(HerstellerLoeschenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onKuchenLoeschenEvent(KuchenLoeschenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onInspektionsEvent(InspektionsEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onAllergeneAnzeigenEvent(AllergeneAnzeigenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onHerstellerAnzeigenEvent(HerstellerAnzeigenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onKuchenAnzeigenEvent(KuchenAnzeigenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }

    @Override
    public void onModelSpeichernEvent(ModelSpeichernLadenEvent event) {
        System.out.println("Eine Operation wurde ausgefuehrt");
    }
}
