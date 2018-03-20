package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class Find implements Command {

    private DataBaseManager dataBaseManager;
    private View view;

    public Find(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] arr = command.split("[|]");
        String tableName = arr[1];
        try {
            view.printTable(dataBaseManager.find(tableName));
        } catch (SQLException e) {
            view.printError(e);
        }
    }

}
