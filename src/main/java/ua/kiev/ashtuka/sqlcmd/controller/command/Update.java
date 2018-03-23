package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndValuesSet;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Update implements Command {
    private DataBaseManager dataBaseManager;
    private View view;
    private ColumnsAndValuesSet checked;
    private ColumnsAndValuesSet update;

    public Update(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
        this.checked = new ColumnsAndValuesSet();
        this.update = new ColumnsAndValuesSet();
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        String[] parameters = command.split("[|]");
        try {
            if (parameters.length < 6 || parameters.length % 2 != 0) {
                throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                        "update|tableName|column1Name|Value1|column2Name|Value2|...." +
                        "|column_N_Name|Value_N and you have entered: %s", command));
            }
        } catch (WrongCommandFormat e) {
            view.printError(e);
            return;
        }
        String tableName = parameters[1];
        checked.put(parameters[2],parameters[3]);
        update = getUpdate(parameters);
        if (checked.size() != 0 && update.size() != 0) {
            try {
                dataBaseManager.update(tableName, checked, update);
                checked.clear();
                update.clear();
                view.printTable(dataBaseManager.find(tableName));
            } catch (SQLException e) {
                checked.clear();
                update.clear();
                view.printError(e);
            }
        }
    }

    private ColumnsAndValuesSet getUpdate(String[] parameters) {
            for (int name = 4, value = 5; name <= parameters.length - 2; name += 2, value += 2) {
                update.put(parameters[name], parameters[value]);
            }
        return update;
    }
}
