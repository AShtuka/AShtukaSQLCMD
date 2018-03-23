package ua.kiev.ashtuka.sqlcmd.model;

import java.util.ArrayList;
import java.util.List;

public class ColumnsAndPropertiesSet {
    private List<ColumnProperties> columnsAndPropertiesSets = new ArrayList<>();

    static class ColumnProperties{
        private String columnName;
        private ColumnType columnType;
        private int VarcharSize;

        ColumnProperties(String columnName, ColumnType columnType, int VarcharSize){
            this.columnName = columnName;
            this.columnType = columnType;
            this.VarcharSize = VarcharSize;
        }

        ColumnProperties(String columnName, ColumnType columnType){
            this.columnName = columnName;
            this.columnType = columnType;
        }

        public int getVarcharSize() {
            return VarcharSize;
        }

    }

    public void put(String columnName, ColumnType columnType, int VarcharSize){
        columnsAndPropertiesSets.add(new ColumnProperties(columnName, columnType, VarcharSize));
    }

    public void put(String columnName, ColumnType columnType){
        columnsAndPropertiesSets.add(new ColumnProperties(columnName, columnType));
    }

    public String getColumnNamePlusColumnType(){
        String result = "";
        for (int i = 0; i < columnsAndPropertiesSets.size(); i++){
            if (columnsAndPropertiesSets.get(i).getVarcharSize() == 0){
                result = result + columnsAndPropertiesSets.get(i).columnName + " "
                                + columnsAndPropertiesSets.get(i).columnType + ", ";
            } else {
                result = result + columnsAndPropertiesSets.get(i).columnName + " "
                                + columnsAndPropertiesSets.get(i).columnType + "("
                                + columnsAndPropertiesSets.get(i).VarcharSize + ")" + ", ";
            }
        }
        if (result.equals("")){
            return null;
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void clear(){
        columnsAndPropertiesSets.clear();
    }


    public int size(){
        return columnsAndPropertiesSets.size();
    }
}
