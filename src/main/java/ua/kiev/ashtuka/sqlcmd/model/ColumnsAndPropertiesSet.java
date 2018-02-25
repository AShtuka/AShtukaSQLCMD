package ua.kiev.ashtuka.sqlcmd.model;

public class ColumnsAndPropertiesSet {

    private ColumnProperties[] columnProperties = new ColumnProperties[10];
    private int index = 0;

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

        public String getColumnName() {
            return columnName;
        }

        public int getVarcharSize() {
            return VarcharSize;
        }

        public ColumnType getColumnType() {
            return columnType;
        }
    }

    public void put(String columnName, ColumnType columnType, int VarcharSize){
        columnProperties[index++] = new ColumnProperties(columnName, columnType, VarcharSize);
    }

    public void put(String columnName, ColumnType columnType){
        columnProperties[index++] = new ColumnProperties(columnName, columnType);
    }

    public String getColumnNamePlusColumnType(){
        String str = "";
        for (int i = 0; i < index; i++){
            if (index == 0){
                return null;
            }
            if (columnProperties[i].getVarcharSize() == 0){
                str = str + columnProperties[i].columnName + " " + columnProperties[i].columnType + ", ";
            } else {
                str = str + columnProperties[i].columnName + " " + columnProperties[i].columnType + "(" + columnProperties[i].VarcharSize + ")" + ", ";
            }
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }
}
