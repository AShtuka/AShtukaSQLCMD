package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndValuesSet;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Insert implements Command {
    private DataBaseManager dataBaseManager;
    private View view;
    private ColumnsAndValuesSet columnsAndValuesSet;

    public Insert(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
        this.columnsAndValuesSet = new ColumnsAndValuesSet();
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] parameters = command.split("[|]");
        String tableName = parameters[1];
        columnsAndValuesSet = getColumnAndValueSet(parameters, command);
        if (columnsAndValuesSet.size() != 0) {
            try {
                dataBaseManager.insert(tableName, columnsAndValuesSet);
                columnsAndValuesSet.clear();
                view.printTable(dataBaseManager.find(tableName));
            } catch (SQLException e) {
                columnsAndValuesSet.clear();
                view.printError(e);
            }
        }
    }

    private ColumnsAndValuesSet getColumnAndValueSet(String[] parameters, String command) {
        try {
            if (parameters.length % 2 != 0 || parameters.length == 2) {
                throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                        "insert|tableName|column1Name|Value1|column2Name|Value2|...." +
                        "|column_N_Name|Value_N and you have entered: %s", command));
            }
            for (int name = 2, value = 3; name <= parameters.length - 2; name += 2, value += 2) {
                columnsAndValuesSet.put(parameters[name], parameters[value]);
            }
        } catch (WrongCommandFormat e){
            columnsAndValuesSet.clear();
            view.printError(e);
        }
        return columnsAndValuesSet;
    }
}
