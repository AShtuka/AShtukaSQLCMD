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

public class InsertTest extends OutputTest{

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommand() {

        //given
        Command command = new Insert(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("insert|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Insert(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("wrongInsert|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void normalProcess() throws Exception {

        //given
        Command command = new Insert(dataBaseManager, view);

        // when
        ArrayList<String> list = new ArrayList<>();
        list.add("name VARCHAR secondName VARCHAR age INT");
        list.add("Sasha Shtuka 31");
        list.add("Marina Shtuka 28");
        list.add("Nick Shtuka 2");

        Mockito.doReturn(list).when(dataBaseManager).find("user");
        command.process("insert|user|name|Nick|secondName|Shtuka|age|2");

        // than
        assertEquals(  "+---------------------+---------------------+---------------------+\r\n" +
                                "| name                | secondName          | age                 | \r\n" +
                                "+---------------------+---------------------+---------------------+\r\n" +
                                "| Sasha               | Shtuka              | 31                  | \r\n" +
                                "| Marina              | Shtuka              | 28                  | \r\n" +
                                "| Nick                | Shtuka              | 2                   | \r\n" +
                                "+---------------------+---------------------+---------------------+\r\n", output.toString());
    }

    @Test
    public void WrongNumberParametersProcess() throws Exception {

        //given
        Command command = new Insert(dataBaseManager, view);

        // when
        command.process("insert|user|name|Nick|secondName|Shtuka|age");


        // than
        assertEquals(  "Fail for a reason: Invalid command format." +
                                " Expected insert|tableName|column1Name|Value1|column2Name|Value2|....|column_N_Name|Value_N" +
                                " and you have entered: insert|user|name|Nick|secondName|Shtuka|age\r\n" +
                                "Please try again!\r\n", output.toString());
    }

    @Test
    public void deleteProcessWithSQLException() throws Exception {

        //given
        Command command = new Insert(dataBaseManager, view);

        // when
        ColumnsAndValuesSet set = Mockito.any(ColumnsAndValuesSet.class);
        Mockito.when(dataBaseManager.insert("unrealTable", any(ColumnsAndValuesSet.class))).thenThrow(SQLException.class);
        command.process("insert|unrealTable|name|Nick|secondName|Shtuka|age|31");


        // than
        assertEquals(  "Fail for a reason: null\r\n" +
                "Please try again!\r\n", output.toString());
    }


}