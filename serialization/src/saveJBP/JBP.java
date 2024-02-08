package saveJBP;
import administration.HerstellerImpl;
import administration.HerstellerStorage;
import administration.VendingMachine;
import cakes.KremkuchenImpl;
import cakes.ObstkuchenImpl;
import cakes.ObsttorteImpl;

import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

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
            customizePersistenceDelegates(encoder);
            encoder.writeObject(vendingMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public VendingMachine deserialisierenJBP() {
        VendingMachine vendingMachine = null;
        File file = new File("/serialization/src/saveModeJBP/saveModeJBP.xml");

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            vendingMachine = (VendingMachine) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendingMachine;
    }


    private void customizePersistenceDelegates(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(VendingMachine.class, new DefaultPersistenceDelegate(new String[]{"capacity"}));
        encoder.setPersistenceDelegate(HerstellerImpl.class, new DefaultPersistenceDelegate(new String[]{"name"}));
        encoder.setPersistenceDelegate(HerstellerStorage.class, new DefaultPersistenceDelegate() {
            protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
                super.initialize(type, oldInstance, newInstance, out);
                HerstellerStorage storage = (HerstellerStorage) oldInstance;
                out.writeExpression(new Expression(oldInstance, oldInstance.getClass(), "new", null));
                out.writeStatement(new Statement(oldInstance, "addAllHersteller", new Object[]{storage.getHerstellerList()}));
            }
        });
        encoder.setPersistenceDelegate(KremkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp", "hersteller", "allergene", "naehrwert", "haltbarkeit", "preis", "kremsorte"}));
        encoder.setPersistenceDelegate(ObstkuchenImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp", "hersteller", "allergene", "naehrwert", "haltbarkeit", "preis", "obstsorte"}));
        encoder.setPersistenceDelegate(ObsttorteImpl.class, new DefaultPersistenceDelegate(new String[]{"kuchenTyp", "hersteller", "allergene", "naehrwert", "haltbarkeit", "preis", "kremsorte", "obstsorte"}));


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
        encoder.setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BigDecimal bd = (BigDecimal) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{bd.toString()});
            }

            protected boolean mutatesTo(Object oldInstance, Object newInstance) {
                return oldInstance.equals(newInstance);
            }
        });

        //setPersistenceDelegateForLinkedList(encoder);
    }

/*    private void setPersistenceDelegateForLinkedList(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(LinkedList.class, new DefaultPersistenceDelegate() {
            protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
                super.initialize(type, oldInstance, newInstance, out);
                LinkedList<?> list = (LinkedList<?>) oldInstance;
                for (Object item : list) {
                    out.writeStatement(new Statement(oldInstance, "add", new Object[]{item}));
                }
            }
        });
    }*/
}
