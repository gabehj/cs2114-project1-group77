import java.util.Scanner;
import student.TestCase;

/**
 * Tests the methods currently exposed by Portfolio.
 *
 * @author janbe
 * @version Sep 25, 2026
 */
public class PortfolioTest extends TestCase
{
    private Portfolio portfolio;

    public void setUp()
    {
        portfolio = new Portfolio("Test");
    }

    public void testConstructor()
    {
        assertEquals("Test", portfolio.getName());
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            0.0001);
        assertEquals(0.0, portfolio.getDeposited(), 0.0001);
    }

    public void testSetNameAcceptsNonEmptyName()
    {
        portfolio.setName("Renamed");

        assertEquals("Renamed", portfolio.getName());
    }

    public void testSetNameIgnoresEmptyName()
    {
        portfolio.setName("");

        assertEquals("Test", portfolio.getName());
    }

    public void testValidInputMatchesTextIgnoringCase()
    {
        portfolio.input = new Scanner("wait\n");

        assertEquals("Wait", portfolio.validInput("Choose",
            new String[] {"Display", "Wait", "Exit"}));
    }

    public void testValidInputMatchesNumericIndex()
    {
        portfolio.input = new Scanner("1\n");

        assertEquals("Wait", portfolio.validInput("Choose",
            new String[] {"Display", "Wait", "Exit"}));
    }

    public void testValidInputRepeatsUntilValid()
    {
        portfolio.input = new Scanner("invalid\nexit\n");

        assertEquals("Exit", portfolio.validInput("Choose",
            new String[] {"Display", "Wait", "Exit"}));
    }
}
