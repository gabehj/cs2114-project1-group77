/**
 * A Bond is the safest kind of security in the market: its volatility is
 * always 2, so its price barely moves from one update to the next.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Bond
    extends Stock
{
    /** Volatility shared by every bond. */
    private static final int VOLATILITY = 2;

    /**
     * Creates a new bond.
     *
     * @param name
     *            ticker symbol or display name
     * @param price
     *            starting price of one share
     * @param volume
     *            trading volume
     */
    public Bond(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }


    /**
     * {@inheritDoc}
     */
    public String getCategory()
    {
        return "Bond";
    }
}
