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
                        for (Stock s : stocks) {
                            if (name.equals(s.getName())) {
                                System.out.println("You already have a stock named " + s.getName() + ".");
                                name = "";
                                break;
                            }
                        }
                    }
                    double value = 0;
                    while ((value <= 0) || (value > balance)) {
                        System.out.println("Please enter the amount your would like to invest in " + name + " (between 0 and " + balance + ")");
                        value = input.nextDouble();
                    }
                    stocks.add(new Stock(name, value));
                }
            
            } else if (option.equals("Sell")) {
                validNames = new String[stocks.size()];
                for (int i = 0; i < stocks.size(); i++) {
                    validNames[i] = stocks.get(i).getName();
                }
                name = validInput("Which stock would you like to sell?",validNames);
                
                for (int j = 0; j < stocks.size(); j++) {
                    if (stocks.get(j).getName().equals(name)) {
                        Double amount = stocks.get(j).sell();
                        balance += amount;
                        System.out.println("You sold " + name + " for " + amount + ". Cash now: " + balance);
                    }
                    if (stocks.get(j).getVolume() <= 0.1) {
                        stocks.remove(j);
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
     */
    public Portfolio(String name)
    {
        this.name = name;
        this.balance = STARTING_BALANCE;
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
     * Gets what all the shares are worth at current prices.
     *
     * @return the total value of the holdings
     */
    public double getStockValue()
    {
        double total = 0;
        for (Stock s : stocks) {
            total += s.getPrice() * s.getVolume();
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
        return getProfit() >= 0;
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
        if (stocks.isEmpty())
        {
            text += "  Holdings: none yet\n";
        }
        else
        {
            text += "  Holdings:\n";
            for (Stock stock : stocks)
            {
                text += "    " + stock + "\n";
            }
        }
        text += "  Stocks worth: " + Money.format(getStockValue()) + "\n";
        text += "  Total value: " + Money.format(getTotalValue()) + "\n";
        String result = "Loss";
        if (isProfitable())
        {
            result = "Profit";
        }
        else if (getProfit() > 0)
        {
            result = "Break even";
        }
        text += "  " + result + ": " + Money.format(getProfit())
            + " on " + Money.format(deposited) + " put in\n";
        return text;
    }
}