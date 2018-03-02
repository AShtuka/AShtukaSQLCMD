package ua.kiev.ashtuka.sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void formatWrite(String format, Object... arg) {
        System.out.printf(format,arg);
    }

    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e){
            return null;
        }

    }
}
