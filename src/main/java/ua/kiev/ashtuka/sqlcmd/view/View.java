package ua.kiev.ashtuka.sqlcmd.view;

public interface View {
    void write(String message);
    void formatWrite(String format, Object...arg);
    String read();
}
