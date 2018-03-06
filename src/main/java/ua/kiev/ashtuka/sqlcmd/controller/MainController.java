package ua.kiev.ashtuka.sqlcmd.controller;

import ua.kiev.ashtuka.sqlcmd.controller.command.*;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.View;

public class MainController {
    private View view;
    private DataBaseManager dataBaseManager;
    private Command[] commands;

    MainController(View view, DataBaseManager dataBaseManager){
        this.view = view;
        this.dataBaseManager = dataBaseManager;
        this.commands = new Command[]{
                new Connect(dataBaseManager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(dataBaseManager, view),
                new Tables(dataBaseManager, view),
                new Find(dataBaseManager, view),
                new Clear(dataBaseManager, view),
                new Drop(dataBaseManager, view),
                new Create(dataBaseManager, view),
                new Insert(dataBaseManager, view),
                new Update(dataBaseManager, view),
                new Delete(dataBaseManager, view),
                new Unsupported(view)};
    }

    public void run(){
        try {
            doWork();
        } catch (ExitException e){
            // do nothing
        }
        return;
    }

    private void doWork() {
        view.write("Hi user!");
        view.write("Enter please name of model, user's name and password in format: connect|model|userName|password");
        while (true) {
            String input = view.read();
            if (input == null){ // null if close application but it does not work in my IDEA
                new Exit(view).process(input);
            }
            for (Command command : commands){
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("Enter your command or type 'help'");
        }
    }

}
