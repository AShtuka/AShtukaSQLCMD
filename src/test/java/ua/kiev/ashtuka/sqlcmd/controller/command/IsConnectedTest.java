package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class IsConnectedTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void canProcessBeforeConnection() throws Exception {
        //given
        Command command = new IsConnected(dataBaseManager, view);

        // when
        when(dataBaseManager.isConnected()).thenReturn(false);

        // than
        boolean canProcess = command.canProcess("help|");
        assertTrue(canProcess);
    }

    @Test
    public void process() throws Exception {

        //given
        Command command = new IsConnected(dataBaseManager, view);

        // when
        command.process("clear|");

        // than
        assertEquals(  "You can not use the command 'clear|' till do not connect using the command connect|model|user|password\r\n" , output.toString());

    }

}