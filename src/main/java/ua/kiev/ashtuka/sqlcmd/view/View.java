package ua.kiev.ashtuka.sqlcmd.view;

import java.util.List;

public interface View {
    void write(String message);
    void printError(Exception e);
    String read();
    void printTable(List<String> list);
}
