package ua.kiev.ashtuka.sqlcmd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBaseManager {

    Connection getConnection(String URL, String dataBaseName, String USERNAME, String PASSWORD);

    ArrayList<String> tables() throws SQLException;

    ArrayList<String> databases() throws SQLException;

    ArrayList<String> tables(String dataBase_Name) throws SQLException;

    void clear(String tableName) throws SQLException;

    void drop(String tableName) throws SQLException;

    ArrayList<String> find(String tableName) throws SQLException;

    int create(String tableName, ColumnsAndPropertiesSet columnsAndPropertiesSet) throws SQLException;

    int insert(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    int update(String tableName, ColumnsAndValuesSet checkedColumnsAndValuesSet, ColumnsAndValuesSet updatedColumnAndValueSet) throws SQLException;

    int delete(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    boolean isConnected();

    void closeConnection() throws SQLException;
}
