package ua.kiev.ashtuka.sqlcmd.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnsAndValuesSet {
    private Map<String, Object> columnValue = new LinkedHashMap<>();

    public void put(String columnName, Object value){
        columnValue.put(columnName, value);
    }

    public String getColumnName(){
        if (columnValue.isEmpty()) {
            return null;
        }
        String result = "";
        for (Map.Entry<String, Object> entry : columnValue.entrySet()){
            result = result + entry.getKey() + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public String getColumnValues(){
        if (columnValue.isEmpty()) {
            return null;
        }
        String result = "";
        for (Map.Entry<String, Object> entry : columnValue.entrySet()){
            result = result + "\"" + entry.getValue() + "\"" + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public String getColumnNameColumnValue(){
        if (columnValue.isEmpty()) {
            return null;
        }
        String result = "";
        for (Map.Entry<String, Object> entry : columnValue.entrySet()) {
            result = result + entry.getKey() + " = " + "\"" + entry.getValue() + "\"" + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void clear(){
        columnValue.clear();
    }

    public int size(){
        return columnValue.size();
    }
}
