package ua.kiev.ashtuka.sqlcmd.view;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    @Override
    public void write(String message) {
        System.out.println(message);
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

    @Override
    public void printTable(ArrayList<String> list) {
        String str = list.get(0);
        String[] columnName = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < columnName.length - 1; i += 2) {
            stringBuilder.append(columnName[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        list.set(0,stringBuilder.toString());
        String strForPrint = list.get(0);
        list.remove(0);
        String[] obgArg = obgArg(strForPrint);
        String format = format(strForPrint);
        String[] horizontalLineObjArg = horizontalLine(strForPrint);
        tablePrint(list, obgArg, format, horizontalLineObjArg);
    }

    @Override
    public void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null){
            message = message + " " + e.getCause().getMessage();
        }
        System.out.println("Fail for a reason: " + message);
        System.out.println("Please try again!");
    }

    private String format(String str) {
        String[] strings = str.split(" ");
        String st = "";
        for (int i = 0; i < strings.length; i++){

            st = st + "-20s%-2s%";
        }
        return "%-2s%" + st + "n";
    }

    private String[] obgArg(String str) {
        String[] strings = str.split(" ");
        String[] argsArr = new String[strings.length * 2 + 1];
        argsArr[0] = "|";
        argsArr[argsArr.length - 1] = "|";
        for (int i = 0, j = 1; i < strings.length; i++, j = j + 2 ){
            argsArr[j] = strings[i];
        }
        for (int i = 1; i < argsArr.length; i++){
            if (i % 2 == 0){
                argsArr[i] = "|";
            }
        }
        return argsArr;
    }

    private String[] horizontalLine(String str) {
        String[] strings = str.split(" ");
        String[] argsArr = new String[strings.length * 2 + 1];
        argsArr[0] = "+";
        argsArr[argsArr.length - 1] = "+";
        for (int i = 0, j = 1; i < strings.length; i++, j = j + 2 ){
            argsArr[j] = "--------------------";
        }
        for (int i = 1; i < argsArr.length; i++){
            if (i % 2 == 0){
                argsArr[i] = "+";
            }
        }
        return argsArr;
    }

    private void tablePrint(ArrayList<String> list, String[] obgArg, String format, String[] horizontalLineObjArg) {
        formatWrite(format, horizontalLineObjArg);
        formatWrite(format, obgArg);
        formatWrite(format, horizontalLineObjArg);
        for (int i = 0; i < list.size(); i ++){
            String row = list.get(i);
            String[] obgArgForRow = obgArg(row);
            formatWrite(format, obgArgForRow);
        }
        formatWrite(format, horizontalLineObjArg);
    }

    private void formatWrite(String format, Object... arg) {
        System.out.printf(format,arg);
    }
}
