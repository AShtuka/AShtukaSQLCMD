package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.view.Console;
import ua.kiev.ashtuka.sqlcmd.view.View;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HelpTest extends OutputTest{

    private View view = new Console();


    @Test
    public void testCanProcessRightCommandHelp(){
        // given
        Command command = new Help(view);

        // when
        boolean canProcess = command.canProcess("help");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommandNotHelp(){
        // given
        Command command = new Help(view);

        // when
        boolean canProcess = command.canProcess("notHelp");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void process() throws Exception {
        // given
        Command command = new Help(view);

        // when
        command.process("help");

        // than
        assertEquals(  "Available commands:\r\n" +
                                "\thelp\r\n" +
                                "\t\tThe command displays a list of all available commands to the console\r\n" +
                                "\tconnect|model|user|password\r\n" +
                                "\t\tThis command to connect to the specified model 'model'\r\n" +
                                "\ttables\r\n" +
                                "\t\tThe command displays a list of all tables this model\r\n" +
                                "\tcreate|tableName|column1Name|column2Name|....|column_N_Name\r\n" +
                                "\t\tThe command creates a new table with the specified fields\r\n" +
                                "\t\t\twhere: tableName - table name\r\n" +
                                "\t\t\tcolumn1 - name of the first column of the record\r\n" +
                                "\t\t\tcolumn2 - the name of the second column of the record\r\n" +
                                "\t\t\tcolumnN is the name of the nth column of the record\r\n" +
                                "\t\tDefault type of field is VARCHAR. Record length 20 characters. If you want to change the field type enter the command in the format\r\n" +
                                "\t\tcreate|tableName|column1Name|column1Type|column2Name|column2Type|....|column_N_Name|column_N_Type\r\n" +
                                "\t\t\twhere columnType is field type\r\n" +
                                "\t\tYou can use the following types only\r\n" +
                                "\t\t\tVARCHAR\r\n" +
                                "\t\t\tINT\r\n" +
                                "\tinsert|tableName|column1Name|value1|column2Name|value2|....|column_N_Name|value_N\r\n" +
                                "\t\tA command to insert one row into a specified table\r\n" +
                                "\t\t\twhere: tableName - table name\r\n" +
                                "\t\t\tcolumn1Name - name of the first column of the record\r\n" +
                                "\t\t\tvalue1 - the value of the first column of the record\r\n" +
                                "\t\t\tcolumn2Name - the name of the second column of the record\r\n" +
                                "\t\t\tvalue2 - the value of the second column of the record\r\n" +
                                "\t\t\tcolumn_N_Name is the name of the nth column of the record\r\n" +
                                "\t\t\tvalue_N is the value of the nth column of the record\r\n" +
                                "\tupdate|tableName|column1Name|Value1|column2Name|Value2|......|column_N_Name|Value_N\r\n" +
                                "\t\tThe command will update the entry, setting the value of 'column2Name = Value2', for which the condition 'column1Name = Value1' is met\r\n" +
                                "\t\t\twhere: tableName - table name\r\n" +
                                "\t\t\tcolumn1Name - the name of the column of the record to be checked\r\n" +
                                "\t\t\tvalue1Name - value to which the column 'column1Name' for the record to be updated must match\r\n" +
                                "\t\t\tcolumn2Name - the name of the column to be updated\r\n" +
                                "\t\t\tValue2 - the value of the column to be updated\r\n" +
                                "\t\t\tcolumn_N_Name - name of the n-th updated column of the record\r\n" +
                                "\t\t\tValue_N is the value of the n-th updated column of the record\r\n" +
                                "\tdelete|tableName|columnName|Value\r\n" +
                                "\t\tThe command deletes one or more records for which the 'column = value' condition is met\r\n" +
                                "\t\t\twhere: tableName - table name\r\n" +
                                "\t\t\tcolumnName - the name of the column of the record that is checked\r\n" +
                                "\t\t\tValue - the value to which the column 'columnName' must match for the record to be deleted\r\n" +
                                "\tfind|tableName\r\n" +
                                "\t\tThe command to retrieve the contents of the specified table 'tableName'\r\n" +
                                "\tclear|tableName\r\n" +
                                "\t\tThe command deletes all records from the specified table 'tableName'\r\n" +
                                "\tdrop|tableName\r\n" +
                                "\t\tThe command deletes the specified table 'tableName'\r\n" +
                                "\texit\r\n" +
                                "\t\tCommand to disconnect from the model and exit the application\r\n" , output.toString());

    }
}