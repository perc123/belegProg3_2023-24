package saveJBP;
import administration.HerstellerImpl;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;

import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.Statement;
import java.beans.XMLEncoder;
import java.util.LinkedList;

public class JBP {

    private final VendingMachine vendingMachine;

    public JBP(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void serialisierenJBP() {
        File folder = new File("src/serialization/saveJBP/");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Could not create folder: " + folder);
            }
        }
        File file = new File(folder, "saveModelJBP.xml");

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            encoder.setPersistenceDelegate(VendingMachine.class, new DefaultPersistenceDelegate(new String[]{"capacity", "inventory"}));
            encoder.setPersistenceDelegate(HerstellerImpl.class, new DefaultPersistenceDelegate(new String[]{"name"}));
            encoder.setPersistenceDelegate(KremkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp"}));
            encoder.setPersistenceDelegate(ObstkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "sorte"}));
            encoder.setPersistenceDelegate(ObsttorteImpl.class, new DefaultPersistenceDelegate(new String[]{"hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "sorteEins", "sorteZwei"}));
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
            setPersistenceDelegateForLinkedList(encoder);
            encoder.writeObject(vendingMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VendingMachine deserialisierenJBP() {
        VendingMachine vendingMachine = null;
        File file = new File("src/serialization/saveJBP/saveModelJBP.xml");

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            vendingMachine = (VendingMachine) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Data not found: " + file.getAbsolutePath());
        }
        return vendingMachine;
    }

    private void setPersistenceDelegateForLinkedList(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(LinkedList.class, new DefaultPersistenceDelegate() {
            protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
                super.initialize(type, oldInstance, newInstance, out);
                LinkedList<?> list = (LinkedList<?>) oldInstance;
                for (Object item : list) {
                    out.writeStatement(new Statement(oldInstance, "add", new Object[]{item}));
                }
            }
        });
    }
}
