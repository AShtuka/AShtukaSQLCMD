package ua.kiev.ashtuka.sqlcmd.view;

import org.mockito.internal.verification.api.VerificationData;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;

import java.util.ArrayList;

public interface View {
    void write(String message);
    void printError(Exception e);
    String read();
    void printTable(ArrayList<String> list);
}
