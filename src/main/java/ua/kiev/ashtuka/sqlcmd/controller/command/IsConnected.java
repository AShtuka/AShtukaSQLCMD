package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

public class IsConnected implements Command {
    private DataBaseManager dataBaseManager;
    private View view;

    public IsConnected(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return !dataBaseManager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("You can not use the command '%s' till " +
                                 "do not connect using the command " + "connect|model|user|password", command));
    }
}
