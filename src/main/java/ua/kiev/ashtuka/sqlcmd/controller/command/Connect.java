package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

public class Connect implements Command {
    private static String COMMAND_SAMPLE = "connect|modelName|userName|password";
    private DataBaseManager dataBaseManager;
    private View view;

    public Connect(DataBaseManager dataBaseManager, View view){
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
                try {
                    String[] data = command.split("[|]");
                    if (data.length != count()){
                        throw new IllegalArgumentException(String.format("Wrong number of parameters separated by a sign '|' , expected '%s', but we have '%s'", data.length, count()));
                    }
                    String dataBaseName = data[1];
                    String userName = data[2];
                    String password = data[3];

                    dataBaseManager.getConnection(dataBaseName, userName, password);
                    view.write("Connection successful!");
                } catch (Exception e){
                    printError(e);
                }
    }

    private int count() {
        return COMMAND_SAMPLE.split("[|]").length;
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
