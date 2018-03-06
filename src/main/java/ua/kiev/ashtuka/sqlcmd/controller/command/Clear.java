package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Clear implements Command {

    private View view;
    private DataBaseManager dataBaseManager;

    public Clear(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] arr = command.split("[|]");
        String tableName = arr[1];
        try {
            dataBaseManager.clear(tableName);
            view.write(String.format("From table '%s' all data deleted", tableName));
        } catch (SQLException e) {
            view.printError(e);
        }
    }
}
