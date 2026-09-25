import java.util.ArrayList;

/**
 * The Market is the list of every security that can be traded, and it keeps
 * count of how many trading days have passed. Every portfolio shares the
 * same market, so a price move is seen by everyone.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Market
{
    /** Every security that can be traded, in listing order. */
    private ArrayList<Stock> stocks;

    /** How many times the whole market has been updated. */
    private int day;

    /**
     * Creates an empty market. Use createDefault() for the standard lineup.
     */
    public Market()
    {
        stocks = new ArrayList<Stock>();
        day = 0;
    }


    /**
     * Creates the standard InfiniStocks market: one bond, two tech stocks,
     * one energy stock, and two ETFs.
     *
     * @return a market containing the six default securities
     */
    public static Market createDefault()
    {
        Market market = new Market();
        market.add(new Bond("USA", 100.00, 50000));
        market.add(new Tech("NK", 150.00, 250000));
        market.add(new Tech("Grok", 42.00, 180000));
        market.add(new Energy("Solar City", 75.00, 90000));
        market.add(new ETF("s&p5", 500.00, 400000));
        market.add(new ETF("QQQ", 400.00, 300000));
        return market;
    }


    /**
     * Adds a security to the market. Names must be unique, ignoring case,
     * because players pick stocks by typing their names.
     *
     * @param stock
     *            the security to add
     * @throws IllegalArgumentException
     *             if the stock is null or its name is already in the market
     */
    public void add(Stock stock)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("Cannot add a null stock.");
        }
        if (find(stock.getName()) != null)
        {
            throw new IllegalArgumentException(
                "The market already has a stock named " + stock.getName()
                    + ".");
        }
        stocks.add(stock);
    }


    /**
     * Looks up a security by name, ignoring case.
     *
     * @param name
     *            the name to look for
     * @return the matching stock, or null if there is none
     */
    public Stock find(String name)
    {
        if (name == null)
        {
            return null;
        }
        for (Stock stock : stocks)
        {
            if (stock.getName().equalsIgnoreCase(name.trim()))
            {
                return stock;
            }
        }
        return null;
    }


    /**
     * Gets a security by its position in the listing.
     *
     * @param index
     *            position, from 0 to size() - 1
     * @return the stock at that position
     */
    public Stock get(int index)
    {
        return stocks.get(index);
    }


    /**
     * Gets how many securities are in the market.
     *
     * @return the number of stocks
     */
    public int size()
    {
        return stocks.size();
    }


    /**
     * Gets how many trading days have passed, which is the number of times
     * update() has been called.
     *
     * @return the day counter
     */
    public int getDay()
    {
        return day;
    }


    /**
     * Gets the names of every security, in listing order, for use as menu
     * options.
     *
     * @return a new array of stock names
     */
    public String[] names()
    {
        String[] names = new String[stocks.size()];
        for (int i = 0; i < stocks.size(); i++)
        {
            names[i] = stocks.get(i).getName();
        }
        return names;
    }


    /**
     * Moves one trading day forward: every security gets a random price
     * update and the day counter goes up by one.
     */
    public void update()
    {
        for (Stock stock : stocks)
        {
            stock.update();
        }
        day++;
    }


    /**
     * Summarizes the whole market on a single line, for example
     * "Day 3: USA $100.40 (+0.40%) | NK $151.20 (+0.80%)".
     *
     * @return the one-line summary
     */
    public String ticker()
    {
        String line = "Day " + day + ":";
        for (int i = 0; i < stocks.size(); i++)
        {
            Stock stock = stocks.get(i);
            if (i > 0)
            {
                line += " |";
            }
            line += " " + stock.getName() + " " + Money.format(stock.getPrice())
                + " (" + String.format("%+.2f", stock.getPercentChange())
                + "%)";
        }
        return line;
    }


    /**
     * Lists every security on its own numbered line, with its category,
     * price, and most recent percent change.
     *
     * @return the multi-line listing
     */
    public String toString()
    {
        String text = "Market prices on day " + day + ":\n";
        for (int i = 0; i < stocks.size(); i++)
        {
            text += "  " + (i + 1) + ". " + stocks.get(i) + "\n";
        }
        if (stocks.isEmpty())
        {
            text += "  (no stocks are listed)\n";
        }
        return text;
    }
}
