/**
 * 
 * This is Bond subclass of stock where the only diffrence is that its VOLOTILITY is always set to 2
 * 
 * 
 * 
 *  @author janbe
 *  @version Sep 23, 2026
 */

public class Bond extends Stock
{
    private static final int VOLATILITY = 2;

    public Bond(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }
}