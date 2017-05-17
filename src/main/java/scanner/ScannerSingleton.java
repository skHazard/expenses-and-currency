package scanner;

import java.util.Scanner;

/**
 * Created by Hazard on 17.05.2017.
 */
public class ScannerSingleton {

    private static final Scanner sc = new Scanner(System.in);

    public static Scanner getSc() {
        return sc;
    }
}
