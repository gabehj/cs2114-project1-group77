/**
 * 
 * This is Tech subclass of stock where the only diffrence is that its VOLOTILITY is always set to 15
 * 
 * 
 * 
 *  @author janbe
 *  @version Sep 23, 2026
 */



public class Tech
    extends Stock
{
    private static final int VOLATILITY = 15;

    public Tech(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }

}
