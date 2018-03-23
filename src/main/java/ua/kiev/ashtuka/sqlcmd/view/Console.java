package ua.kiev.ashtuka.sqlcmd.view;

import java.util.List;
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
    public void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null){
            message = message + " " + e.getCause().getMessage();
        }
        System.out.println("Fail for a reason: " + message);
        System.out.println("Please try again!");
    }

    @Override
    public void printTable(List<String> tableData) {
        String nameAndType = tableData.get(0);
        tableData.remove(0);
        String[] columnNameWithType = nameAndType.split(" ");
        StringBuilder lineWithName = new StringBuilder("");
        for (int name = 0; name < columnNameWithType.length - 1; name += 2) {
            lineWithName.append(columnNameWithType[name]);
            lineWithName.append(" ");
        }
        String columnNameLine = lineWithName.deleteCharAt(lineWithName.length() - 1).toString();
        String[] header = valueForPrint(columnNameLine);
        String format = getFormat(columnNameLine);
        String[] horizontalLine = getHorizontalLine(columnNameWithType.length / 2);
        print(tableData, header, format, horizontalLine);
    }

    private String getFormat(String columnNameLine) {
        String[] strings = columnNameLine.split(" ");
        String result = "";
        for (int i = 0; i < strings.length; i++){
            result = result + "-20s%-2s%";
        }
        return "%-2s%" + result + "n";
    }

    private String[] valueForPrint(String columnNameLine) {
        String[] name = columnNameLine.split(" ");
        String[] header = new String[name.length * 2 + 1];
        header[0] = "|";
        header[header.length - 1] = "|";
        for (int nameValue = 0, namePosition = 1; nameValue < name.length; nameValue++, namePosition = namePosition + 2 ){
            header[namePosition] = name[nameValue];
        }
        for (int separatorPosition = 1; separatorPosition < header.length; separatorPosition++){
            if (separatorPosition % 2 == 0){
                header[separatorPosition] = "|";
            }
        }
        return header;
    }

    private String[] getHorizontalLine(int nameNumber) {
        String[] result = new String[nameNumber * 2 + 1];
        result[0] = "+";
        result[result.length - 1] = "+";
        for (int plusPosition = 0, hyphenPosition = 1; plusPosition < nameNumber; plusPosition++, hyphenPosition = hyphenPosition + 2 ){
            result[hyphenPosition] = "--------------------";
        }
        for (int plusPosition = 1; plusPosition < result.length; plusPosition++){
            if (plusPosition % 2 == 0){
                result[plusPosition] = "+";
            }
        }
        return result;
    }

    private void print(List<String> tableData, String[] header, String format, String[] horizontalLine) {
        formatWrite(format, horizontalLine);
        formatWrite(format, header);
        formatWrite(format, horizontalLine);
        for (int i = 0; i < tableData.size(); i ++){
            String valueRow = tableData.get(i);
            String[] valueRowForPrint = valueForPrint(valueRow);
            formatWrite(format, valueRowForPrint);
        }
        formatWrite(format, horizontalLine);
    }

    private void formatWrite(String format, Object... arg) {
        System.out.printf(format,arg);
    }
}
