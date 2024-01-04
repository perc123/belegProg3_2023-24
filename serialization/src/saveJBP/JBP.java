package saveJBP;


import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDateTime;

import administration.HerstellerImpl;
import administration.HerstellerStorage;
import administration.VendingMachine;
import cakes.KuchenImpl;
import verwaltung.Hersteller;


import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

public class JBP implements Serializable {

    private final VendingMachine vendingMachine;
    private final HerstellerStorage herstellerStorage;

    public JBP(VendingMachine vendingMachine, HerstellerStorage herstellerStorage) {
        this.vendingMachine = vendingMachine;
        this.herstellerStorage = herstellerStorage;
    }

    public void serializeData() {
        try {
            serializeVendingMachine();
            serializeHerstellerStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VendingMachine deserializeData() {
        try {
            deserializeVendingMachine();
            deserializeHerstellerStorage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vendingMachine;
    }

    private void serializeVendingMachine() throws IOException {
        serializeObject(vendingMachine, "saveVendingMachine.xml");
    }

    private void deserializeVendingMachine() throws IOException, ClassNotFoundException {
        vendingMachine.setModel(deserializeObject("saveVendingMachine.xml", VendingMachine.class));
    }

    private void serializeHerstellerStorage() throws IOException {
        serializeObject(herstellerStorage, "saveHerstellerStorage.xml");
    }

    private void deserializeHerstellerStorage() throws IOException, ClassNotFoundException {
        HerstellerStorage loadedHerstellerStorage = deserializeObject("saveHerstellerStorage.xml", HerstellerStorage.class);
        if (loadedHerstellerStorage != null) {
            herstellerStorage.getAllHersteller().clear();

            for (Hersteller hersteller : loadedHerstellerStorage.getAllHersteller()) {
                herstellerStorage.addHersteller((HerstellerImpl) hersteller);
            }
        }
    }

    private void serializeObject(Object object, String fileName) throws IOException {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            addPersistenceDelegates(encoder);
            encoder.writeObject(object);
        }
    }

    private <T> T deserializeObject(String fileName, Class<T> type) throws IOException, ClassNotFoundException {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
            return type.cast(decoder.readObject());
        }
    }

    private void addPersistenceDelegates(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(ArrayList.class, new DefaultPersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, oldInstance.getClass(), "new", null);
            }
        });

        encoder.setPersistenceDelegate(LinkedList.class, new DefaultPersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, ArrayList.class, "new", null);
            }
        });

        encoder.setPersistenceDelegate(VendingMachine.class, new DefaultPersistenceDelegate(new String[]{"inventory", "capacity"}));
        encoder.setPersistenceDelegate(KuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"fachnummer", "inspektionsdatum", "hersteller", "kuchenTyp", "allergene", "naehrwert", "haltbarkeit", "preis"}));
        encoder.setPersistenceDelegate(HerstellerImpl.class, new DefaultPersistenceDelegate(new String[]{"name"}));

        //encoder.setPersistenceDelegate(HerstellerStorage.class, new DefaultPersistenceDelegate(new String[]{"herstellerList"}));

        encoder.setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BigDecimal bd = (BigDecimal) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{bd.toString()});
            }

            protected boolean mutatesTo(Object oldInstance, Object newInstance) {
                return oldInstance.equals(newInstance);
            }
        });



        encoder.setPersistenceDelegate(Duration.class, new PersistenceDelegate() {
            @Override
            protected Expression instantiate(Object duration, Encoder encdr) {
                return new Expression(duration,
                        Duration.class,
                        "parse",
                        new Object[]{duration.toString()});
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
    }
}
