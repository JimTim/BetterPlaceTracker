package Control;

import static org.junit.Assert.assertTrue;

/**
 * Created by karl on 05.09.15.
 */
public class Soundhandler {
    /**
     * Spielt den Geldsound ab.
     */
    public static void playSound(){
        //Quelle: http://www.salamisound.de/1020110-geldregen-muenzen#
        //TODO Pfad anpassen
        //Fixme: Test!!! (geht das überhaupt?)
        SoundThread donationsound = new SoundThread("http://www.salamisound.de/1020110-geldregen-muenzen#");
        donationsound.run();
    }
}
