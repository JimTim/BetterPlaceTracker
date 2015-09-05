package Control;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * Created by karl on 05.09.15.
 */
public class SoundThread extends Thread {
    String soundfile;
    public SoundThread(String soundfile){
        this.soundfile = soundfile;
    }
    public void run() {
        //Quelle: http://www.salamisound.de/1020110-geldregen-muenzen#
        //String bip = "geldregen.mp3";
        Media sound = new Media(soundfile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.stop();
    }
}
