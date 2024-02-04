package saveJOS;



import administration.VendingMachine;

import java.io.*;

public class JOS {

    private final VendingMachine vendingMachine;

    public JOS(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void serialisierenJOS() {
        File folder = new File("src/serialisierung/speicherstandJOS/");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Konnte Ordner nicht erstellen: " + folder);
                return;
            }
        }
        File file = new File(folder, "saveModel.ser");
        try (FileOutputStream outputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(vendingMachine);
        } catch (IOException e) {
            System.out.println("Ein I/O-Fehler ist aufgetreten: " + e.getMessage());
        }
    }


    public VendingMachine deserialisierenJOS(){
        File file = new File("src/serialisierung/speicherstandJOS/saveModel.ser");
        VendingMachine vendingMachine = null;

        try (FileInputStream inputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            vendingMachine = (VendingMachine) objectInputStream.readObject();
            AllergenObserver allergenObserver = new AllergenObserver(vendingMachine);
            vendingMachine.add(allergenObserver);
            KapazitaetsObserver kapazitaetsObserver = new KapazitaetsObserver(vendingMachine);
            vendingMachine.add(kapazitaetsObserver);
        } catch (FileNotFoundException e) {
            System.out.println("Die Datei ist nicht vorhanden: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Ein I/O-Fehler ist aufgetreten: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Die Model-Klasse wurde nicht gefunden: " + e.getMessage());
        }

        return vendingMachine;
    }
}