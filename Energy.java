/**
 * An Energy stock is the riskiest security in the market: its volatility is
 * always 25, so its price can jump or crash in a single update.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Energy
    extends Stock
{
    /** Volatility shared by every energy stock. */
    private static final int VOLATILITY = 25;

    /**
     * Creates a new energy stock.
     *
     * @param name
     *            ticker symbol or display name
     * @param price
     *            starting price of one share
     * @param volume
     *            trading volume
     */
    public Energy(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }


    /**
     * {@inheritDoc}
     */
    public String getCategory()
    {
        return "Energy";
    }
}
