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
import static org.mockito.Mockito.*;

public class ConnectTest extends OutputTest{

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setUp() throws Exception {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void canProcess() throws Exception {
        //given
        Command command = new Connect(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("connect|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Connect(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("con|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void process() throws SQLException {

        //given
        Command command = new Connect(dataBaseManager, view);

        // when
        when(dataBaseManager.getConnection("modelName","userName", "password")).thenReturn(null);

        // than
        command.process("connect|modelName|userName|password");
        assertEquals(  "Connection successful!\r\n" , output.toString());
    }

    @Test
    public void processWithSQLException() throws SQLException {

        //given
        Command command = new Connect(dataBaseManager, view);

        // when
        when(dataBaseManager.getConnection("modelName","userName", "password")).thenThrow(SQLException.class);

        // than
        command.process("connect|modelName|userName|password");
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n" , output.toString());
    }

    @Test
    public void processWithWrongNumberOfParameters() throws SQLException {

        //given
        Command command = new Connect(dataBaseManager, view);

        // when
        command.process("connect|modelName|userName");

        // than
        assertEquals(  "Fail for a reason: Wrong number of parameters separated by a sign '|' , expected '4', but we have '3'\r\n" +
                                "Please try again!\r\n" , output.toString());
    }

}