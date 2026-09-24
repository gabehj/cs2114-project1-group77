import student.TestCase;

/**
 * Tests the Stock base class. The accessors are checked exactly. Because
 * update() draws from Math.random(), the random cases assert bounds and
 * invariants instead of exact prices, plus two deterministic edge cases
 * (zero volatility and zero price) where the outcome is fixed.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class StockTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    /** How many random draws each bounds test takes. */
    private static final int TRIALS = 1000;

    private Stock stock;

    /**
     * Builds a fresh stock before every test so no test can be affected by
     * price changes made by another one.
     */
    public void setUp()
    {
        stock = new Stock("VTECH", 100.0, 10, 5000);
    }


    /**
     * The constructor stores the name it was given.
     */
    public void testGetName()
    {
        assertEquals("VTECH", stock.getName());
    }


    /**
     * The constructor stores the starting price.
     */
    public void testGetPrice()
    {
        assertEquals(100.0, stock.getPrice(), DELTA);
    }


    /**
     * The constructor stores the volatility, widened from int to double.
     */
    public void testGetVolatility()
    {
        assertEquals(10.0, stock.getVolatility(), DELTA);
    }


    /**
     * The constructor stores the volume, widened from int to double.
     */
    public void testGetVolume()
    {
        assertEquals(5000.0, stock.getVolume(), DELTA);
    }


    /**
     * A single update moves the price by between -volatility/2 percent and
     * +volatility percent. At volatility 10 and a starting price of 100 that
     * is the range [95.0, 110.0). Each trial starts from a fresh stock so the
     * bounds stay tied to the same base price.
     */
    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < TRIALS; i++)
        {
            Stock fresh = new Stock("VTECH", 100.0, 10, 5000);
            fresh.update();
            double after = fresh.getPrice();

            assertTrue("price dropped below 95.0: " + after,
                after >= 95.0 - DELTA);
            assertTrue("price rose above 110.0: " + after,
                after <= 110.0 + DELTA);
        }
    }


    /**
     * Repeated updates compound: each one is measured against the price left
     * by the previous one, not against the original price.
     */
    public void testUpdateCompoundsFromCurrentPrice()
    {
        for (int i = 0; i < 100; i++)
        {
            double before = stock.getPrice();
            stock.update();
            double after = stock.getPrice();

            // Relative slack, since the price grows large over many updates.
            double slack = before * 1e-9;

            assertTrue("price fell more than 5% in one update",
                after >= before * 0.95 - slack);
            assertTrue("price rose more than 10% in one update",
                after <= before * 1.10 + slack);
        }
    }


    /**
     * Update actually moves the price. A draw of exactly zero is possible in
     * principle, so this allows several attempts before failing.
     */
    public void testUpdateChangesPrice()
    {
        double before = stock.getPrice();
        boolean changed = false;

        for (int i = 0; i < 20 && !changed; i++)
        {
            stock.update();
            changed = stock.getPrice() != before;
        }

        assertTrue("price never moved across 20 updates", changed);
    }


    /**
     * With volatility 0 the range collapses to a single point, so the price
     * is frozen no matter how many times update() runs. This case is fully
     * deterministic.
     */
    public void testZeroVolatilityLeavesPriceAlone()
    {
        Stock flat = new Stock("FLAT", 50.0, 0, 100);

        for (int i = 0; i < 100; i++)
        {
            flat.update();
        }

        assertEquals(50.0, flat.getPrice(), DELTA);
    }


    /**
     * Update scales the price by a percentage, so a price of zero can never
     * move off zero regardless of volatility.
     */
    public void testZeroPriceStaysZero()
    {
        Stock worthless = new Stock("ZERO", 0.0, 25, 100);

        for (int i = 0; i < 100; i++)
        {
            worthless.update();
        }

        assertEquals(0.0, worthless.getPrice(), DELTA);
    }


    /**
     * Update touches the price and nothing else.
     */
    public void testUpdateLeavesOtherFieldsAlone()
    {
        for (int i = 0; i < 50; i++)
        {
            stock.update();
        }

        assertEquals("VTECH", stock.getName());
        assertEquals(10.0, stock.getVolatility(), DELTA);
        assertEquals(5000.0, stock.getVolume(), DELTA);
    }


    /**
     * Documents the current shape of the random draw: it runs from
     * -volatility/2 to +volatility, so its average is +volatility/4 percent
     * rather than zero and prices drift upward over time. At volatility 10
     * the average price after one update is about 102.5. If the drift is
     * later removed, this test is the one that should be updated.
     */
    public void testUpdateDriftsUpwardOnAverage()
    {
        int samples = 20000;
        double total = 0.0;

        for (int i = 0; i < samples; i++)
        {
            Stock fresh = new Stock("VTECH", 100.0, 10, 5000);
            fresh.update();
            total += fresh.getPrice();
        }

        double mean = total / samples;

        assertTrue("average price after one update was " + mean,
            mean > 101.0 && mean < 104.0);
    }
}
