package org.example.utiles;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt() {
        while(true) {
            try {
                int carIndex = scanner.nextInt();
                scanner.nextLine();
                return carIndex;
            } catch (InputMismatchException ignored) {scanner.nextLine();}
            System.out.println("Proszę podać poprawny indeks.");
        }
    }

    public static double readDouble() {
        while(true) {
            try {
                double number = scanner.nextDouble();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException ignored) {scanner.nextLine();}
            System.out.println("Proszę podać poprawny format.");
        }
    }

    public static String readString() {
        return scanner.nextLine();
    }
}
