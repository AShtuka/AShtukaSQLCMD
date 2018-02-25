package ua.kiev.ashtuka.sqlcmd.model;

public class ColumnsAndValuesSet {
    private ColumnValue[] columnValues = new ColumnValue[10];
    private int index = 0;

    static class ColumnValue{
        private String columnName;
        private Object value;
        private Object newValue;


        ColumnValue(String columnName, Object value){
            this.columnName = columnName;
            this.value = value;
        }

        ColumnValue(String columnName, Object oldValue, Object newValue){
            this.columnName = columnName;
            this.value = oldValue;
            this.newValue = newValue;
        }
    }

    public void put(String columnName, Object value){
        columnValues[index++] = new ColumnValue(columnName, value);
    }

    public void put(String columnName, Object oldValue, Object newValue){
        columnValues[index++] = new ColumnValue(columnName, oldValue, newValue);
    }

    public String getColumnName(){
        String str = "";
        for (int i = 0; i < index; i++) {
            if (index == 0) {
                return null;
            }
            str = str + columnValues[i].columnName + ", ";
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public String getColumnValues(){
        String str = "";
        for (int i = 0; i < index; i++) {
            if (index == 0) {
                return null;
            }
            str = str + "\"" + columnValues[i].value.toString() + "\"" + ", ";
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public String getColumnNewValues(){
        String str = "";
        for (int i = 0; i < index; i++) {
            if (index == 0) {
                return null;
            }
            str = str + "\"" + columnValues[i].newValue.toString() + "\"" + ", ";
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public void clearColumnAndValueSet(){
        index = 0;
    }
}
