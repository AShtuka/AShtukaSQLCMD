package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class TablesTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void canProcess()  {
        //given
        Command command = new Tables(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("tables");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void canProcessWithWrongCommand()  {
        //given
        Command command = new Tables(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("wrongCommand");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void processTablesNonExistentTable() throws SQLException {

        //given
        Command command = new Tables(dataBaseManager, view);

        // when
        Mockito.doThrow(SQLException.class).when(dataBaseManager).tables();

        // than
        command.process("tables|NonExistTable");
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n" , output.toString());
    }

    @Test
    public void process() throws Exception {

        //given
        Command command = new Tables(dataBaseManager, view);

        // when
        Set<String> list = new LinkedHashSet<>();
        list.add("Table_name = contact");
        list.add("Table_name = contact_hobby_detail");
        list.add("Table_name = contact_tel_detail");
        list.add("Table_name = hobby");
        list.add("Table_name = user");
        Mockito.doReturn(list).when(dataBaseManager).tables();

        // than
        command.process("tables");
        assertEquals(  "Table_name = contact\r\n" +
                                "Table_name = contact_hobby_detail\r\n" +
                                "Table_name = contact_tel_detail\r\n" +
                                "Table_name = hobby\r\n" +
                                "Table_name = user\r\n", output.toString());

    }

}

