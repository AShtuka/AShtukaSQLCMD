package ua.kiev.ashtuka.sqlcmd.controller;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.model.JDBCDataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        View view = new Console();
        DataBaseManager dataBaseManager = null;
        try {
            dataBaseManager = new JDBCDataBaseManager();
        } catch (SQLException e) {
            view.printError(e);
        }

        MainController mainController = new MainController(view, dataBaseManager);
        mainController.run();
    }
}
// create|fortest|col1|VARCHAR|col2|INT|col3|VARCHAR|col4|INT
//insert|simple|column1|Mary|column2|Shtuka
// connect|test|root|root
//delete|simple|column1|Mary
// update|contact|ID|1|first_name|Barni|last_name|Cat|version|5
//insert|fortest|col1|Mary|col2|28|col3|Shtuka