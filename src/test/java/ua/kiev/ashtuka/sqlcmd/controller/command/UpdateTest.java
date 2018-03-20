package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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

public class UpdateTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommand() {

        //given
        Command command = new Update(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("update|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Update(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("wrongUpdate|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void normalProcess() throws Exception {

        //given
        Command command = new Update(dataBaseManager, view);

        // when
        ArrayList<String> list = new ArrayList<>();
        list.add("name secondName age");
        list.add("Sasha Shtuka 31");
        list.add("Marina Shtuka 28");
        list.add("Nick King 4");

        Mockito.doReturn(list).when(dataBaseManager).find("user");
        command.process("update|user|name|Nick|secondName|King|age|4");

        // than
        assertEquals(  "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| name                | secondName          | age                 | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                                "| Sasha               | Shtuka              | 31                  | \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "| Nick                | King                | 4                   | \r\n" +
                                "+ --------------------+ --------------------+ --------------------+ \r\n", output.toString());
    }

    @Test
    public void WrongNumberParametersProcess() throws Exception {

        //given
        Command command = new Update(dataBaseManager, view);

        // when
        command.process("update|user|name|Nick|secondName|King|age");


        // than
        assertEquals(  "Fail for a reason: Invalid command format." +
                                " Expected update|tableName|column1Name|Value1|column2Name|Value2|....|column_N_Name|Value_N" +
                                " and you have entered: update|user|name|Nick|secondName|King|age\r\n" +
                                "Please try again!\r\n", output.toString());
    }

    @Test
    public void updateProcessWithSQLException() throws Exception {

        //given
        Command command = new Update(dataBaseManager, view);

        // when
        ColumnsAndValuesSet set = Mockito.any(ColumnsAndValuesSet.class);
        Mockito.when(dataBaseManager.update("unrealTable", any(ColumnsAndValuesSet.class), any(ColumnsAndValuesSet.class))).thenThrow(SQLException.class);
        command.process("update|user|name|Nick|secondName|King|age|4");


        // than
        assertEquals(  "Fail for a reason: null\r\n" +
                "Please try again!\r\n", output.toString());
    }

}