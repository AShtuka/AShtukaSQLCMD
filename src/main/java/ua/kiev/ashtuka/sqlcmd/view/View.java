package ua.kiev.ashtuka.sqlcmd.view;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;

public interface View {
    void write(String message);
    void printError(Exception e);
    String read();
    void printTable(DataBaseManager dataBaseManager, String command);
}
