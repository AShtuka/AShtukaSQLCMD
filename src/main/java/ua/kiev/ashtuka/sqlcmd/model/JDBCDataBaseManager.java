package ua.kiev.ashtuka.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;

public class JDBCDataBaseManager implements DataBaseManager {

    public static final String SEPARATOR_SPACE = " ";
    private Connection connection;

    public JDBCDataBaseManager() throws SQLException{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
    }
    @Override
    public Connection getConnection(String URL, String dataBaseName, String USERNAME, String PASSWORD)  {
        if (connection != null){
            return connection;
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Can't get connection for model: %s user: %s", dataBaseName, USERNAME), e);
        }
        return connection;
    }

    @Override
    public ArrayList<String> tables() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try(ResultSet rs = databaseMetaData.getTables(null, null, "%",null)) {
            while (rs.next()) {
                list.add("Table_name = " + rs.getString(3));
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> databases() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try(ResultSet rs = databaseMetaData.getCatalogs()) {
            while (rs.next()) {
                list.add("DataBases_Name = " + rs.getString("TABLE_CAT"));
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> tables(String dataBase_Name) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String[] types = {"TABLE"};
        try(ResultSet rs = databaseMetaData.getTables(dataBase_Name, null, "%",types)) {
            while (rs.next()) {
                list.add("Table_name = " + rs.getString("TABLE_NAME"));
            }
        }
        return list;
    }

    @Override
    public void clear(String tableName) throws SQLException {
        try(Statement statement = connection.createStatement();) {
            String query = "DELETE FROM " + tableName;
            statement.executeUpdate(query);
        }
    }

    @Override
    public void drop(String tableName) throws SQLException {
        String query = "DROP TABLE " +  tableName;
        try(PreparedStatement dropTable = connection.prepareStatement(query)) {
            dropTable.executeUpdate();
        }
    }

    @Override
    public ArrayList<String> find(String tableName) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        try(Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM " + tableName)) {
            String str = ColumnNameAndColumnType(tableName);
            list.add(str);
            String[] columnNameAndColumnType = str.split(SEPARATOR_SPACE);
            StringBuilder stringBuilder = new StringBuilder("");
            while (rs.next()) {
                for (int columnName = 0, columnType = 1; columnName < columnNameAndColumnType.length - 1; columnName += 2, columnType += 2) {
                    String[] strings = str.split(SEPARATOR_SPACE);
                    if (strings[columnType].startsWith("VARCHAR")) {
                        stringBuilder.append(rs.getString(strings[columnName]));
                        stringBuilder.append(SEPARATOR_SPACE);
                    } else {
                        stringBuilder.append(rs.getInt(strings[columnName]));
                        stringBuilder.append(SEPARATOR_SPACE);
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                list.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
        return list;
    }

    private String ColumnNameAndColumnType(String tableName) throws SQLException {
        String str = "";
        DatabaseMetaData dbm = connection.getMetaData();
        try(ResultSet rs = dbm.getColumns(null, "%", tableName, "%")) {
            while (rs.next()) {
                String col_name = rs.getString("COLUMN_NAME");
                String data_type = rs.getString("TYPE_NAME");
                str = str + col_name + SEPARATOR_SPACE + data_type + SEPARATOR_SPACE;
            }
        }
        return str.substring(0,str.length() - 1);
    }

    @Override
    public int create(String tableName, ColumnsAndPropertiesSet columnsAndPropertiesSet) throws SQLException {
        int result = 0;
        try(Statement statement = connection.createStatement();) {
            String sql = "CREATE TABLE " + tableName + "(" + columnsAndPropertiesSet.getColumnNamePlusColumnType() + ")";
             result = statement.executeUpdate(sql);
        }
        return result;
    }

    @Override
    public int insert(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException {
        int result = 0;
        try(Statement statement = connection.createStatement();) {
            String sql = "INSERT INTO " + tableName + "(" + columnsAndValuesSet.getColumnName() + ")" + " VALUES " + "(" + columnsAndValuesSet.getColumnValues() + ")";
            result = statement.executeUpdate(sql);
        }
        return result;
    }

    @Override
    public int update(String tableName, ColumnsAndValuesSet checkedColumnsAndValuesSet, ColumnsAndValuesSet updatedColumnAndValueSet) throws SQLException {
        String nameAndValueForUpdate = updatedColumnAndValueSet.getColumnNameColumnValue();
        String query = "UPDATE " + tableName +" SET " + nameAndValueForUpdate + " WHERE "
                + checkedColumnsAndValuesSet.getColumnName() + " = " + checkedColumnsAndValuesSet.getColumnValues();
        int result = 0;
        try(Statement statement = connection.createStatement();) {
            result = statement.executeUpdate(query);
        }
        return result;
    }

    @Override
    public int delete(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException {
        int result = 0;
        try(Statement statement = connection.createStatement();) {
            String query = "DELETE FROM " + tableName + " WHERE " + columnsAndValuesSet.getColumnName() + " = " + columnsAndValuesSet.getColumnValues();
            result = statement.executeUpdate(query);
        }
        return result;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null){
            connection.close();
        }
    }
}
