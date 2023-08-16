package hb.exam.utils;

import java.util.Scanner;

public class SysOut {
    public static String saisieTexteConsole(String libelle, Scanner scanner){
        String line = "-".repeat(libelle.length());
        System.out.println(line);
        System.out.println(libelle);
        System.out.println(line);
        String st = scanner.nextLine();

        return st;
    }
    public static void printTitle(String title){
        String line = "-".repeat(title.length());
        System.out.println(line);
        System.out.println(title);
        System.out.println(line);
    }
    public static void printLine(int length){
        String line = "-".repeat(length);
        System.out.println(line);
    }

}
