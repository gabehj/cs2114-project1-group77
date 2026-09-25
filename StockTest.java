import student.TestCase;

/**
 * Tests the current two-argument Stock API.
 *
 * @author janbe
 * @version Sep 25, 2026
 */
public class StockTest extends TestCase
{
    private static final double DELTA = 0.0001;

    private Stock stock;

    public void setUp()
    {
        stock = new Stock("VTECH", 5000.0);
    }

    public void testConstructorCalculatesPriceAndVolume()
    {
        assertEquals("VTECH", stock.getName());
        assertTrue(stock.getPrice() >= 100.0);
        assertTrue(stock.getPrice() < 250.0);
        assertEquals(5000.0 / stock.getPrice(), stock.getVolume(), DELTA);
        assertEquals(5.0, stock.getVolatility(), DELTA);
    }

    public void testUpdateStaysWithinBounds()
    {
        for (int i = 0; i < 1000; i++)
        {
            Stock fresh = new Stock("VTECH", 5000.0);
            double before = fresh.getPrice();
            fresh.update();

            assertTrue(fresh.getPrice() >= before * 0.975);
            assertTrue(fresh.getPrice() <= before * 1.05);
        }
    }

    public void testUpdateChangesOnlyPrice()
    {
        String name = stock.getName();
        double volume = stock.getVolume();
        double volatility = stock.getVolatility();

        stock.update();

        assertEquals(name, stock.getName());
        assertEquals(volume, stock.getVolume(), DELTA);
        assertEquals(volatility, stock.getVolatility(), DELTA);
    }

    public void testGetChangeAndPercentChange()
    {
        assertEquals(0.0, stock.getChange(), DELTA);
        assertEquals(0.0, stock.getPercentChange(), DELTA);

        double before = stock.getPrice();
        stock.update();

        assertEquals(stock.getPrice() - before, stock.getChange(), DELTA);
        assertEquals(stock.getChange() / before * 100.0,
            stock.getPercentChange(), DELTA);
    }

    public void testSetPriceResetsChangeBaseline()
    {
        stock.setPrice(250.0);

        assertEquals(250.0, stock.getPrice(), DELTA);
        assertEquals(0.0, stock.getChange(), DELTA);
        assertEquals(0.0, stock.getPercentChange(), DELTA);
    }

    public void testSetPriceRejectsInvalidValues()
    {
        double before = stock.getPrice();
        double[] invalid = {-1.0, Double.NaN, Double.POSITIVE_INFINITY};

        for (double value : invalid)
        {
            try
            {
                stock.setPrice(value);
                fail("invalid price should be rejected");
            }
            catch (IllegalArgumentException e)
            {
                assertEquals(before, stock.getPrice(), DELTA);
            }
        }
    }

    public void testCategoryAndString()
    {
        assertEquals("Stock", stock.getCategory());
        assertTrue(stock.toString().contains("VTECH (Stock)"));
    }
}
