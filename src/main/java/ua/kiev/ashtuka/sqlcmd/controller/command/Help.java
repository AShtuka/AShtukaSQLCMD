package ua.kiev.ashtuka.sqlcmd.controller.command;

import ua.kiev.ashtuka.sqlcmd.view.View;

public class Help implements Command {
    private View view;

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Available commands:");

        view.write("\thelp");
        view.write("\t\tThe command displays a list of all available commands to the console");

        view.write("\tconnect|model|user|password");
        view.write("\t\tThis command to connect to the specified model 'model'");

        view.write("\ttables");
        view.write("\t\tThe command displays a list of all tables this model");

        view.write("\tcreate|tableName|column1Name|column2Name|....|column_N_Name");
        view.write("\t\tThe command creates a new table with the specified fields");
        view.write("\t\t\twhere: tableName - table name\n" +
                   "\t\t\tcolumn1 - name of the first column of the record\n" +
                   "\t\t\tcolumn2 - the name of the second column of the record\n" +
                   "\t\t\tcolumnN is the name of the nth column of the record\n" +
                   "\t\tDefault type of field is VARCHAR. Record length 20 characters." +
                      " If you want to change the field type enter the command in the format\n" +
                   "\t\tcreate|tableName|column1Name|column1Type|column2Name|column2Type|....|column_N_Name|column_N_Type\n" +
                   "\t\t\twhere columnType is field type\n" +
                   "\t\tYou can use the following types only\n" +
                   "\t\t\tVARCHAR\n" +
                   "\t\t\tINT");

        view.write("\tinsert|tableName|column1Name|value1|column2Name|value2|....|column_N_Name|value_N");
        view.write("\t\tA command to insert one row into a specified table");
        view.write("\t\t\twhere: tableName - table name\n" +
                   "\t\t\tcolumn1Name - name of the first column of the record\n" +
                   "\t\t\tvalue1 - the value of the first column of the record\n" +
                   "\t\t\tcolumn2Name - the name of the second column of the record\n" +
                   "\t\t\tvalue2 - the value of the second column of the record\n" +
                   "\t\t\tcolumn_N_Name is the name of the nth column of the record\n" +
                   "\t\t\tvalue_N is the value of the nth column of the record");

        view.write("\tupdate|tableName|column1Name|Value1|column2Name|Value2|......|column_N_Name|Value_N");
        view.write("\t\tThe command will update the entry, setting the value of 'column2Name = Value2'," +
                   " for which the condition 'column1Name = Value1' is met");
        view.write("\t\t\twhere: tableName - table name\n" +
                   "\t\t\tcolumn1Name - the name of the column of the record to be checked\n" +
                   "\t\t\tvalue1Name - value to which the column 'column1Name' for the record to be updated must match\n" +
                   "\t\t\tcolumn2Name - the name of the column to be updated\n" +
                   "\t\t\tValue2 - the value of the column to be updated\n" +
                   "\t\t\tcolumn_N_Name - name of the n-th updated column of the record\n" +
                   "\t\t\tValue_N is the value of the n-th updated column of the record");

        view.write("\tdelete|tableName|columnName|Value");
        view.write("\t\tThe command deletes one or more records for which the 'column = value' condition is met");
        view.write("\t\t\twhere: tableName - table name\n" +
                   "\t\t\tcolumnName - the name of the column of the record that is checked\n" +
                   "\t\t\tValue - the value to which the column 'columnName' must match for the record to be deleted");

        view.write("\tfind|tableName");
        view.write("\t\tThe command to retrieve the contents of the specified table 'tableName'");

        view.write("\tclear|tableName");
        view.write("\t\tThe command deletes all records from the specified table 'tableName'");

        view.write("\tdrop|tableName");
        view.write("\t\tThe command deletes the specified table 'tableName'");

        view.write("\texit");
        view.write("\t\tCommand to disconnect from the model and exit the application");
    }
}
