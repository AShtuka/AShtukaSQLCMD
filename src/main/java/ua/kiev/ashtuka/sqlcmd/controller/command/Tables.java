package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class Tables implements Command {
    private View view;
    private DataBaseManager dataBaseManager;

    public Tables(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        try {
            Set<String> tablesList = dataBaseManager.tables();
            for (String tableName : tablesList){
                view.write(tableName);
            }
        } catch (SQLException e) {
            view.printError(e);
        }
    }
}
