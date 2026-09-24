/**
 * 
 * This is ETF subclass of stock where the only diffrence is that its VOLOTILITY is always set to 12
 * 
 * 
 * 
 *  @author janbe
 *  @version Sep 23, 2026
 */

public class ETF
    extends Stock
{
    private static final int VOLATILITY = 12;

    public ETF(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }
}
