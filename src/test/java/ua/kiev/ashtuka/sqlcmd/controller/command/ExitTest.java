package ua.kiev.ashtuka.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.kiev.ashtuka.sqlcmd.view.View;

import static junit.framework.TestCase.*;

public class ExitTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessRightCommandExit(){
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("exit");

        // than
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommandExit(){
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("ex");

        // than
        assertFalse(canProcess);
    }

    @Test
    public void testProcessCommandExit_throwExitException(){
        // given
        Command command = new Exit(view);

        // when
        try{
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException ex){
            // do nothing
        }
        // than
        Mockito.verify(view).write("GoodBye!");
    }
}
