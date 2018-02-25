package ua.kiev.ashtuka.sqlcmd.controller;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.model.JDBCDataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        View view = new Console();
        DataBaseManager dataBaseManager = new JDBCDataBaseManager();

        MainController mainController = new MainController(view, dataBaseManager);
        mainController.run();
    }
}
