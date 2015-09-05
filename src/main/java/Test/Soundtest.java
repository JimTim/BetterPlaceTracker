package Test;

import Control.SoundThread;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by karl on 05.09.15.
 */
public class Soundtest {
    @Test
    public void testDonationSound(){
        //Quelle: http://www.salamisound.de/1020110-geldregen-muenzen#
        //TODO Pfad anpassen
        SoundThread donationsound = new SoundThread("http://www.salamisound.de/1020110-geldregen-muenzen#");
        try {
            donationsound.run();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }
}
