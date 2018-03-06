package ua.kiev.ashtuka.sqlcmd.controller.command;

public class WrongTypeFormatException extends RuntimeException {
    public WrongTypeFormatException(String msg){
        super(msg);
    }
}
