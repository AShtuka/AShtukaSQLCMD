package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view){
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Non-existent command:" + command);
    }
}
