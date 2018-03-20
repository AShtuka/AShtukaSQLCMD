package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;


import java.sql.SQLException;
import java.util.ArrayList;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class FindTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommand() {

        //given
        Command command = new Find(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("find|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Find(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("fin|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void testFind() throws SQLException {

        //given
        Command command = new Find(dataBaseManager, view);

        // when
        ArrayList<String> list = new ArrayList<>();
        list.add("name secondName age");
        list.add("Sasha Shtuka 31");
        list.add("Marina Shtuka 28");
        list.add("Sasha Shtuka 31");
        list.add("Marina Shtuka 28");
        Mockito.doReturn(list).when(dataBaseManager).find("user");

        // than
        command.process("test|user");
        assertEquals(  "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| name                | secondName          | age                 | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| Sasha               | Shtuka              | 31                  | \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "| Sasha               | Shtuka              | 31                  | \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n", output.toString());
    }

    @Test
    public void testFindNonExistentTable() throws SQLException  {

        //given
        Command command = new Find(dataBaseManager, view);

        // when
        Mockito.doThrow(SQLException.class).when(dataBaseManager).find("NonExistTable");

        // than
        command.process("test|NonExistTable");
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n", output.toString());
    }
}
