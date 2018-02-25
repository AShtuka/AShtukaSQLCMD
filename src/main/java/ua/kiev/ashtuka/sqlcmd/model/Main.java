package ua.kiev.ashtuka.sqlcmd.model;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
   // private static final String URL = "jdbc:mysql://localhost:3306/test";

    public static void main(String[] args) throws SQLException {
        DataBaseManager dbm = new JDBCDataBaseManager();
        Connection connection = dbm.getConnection("test", USERNAME, PASSWORD);
       // dbm.tables("test");
        //ColumnsAndPropertiesSet columnsAndPropertiesSet = new ColumnsAndPropertiesSet();
        //columnsAndPropertiesSet.put("name", ColumnType.VARCHAR, 30);
       // columnsAndPropertiesSet.put("secondName", ColumnType.VARCHAR, 30);
       // columnsAndPropertiesSet.put("age", ColumnType.INT);
      ColumnsAndValuesSet columnsAndValuesSet = new ColumnsAndValuesSet();
      /*  columnsAndValuesSet.put("name" , "Sasha");
        columnsAndValuesSet.put("secondName" , "Shtuka");
        columnsAndValuesSet.put("age" , 31);
        dbm.insert("user", columnsAndValuesSet);
        columnsAndValuesSet.clearColumnAndValueSet();
        columnsAndValuesSet.put("name" , "Marina");
        columnsAndValuesSet.put("secondName" , "Shtuka");
        columnsAndValuesSet.put("age" , 28);
        dbm.insert("user", columnsAndValuesSet);
        columnsAndValuesSet.clearColumnAndValueSet();
        columnsAndValuesSet.put("name" , "Jim");
        columnsAndValuesSet.put("secondName" , "Kerry");
        columnsAndValuesSet.put("age" , 58);
        dbm.insert("user", columnsAndValuesSet);
        columnsAndValuesSet.clearColumnAndValueSet();*/

       // dbm.create("user", columnsAndPropertiesSet);
       /* dbm.tables("test");
        dbm.insert("user", columnsAndValuesSet);
        dbm.insert("user", columnsAndValuesSet);
        dbm.insert("user", columnsAndValuesSet);
        dbm.insert("user", columnsAndValuesSet);*/
      // dbm.find("user");
      // dbm.clear("user");
       // System.out.println("after");
      // dbm.find("user");
      //  columnsAndValuesSet.clearColumnAndValueSet();
       // columnsAndValuesSet.put("name" , "Jim");
       // dbm.delete("user", columnsAndValuesSet);
      //  columnsAndValuesSet.clearColumnAndValueSet();
       // System.out.println("/////////////////////////////////////");
       // dbm.find("user");
        //System.out.println("/////////////////////////////////////");
        //columnsAndValuesSet.put("name", "Mudac", "Sasha");
        //dbm.update("user", columnsAndValuesSet);
        //columnsAndValuesSet.clearColumnAndValueSet();
        ArrayList<String> list = dbm.find("user");
       // System.out.println(list);
       // System.out.printf("%-20s%-10s%-10s%n", "имя поля", "значение", "тип поля");
        String str = list.get(0);
        list.remove(0);
        String[] strings = str.split(" ");
        String[] forPrint = new String[strings.length * 2 + 1];
        String st = "";
        forPrint[0] = "|";
        forPrint[forPrint.length - 1] = "|";
        st = getStringForPrint(strings, forPrint, st);
        st = "%-2s%" + st + "n";
        System.out.printf(st, "+", "--------------------", "+",  "--------------------", "+", "--------------------", "+");
       System.out.printf(st, "|", strings[0], "|",  strings[1], "|", strings[2], "|");
        System.out.printf(st, "+", "--------------------", "+",  "--------------------", "+", "--------------------", "+");
       for (int i = 0; i < list.size(); i++){
           String str1 = list.get(i);
           String[] string = str1.split(" ");
           System.out.printf(st, "|", string[0], "|",  string[1], "|", string[2], "|");
       }
        System.out.printf(st, "+", "--------------------", "+",  "--------------------", "+", "--------------------", "+");



       // * output.println("%-20s%-10s%-10s%n", "имя поля", "значение", "тип поля");
       // System.out.printf("%s%n", strings[0]);
        //System.out.printf("%s%n", strings[1]);
       // System.out.printf("%s%n", strings[2]);
       // ..............
       // for (Field field : list) {
        // Выводим имя поля
       // output.printf("%-20s", field.getName());
        // Выводим значение поля
       // field.setAccessible(true);  // !!! разрешить доступ к значениям полей !!!
       // output.printf("%10s", field.get(object));
        // Выводим тип поля
       // if (field.getType().toString().equals("class java.lang.String"))
       //  output.println("String");
       // else
       //  output.println(field.getType().toString());
        }

    private static String getStringForPrint(String[] strings, String[] forPrint, String st) {
        for (int i = 0, j = 1; i < strings.length; i++, j = j + 2 ){
            if (i % 2 == 0){
                forPrint[i + 2] = "|";
            }
            forPrint[j] = strings[i];
            st = st + "-20s%-2s%";
        }
        return st;
    }
}
