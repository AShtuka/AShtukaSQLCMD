package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Exit implements Command {
    private View view;
    private DataBaseManager dataBaseManager;

    public Exit(DataBaseManager dataBaseManager, View view){
        this.view = view;
        this.dataBaseManager = dataBaseManager;
    }
    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) throws SQLException {
        dataBaseManager.closeConnection();
        view.write("GoodBye!");
        throw new ExitException();
    }
}
