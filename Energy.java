/**
 * 
 * This is Energy subclass of stock where the only diffrence is that its VOLOTILITY is always set to 25
 * 
 * 
 * 
 *  @author janbe
 *  @version Sep 23, 2026
 */

public class Energy
    extends Stock
{

    private static final int VOLATILITY = 25;

    public Energy(String name, double price, int volume)
    {
        super(name, price, VOLATILITY, volume);
    }
    
}
