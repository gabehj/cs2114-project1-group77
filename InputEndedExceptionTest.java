import student.TestCase;

/**
 * Tests InputEndedException, which only needs to carry a message and be an
 * unchecked exception so that it can pass through the menu loops.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class InputEndedExceptionTest extends TestCase
{
    /**
     * Nothing to set up.
     */
    public void setUp()
    {
        // nothing to do
    }


    /**
     * The message is kept, and the exception is a RuntimeException so no
     * method has to declare it.
     */
    public void testMessageAndType()
    {
        InputEndedException e = new InputEndedException("no more input");

        assertEquals("no more input", e.getMessage());
        assertTrue(e instanceof RuntimeException);
    }


    /**
     * The exception can be thrown and caught like any other.
     */
    public void testCanBeThrown()
    {
        try
        {
            throw new InputEndedException("thrown on purpose");
        }
        catch (InputEndedException e)
        {
            assertEquals("thrown on purpose", e.getMessage());
        }
    }
}
