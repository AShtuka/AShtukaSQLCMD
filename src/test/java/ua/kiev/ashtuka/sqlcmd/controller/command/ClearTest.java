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

public class ClearTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void canProcess()  {
        //given
        Command command = new Clear(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("clear|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Clear(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("fin|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void process() throws SQLException{


        //given
        Command command = new Clear(dataBaseManager, view);

        // when
        Mockito.doNothing().when(dataBaseManager).clear("user");

        // than
       command.process("clear|user");
       assertEquals(  "From table 'user' all data deleted\r\n" , output.toString());
    }

    @Test
    public void processClearNonExistentTable() throws SQLException{


        //given
        Command command = new Clear(dataBaseManager, view);

        // when
        Mockito.doThrow(SQLException.class).when(dataBaseManager).clear("NonExistTable");

        // than
        command.process("clear|NonExistTable");
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n" , output.toString());
    }

}