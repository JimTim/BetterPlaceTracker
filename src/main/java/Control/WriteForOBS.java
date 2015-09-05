package Control;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ListIterator;

/**
 * Created by karl on 05.09.15.
 */
public class WriteForOBS {

    /** Neuer Filepfad zum Bsp mit FileChooser (Dateiauswahlfenster von JavaFX
     * FileChooser fileChooser = new FileChooser();

     // Set extension filter
     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
     fileChooser.getExtensionFilters().add(extFilter);

     // Show save file dialog
     File file = fileChooser.showSaveDialog(Main.getInstance().getPrimaryStage());

     if (file != null) {
     // Make sure it has the correct extension
     if (!file.getPath().endsWith(".txt")) {
     file = new File(file.getPath() + ".txt");
     }
     String pfadname = file.getAbsolutePath();
     }
     */

    //Pfad der Datei die überwacht wird
    String filepath;

    /**
     * Constructor, setzen des Filepfades
     * @param filepath
     */
    public WriteForOBS(String filepath){
        this.filepath = filepath;
    }

    /**
     * Neuen Eintrag in die Datei einfügen
     * @param entry
     * @throws FileNotFoundException
     */
    public void writeNewEntry(String entry) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filepath);
        out.println(entry);
        out.close();
    }
}
