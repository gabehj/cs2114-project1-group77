import student.TestCase;

/**
 * Tests the current Main input and startup behavior.
 *
 * @author janbe
 * @version Sep 25, 2026
 */
public class MainTest extends TestCase
{
    private Main main;

    public void setUp()
    {
        main = new Main();
    }

    public void testValidInputMatchesTextIgnoringCase()
    {
        main.input = new java.util.Scanner("quit\n");

        assertEquals("Quit", main.validInput("Choose",
            new String[] {"View portfolios", "Quit"}));
    }

    public void testValidInputMatchesNumericIndex()
    {
        main.input = new java.util.Scanner("1\n");

        assertEquals("Quit", main.validInput("Choose",
            new String[] {"View portfolios", "Quit"}));
    }

    public void testMainWithQuitPrintsWelcomeAndGoodbye()
    {
        setSystemIn("quit\n");
        Main.main(null);

        String output = systemOut().getHistory();
        assertTrue(output.startsWith("Welcome to InfiniStocks!"));
        assertTrue(output.contains("Have a nice day!"));
    }
}
