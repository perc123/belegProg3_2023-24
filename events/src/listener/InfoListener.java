package listener;


import infrastructure.AddManufacturer.AddHerstellerEvent;
import infrastructure.PrintAllergies.PrintAllergiesEvent;
import infrastructure.PrintAllergies.PrintAllergiesEventListener;
import infrastructure.PrintManufacturers.PrintHerstellerEvent;
import infrastructure.PrintManufacturers.PrintHerstellerEventListener;
import infrastructure.AddManufacturer.AddHerstellerEventListener;
import infrastructure.RemoveManufacturer.RemoveHerstellerEvent;
import infrastructure.RemoveManufacturer.RemoveHerstellerEventListener;
import infrastructure.InspectionsDate.InspectionEvent;
import infrastructure.InspectionsDate.InspectionEventListener;
import infrastructure.PrintCakes.PrintCakeEvent;
import infrastructure.PrintCakes.PrintCakeEventListener;
import infrastructure.AddCake.AddCakeEvent;
import infrastructure.AddCake.AddCakeEventListener;
import infrastructure.RemoveCake.RemoveCakeEvent;
import infrastructure.RemoveCake.RemoveCakeEventListener;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEvent;
import infrastructure.SaveAndLoadVendingMachine.SaveVendingMachineEventListener;

public class InfoListener implements AddHerstellerEventListener, AddCakeEventListener, RemoveHerstellerEventListener, RemoveCakeEventListener, InspectionEventListener, PrintAllergiesEventListener, PrintCakeEventListener, PrintHerstellerEventListener, SaveVendingMachineEventListener {
    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent event) {
        System.out.println("A manufacturer was added");
    }

    @Override
    public void onAddEvent(AddCakeEvent event) {
        System.out.println("A cake was added");
    }

    @Override
    public void onRemoveHerstellerEvent(RemoveHerstellerEvent event) {
        System.out.println("A manufacturer was removed");
    }

    @Override
    public void onRemoveCakeEvent(RemoveCakeEvent event) {
        System.out.println("A cake was removed");
    }

    @Override
    public void onInspectionEvent(InspectionEvent event) {
        System.out.println("A date was updated");
    }

    @Override
    public void onPrintAllergiesEvent(PrintAllergiesEvent event) {
        System.out.println("Print allergies");
    }

    @Override
    public void onPrintHerstellerEvent(PrintHerstellerEvent event) {
        System.out.println("Print manufacturer");
    }

    @Override
    public void onPrintCakeEvent(PrintCakeEvent event) {
        System.out.println("Print cake");
    }

    @Override
    public void onSaveVendingMachineEvent(SaveVendingMachineEvent event) {
        System.out.println("Serialize vending machine");
    }
}
