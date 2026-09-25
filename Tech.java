/**
 * A Tech stock is a growth stock: its volatility is always 15, so its price
 * can swing noticeably in a single update.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Tech
    extends Stock
{
    /** Volatility shared by every tech stock. */
    private static final int VOLATILITY = 15;

    /**
     * Creates a new tech stock.
     *
     * @param name
     *            ticker symbol or display name
     * @param price
     *            starting price of one share
     * @param volume
     *            trading volume
     */
    public Tech(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }


    /**
     * {@inheritDoc}
     */
    public String getCategory()
    {
        return "Tech";
    }
}
