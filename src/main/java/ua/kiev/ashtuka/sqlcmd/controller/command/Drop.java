package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private DataBaseManager dataBaseManager;
    private View view;

    public Drop(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        String[] arr = command.split("[|]");
        String tableName = arr[1];
        try {
            dataBaseManager.drop(tableName);
            view.write(String.format("Table '%s' deleted", tableName));
        } catch (SQLException e) {
            view.printError(e);
        }
    }
}
