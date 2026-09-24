import student.TestCase;

/**
 * Tests the Energy subclass, whose only job is to fix volatility at 25 and
 * pass the remaining values up to Stock.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class EnergyTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** How many random draws the bounds test takes. */
    private static final int TRIALS = 1000;

    /** The volatility every Energy stock is supposed to have. */
    private static final double EXPECTED_VOLATILITY = 25.0;

    private Energy energy;

    /**
     * Builds a fresh energy stock before every test.
     */
    public void setUp()
    {
        energy = new Energy("XOM", 118.40, 14000);
    }


    /**
     * Every Energy stock gets volatility 25, whatever else it is constructed
     * with.
     */
    public void testVolatilityIsFixed()
    {
        assertEquals(EXPECTED_VOLATILITY, energy.getVolatility(), DELTA);
        assertEquals(EXPECTED_VOLATILITY,
            new Energy("OTHER", 1.0, 1).getVolatility(), DELTA);
    }


    /**
     * The other three constructor arguments reach Stock unchanged.
     */
    public void testConstructorPassesValuesThrough()
    {
        assertEquals("XOM", energy.getName());
        assertEquals(118.40, energy.getPrice(), DELTA);
        assertEquals(14000.0, energy.getVolume(), DELTA);
    }


    /**
     * An Energy stock can be used anywhere a Stock is expected, and the
     * inherited accessors still report its own values through that reference.
     */
    public void testEnergyIsAStock()
    {
        Stock asStock = energy;

        assertEquals(EXPECTED_VOLATILITY, asStock.getVolatility(), DELTA);
        assertEquals("XOM", asStock.getName());
    }


    /**
     * At volatility 25 a single update moves the price by between -12.5 and
     * +25 percent, so from 100.0 the result lands in [87.5, 125.0).
     */
    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < TRIALS; i++)
        {
            Energy fresh = new Energy("XOM", 100.0, 14000);
            fresh.update();
            double after = fresh.getPrice();

            assertTrue("price dropped below 87.5: " + after,
                after >= 87.5 - DELTA);
            assertTrue("price rose above 125.0: " + after,
                after <= 125.0 + DELTA);
        }
    }


    /**
     * Energy is the most volatile of the four, so its band is strictly wider
     * than the least volatile one. This pins down the ordering the four
     * subclasses are meant to express.
     */
    public void testEnergyIsMoreVolatileThanBond()
    {
        Bond bond = new Bond("US10Y", 100.0, 5000);

        assertTrue("Energy should be more volatile than Bond",
            energy.getVolatility() > bond.getVolatility());
    }


    /**
     * There is no setter for volatility, so no amount of updating can move it
     * off 25.
     */
    public void testUpdateKeepsVolatilityFixed()
    {
        for (int i = 0; i < 100; i++)
        {
            energy.update();
        }

        assertEquals(EXPECTED_VOLATILITY, energy.getVolatility(), DELTA);
    }
}
