package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndPropertiesSet;
import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndValuesSet;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class DeleteTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommand() {

        //given
        Command command = new Delete(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("delete|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Delete(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("wrongDelete|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void normalProcess() throws Exception {
        //given
        Command command = new Delete(dataBaseManager, view);

        // when
        ArrayList<String> list = new ArrayList<>();
        list.add("name secondName age");
        list.add("Marina Shtuka 28");
        list.add("Marina Shtuka 28");
        Mockito.doReturn(list).when(dataBaseManager).find("user");
        command.process("delete|user|age|31");


        // than
        assertEquals(  "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| name                | secondName          | age                 | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n", output.toString());
    }

    @Test
    public void WrongNumberParametersProcess() throws Exception {

        //given
        Command command = new Delete(dataBaseManager, view);

        // when
        command.process("delete|user|age");


        // than
        assertEquals(  "Fail for a reason: Invalid command format. Expected delete|tableName|columnName|Value and you have entered: delete|user|age\r\n" +
                                "Please try again!\r\n", output.toString());
    }

    @Test
    public void deleteProcessWithSQLException() throws Exception {

        //given
        Command command = new Delete(dataBaseManager, view);

        // when
        ColumnsAndValuesSet set = Mockito.any(ColumnsAndValuesSet.class);
        Mockito.when(dataBaseManager.delete("user", any(ColumnsAndValuesSet.class))).thenThrow(SQLException.class);
        command.process("delete|user|age|31");


        // than
        assertEquals(  "Fail for a reason: null\r\n" +
                "Please try again!\r\n", output.toString());
    }

}