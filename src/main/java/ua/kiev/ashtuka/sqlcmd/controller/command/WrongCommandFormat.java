package ua.kiev.ashtuka.sqlcmd.controller.command;

public class WrongCommandFormat extends RuntimeException{
    public WrongCommandFormat(String smg){
        super(smg);
    }
}
