/**
 * A Holding is one line in a portfolio: a particular stock together with the
 * number of shares of it that the portfolio owns. The stock object is shared
 * with the market, so the holding's value always reflects the current price.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Holding
{
    /** The stock that is owned. */
    private Stock stock;

    /** How many shares of the stock are owned. */
    private int shares;

    /**
     * Creates a holding of the given stock.
     *
     * @param stock
     *            the stock that is owned
     * @param shares
     *            how many shares are owned, at least 1
     * @throws IllegalArgumentException
     *             if the stock is null or the share count is less than 1
     */
    public Holding(Stock stock, int shares)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("A holding needs a stock.");
        }
        if (shares < 1)
        {
            throw new IllegalArgumentException(
                "A holding needs at least 1 share, not " + shares + ".");
        }
        this.stock = stock;
        this.shares = shares;
    }


    /**
     * Gets the stock that is owned.
     *
     * @return the stock
     */
    public Stock getStock()
    {
        return stock;
    }


    /**
     * Gets how many shares are owned.
     *
     * @return the share count
     */
    public int getShares()
    {
        return shares;
    }


    /**
     * Adds shares to this holding.
     *
     * @param count
     *            how many shares to add, at least 1
     * @throws IllegalArgumentException
     *             if the count is less than 1
     */
    public void addShares(int count)
    {
        if (count < 1)
        {
            throw new IllegalArgumentException(
                "Can only add a positive number of shares, not " + count
                    + ".");
        }
        shares += count;
    }


    /**
     * Removes shares from this holding. The holding may end up with zero
     * shares, and it is up to the portfolio to drop it at that point.
     *
     * @param count
     *            how many shares to remove, between 1 and the shares owned
     * @throws IllegalArgumentException
     *             if the count is less than 1 or more than the shares owned
     */
    public void removeShares(int count)
    {
        if (count < 1)
        {
            throw new IllegalArgumentException(
                "Can only remove a positive number of shares, not " + count
                    + ".");
        }
        if (count > shares)
        {
            throw new IllegalArgumentException(
                "Cannot remove " + count + " shares from a holding of only "
                    + shares + ".");
        }
        shares -= count;
    }


    /**
     * Gets what the shares are worth at the stock's current price.
     *
     * @return shares times the current price
     */
    public double getValue()
    {
        return shares * stock.getPrice();
    }


    /**
     * Describes this holding in one line, for example
     * "3 shares of NK (Tech) $150.00 (+1.25%) worth $450.00".
     *
     * @return the description
     */
    public String toString()
    {
        String unit = "shares";
        if (shares == 1)
        {
            unit = "share";
        }
        return shares + " " + unit + " of " + stock + " worth "
            + Money.format(getValue());
    }
}
