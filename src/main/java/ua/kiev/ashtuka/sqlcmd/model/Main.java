package ua.kiev.ashtuka.sqlcmd.model;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
   // private static final String URL = "jdbc:mysql://localhost:3306/test";

    public static void main(String[] args) throws SQLException {
       ColumnsAndValuesSet checked = new ColumnsAndValuesSet();
       ColumnsAndValuesSet update = new ColumnsAndValuesSet();
       checked.put("ID", "1");
       update.put("first_name", "Barni");
       update.put("last_name", "Cat");
       update.put("version", "5");
       JDBCDataBaseManager manager = new JDBCDataBaseManager();
       manager.update("contact", checked, update);
    }
}
