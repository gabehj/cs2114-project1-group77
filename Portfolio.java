import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Portfolio is one player's account: a name, the cash it holds, and the
 * shares it owns. It can buy and sell shares of any stock in the market,
 * refuses trades the player cannot afford, and reports whether the player is
 * ahead of the money they put in. The interact() method is the menu loop a
 * player uses to manage the portfolio from the command line.
 *
 * @author gabehj
 * @version Sep 24, 2026
 */
public class Portfolio
{
    Scanner input = new Scanner(System.in);

    private String name;

    /** Cash on hand. */
    private double balance;

    /** Every dollar the player has put in, including the starting cash. */
    private double deposited;

    /** The shares the portfolio owns, one holding per stock. */
    private ArrayList<Stock> stocks;

    /**
     * Runs the portfolio menu until the player chooses Exit. One trading
     * day passes each time the menu is shown, so prices move whether the
     * player trades or just waits.
     *
     * @throws InputEndedException
     *             if the input runs out before the player exits
     */
    public void interact() {
        String validOptions[] = {"Display","Buy","Sell","Wait","Exit"};
        String option = "";
	    String name;
	    int price;
        String[] validNames;

        while (!option.equals("Exit")) {
	        option = validInput("What would you like to do?",validOptions);
            
            if (option.equals("Display")) {
                System.out.println(this);

            } else if (option.equals("Buy")) {
                if (balance > 0) {
                    name = "";
                    name = input.nextLine();
                    while (name.equals("")) {
                        System.out.println("Please enter a stock name");
                        name = input.nextLine();
                    }
                    ///
                    stocks.add(new Stock(name, price, price, price));
                }
            
            } else if (option.equals("Sell")) {
                validNames = new String[stocks.size()];
                for (int i = 0; i < stocks.size(); i++) {
                    validNames[i] = stocks.get(i).getName();
                }
                name = validInput("Which stock would you like to sell?",validNames);
                
                for (int j = 0; j < stocks.size(); j++) {
                    if (stocks.get(j).getName().equals(name)) {
                        stocks.get(j).sell();
                    }
                }

            } else if (option.equals("Wait")) {
                System.out.println("You decide to keep cool and let it ride...");
            
            } else if (option.equals("Exit")) {
                System.out.println("You run away from the bear with your tail between your legs :(");
                break;
            
            } else {
                System.out.println("Universe Explodes! You have chosen an invalid option.");
            }
            for (int i = 0; i < stocks.size(); i++) {
                stocks.get(i).update();
            }
        }
    }


    /**
     * Takes input message and list of acceptable responses.
     *
     * @param message
     *            message to display
     * @param validOptions
     *            list of acceptable responses
     * @return valid response from user
     */
    public String validInput(String message, String[] validOptions) {
        message += " (";
        for (String option : validOptions) {
            message += option + ", ";
        }
        message = message.substring(0, message.length() - 2) + ") ";
        
        while (true) {
            System.out.println(message);
            String userOption = input.nextLine();
            
            try {
                int index = Integer.parseInt(userOption);
                if ((index >= 0) && (index < validOptions.length)) {
                    return validOptions[index];
                }
            }
            catch (Exception e) {
                // Ignore and check the string options below
            }
            finally {
                for (String option : validOptions) {
                    if (userOption.equalsIgnoreCase(option)) {
                        return option;
                    }
                }
            }
        }  
    }

    /** Cash every new portfolio starts with. */
    public static final double STARTING_BALANCE = 10000.00;

    /** Longest name a portfolio may have. */
    public static final int MAX_NAME_LENGTH = 20;

    /** Smallest deposit the game accepts, in dollars. */
    private static final double MIN_DEPOSIT = 0.01;

    /** Half a cent, below which a difference is treated as zero. */
    private static final double HALF_CENT = 0.005;

    /**
     * Creates a portfolio with the standard starting cash.
     *
     * @param name
     *            the portfolio's name
     * @throws IllegalArgumentException
     *             if the name is not valid (see validateName)
     */
    public Portfolio(String name)
    {
        this.name = name;
    }


