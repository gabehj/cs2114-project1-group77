/**
 * The base class for every security in the market. A stock has a name, a
 * current price, a volatility that controls how far the price can move in a
 * single update, and a trading volume. Every concrete security (Bond, Tech,
 * Energy, ETF) extends this class and fixes the volatility for its category.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Stock
{
    /** Ticker symbol or display name of this security. */
    private String name;

    /** Current price of one share. */
    private double price;

    /** Price of one share before the most recent update. */
    private double previousPrice;

    /** Largest percentage the price can rise in one update. */
    private double volatility;

    /** Trading volume, used to describe how heavily the stock is traded. */
    private double volume;

    /**
     * Creates a new stock.
     *
     * @param name
     *            ticker symbol or display name
     * @param price
     *            starting price of one share
     * @param volatility
     *            largest percentage the price can rise in one update
     * @param volume
     *            trading volume
     */
    public Stock(String name, double price, int volatility, int volume)
    {
        this.name = name;
        this.price = price;
        this.previousPrice = price;
        this.volatility = volatility;
        this.volume = volume;
    }


    /**
     * Moves the price by a random percentage. The change is drawn uniformly
     * from -volatility/2 percent up to +volatility percent, so the price can
     * fall by at most half as much as it can rise.
     */
    public void update()
    {
        double max = volatility;
        double min = -(volatility / 2);
        double percentChange = Math.random() * (max - min) + min;
        previousPrice = price;
        price += price * percentChange / 100;
    }


    /**
     * Replaces the current price outright, the way a test does to put a
     * stock at a known price. The new price also becomes the baseline that
     * getChange() measures against.
     *
     * @param newPrice
     *            the new price of one share
     * @throws IllegalArgumentException
     *             if the price is negative, NaN, or infinite
     */
    public void setPrice(double newPrice)
    {
        if (Double.isNaN(newPrice) || Double.isInfinite(newPrice)
            || newPrice < 0)
        {
            throw new IllegalArgumentException(
                "Price must be a non-negative number, not " + newPrice);
        }
        price = newPrice;
        previousPrice = newPrice;
    }


    /**
     * Gets the trading volume.
     *
     * @return the volume
     */
    public double getVolume()
    {
        return volume;
    }


    /**
     * Gets the volatility.
     *
     * @return the largest percentage the price can rise in one update
     */
    public double getVolatility()
    {
        return volatility;
    }


    /**
     * Gets the current price of one share.
     *
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }


    /**
     * Gets the name of this security.
     *
     * @return the ticker symbol or display name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Gets how much the price moved in the most recent update.
     *
     * @return current price minus the price before the last update
     */
    public double getChange()
    {
        return price - previousPrice;
    }


    /**
     * Gets the most recent price move as a percentage of the old price.
     *
     * @return the percent change, or 0 if the old price was 0
     */
    public double getPercentChange()
    {
        if (previousPrice == 0)
        {
            return 0;
        }
        return getChange() / previousPrice * 100;
    }


    /**
     * Describes which category of security this is. Subclasses override this
     * to report their own category.
     *
     * @return the category name
     */
    public String getCategory()
    {
        return "Stock";
    }


    /**
     * Describes this security in one line, for example
     * "NK (Tech) $150.00 (+1.25%)".
     *
     * @return the name, category, price, and most recent percent change
     */
    public String toString()
    {
        return name + " (" + getCategory() + ") " + Money.format(price)
            + " (" + String.format("%+.2f", getPercentChange()) + "%)";
    }
}
