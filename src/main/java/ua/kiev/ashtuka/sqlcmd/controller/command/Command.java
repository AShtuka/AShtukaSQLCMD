package ua.kiev.ashtuka.sqlcmd.controller.command;

import java.sql.SQLException;

public interface Command {
    boolean canProcess(String command);
    void process(String command);
}