    /**
     * Creates a portfolio with a particular cash balance instead of the
     * standard starting cash.
     *
     * @param name
     *            the portfolio's name
     * @param balance
     *            cash on hand
     * @param deposited
     *            total cash the player has put in over time
     * @throws IllegalArgumentException
     *             if the name is not valid or either amount is negative,
     *             NaN, or infinite
     */
    public Portfolio(String name, double balance, double deposited)
    {
        this.name = validateName(name);
        if (!isValidAmount(balance) || !isValidAmount(deposited))
        {
            throw new IllegalArgumentException(
                "Balance and deposits must be non-negative amounts.");
        }
        this.balance = balance;
        this.deposited = deposited;
        this.holdings = new ArrayList<Holding>();
    }


    /**
     * Tells whether a number is usable as an amount of money.
     *
     * @param amount
     *            the number to check
     * @return true if it is finite and not negative
     */
    private static boolean isValidAmount(double amount)
    {
        return !Double.isNaN(amount) && !Double.isInfinite(amount)
            && amount >= 0;
    }


    /**
     * Gets the portfolio's name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Renames the portfolio.
     *
     * @param name
     *            the new name
     * @throws IllegalArgumentException
     *             if the name is not valid (see validateName)
     */
    public void setName(String name)
    {
        if (!(name.equals("")))
        {
            this.name = name;
        }
    }


    /**
     * Gets the cash on hand.
     *
     * @return the balance
     */
    public double getBalance()
    {
        return balance;
    }


    /**
     * Gets every dollar the player has put in, including the starting cash.
     *
     * @return the total deposited
     */
    public double getDeposited()
    {
        return deposited;
    }


    /**
     * Adds cash to the portfolio.
     *
     * @param amount
     *            how much to add, at least one cent
     * @throws IllegalArgumentException
     *             if the amount is less than a cent, NaN, or infinite
     */
    public void deposit(double amount)
    {
        if (!isValidAmount(amount) || amount < MIN_DEPOSIT)
        {
            throw new IllegalArgumentException(
                "You can only deposit a positive amount of money.");
        }
        balance += amount;
        deposited += amount;
    }


    /**
     * Gets a copy of the list of holdings, in the order the stocks were
     * first bought.
     *
     * @return a new list containing the holdings
     */
    public ArrayList<Holding> getHoldings()
    {
        return new ArrayList<Holding>(holdings);
    }


    /**
     * Finds the holding for a stock, by the stock's name, ignoring case.
     *
     * @param stockName
     *            the stock's name
     * @return the holding, or null if no shares of that stock are owned
     */
    public Holding getHolding(String stockName)
    {
        if (stockName == null)
        {
            return null;
        }
        for (Holding holding : holdings)
        {
            if (holding.getStock().getName().equalsIgnoreCase(
                stockName.trim()))
            {
                return holding;
            }
        }
        return null;
    }


    /**
     * Gets how many shares of a stock are owned.
     *
     * @param stockName
     *            the stock's name
     * @return the share count, or 0 if none are owned
     */
    public int getShares(String stockName)
    {
        Holding holding = getHolding(stockName);
        if (holding == null)
        {
            return 0;
        }
        return holding.getShares();
    }


    /**
     * Gets the names of every stock the portfolio owns shares of, for use as
     * menu options.
     *
     * @return a new array of stock names, in holding order
     */
    public String[] ownedNames()
    {
        String[] names = new String[holdings.size()];
        for (int i = 0; i < holdings.size(); i++)
        {
            names[i] = holdings.get(i).getStock().getName();
        }
        return names;
    }


