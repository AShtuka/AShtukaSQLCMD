package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

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
            List<String> tablesList = dataBaseManager.tables();
            for (int i = 0; i < tablesList.size(); i++){
                view.write(tablesList.get(i));
            }
        } catch (SQLException e) {
            view.printError(e);
        }
    }
}
