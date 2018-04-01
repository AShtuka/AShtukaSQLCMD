package ua.kiev.ashtuka.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.*;

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
    public Set<String> tables() throws SQLException {
        Set<String> tablesList = new LinkedHashSet<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try(ResultSet rs = databaseMetaData.getTables(null, null, "%",null)) {
            while (rs.next()) {
                tablesList.add("Table_name = " + rs.getString(3));
            }
        }
        return tablesList;
    }

    @Override
    public Set<String> databases() throws SQLException {
        Set<String> dbList = new LinkedHashSet<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        try(ResultSet rs = databaseMetaData.getCatalogs()) {
            while (rs.next()) {
                dbList.add("DataBases_Name = " + rs.getString("TABLE_CAT"));
            }
        }
        return dbList;
    }

    @Override
    public Set<String> tables(String dataBase_Name) throws SQLException {
        Set<String> tablesList = new LinkedHashSet<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String[] types = {"TABLE"};
        try(ResultSet rs = databaseMetaData.getTables(dataBase_Name, null, "%",types)) {
            while (rs.next()) {
                tablesList.add("Table_name = " + rs.getString("TABLE_NAME"));
            }
        }
        return tablesList;
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
    public List<String> find(String tableName) throws SQLException {
        List<String> resultList = new ArrayList<>();
        try(Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM " + tableName)) {
            String columnNameAndType = ColumnNameAndColumnType(tableName);
            resultList.add(columnNameAndType);
            String[] columnNameAndColumnType = columnNameAndType.split(SEPARATOR_SPACE);
            StringBuilder value = new StringBuilder("");
            while (rs.next()) {
                for (int columnName = 0, columnType = 1; columnName < columnNameAndColumnType.length - 1;
                         columnName += 2, columnType += 2) {
                    String[] nameAndType = columnNameAndType.split(SEPARATOR_SPACE);
                    if (nameAndType[columnType].startsWith("VARCHAR")) {
                        value.append(rs.getString(nameAndType[columnName]));
                        value.append(SEPARATOR_SPACE);
                    } else {
                        value.append(rs.getInt(nameAndType[columnName]));
                        value.append(SEPARATOR_SPACE);
                    }
                }
                value.deleteCharAt(value.length() - 1);
                resultList.add(value.toString());
                value.delete(0, value.length());
            }
        }
        return resultList;
    }

    private String ColumnNameAndColumnType(String tableName) throws SQLException {
        String result = "";
        DatabaseMetaData dbm = connection.getMetaData();
        try(ResultSet rs = dbm.getColumns(null, "%", tableName, "%")) {
            while (rs.next()) {
                String col_name = rs.getString("COLUMN_NAME");
                String data_type = rs.getString("TYPE_NAME");
                result = result + col_name + SEPARATOR_SPACE + data_type + SEPARATOR_SPACE;
            }
        }
        return result.substring(0,result.length() - 1);
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
            String sql = "INSERT INTO " + tableName + "(" + columnsAndValuesSet.getColumnName() + ")" + " VALUES "
                                        + "(" + columnsAndValuesSet.getColumnValues() + ")";
            result = statement.executeUpdate(sql);
        }
        return result;
    }

    @Override
    public int update(String tableName, ColumnsAndValuesSet checkedColumnsAndValuesSet,
                      ColumnsAndValuesSet updatedColumnAndValueSet) throws SQLException {
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
            String query = "DELETE FROM " + tableName + " WHERE " + columnsAndValuesSet.getColumnName() + " = "
                                          + columnsAndValuesSet.getColumnValues();
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