    /**
     * Adds shares to the portfolio without charging for them, merging them
     * into the existing holding for that stock if there is one. Buying goes
     * through buy(), which pays for the shares and then calls this.
     *
     * @param stock
     *            the stock
     * @param shares
     *            how many shares to add, at least 1
     * @throws IllegalArgumentException
     *             if the stock is null or the share count is less than 1
     */
    public void addHolding(Stock stock, int shares)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("There is no such stock.");
        }
        Holding holding = getHolding(stock.getName());
        if (holding == null)
        {
            holdings.add(new Holding(stock, shares));
        }
        else
        {
            holding.addShares(shares);
        }
    }


    /**
     * Buys shares of a stock at its current price, paying out of the cash
     * balance.
     *
     * @param stock
     *            the stock to buy
     * @param shares
     *            how many shares to buy, at least 1
     * @throws IllegalArgumentException
     *             if the stock is null, the share count is less than 1, or
     *             the shares cost more than the cash on hand; the message
     *             explains which
     */
    public void buy(Stock stock, int shares)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("There is no such stock.");
        }
        if (shares < 1)
        {
            throw new IllegalArgumentException(
                "You have to buy at least one share.");
        }
        double cost = stock.getPrice() * shares;
        if (cost > balance)
        {
            throw new IllegalArgumentException("Buying " + shares
                + " share(s) of " + stock.getName() + " costs "
                + Money.format(cost) + " but you only have "
                + Money.format(balance) + ".");
        }
        balance -= cost;
        addHolding(stock, shares);
    }


    /**
     * Sells shares of a stock at its current price, adding the proceeds to
     * the cash balance. A holding that reaches zero shares is removed.
     *
     * @param stock
     *            the stock to sell
     * @param shares
     *            how many shares to sell, at least 1
     * @throws IllegalArgumentException
     *             if the stock is null, the share count is less than 1, or
     *             more shares are sold than are owned; the message explains
     *             which
     */
    public void sell(Stock stock, int shares)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("There is no such stock.");
        }
        if (shares < 1)
        {
            throw new IllegalArgumentException(
                "You have to sell at least one share.");
        }
        Holding holding = getHolding(stock.getName());
        int owned = 0;
        if (holding != null)
        {
            owned = holding.getShares();
        }
        if (shares > owned)
        {
            throw new IllegalArgumentException("You only own " + owned
                + " share(s) of " + stock.getName() + ", so you can't sell "
                + shares + ".");
        }
        holding.removeShares(shares);
        if (holding.getShares() == 0)
        {
            holdings.remove(holding);
        }
        balance += holding.getStock().getPrice() * shares;
    }


    /**
     * Gets what all the shares are worth at current prices.
     *
     * @return the total value of the holdings
     */
    public double getStockValue()
    {
        double total = 0;
        for (Holding holding : holdings)
        {
            total += holding.getValue();
        }
        return total;
    }


    /**
     * Gets what the whole portfolio is worth: cash plus shares.
     *
     * @return the total value
     */
    public double getTotalValue()
    {
        return balance + getStockValue();
    }


    /**
     * Gets how far ahead (positive) or behind (negative) the player is,
     * compared with the money they put in.
     *
     * @return total value minus total deposits
     */
    public double getProfit()
    {
        return getTotalValue() - deposited;
    }


    /**
     * Tells whether the player has made money overall. Gains of less than
     * half a cent do not count, so floating-point noise from buying and
     * selling at the same price is not reported as a profit.
     *
     * @return true if the profit is at least one cent
     */
    public boolean isProfitable()
    {
        return getProfit() >= HALF_CENT;
    }


    /**
     * Describes the portfolio: cash, every holding, total value, and the
     * profit or loss so far.
     *
     * @return the multi-line description
     */
    public String toString()
    {
        String text = "Portfolio \"" + name + "\"\n";
        text += "  Cash: " + Money.format(balance) + "\n";
        if (holdings.isEmpty())
        {
            text += "  Holdings: none yet\n";
        }
        else
        {
            text += "  Holdings:\n";
            for (Holding holding : holdings)
            {
                text += "    " + holding + "\n";
            }
        }
        text += "  Stocks worth: " + Money.format(getStockValue()) + "\n";
        text += "  Total value: " + Money.format(getTotalValue()) + "\n";
        String result = "Loss";
        if (isProfitable())
        {
            result = "Profit";
        }
        else if (getProfit() > -HALF_CENT)
        {
            result = "Break even";
        }
        text += "  " + result + ": " + Money.format(getProfit())
            + " on " + Money.format(deposited) + " put in\n";
        return text;
    }


    /**
     * Walks the player through buying shares: which stock, then how many,
     * limited to what the cash on hand can pay for.
     *
     * @param input
     *            where the player's answers come from
     * @param market
     *            the stocks that can be bought
     */
    private void buyFromInput(Input input, Market market)
    {
        String choice = input.choose("Which stock would you like to buy?",
            withBack(market.names()));
        if (choice.equals(BACK))
        {
            return;
        }
        Stock stock = market.find(choice);
        int affordable = affordableShares(stock);
        System.out.println(stock.getName() + " costs "
            + Money.format(stock.getPrice()) + " per share. You have "
            + Money.format(balance) + ", enough for " + affordable
            + " share(s).");
        if (affordable < 1)
        {
            System.out.println("You can't afford any shares of "
                + stock.getName() + " right now.");
            return;
        }
        // The share count is capped at what the cash covers, so buy()
        // cannot refuse it.
        int shares = input.readInt("How many shares would you like to buy?",
            1, affordable);
        buy(stock, shares);
        System.out.println("You bought " + shares + " share(s) of "
            + stock.getName() + " for "
            + Money.format(stock.getPrice() * shares)
            + ". Cash left: " + Money.format(balance) + ".");
    }


    /**
     * Works out the most shares of a stock the cash on hand can pay for.
     * This is the answer buy() agrees with: floor(balance / price) can be
     * one share too many when floating-point rounding makes that many
     * shares cost a hair more than the balance, so the count is checked
     * against the same multiplication buy() uses.
     *
     * @param stock
     *            the stock to price
     * @return the largest share count whose cost is not more than the
     *         balance, or 0 if the stock is free or unaffordable
     */
    public int affordableShares(Stock stock)
    {
        if (stock == null || stock.getPrice() <= 0)
        {
            return 0;
        }
        int affordable = (int)Math.min(Integer.MAX_VALUE,
            Math.floor(balance / stock.getPrice()));
        while (affordable > 0 && stock.getPrice() * affordable > balance)
        {
            affordable--;
        }
        return affordable;
    }


    /**
     * Walks the player through selling shares: which of the stocks they
     * own, then how many, limited to the shares they have.
     *
     * @param input
     *            where the player's answers come from
     * @param market
     *            the stocks that can be traded
     */
    private void sellFromInput(Input input, Market market)
    {
        if (holdings.isEmpty())
        {
            System.out.println("You don't own any shares yet.");
            return;
        }
        String choice = input.choose("Which stock would you like to sell?",
            withBack(ownedNames()));
        if (choice.equals(BACK))
        {
            return;
        }
        Holding holding = getHolding(choice);
        Stock stock = holding.getStock();
        int owned = holding.getShares();
        System.out.println("You own " + owned + " share(s) of "
            + stock.getName() + " at " + Money.format(stock.getPrice())
            + " each, worth " + Money.format(holding.getValue()) + ".");
        // The share count is capped at what is owned, so sell() cannot
        // refuse it.
        int shares = input.readInt("How many shares would you like to sell?",
            1, owned);
        sell(stock, shares);
        System.out.println("You sold " + shares + " share(s) of "
            + stock.getName() + " for "
            + Money.format(stock.getPrice() * shares)
            + ". Cash now: " + Money.format(balance) + ".");
    }


    /**
     * Asks the player how much cash to add and adds it.
     *
     * @param input
     *            where the player's answers come from
     */
    private void depositFromInput(Input input)
    {
        double amount = input.readMoney(
            "How much money would you like to add?", MIN_DEPOSIT);
        deposit(amount);
        System.out.println("Added " + Money.format(amount)
            + ". Cash now: " + Money.format(balance) + ".");
    }
}