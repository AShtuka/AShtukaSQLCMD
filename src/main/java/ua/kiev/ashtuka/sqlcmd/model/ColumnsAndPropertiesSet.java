package ua.kiev.ashtuka.sqlcmd.model;

import java.util.ArrayList;

public class ColumnsAndPropertiesSet {

    private ArrayList<ColumnProperties> columnsAndPropertiesSets = new ArrayList<>();

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
        String str = "";
        for (int i = 0; i < columnsAndPropertiesSets.size(); i++){
            if (columnsAndPropertiesSets.get(i).getVarcharSize() == 0){
                str = str + columnsAndPropertiesSets.get(i).columnName + " " + columnsAndPropertiesSets.get(i).columnType + ", ";
            } else {
                str = str + columnsAndPropertiesSets.get(i).columnName + " " + columnsAndPropertiesSets.get(i).columnType + "(" + columnsAndPropertiesSets.get(i).VarcharSize + ")" + ", ";
            }
        }
        if (str.equals("")){
            return null;
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public void clear(){
        columnsAndPropertiesSets.clear();
    }


    public int size(){
        return columnsAndPropertiesSets.size();
    }
}
