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

public class DropTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void canProcess() throws Exception {
        //given
        Command command = new Drop(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("drop|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Drop(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("drop");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void process() throws SQLException {

        //given
        Command command = new Drop(dataBaseManager, view);

        // when
        Mockito.doNothing().when(dataBaseManager).drop("user");

        // than
        command.process("drop|user");
        assertEquals(  "Table 'user' deleted\r\n" , output.toString());
    }

    @Test
    public void processDropNonExistentTable() throws SQLException{

        //given
        Command command = new Drop(dataBaseManager, view);

        // when
        Mockito.doThrow(SQLException.class).when(dataBaseManager).drop("NonExistTable");

        // than
        command.process("drop|NonExistTable");
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n" , output.toString());
    }


}