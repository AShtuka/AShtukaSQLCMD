package ua.kiev.ashtuka.sqlcmd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBaseManager {

    Connection getConnection(String dataBaseName, String USERNAME, String PASSWORD);

    void tables() throws SQLException;

    void databases() throws SQLException;

    void tables(String dataBase_Name) throws SQLException;

    void clear(String tableName) throws SQLException;

    void drop(String tableName) throws SQLException;

    ArrayList<String> find(String tableName) throws SQLException;

    void create(String tableName, ColumnsAndPropertiesSet columnsAndPropertiesSet) throws SQLException;

    void insert(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    void update(String tableName, ColumnsAndValuesSet checkedColumnsAndValuesSet, ColumnsAndValuesSet updatedColumnAndValueSet) throws SQLException;

    void delete(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    boolean isConnected();
}
