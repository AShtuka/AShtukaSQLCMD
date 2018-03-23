package ua.kiev.ashtuka.sqlcmd.model;

import java.util.ArrayList;
import java.util.List;

public class ColumnsAndValuesSet {
    private List<ColumnValue> columnValues = new ArrayList<>();

    static class ColumnValue{
        private String columnName;
        private Object value;

        ColumnValue(String columnName, Object value){
            this.columnName = columnName;
            this.value = value;
        }
    }

    public void put(String columnName, Object value){
        columnValues.add(new ColumnValue(columnName, value));
    }

    public String getColumnName(){
        String result = "";
        for (int i = 0; i < columnValues.size(); i++) {
            result = result + columnValues.get(i).columnName + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public String getColumnValues(){
        String result = "";
        for (int i = 0; i < columnValues.size(); i++) {
            result = result + "\"" + columnValues.get(i).value.toString() + "\"" + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public String getColumnNameColumnValue(){
        String result = "";
        for (int i = 0; i < columnValues.size(); i++) {
            result = result + columnValues.get(i).columnName + " = " + "\"" + columnValues.get(i).value + "\"" + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void clear(){
        columnValues.clear();
    }

    public int size(){
        return columnValues.size();
    }
}
