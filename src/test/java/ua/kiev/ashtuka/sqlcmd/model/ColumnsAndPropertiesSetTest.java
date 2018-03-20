package ua.kiev.ashtuka.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnsAndPropertiesSetTest {

    @Test
    public void putWithVarcharSize() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when
        set.put("columnName", ColumnType.VARCHAR, 30);

        //then
        assertEquals("columnName VARCHAR(30)", set.getColumnNamePlusColumnType());

    }

    @Test
    public void putWithoutVarcharSize() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when
        set.put("columnName", ColumnType.VARCHAR);

        //then
        assertEquals("columnName VARCHAR", set.getColumnNamePlusColumnType());
    }

    @Test
    public void getColumnNamePlusColumnType() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when
        set.put("columnOne", ColumnType.VARCHAR,30);
        set.put("columnTwo", ColumnType.VARCHAR);
        set.put("columnThree", ColumnType.INT);

        //then
        assertEquals("columnOne VARCHAR(30), columnTwo VARCHAR, columnThree INT", set.getColumnNamePlusColumnType());
    }

    @Test
    public void getColumnNamePlusColumnTypeEmptySet() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when


        //then
        assertEquals(null, set.getColumnNamePlusColumnType());
    }

    @Test
    public void clear() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when
        set.put("columnOne", ColumnType.VARCHAR,30);
        set.put("columnTwo", ColumnType.VARCHAR);
        set.put("columnThree", ColumnType.INT);
        set.clear();

        //then
        assertEquals(0, set.size());
    }

    @Test
    public void size() throws Exception {

        //given
        ColumnsAndPropertiesSet set = new ColumnsAndPropertiesSet();

        //when
        set.put("columnOne", ColumnType.VARCHAR,30);
        set.put("columnTwo", ColumnType.VARCHAR);
        set.put("columnThree", ColumnType.INT);


        //then
        assertEquals(3, set.size());

    }

}