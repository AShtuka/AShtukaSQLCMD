package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.view.View;

public class Help implements Command {

    private View view;

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Available commands:");

        view.write("\tconnect|model|user|password");
        view.write("\t\tThis command to connect to the specified model 'model'");

        view.write("\ttables");
        view.write("\t\tThe command displays a list of all tables this model");

        view.write("\thelp");
        view.write("\t\tThe command displays a list of all available commands to the console");

        view.write("\texit");
        view.write("\t\tCommand to disconnect from the model and exit the application");

        view.write("\tfind|tableName");
        view.write("\t\tThe command to retrieve the contents of the specified table 'tableName'");
    }
}
