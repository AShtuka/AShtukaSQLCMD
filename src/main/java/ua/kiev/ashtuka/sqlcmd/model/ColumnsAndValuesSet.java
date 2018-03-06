package ua.kiev.ashtuka.sqlcmd.model;

import java.util.ArrayList;

public class ColumnsAndValuesSet {
    private ArrayList<ColumnValue> columnValues = new ArrayList<>();

    static class ColumnValue{
        private String columnName;
        private Object value;
        private Object newValue;


        ColumnValue(String columnName, Object value){
            this.columnName = columnName;
            this.value = value;
        }

    }

    public void put(String columnName, Object value){
        columnValues.add(new ColumnValue(columnName, value));
    }


    public String getColumnName(){
        String str = "";
        for (int i = 0; i < columnValues.size(); i++) {

            str = str + columnValues.get(i).columnName + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public String getColumnValues(){
        String str = "";
        for (int i = 0; i < columnValues.size(); i++) {
            str = str + "\"" + columnValues.get(i).value.toString() + "\"" + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public String getColumnNameColumnValue(){
        String str = "";
        for (int i = 0; i < columnValues.size(); i++) {

            str = str + columnValues.get(i).columnName + " = " + "\"" + columnValues.get(i).value + "\"" + ", ";
        }
        if (columnValues.size() == 0) {
            return null;
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public void clear(){
        columnValues.clear();
    }

    public int size(){
        return columnValues.size();
    }
}
