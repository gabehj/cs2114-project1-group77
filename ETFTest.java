import student.TestCase;

/**
 * Tests the ETF subclass, whose only job is to fix volatility at 12 and pass
 * the remaining values up to Stock.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class ETFTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** How many random draws the bounds test takes. */
    private static final int TRIALS = 1000;

    /** The volatility every ETF is supposed to have. */
    private static final double EXPECTED_VOLATILITY = 12.0;

    private ETF etf;

    /**
     * Builds a fresh ETF before every test.
     */
    public void setUp()
    {
        etf = new ETF("VOO", 512.75, 80000);
    }


    /**
     * Every ETF gets volatility 12, whatever else it is constructed with.
     */
    public void testVolatilityIsFixed()
    {
        assertEquals(EXPECTED_VOLATILITY, etf.getVolatility(), DELTA);
        assertEquals(EXPECTED_VOLATILITY,
            new ETF("OTHER", 1.0, 1).getVolatility(), DELTA);
    }


    /**
     * The other three constructor arguments reach Stock unchanged.
     */
    public void testConstructorPassesValuesThrough()
    {
        assertEquals("VOO", etf.getName());
        assertEquals(512.75, etf.getPrice(), DELTA);
        assertEquals(80000.0, etf.getVolume(), DELTA);
    }


    /**
     * An ETF can be used anywhere a Stock is expected, and the inherited
     * accessors still report the ETF's own values through that reference.
     */
    public void testETFIsAStock()
    {
        Stock asStock = etf;

        assertEquals(EXPECTED_VOLATILITY, asStock.getVolatility(), DELTA);
        assertEquals("VOO", asStock.getName());
    }


    /**
     * At volatility 12 a single update moves the price by between -6 and +12
     * percent, so from 100.0 the result lands in [94.0, 112.0).
     */
    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < TRIALS; i++)
        {
            ETF fresh = new ETF("VOO", 100.0, 80000);
            fresh.update();
            double after = fresh.getPrice();

            assertTrue("price dropped below 94.0: " + after,
                after >= 94.0 - DELTA);
            assertTrue("price rose above 112.0: " + after,
                after <= 112.0 + DELTA);
        }
    }


    /**
     * There is no setter for volatility, so no amount of updating can move it
     * off 12.
     */
    public void testUpdateKeepsVolatilityFixed()
    {
        for (int i = 0; i < 100; i++)
        {
            etf.update();
        }

        assertEquals(EXPECTED_VOLATILITY, etf.getVolatility(), DELTA);
    }
}
