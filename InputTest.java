import java.util.Scanner;
import student.TestCase;

/**
 * Tests the Input class by feeding it scripted answers through a Scanner
 * and checking what it returns and what it prints.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class InputTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** The menu used by the choose() tests. */
    private static final String[] OPTIONS = {"Buy", "Sell", "Wait"};

    /**
     * Nothing shared to set up; each test builds its own Input.
     */
    public void setUp()
    {
        // nothing to do
    }


    /**
     * Builds an Input that will read the given text.
     *
     * @param script
     *            the lines the "player" types, separated by newlines
     * @return the input reader
     */
    private Input scripted(String script)
    {
        return new Input(new Scanner(script));
    }


    /**
     * An Input must have a scanner to read from.
     */
    public void testConstructorRejectsNull()
    {
        try
        {
            new Input(null);
            fail("a null scanner should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * readLine() prints the prompt and returns the line with surrounding
     * spaces removed.
     */
    public void testReadLine()
    {
        Input input = scripted("  hello world  \nsecond\n");

        assertEquals("hello world", input.readLine("Say something"));
        assertEquals("second", input.readLine("Again"));
        assertTrue(systemOut().getHistory().contains("Say something"));
        assertTrue(systemOut().getHistory().contains("Again"));
    }


    /**
     * When the input runs out, readLine() throws InputEndedException
     * instead of the Scanner's NoSuchElementException.
     */
    public void testReadLineWhenInputEnds()
    {
        Input input = scripted("only line\n");
        input.readLine("First");

        try
        {
            input.readLine("Second");
            fail("running out of input should throw");
        }
        catch (InputEndedException e)
        {
            assertTrue(e.getMessage().contains("Second"));
        }
    }


    /**
     * choose() accepts an option typed in any capitalization and returns
     * it spelled the way the menu spells it.
     */
    public void testChooseByName()
    {
        Input input = scripted("buy\nSELL\n  Wait \n");

        assertEquals("Buy", input.choose("Pick", OPTIONS));
        assertEquals("Sell", input.choose("Pick", OPTIONS));
        assertEquals("Wait", input.choose("Pick", OPTIONS));
        assertTrue(systemOut().getHistory().contains(
            "Pick (Buy/Sell/Wait)"));
    }


    /**
     * choose() also accepts the option's number in the list, counting from
     * one.
     */
    public void testChooseByNumber()
    {
        Input input = scripted("1\n3\n2\n");

        assertEquals("Buy", input.choose("Pick", OPTIONS));
        assertEquals("Wait", input.choose("Pick", OPTIONS));
        assertEquals("Sell", input.choose("Pick", OPTIONS));
    }


    /**
     * Anything that is neither an option nor a valid number is rejected and
     * the question is repeated until a good answer arrives, as in the
     * scope document's "BUY!" example.
     */
    public void testChooseRepeatsUntilValid()
    {
        Input input = scripted("BUY!\n0\n4\nbu\n\nbuy\n");

        assertEquals("Buy", input.choose("Pick", OPTIONS));

        String out = systemOut().getHistory();
        assertTrue(out.contains(
            "Please enter one of the following exactly (Buy/Sell/Wait)"));
        assertTrue(out.contains("or its number (1-3)"));
    }


    /**
     * A menu with no options is a programming error and is refused.
     */
    public void testChooseRejectsEmptyMenu()
    {
        Input input = scripted("anything\n");
        try
        {
            input.choose("Pick", new String[0]);
            fail("an empty menu should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
        try
        {
            input.choose("Pick", null);
            fail("a null menu should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * readInt() returns a number inside the range straight away.
     */
    public void testReadIntAcceptsInRange()
    {
        Input input = scripted("7\n1\n10\n");

        assertEquals(7, input.readInt("How many?", 1, 10));
        assertEquals(1, input.readInt("How many?", 1, 10));
        assertEquals(10, input.readInt("How many?", 1, 10));
    }


    /**
     * readInt() rejects text, decimals, and out-of-range numbers, explains
     * the problem, and asks again.
     */
    public void testReadIntRepeatsUntilValid()
    {
        Input input = scripted("ten\n2.5\n0\n11\n-4\n\n3\n");

        assertEquals(3, input.readInt("How many?", 1, 10));

        String out = systemOut().getHistory();
        assertTrue(out.contains("\"ten\" is not a whole number."));
        assertTrue(out.contains("\"2.5\" is not a whole number."));
        assertTrue(out.contains("0 is out of range."));
        assertTrue(out.contains("11 is out of range."));
        assertTrue(out.contains("-4 is out of range."));
        assertTrue(out.contains("between 1 and 10"));
    }


    /**
     * When there is no real upper limit the message only mentions the
     * minimum.
     */
    public void testReadIntWithoutUpperLimit()
    {
        Input input = scripted("0\n5000000\n");

        assertEquals(5000000,
            input.readInt("How many?", 1, Integer.MAX_VALUE));
        assertTrue(systemOut().getHistory().contains(
            "a whole number of at least 1"));
    }


    /**
     * readMoney() accepts plain numbers as well as amounts written with a
     * dollar sign and commas, and rounds to whole cents.
     */
    public void testReadMoneyAcceptsFormats()
    {
        Input input = scripted("25.5\n$1,250.75\n0.01\n9.999\n");

        assertEquals(25.5, input.readMoney("How much?", 0.01), DELTA);
        assertEquals(1250.75, input.readMoney("How much?", 0.01), DELTA);
        assertEquals(0.01, input.readMoney("How much?", 0.01), DELTA);
        assertEquals(10.0, input.readMoney("How much?", 0.01), DELTA);
    }


    /**
     * readMoney() rejects text, NaN, infinity, and amounts below the
     * minimum, and asks again.
     */
    public void testReadMoneyRepeatsUntilValid()
    {
        Input input = scripted("lots\nNaN\nInfinity\n0\n-3\n\n42\n");

        assertEquals(42.0, input.readMoney("How much?", 0.01), DELTA);

        String out = systemOut().getHistory();
        assertTrue(out.contains("\"lots\" is not an amount of money."));
        assertTrue(out.contains("\"NaN\" is not an amount of money."));
        assertTrue(out.contains("\"Infinity\" is not an amount of money."));
        assertTrue(out.contains("$0.00 is too small."));
        assertTrue(out.contains("-$3.00 is too small."));
        assertTrue(out.contains("Please enter at least $0.01."));
    }
}
