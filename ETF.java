/**
 * An ETF tracks a basket of stocks, so it sits between bonds and single
 * stocks in risk: its volatility is always 12.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class ETF
    extends Stock
{
    /** Volatility shared by every ETF. */
    private static final int VOLATILITY = 12;

    /**
     * Creates a new ETF.
     *
     * @param name
     *            ticker symbol or display name
     * @param price
     *            starting price of one share
     * @param volume
     *            trading volume
     */
    public ETF(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }


    /**
     * {@inheritDoc}
     */
    public String getCategory()
    {
        return "ETF";
    }
}
