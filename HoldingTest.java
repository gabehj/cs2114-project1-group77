import student.TestCase;

/**
 * Tests the Holding class, which pairs a stock with a number of shares.
 * The stock used here has zero volatility so its price is fixed and every
 * value can be checked exactly.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class HoldingTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    private Stock stock;
    private Holding holding;

    /**
     * Builds a fresh holding of 10 shares at $25.00 before every test.
     */
    public void setUp()
    {
        stock = new Stock("FLAT", 25.0, 0, 100);
        holding = new Holding(stock, 10);
    }


    /**
     * The constructor stores the stock and the share count.
     */
    public void testConstructor()
    {
        assertSame(stock, holding.getStock());
        assertEquals(10, holding.getShares());
    }


    /**
     * A holding must have a stock.
     */
    public void testConstructorRejectsNullStock()
    {
        try
        {
            new Holding(null, 5);
            fail("a null stock should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * A holding must start with at least one share.
     */
    public void testConstructorRejectsBadShareCounts()
    {
        try
        {
            new Holding(stock, 0);
            fail("zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
        try
        {
            new Holding(stock, -3);
            fail("negative shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * Adding shares increases the count.
     */
    public void testAddShares()
    {
        holding.addShares(5);
        assertEquals(15, holding.getShares());

        holding.addShares(1);
        assertEquals(16, holding.getShares());
    }


    /**
     * Adding zero or a negative number of shares is refused and changes
     * nothing.
     */
    public void testAddSharesRejectsNonPositive()
    {
        try
        {
            holding.addShares(0);
            fail("adding zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(10, holding.getShares());
        }
        try
        {
            holding.addShares(-1);
            fail("adding negative shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(10, holding.getShares());
        }
    }


    /**
     * Removing shares decreases the count, and removing every share is
     * allowed so the portfolio can drop the empty holding.
     */
    public void testRemoveShares()
    {
        holding.removeShares(4);
        assertEquals(6, holding.getShares());

        holding.removeShares(6);
        assertEquals(0, holding.getShares());
    }


    /**
     * Removing more shares than are owned, or a non-positive number, is
     * refused and changes nothing.
     */
    public void testRemoveSharesRejectsBadCounts()
    {
        try
        {
            holding.removeShares(11);
            fail("overselling should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(10, holding.getShares());
        }
        try
        {
            holding.removeShares(0);
            fail("removing zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(10, holding.getShares());
        }
        try
        {
            holding.removeShares(-2);
            fail("removing negative shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(10, holding.getShares());
        }
    }


    /**
     * The value is shares times the stock's current price, so it follows
     * the price when it changes.
     */
    public void testGetValue()
    {
        assertEquals(250.0, holding.getValue(), DELTA);

        stock.setPrice(30.0);
        assertEquals(300.0, holding.getValue(), DELTA);

        holding.addShares(10);
        assertEquals(600.0, holding.getValue(), DELTA);
    }


    /**
     * toString() reports the share count, the stock, and the value, and
     * uses "share" rather than "shares" for a single share.
     */
    public void testToString()
    {
        assertEquals("10 shares of FLAT (Stock) $25.00 (+0.00%) worth $250.00",
            holding.toString());

        Holding single = new Holding(stock, 1);
        assertEquals("1 share of FLAT (Stock) $25.00 (+0.00%) worth $25.00",
            single.toString());
    }
}
