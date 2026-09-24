import student.TestCase;

/**
 * Tests the Tech subclass, whose only job is to fix volatility at 15 and pass
 * the remaining values up to Stock.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class TechTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** How many random draws the bounds test takes. */
    private static final int TRIALS = 1000;

    /** The volatility every Tech stock is supposed to have. */
    private static final double EXPECTED_VOLATILITY = 15.0;

    private Tech tech;

    /**
     * Builds a fresh tech stock before every test.
     */
    public void setUp()
    {
        tech = new Tech("NVDA", 184.20, 250000);
    }


    /**
     * Every Tech stock gets volatility 15, whatever else it is constructed
     * with.
     */
    public void testVolatilityIsFixed()
    {
        assertEquals(EXPECTED_VOLATILITY, tech.getVolatility(), DELTA);
        assertEquals(EXPECTED_VOLATILITY,
            new Tech("OTHER", 1.0, 1).getVolatility(), DELTA);
    }


    /**
     * The other three constructor arguments reach Stock unchanged.
     */
    public void testConstructorPassesValuesThrough()
    {
        assertEquals("NVDA", tech.getName());
        assertEquals(184.20, tech.getPrice(), DELTA);
        assertEquals(250000.0, tech.getVolume(), DELTA);
    }


    /**
     * A Tech stock can be used anywhere a Stock is expected, and the
     * inherited accessors still report its own values through that reference.
     */
    public void testTechIsAStock()
    {
        Stock asStock = tech;

        assertEquals(EXPECTED_VOLATILITY, asStock.getVolatility(), DELTA);
        assertEquals("NVDA", asStock.getName());
    }


    /**
     * At volatility 15 a single update moves the price by between -7.5 and
     * +15 percent, so from 100.0 the result lands in [92.5, 115.0).
     */
    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < TRIALS; i++)
        {
            Tech fresh = new Tech("NVDA", 100.0, 250000);
            fresh.update();
            double after = fresh.getPrice();

            assertTrue("price dropped below 92.5: " + after,
                after >= 92.5 - DELTA);
            assertTrue("price rose above 115.0: " + after,
                after <= 115.0 + DELTA);
        }
    }


    /**
     * There is no setter for volatility, so no amount of updating can move it
     * off 15.
     */
    public void testUpdateKeepsVolatilityFixed()
    {
        for (int i = 0; i < 100; i++)
        {
            tech.update();
        }

        assertEquals(EXPECTED_VOLATILITY, tech.getVolatility(), DELTA);
    }
}
