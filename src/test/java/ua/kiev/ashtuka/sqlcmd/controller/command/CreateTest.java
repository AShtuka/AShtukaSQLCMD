package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.model.ColumnsAndPropertiesSet;
import ua.kiev.ashtuka.sqlcmd.model.DataBaseManager;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class CreateTest extends OutputTest {

    private DataBaseManager dataBaseManager;
    private View view = new Console();

    @Before
    public void setup() {
        dataBaseManager = Mockito.mock(DataBaseManager.class);
    }

    @Test
    public void testCanProcessRightCommand() {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("create|");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        boolean canProcess = command.canProcess("wrongCreate|");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void normalProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        command.process("create|forTest|col1|VARCHAR|col2|INT|col3|VARCHAR|col4|INT");


        // than
        assertEquals(  "Table  - 'forTest' created\r\n", output.toString());
    }

    @Test
    public void WrongCommandFormatProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        command.process("create|");


        // than
        assertEquals(  "Fail for a reason: Invalid command format. Expected create|tableName|column1Name|column2Name|....|column_N_Name and you have entered: create|\r\n" +
                                "Please try again!\r\n", output.toString());
    }

    @Test
    public void WrongTypeFormatExceptionProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        command.process("create|forTest|col1|VARCHAR|col2|INT|col3|VARCHAR|col4|IN");


        // than
        assertEquals(  "Fail for a reason: Invalid type specified. Expected VARCHAR or INT and you have entered: IN\r\n" +
                "Please try again!\r\n", output.toString());
    }

    @Test
    public void WrongNumberParametersProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        command.process("create|forTest|col1|VARCHAR|col2|INT|col3|VARCHAR|col4");


        // than
        assertEquals(  "Fail for a reason: Invalid command format." +
                                " Expected create|tableName|column1Name|column1Type|column2Name|column2Type|....|column_N_Name|column_N_Type" +
                                " and you have entered: create|forTest|col1|VARCHAR|col2|INT|col3|VARCHAR|col4\r\n" +
                                "Please try again!\r\n", output.toString());
    }

    @Test
    public void defaultProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        command.process("create|forTest|col1|col2|col3|col4");

        // than
        assertEquals(  "Table  - 'forTest' created\r\n", output.toString());
    }

    @Test
    public void createExistTableProcess() throws Exception {

        //given
        Command command = new Create(dataBaseManager, view);

        // when
        ColumnsAndPropertiesSet set = Mockito.any(ColumnsAndPropertiesSet.class);
        Mockito.when(dataBaseManager.create("existTable", any(ColumnsAndPropertiesSet.class))).thenThrow(SQLException.class);
        command.process("create|existTable|col1|VARCHAR|col2|INT|col3|VARCHAR|col4|INT");


        // than
        assertEquals(  "Fail for a reason: null\r\n" +
                                "Please try again!\r\n", output.toString());
    }

}