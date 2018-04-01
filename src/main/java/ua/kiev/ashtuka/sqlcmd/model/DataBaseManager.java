package ua.kiev.ashtuka.sqlcmd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DataBaseManager {
    Connection getConnection(String URL, String dataBaseName, String USERNAME, String PASSWORD);

    Set<String> tables() throws SQLException;

    Set<String> databases() throws SQLException;

    Set<String> tables(String dataBase_Name) throws SQLException;

    void clear(String tableName) throws SQLException;

    void drop(String tableName) throws SQLException;

    List<String> find(String tableName) throws SQLException;

    int create(String tableName, ColumnsAndPropertiesSet columnsAndPropertiesSet) throws SQLException;

    int insert(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    int update(String tableName, ColumnsAndValuesSet checkedColumnsAndValuesSet,
               ColumnsAndValuesSet updatedColumnAndValueSet) throws SQLException;

    int delete(String tableName, ColumnsAndValuesSet columnsAndValuesSet) throws SQLException;

    boolean isConnected();

    void closeConnection() throws SQLException;
}
