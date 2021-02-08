import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class Main {
    private static String[] splitNameAndExtension(String fileName) {
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return new String[] { fileName, "" };
        return new String[] { fileName.substring(0, pos), fileName.substring(pos) };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        String fileName = scanner.next();

        String[] file = splitNameAndExtension(fileName);

        Reader reader = null;
        Writer stats = null;

        try {
            reader = new InputStreamReader(new FileInputStream(fileName));
            stats = new OutputStreamWriter(new FileOutputStream(file[0] + "_stats" + file[1]));
            MorseCoder morseCoder = new MorseCoder("properties/morse_code.properties");

            switch (command) {
                case "code" -> morseCoder.encodeFile(reader);
                case "decode" -> morseCoder.decodeFile(reader);
                default -> System.out.println("Unknown command!");
            }

            morseCoder.generateStats(stats);
        }
        catch (IOException e) {
            System.err.println("Error while working with files: " + e.getLocalizedMessage());
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stats != null) {
                try {
                    stats.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
