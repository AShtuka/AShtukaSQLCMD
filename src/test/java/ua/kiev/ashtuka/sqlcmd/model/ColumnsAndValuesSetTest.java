package ua.kiev.ashtuka.sqlcmd.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnsAndValuesSetTest {


    @Test
    public void put() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);

        //then
        assertEquals("Name = \"Sasha\", age = \"21\"", set.getColumnNameColumnValue());
    }

    @Test
    public void getColumnName() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);

        //then
        assertEquals("Name, age", set.getColumnName());

        //when
        set.clear();

        //then
        assertEquals(null, set.getColumnName());
    }

    @Test
    public void getColumnValues() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);

        //then
        assertEquals("\"Sasha\", \"21\"", set.getColumnValues());

        //when
        set.clear();

        //then
        assertEquals(null, set.getColumnValues());
    }

    @Test
    public void getColumnNameColumnValue() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);

        //then
        assertEquals("Name = \"Sasha\", age = \"21\"", set.getColumnNameColumnValue());
    }

    @Test
    public void clear() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);
        set.clear();

        //then
        assertEquals(null, set.getColumnNameColumnValue());
    }

    @Test
    public void size() throws Exception {
        //given
        ColumnsAndValuesSet set = new ColumnsAndValuesSet();

        //when
        set.put("Name","Sasha");
        set.put("age",21);

        //then
        assertEquals(2, set.size());
    }

}