package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.ColumnType;
import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndPropertiesSet;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Create implements Command {
    private DataBaseManager dataBaseManager;
    private View view;
    private ColumnsAndPropertiesSet columnsAndPropertiesSet;

    public Create(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
        this.columnsAndPropertiesSet = new ColumnsAndPropertiesSet();
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] parameters = command.split("[|]");
        try {
            if (parameters.length <= 2){
                throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                    "create|tableName|column1Name|column2Name|....|column_N_Name and you have entered: %s", command));
            }
        } catch (WrongCommandFormat e){
            view.printError(e);
            return;
        }
        String tableName = parameters[1];
        columnsAndPropertiesSet = getColumnAndColumnTypeSet(parameters, command);
        if (columnsAndPropertiesSet.size() != 0) {
            try {
                dataBaseManager.create(tableName, columnsAndPropertiesSet);
                columnsAndPropertiesSet.clear();
                view.write(String.format("Table  - '%s' created", tableName));
            } catch (SQLException e) {
                columnsAndPropertiesSet.clear();
                view.printError(e);
                return;
            }
        }
    }

    private ColumnsAndPropertiesSet getColumnAndColumnTypeSet(String[] parameters,String command) {
        try {
            if (parameters[3].equals("VARCHAR") || parameters[3].equals("INT")) {
                if (parameters.length % 2 != 0 ){
                    throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                            "create|tableName|column1Name|column1Type|column2Name|column2Type|...." +
                            "|column_N_Name|column_N_Type and you have entered: %s", command));
                }
                for (int name = 2, type = 3; name <= parameters.length - 2; name += 2, type += 2) {
                    if (parameters[type].equals("VARCHAR")) {
                        columnsAndPropertiesSet.put(parameters[name], ColumnType.VARCHAR, 20);
                    } else if (parameters[type].equals("INT")) {
                        columnsAndPropertiesSet.put(parameters[name], ColumnType.INT);
                    } else {
                        throw new WrongTypeFormatException(String.format("Invalid type specified." +
                                " Expected VARCHAR or INT and you have entered: %s",parameters[type]));
                    }
                }
            } else {
                for (int name = 2; name < parameters.length; name++) {
                    columnsAndPropertiesSet.put(parameters[name], ColumnType.VARCHAR, 20);
                }
            }
        } catch (Exception e){
            columnsAndPropertiesSet.clear();
            view.printError(e);
        }
        return columnsAndPropertiesSet;
    }
}
