package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

public class Connect implements Command {
    private static String COMMAND_SAMPLE = "connect|modelName|userName|password";
    private final String URL = "jdbc:mysql://localhost:3306/";
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
                    String[] parameters = command.split("[|]");
                    if (parameters.length != parametersNumber()){
                        throw new IllegalArgumentException(String.format("Wrong number of parameters separated by a sign '|' ," +
                                " expected '%s', but we have '%s'", parametersNumber(), parameters.length));
                    }
                    String dataBaseName = parameters[1];
                    String userName = parameters[2];
                    String password = parameters[3];
                    String fullURL = URL + dataBaseName;
                    dataBaseManager.getConnection(fullURL, dataBaseName, userName, password);
                    view.write("Connection successful!");
                } catch (Exception e){
                    view.printError(e);
                }
    }

    private int parametersNumber() {
        return COMMAND_SAMPLE.split("[|]").length;
    }
}
