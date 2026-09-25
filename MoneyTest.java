import student.TestCase;

/**
 * Tests the Money helpers: rounding to cents and formatting dollar amounts.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class MoneyTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /**
     * Nothing to set up; the helpers are static.
     */
    public void setUp()
    {
        // nothing to do
    }


    /**
     * Rounding goes to the nearest cent, with halves rounding up.
     */
    public void testRoundToCents()
    {
        assertEquals(1.23, Money.roundToCents(1.234), DELTA);
        assertEquals(1.24, Money.roundToCents(1.235), DELTA);
        assertEquals(1.24, Money.roundToCents(1.2399999), DELTA);
        assertEquals(0.0, Money.roundToCents(0.004), DELTA);
        assertEquals(-2.5, Money.roundToCents(-2.5), DELTA);
        assertEquals(100.0, Money.roundToCents(100.0), DELTA);
    }


    /**
     * Whole and fractional amounts get two decimals and a thousands
     * separator.
     */
    public void testFormatPositive()
    {
        assertEquals("$0.00", Money.format(0.0));
        assertEquals("$5.00", Money.format(5));
        assertEquals("$25.50", Money.format(25.5));
        assertEquals("$1,234.57", Money.format(1234.567));
        assertEquals("$10,000.00", Money.format(10000));
        assertEquals("$1,000,000.00", Money.format(1000000));
    }


    /**
     * Negative amounts put the minus sign before the dollar sign.
     */
    public void testFormatNegative()
    {
        assertEquals("-$0.50", Money.format(-0.5));
        assertEquals("-$1,234.57", Money.format(-1234.567));
    }


    /**
     * An amount that rounds to zero is shown as $0.00 rather than -$0.00,
     * so tiny floating-point noise never shows up as a loss.
     */
    public void testFormatTinyNegativeIsZero()
    {
        assertEquals("$0.00", Money.format(-0.001));
        assertEquals("$0.00", Money.format(0.004));
    }
}
