package sort;
import java.util.Locale;

import java.util.Scanner;
import java.io.BufferedInputStream;

public final class In{
    private static String charsetName = "UTF-8";
    private static Locale locale = new Locale("zh", "cn");
    private static Scanner scanner = new Scanner(new BufferedInputStream(System.in), charsetName);

    static { scanner.useLocale(locale); }

    public static String readAll(){
        if(!scanner.hasNextLine())
            return null;
        return scanner.useDelimiter("\\A").next();
    }

    public static String[] readStrings(){
        String[] fields = readAll().trim().split("\\s+");
        return fields;
    }

    public void close(){
        scanner.close();
    }
}
