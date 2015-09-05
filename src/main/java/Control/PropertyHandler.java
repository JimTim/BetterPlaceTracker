package Control;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by karl on 05.09.15.
 */
public class PropertyHandler {
    /**
     * Auslesen der Properties aus dem PropertyFile
     * @param propertyFile
     * @return property
     * @throws IOException
     */
    public static Properties readProperties(String propertyFile) throws IOException {
        java.util.Properties properties = new java.util.Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(propertyFile));
        properties.load(stream);
        stream.close();

        return properties;
    }

    /**
     * Setzen eines neuen/update eines bestehenden PropertyKeys
     * @param propertyFile
     * @param propertyKey
     * @param propertyValue
     * @throws IOException
     */
    public static void wirteProperty(String propertyFile, String propertyKey, String propertyValue) throws IOException {
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(propertyKey, propertyValue);
        FileOutputStream out = new FileOutputStream(propertyFile);
        properties.store(out, null);
    }

}
