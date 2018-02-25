package ua.kiev.ashtuka.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;

public class JDBCDataBaseManager implements DataBaseManager {
    private Connection connection;
    private Statement statement;

    public JDBCDataBaseManager() throws SQLException{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
    }

    @Override
    public Connection getConnection(String dataBaseName, String USERNAME, String PASSWORD)  {
        String URL = "jdbc:mysql://localhost:3306/" + dataBaseName ;
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
    public void tables() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getTables(null, null, "%",null);
        while (rs.next())
        {
            System.out.println("Table_name = " + rs.getString(3));
        }
        rs.close();
    }

    @Override
    public void databases() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getCatalogs();
        while (rs.next()) {
            System.out.println("DataBases_Name = " + rs.getString("TABLE_CAT") );
        }
        rs.close();
    }

    @Override
    public void tables(String dataBase_Name) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = databaseMetaData.getTables(dataBase_Name, null, "%",types);
        while (rs.next())
        {
            System.out.println("Table_name = " + rs.getString( "TABLE_NAME"));
        }
        rs.close();
    }

    @Override
    public void clear(String tableName) throws SQLException {
        statement = connection.createStatement();
        String query = "DELETE FROM " + tableName;
        statement.executeUpdate(query);
        statement.close();
    }

    @Override
    public void drop(String tableName) throws SQLException {
        String query = "DROP TABLE " +  tableName;
        PreparedStatement dropTable = connection.prepareStatement(query);
        int count = dropTable.executeUpdate();
        dropTable.close();
    }

    @Override
    public ArrayList<String> find(String tableName) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
        ArrayList<String> arrayList = ColumnNameAndColumnType(tableName);
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < arrayList.size(); i++){
            String str = arrayList.get(i);
            String [] strings = str.split(" ");
            stringBuilder.append(strings[0]);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        list.add(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
        while (rs.next())
        {
            for (int i = 0; i < arrayList.size(); i++){
                String str = arrayList.get(i);
                String [] strings = str.split(" ");
                if (strings[1].startsWith("VARCHAR")){
                    stringBuilder.append(rs.getString(strings[0]));
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.append(rs.getInt(strings[0]));
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            list.add(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
        st.close();
        rs.close();
        return list;
    }

    private ArrayList<String> ColumnNameAndColumnType(String tableName) throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet rs = dbm.getColumns(null, "%", tableName, "%");
        while (rs.next()) {
            String col_name = rs.getString("COLUMN_NAME");
            String data_type = rs.getString("TYPE_NAME");
            arrayList.add(col_name + " " + data_type);
        }
        rs.close();
        return arrayList;
    }

    @Override
    public void create(String tableName, ColumnsAndPropertiesSet columnsAndPropertiesSet) throws SQLException {
        statement = connection.createStatement();
        String sql = "CREATE TABLE " + tableName + "(" + columnsAndPropertiesSet.getColumnNamePlusColumnType() + ")";
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public void insert(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO " + tableName + "(" + columnsAndValuesSet.getColumnName() + ")" + " VALUES " + "(" + columnsAndValuesSet.getColumnValues() + ")";
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public void update(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException {
        String query = "UPDATE " + tableName +" SET " + columnsAndValuesSet.getColumnName() + " = ? WHERE " + columnsAndValuesSet.getColumnName() + " = ?";
        PreparedStatement preparedStatementstatement = connection.prepareStatement(query);
        preparedStatementstatement.setString( 1, columnsAndValuesSet.getColumnNewValues().substring(1,columnsAndValuesSet.getColumnNewValues().length() - 1));
        preparedStatementstatement.setString(2, columnsAndValuesSet.getColumnValues().substring(1,columnsAndValuesSet.getColumnValues().length() - 1));
        preparedStatementstatement.executeUpdate();
        preparedStatementstatement.close();
    }

    @Override
    public void delete(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException {
        statement = connection.createStatement();
        String query = "DELETE FROM " + tableName + " WHERE " + columnsAndValuesSet.getColumnName() + " = " + columnsAndValuesSet.getColumnValues();
        statement.executeUpdate(query);
        statement.close();
    }
}
