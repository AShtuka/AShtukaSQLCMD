package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class UnsupportedTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcess(){
        // given
        Command command = new Unsupported(view);

        // when
        boolean canProcess = command.canProcess("everyThing");

        // than
        assertTrue(canProcess);
    }


    @Test
    public void testProcess() throws SQLException {
        // given
        Command command = new Unsupported(view);

        // when
        command.process("everyThing");

        // than
        Mockito.verify(view).write("Non-existent command:everyThing");
    }

}