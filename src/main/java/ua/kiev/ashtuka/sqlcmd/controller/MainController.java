package ua.kiev.ashtuka.sqlcmd.controller;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {
    private View view;
    private DataBaseManager dataBaseManager;

    MainController(View view, DataBaseManager dataBaseManager){
        this.view = view;
        this.dataBaseManager = dataBaseManager;
    }

    public void run(){
        connectToDB();
        while (true) {
            view.write("Enter your command or type 'help'");
            String command = view.read();
            if (command.equals("tables")) {
                doTables();
            } else if (command.equals("help")) {
                doHelp();
            } else if (command.equals("exit")){
                view.write("GoodBye!");
                System.exit(0);
            } else if (command.startsWith("find|")){
                doFind(command);
            } else {
                view.write("Non-existent command:" + command);
            }
        }
    }

    private void doFind(String command) {
        String[] arr = command.split("[|]");
        String tableName = arr[1];
        try {
            ArrayList<String> list = dataBaseManager.find(tableName);
            String str = list.get(0);
            list.remove(0);
            String[] obgArg = obgArg(str);
            String format = format(str);
            String[] horizontalLineObjArg = horizontalLine(str);
            tablePrint(list, obgArg, format, horizontalLineObjArg);
        } catch (SQLException e) {
            printError(e);
        }
    }

    private void tablePrint(ArrayList<String> list, String[] obgArg, String format, String[] horizontalLineObjArg) {
        view.formatWrite(format, horizontalLineObjArg);
        view.formatWrite(format, obgArg);
        view.formatWrite(format, horizontalLineObjArg);
        for (int i = 0; i < list.size(); i ++){
            String row = list.get(i);
            String[] string = row.split(" ");
            String[] obgArgForRow = obgArg(row);
            view.formatWrite(format, obgArgForRow);
        }
        view.formatWrite(format, horizontalLineObjArg);
    }

    private String format(String str) {
        String[] strings = str.split(" ");
        String st = "";
        for (int i = 0; i < strings.length; i++){

            st = st + "-20s%-2s%";
        }
        return st = "%-2s%" + st + "n";
    }

    private String[] obgArg(String str) {
        String[] strings = str.split(" ");
        String[] argsArr = new String[strings.length * 2 + 1];
        argsArr[0] = "|";
        argsArr[argsArr.length - 1] = "|";
        for (int i = 0, j = 1; i < strings.length; i++, j = j + 2 ){
            argsArr[j] = strings[i];
        }
        for (int i = 1; i < argsArr.length; i++){
            if (i % 2 == 0){
                argsArr[i] = "|";
            }
        }
        return argsArr;
    }


    private String[] horizontalLine(String str) {
        String[] strings = str.split(" ");
        String[] argsArr = new String[strings.length * 2 + 1];
        argsArr[0] = "+";
        argsArr[argsArr.length - 1] = "+";
        for (int i = 0, j = 1; i < strings.length; i++, j = j + 2 ){
            argsArr[j] = "--------------------";
        }
        for (int i = 1; i < argsArr.length; i++){
            if (i % 2 == 0){
                argsArr[i] = "+";
            }
        }
        return argsArr;
    }

    private void doHelp() {
        view.write("Available commands:");

        view.write("\ttables");
        view.write("\t\tThe command displays a list of all tables this model");

        view.write("\thelp");
        view.write("\t\tThe command displays a list of all available commands to the console");

        view.write("\texit");
        view.write("\t\tCommand to disconnect from the model and exit the application");

        view.write("\tfind|tableName");
        view.write("\t\tThe command to retrieve the contents of the specified table 'tableName'");
    }

    private void doTables() {
        try {
            dataBaseManager.tables();
        } catch (SQLException e) {
            printError(e);
        }
    }


    private void connectToDB() {
        view.write("Hi user!");
        view.write("Enter please name of model, user's name and password in format: model|userName|password");
        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("[|]");
                if (data.length != 3){
                    throw new IllegalArgumentException("Wrong number of parameters separated by a sign '|' , expected 3, but we have " + data.length);
                }
                String dataBaseName = data[0];
                String userName = data[1];
                String password = data[2];

                dataBaseManager.getConnection(dataBaseName, userName, password);
                break;
            } catch (Exception e){
                printError(e);
            }
        }
        view.write("Connection successful!");
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null){
            message = message + " " + e.getCause().getMessage();
        }
        view.write("Fail for a reason: " + message);
        view.write("Please try again!");
    }

}
