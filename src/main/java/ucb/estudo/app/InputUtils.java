package ucb.estudo.app;

import java.util.Scanner;

public class InputUtils {
    public static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try { return Integer.parseInt(line.trim()); }
            catch (Exception e) { System.out.print("Entrada inválida. Digite um inteiro: "); }
        }
    }

    public static double readDouble(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try { return Double.parseDouble(line.trim()); }
            catch (Exception e) { System.out.print("Entrada inválida. Digite um número (ex: 12.34): "); }
        }
    }

    public static double parseDoubleOrDefault(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }

    public static int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    public static String readLine(Scanner sc) {
        return sc.nextLine();
    }
}
