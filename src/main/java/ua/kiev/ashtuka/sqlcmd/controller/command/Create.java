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
        String[] arr = command.split("[|]");
        try {
            if (arr.length <= 2){
                throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                    "create|tableName|column1Name|column2Name|....|column_N_Name and you have entered: %s", command));
            }
        } catch (WrongCommandFormat e){
            view.printError(e);
            return;
        }
        String tableName = arr[1];
        columnsAndPropertiesSet = getColumnAndColumnTypeSet(arr, command);
        if (columnsAndPropertiesSet.size() != 0) {
            try {
                dataBaseManager.create(tableName, columnsAndPropertiesSet);
                columnsAndPropertiesSet.clear();
                view.write(String.format("Table  - '%s' created", tableName));
            } catch (SQLException e) {
                view.printError(e);
            }
        }
    }

    private ColumnsAndPropertiesSet getColumnAndColumnTypeSet(String[] arr,String command) {
        try {
            if (arr[3].equals("VARCHAR") || arr[3].equals("INT")) {
                if (arr.length % 2 != 0 ){
                    throw new WrongCommandFormat(String.format("Invalid command format. Expected " +
                            "create|tableName|column1Name|column1Type|column2Name|column2Type|....|column_N_Name|column_N_Type and you have entered: %s", command));
                }
                for (int i = 2, y = 3; i <= arr.length - 2; i += 2, y += 2) {
                    if (arr[y].equals("VARCHAR")) {
                        columnsAndPropertiesSet.put(arr[i], ColumnType.VARCHAR, 20);
                    } else if (arr[y].equals("INT")) {
                        arr[y].equals("INT");
                        columnsAndPropertiesSet.put(arr[i], ColumnType.INT);
                    } else {
                        throw new WrongTypeFormatException(String.format("Invalid type specified. Expected VARCHAR or INT and you have entered: %s",arr[y]));
                    }
                }
            } else {
                for (int i = 2; i < arr.length; i++) {
                    columnsAndPropertiesSet.put(arr[i], ColumnType.VARCHAR, 20);
                }
            }
        } catch (Exception e){
            columnsAndPropertiesSet.clear();
            view.printError(e);
        }
        return columnsAndPropertiesSet;
    }
}
