import student.TestCase;

/**
 * Tests the current Market API.
 *
 * @author janbe
 * @version Sep 25, 2026
 */
public class MarketTest extends TestCase
{
    private Market market;

    public void setUp()
    {
        market = new Market();
        market.add(new Stock("AAA", 1000.0));
        market.add(new Stock("BBB", 2000.0));
    }

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

    public void testCreateDefault()
    {
        Market standard = Market.createDefault();

        assertEquals(6, standard.size());
        assertTrue(standard.find("USA") instanceof Bond);
        assertTrue(standard.find("NK") instanceof Tech);
        assertTrue(standard.find("Grok") instanceof Tech);
        assertTrue(standard.find("Solar City") instanceof Energy);
        assertTrue(standard.find("s&p5") instanceof ETF);
        assertTrue(standard.find("QQQ") instanceof ETF);
    }

    public void testAddAndGet()
    {
        assertEquals("AAA", market.get(0).getName());
        assertEquals("BBB", market.get(1).getName());

        market.add(new Stock("CCC", 3000.0));
        assertEquals(3, market.size());
        assertEquals("CCC", market.get(2).getName());
    }

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
            market.add(new Stock("aaa", 99.0));
            fail("a duplicate name should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(2, market.size());
        }
    }

    public void testFindAndNames()
    {
        assertSame(market.get(0), market.find("aaa"));
        assertSame(market.get(1), market.find("  bBb "));
        assertNull(market.find("ZZZ"));
        assertNull(market.find(null));

        String[] names = market.names();
        assertEquals("AAA", names[0]);
        names[0] = "changed";
        assertEquals("AAA", market.names()[0]);
    }

    public void testUpdateAdvancesDay()
    {
        market.update();
        market.update();

        assertEquals(2, market.getDay());
    }

    public void testTickerAndToStringContainListings()
    {
        assertTrue(market.ticker().startsWith("Day 0: AAA "));
        assertTrue(market.ticker().contains(" | BBB "));
        assertTrue(market.toString().contains("1. AAA (Stock)"));
        assertTrue(market.toString().contains("2. BBB (Stock)"));
    }
}
