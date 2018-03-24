package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class ExitTest extends OutputTest {

    private View view = new Console();
    private DataBaseManager dataBaseManager;

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommandExit(){
        // given
        Command command = new Exit(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("exit");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommandExit(){
        // given
        Command command = new Exit(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("ex");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void testProcessCommandExit_throwSQLException() throws SQLException {
        // given
        Command command = new Exit(dataBaseManager, view);

        // when
        Mockito.doThrow(SQLException.class).when(dataBaseManager).closeConnection();

        // than
        try {
            command.process("exit");
        } catch (ExitException ex){
            view.write("Expected ExitException");
        }
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n" +
                                "GoodBye!\r\n" +
                                "Expected ExitException\r\n", output.toString());
    }

    @Test
    public void testProcessCommandExit_throwExitException() throws SQLException {
        // given
        Command command = new Exit(dataBaseManager, view);

        // when
        try {
            command.process("exit");
        } catch (ExitException ex){
            view.write("Expected ExitException");
        }

        // than
        assertEquals(  "GoodBye!\r\n"
                              + "Expected ExitException\r\n", output.toString());
    }

}
