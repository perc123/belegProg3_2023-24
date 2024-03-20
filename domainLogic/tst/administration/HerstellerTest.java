package administration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HerstellerTest {

    /*
    This test checks the functionality of the method getName() in the HerstellerImpl class.
    The method should return the name of the manufacturer, which was given to the Object Hersteller
     */
    @Test
    void testGetName() {
        HerstellerImpl hersteller = new HerstellerImpl("Hersteller");
        assertEquals("Hersteller", hersteller.getName());
    }

    // Test for the cake count Getter and Setter methods
    @Test
    void testGetterUndSetterCakeCount(){
        HerstellerImpl hersteller = new HerstellerImpl("Hersteller");
        hersteller.setCakeCount(2);
        assertEquals(2, hersteller.getCakeCount());
    }

    // Test for the Equals method; should return true
    @Test
    public void testEquals() {
        HerstellerImpl hersteller1 = new HerstellerImpl("Hersteller 1");
        HerstellerImpl hersteller2 = new HerstellerImpl("Hersteller 1");
        assertEquals(hersteller1, hersteller2);
    }

    // Test for the Equals method; should return false
    @Test
    public void testEqualsFalse() {
        HerstellerImpl hersteller1 = new HerstellerImpl("Hersteller 1");
        HerstellerImpl hersteller2 = new HerstellerImpl("Hersteller 2");
        assertNotEquals(hersteller1, hersteller2);
    }


}