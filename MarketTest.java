import student.TestCase;

/**
 * Tests the Market class: the default lineup, adding and finding stocks,
 * the day counter, updating every price at once, and the two listings.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class MarketTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    private Market market;

    /**
     * Builds a small market of fixed-price stocks before every test, so
     * that outputs can be checked exactly.
     */
    public void setUp()
    {
        market = new Market();
        market.add(new Stock("AAA", 10.0, 0, 100));
        market.add(new Stock("BBB", 20.0, 0, 200));
    }


    /**
     * A new market is empty and on day zero.
     */
    public void testEmptyMarket()
    {
        Market empty = new Market();

        assertEquals(0, empty.size());
        assertEquals(0, empty.getDay());
        assertEquals(0, empty.names().length);
        assertNull(empty.find("AAA"));
        assertEquals("Market prices on day 0:\n  (no stocks are listed)\n",
            empty.toString());
        assertEquals("Day 0:", empty.ticker());
    }


    /**
     * The default market holds the six securities from the design: one
     * bond, two tech stocks, one energy stock, and two ETFs.
     */
    public void testCreateDefault()
    {
        Market standard = Market.createDefault();

        assertEquals(6, standard.size());
        assertEquals(0, standard.getDay());
        assertTrue(standard.find("USA") instanceof Bond);
        assertTrue(standard.find("NK") instanceof Tech);
        assertTrue(standard.find("Grok") instanceof Tech);
        assertTrue(standard.find("Solar City") instanceof Energy);
        assertTrue(standard.find("s&p5") instanceof ETF);
        assertTrue(standard.find("QQQ") instanceof ETF);

        for (int i = 0; i < standard.size(); i++)
        {
            assertTrue(standard.get(i).getPrice() > 0);
            assertTrue(standard.get(i).getVolume() > 0);
        }
    }


    /**
     * Stocks are listed in the order they were added.
     */
    public void testAddAndGet()
    {
        assertEquals(2, market.size());
        assertEquals("AAA", market.get(0).getName());
        assertEquals("BBB", market.get(1).getName());

        market.add(new Stock("CCC", 30.0, 0, 300));
        assertEquals(3, market.size());
        assertEquals("CCC", market.get(2).getName());
    }


    /**
     * Null stocks and duplicate names (in any capitalization) are refused.
     */
    public void testAddRejectsNullAndDuplicates()
    {
        try
        {
            market.add(null);
            fail("a null stock should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(2, market.size());
        }
        try
        {
            market.add(new Stock("aaa", 99.0, 0, 1));
            fail("a duplicate name should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(2, market.size());
            assertEquals(10.0, market.find("AAA").getPrice(), DELTA);
        }
    }


    /**
     * find() ignores case and surrounding spaces, and returns null for
     * names that are not listed or are null.
     */
    public void testFind()
    {
        assertSame(market.get(0), market.find("AAA"));
        assertSame(market.get(0), market.find("aaa"));
        assertSame(market.get(1), market.find("  bBb "));
        assertNull(market.find("ZZZ"));
        assertNull(market.find(""));
        assertNull(market.find(null));
    }


    /**
     * names() lists every stock name in order and returns a fresh array
     * each time, so changing it does not affect the market.
     */
    public void testNames()
    {
        String[] names = market.names();

        assertEquals(2, names.length);
        assertEquals("AAA", names[0]);
        assertEquals("BBB", names[1]);

        names[0] = "changed";
        assertEquals("AAA", market.names()[0]);
    }


    /**
     * Each update advances the day counter by one.
     */
    public void testUpdateAdvancesDay()
    {
        market.update();
        assertEquals(1, market.getDay());

        market.update();
        market.update();
        assertEquals(3, market.getDay());
    }


    /**
     * Updating the market updates every stock in it. The stocks here have
     * volatility 10, so each price must land within that stock's band.
     */
    public void testUpdateMovesEveryStock()
    {
        Market lively = new Market();
        lively.add(new Stock("ONE", 100.0, 10, 1));
        lively.add(new Stock("TWO", 200.0, 10, 1));

        boolean moved = false;
        for (int i = 0; i < 20 && !moved; i++)
        {
            lively.update();
            moved = lively.get(0).getPrice() != 100.0
                && lively.get(1).getPrice() != 200.0;
        }
        assertTrue("prices never moved across 20 updates", moved);

        for (int i = 0; i < 100; i++)
        {
            double before = lively.get(0).getPrice();
            lively.update();
            double after = lively.get(0).getPrice();
            assertTrue(after >= before * 0.95 - DELTA);
            assertTrue(after <= before * 1.10 + DELTA);
        }
    }


    /**
     * The ticker squeezes every stock onto one line with its price and
     * percent change.
     */
    public void testTicker()
    {
        assertEquals("Day 0: AAA $10.00 (+0.00%) | BBB $20.00 (+0.00%)",
            market.ticker());

        market.update();
        assertEquals("Day 1: AAA $10.00 (+0.00%) | BBB $20.00 (+0.00%)",
            market.ticker());
    }


    /**
     * toString() numbers each stock on its own line so a player can pick
     * one by number.
     */
    public void testToString()
    {
        assertEquals("Market prices on day 0:\n"
            + "  1. AAA (Stock) $10.00 (+0.00%)\n"
            + "  2. BBB (Stock) $20.00 (+0.00%)\n", market.toString());
    }
}
