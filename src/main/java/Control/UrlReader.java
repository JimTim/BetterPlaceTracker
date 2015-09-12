package Control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by karl on 12.09.15.
 */
public class UrlReader {

    /**
     *
     * @param urlString
     * @return String
     * @throws Exception
     */
    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                stringBuilder.append(chars, 0, read);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return stringBuilder.toString();
    }
}
