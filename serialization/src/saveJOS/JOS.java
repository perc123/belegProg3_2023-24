package saveJOS;



import administration.VendingMachine;
import observer.AllergiesObserver;
import observer.CapacityObserver;

import java.io.*;

public class JOS {

    private final VendingMachine vendingMachine;

    public JOS(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void serialisierenJOS() {
        File folder = new File("/serialization/src/saveModeJOS/");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Could not create folder: " + folder);
                return;
            }
        }
        File file = new File(folder, "saveMode.ser");
        try (FileOutputStream outputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(vendingMachine);
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }


    public VendingMachine deserialisierenJOS(){
        File file = new File("/serialization/src/saveModeJOS/saveMode.ser");
        VendingMachine vendingMachine = null;

        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            vendingMachine = (VendingMachine) objectInputStream.readObject();
            AllergiesObserver allergiesObserver = new AllergiesObserver(vendingMachine);
            vendingMachine.add(allergiesObserver);
            CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
            vendingMachine.add(capacityObserver);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Vending machine not found: " + e.getMessage());
        }

        return vendingMachine;
    }
}