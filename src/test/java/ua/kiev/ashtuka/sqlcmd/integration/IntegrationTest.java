package ua.kiev.ashtuka.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.kiev.ashtuka.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private  ConfigurableInputStream in;
    private  ByteArrayOutputStream out;

    @Before
    public  void setup(){
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() throws SQLException {
        // given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Available commands:\r\n" +
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
                "\t\tCommand to disconnect from the model and exit the application\r\n" +
                "Enter your command or type 'help'\r\n" +
                "GoodBye!\r\n", getData());
    }

    @Test
    public void testExit() throws SQLException {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "GoodBye!\r\n", getData());

    }

    @Test
    public void tablesWithoutConnect() throws SQLException {
        // given
        in.add("tables");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                // tables
                "You can not use the command 'tables' till do not connect using the command connect|model|user|password\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void FindUserWithoutConnect() throws SQLException {
        // given
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                // find|user
                "You can not use the command 'find|user' till do not connect using the command connect|model|user|password\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void UnsupportedWithoutConnect() throws SQLException {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                // unsupported
                "You can not use the command 'unsupported' till do not connect using the command connect|model|user|password\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void UnsupportedAfterConnect() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                // unsupported
                "Enter your command or type 'help'\r\n" +
                "Non-existent command:unsupported\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void TablesAfterConnect() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("tables");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // tables
                "Table_name = contact\r\n" +
                        "Table_name = contact_hobby_detail\r\n" +
                        "Table_name = contact_tel_detail\r\n" +
                        "Table_name = hobby\r\n" +
                        "Table_name = user\r\n" +
                        "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void FindNonExistAfterConnect() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("find|nonExist");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // find|nonExist
                "Fail for a reason: Table 'test.nonexist' doesn't exist\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void FindUserAfterConnect() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // find|user
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| name                | secondName          | age                 | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| Sasha               | Shtuka              | 31                  | \r\n" +
                "| Marina              | Shtuka              | 28                  | \r\n" +
                "| Sasha               | Shtuka              | 31                  | \r\n" +
                "| Marina              | Shtuka              | 28                  | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void ConnectAfterConnect() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("tables");
        in.add("connect|start|root|root");
        in.add("tables");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // tables
                "Table_name = contact\r\n" +
                "Table_name = contact_hobby_detail\r\n" +
                "Table_name = contact_tel_detail\r\n" +
                "Table_name = hobby\r\n" +
                "Table_name = user\r\n" +
                "Enter your command or type 'help'\r\n" +
                // connection
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // tables
                "Table_name = contact\r\n" +
                "Table_name = contact_hobby_detail\r\n" +
                "Table_name = contact_tel_detail\r\n" +
                "Table_name = hobby\r\n" +
                "Table_name = user\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void ConnectWithWrongNumberOfParameters() throws SQLException {
        // given
        in.add("connect|test|root");;
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" +
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                // connection with wrong number of parameters
                "Fail for a reason: Wrong number of parameters separated by a sign '|' , expected '4', but we have '3'\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void CreateInsertUpdateDeleteClearDropTest() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("create|fortest|col1|VARCHAR|col2|INT|col3|VARCHAR");
        in.add("insert|fortest|col1|Mary|col2|28|col3|Shtuka");
        in.add("insert|fortest|col1|Vika|col2|38|col3|Nikitina");
        in.add("update|fortest|col1|Vika|col2|18|col3|Golovko");
        in.add("delete|fortest|col1|Vika");
        in.add("clear|fortest");
        in.add("drop|fortest");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // create
                "Table  - 'fortest' created\r\n" +
                "Enter your command or type 'help'\r\n" +
                // insert
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| col1                | col2                | col3                | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| Mary                | 28                  | Shtuka              | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "Enter your command or type 'help'\r\n" +
                // insert
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| col1                | col2                | col3                | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| Mary                | 28                  | Shtuka              | \r\n" +
                "| Vika                | 38                  | Nikitina            | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "Enter your command or type 'help'\r\n" +
                // update
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| col1                | col2                | col3                | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| Mary                | 28                  | Shtuka              | \r\n" +
                "| Vika                | 18                  | Golovko             | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "Enter your command or type 'help'\r\n" +
                // delete
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| col1                | col2                | col3                | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "| Mary                | 28                  | Shtuka              | \r\n" +
                "+ --------------------+ --------------------+ --------------------+ \r\n" +
                "Enter your command or type 'help'\r\n" +
                // clear
                "From table 'fortest' all data deleted\r\n" +
                "Enter your command or type 'help'\r\n" +
                // delete
                "Table 'fortest' deleted\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void ClearWithError() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("clear|unreal");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // clear|unreal
                "Fail for a reason: Table 'test.unreal' doesn't exist\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void CreateWithCommandFormatException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("create|unreal");
        in.add("create|unreal|col1|VARCHAR|clo2");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // create with wrong command format (first case)
                "Fail for a reason: Invalid command format. Expected create|tableName|column1Name|column2Name|....|column_N_Name and you have entered: create|unreal\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // create with wrong command format (second case)
                "Fail for a reason: Invalid command format. Expected create|tableName|column1Name|column1Type|column2Name|column2Type|....|column_N_Name|column_N_Type and you have entered: create|unreal|col1|VARCHAR|clo2\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void CreateWithWrongTypeFormatException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("create|unreal|col1|VARCHAR|clo2|INTT");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // create with wrong type
                "Fail for a reason: Invalid type specified. Expected VARCHAR or INT and you have entered: INTT\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());

    }

    @Test
    public void CreateExistTable() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("create|user|col1|col2");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // create exist table
                "Fail for a reason: Table 'user' already exists\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void DeleteWithCommandFormatException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("delete|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // delete with wrong command format
                "Fail for a reason: Invalid command format. Expected delete|tableName|columnName|Value and you have entered: delete|user\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void DeleteWithSQLException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("delete|user|Petro|Mogila");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // delete unreal column
                "Fail for a reason: Unknown column 'Petro' in 'where clause'\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void DropWithSQLException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("drop|Petro");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // drop unreal table
                "Fail for a reason: Unknown table 'test.petro'\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void InsertWithCommandFormatException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("insert|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // insert with wrong command format
                "Fail for a reason: Invalid command format. Expected insert|tableName|column1Name|Value1|column2Name|Value2|....|column_N_Name|Value_N and you have entered: insert|user\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void InsertWithSQLException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("insert|Petro|col1|Mogyla");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // insert in unreal table
                "Fail for a reason: Table 'test.petro' doesn't exist\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void UpdateWithCommandFormatException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("update|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // update with wrong command format
                "Fail for a reason: Invalid command format. Expected update|tableName|column1Name|Value1|column2Name|Value2|....|column_N_Name|Value_N and you have entered: update|user\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

    @Test
    public void UpdateWithSQLException() throws SQLException {
        // given
        in.add("connect|test|root|root");
        in.add("update|unreal|col1|val1|col2|val2|col3|val4");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hi user!\r\n" + // connection
                "Enter please name of model, user's name and password in format: connect|model|userName|password\r\n" +
                "Connection successful!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // update unreal table
                "Fail for a reason: Table 'test.unreal' doesn't exist\r\n" +
                "Please try again!\r\n" +
                "Enter your command or type 'help'\r\n" +
                // exit
                "GoodBye!\r\n", getData());
    }

}
