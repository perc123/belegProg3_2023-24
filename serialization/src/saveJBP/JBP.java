package saveJBP;
import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;
import observer.AllergiesObserver;
import observer.CapacityObserver;

import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class JBP {

    private final VendingMachine vendingMachine;

    public JBP(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    //Serialize Vending Machine
    public void serialisierenJBP() {
        File folder = new File("/serialization/src/saveModeJBP/");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Could not create folder: " + folder);
            }
        }
        File file = new File(folder, "saveModeJBP.xml");


        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            encoder.setPersistenceDelegate(VendingMachine.class, new DefaultPersistenceDelegate(new String[]{"capacity", "inventory", "herstellerList"}));
            encoder.setPersistenceDelegate(HerstellerImpl.class, new DefaultPersistenceDelegate(new String[]{"name"}));
            encoder.setPersistenceDelegate(KremkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp","hersteller", "allergene", "naehrwert", "haltbarkeit", "preis","sorte"}));
            encoder.setPersistenceDelegate(ObstkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp","hersteller", "allergene", "naehrwert", "haltbarkeit", "preis","sorte"}));
            encoder.setPersistenceDelegate(ObsttorteImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp","hersteller", "allergene", "naehrwert", "haltbarkeit", "preis","sorteEins", "sorteZwei"}));
            //https://gist.github.com/sachin-handiekar/185f5de2a9e027783a9f
            encoder.setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {
                        protected Expression instantiate(Object oldInstance, Encoder out) {
                            BigDecimal bd = (BigDecimal) oldInstance;
                            return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{
                                    bd.toString()
                            });
                        }

                        protected boolean mutatesTo(Object oldInstance, Object newInstance) {
                            return oldInstance.equals(newInstance);
                        }
                    }

            );
            //https://stackoverflow.com/questions/41373566/localdate-serialization-error
            encoder.setPersistenceDelegate(Duration.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    Duration.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });
            encoder.setPersistenceDelegate(LocalDateTime.class, new PersistenceDelegate() {
                @Override
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    LocalDateTime dateTime = (LocalDateTime) oldInstance;
                    return new Expression(
                            dateTime,
                            LocalDateTime.class,
                            "of",
                            new Object[]{
                                    dateTime.getYear(),
                                    dateTime.getMonth(),
                                    dateTime.getDayOfMonth(),
                                    dateTime.getHour(),
                                    dateTime.getMinute(),
                                    dateTime.getSecond(),
                                    dateTime.getNano()
                            }
                    );
                }
            });
            encoder.writeObject(vendingMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public VendingMachine deserialisierenJBP() {
        VendingMachine vendingMachine= null;
        File file = new File("/serialization/src/saveModeJBP/saveModeJBP.xml");

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            vendingMachine = (VendingMachine) decoder.readObject();
            AllergiesObserver allergiesObserver = new AllergiesObserver(vendingMachine);
            vendingMachine.add(allergiesObserver);
            CapacityObserver capacityObserver = new CapacityObserver(vendingMachine);
            vendingMachine.add(capacityObserver);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + file.getAbsolutePath());
        }
        return vendingMachine;
    }

}
