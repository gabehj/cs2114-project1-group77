import student.TestCase;

/**
 * Tests the Bond subclass, whose only job is to fix volatility at 2 and pass
 * the remaining values up to Stock.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class BondTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** How many random draws the bounds test takes. */
    private static final int TRIALS = 1000;

    /** The volatility every Bond is supposed to have. */
    private static final double EXPECTED_VOLATILITY = 2.0;

    private Bond bond;

    /**
     * Builds a fresh bond before every test.
     */
    public void setUp()
    {
        bond = new Bond("US10Y", 98.5, 5000);
    }


    /**
     * Every Bond gets volatility 2, whatever else it is constructed with.
     */
    public void testVolatilityIsFixed()
    {
        assertEquals(EXPECTED_VOLATILITY, bond.getVolatility(), DELTA);
        assertEquals(EXPECTED_VOLATILITY,
            new Bond("OTHER", 1.0, 1).getVolatility(), DELTA);
    }


    /**
     * The other three constructor arguments reach Stock unchanged.
     */
    public void testConstructorPassesValuesThrough()
    {
        assertEquals("US10Y", bond.getName());
        assertEquals(98.5, bond.getPrice(), DELTA);
        assertEquals(5000.0, bond.getVolume(), DELTA);
    }


    /**
     * A Bond can be used anywhere a Stock is expected, and the inherited
     * accessors still report the Bond's own values through that reference.
     */
    public void testBondIsAStock()
    {
        Stock asStock = bond;

        assertEquals(EXPECTED_VOLATILITY, asStock.getVolatility(), DELTA);
        assertEquals("US10Y", asStock.getName());
    }


    /**
     * At volatility 2 a single update moves the price by between -1 and +2
     * percent, so from 100.0 the result lands in [99.0, 102.0).
     */
    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < TRIALS; i++)
        {
            Bond fresh = new Bond("US10Y", 100.0, 5000);
            fresh.update();
            double after = fresh.getPrice();

            assertTrue("price dropped below 99.0: " + after,
                after >= 99.0 - DELTA);
            assertTrue("price rose above 102.0: " + after,
                after <= 102.0 + DELTA);
        }
    }


    /**
     * There is no setter for volatility, so no amount of updating can move it
     * off 2.
     */
    public void testUpdateKeepsVolatilityFixed()
    {
        for (int i = 0; i < 100; i++)
        {
            bond.update();
        }

        assertEquals(EXPECTED_VOLATILITY, bond.getVolatility(), DELTA);
    }
}
