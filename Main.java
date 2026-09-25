import java.util.ArrayList;
import java.util.Scanner;

/**
 * The entry point of InfiniStocks. Main runs the top-level menu, where the
 * player creates, opens, and deletes portfolios. Everything lives in memory
 * for the length of one run; nothing is written to disk. Managing a single
 * portfolio (buying, selling, and so on) is handled by
 * Portfolio.interact().
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public final class Main
{
    /** The top-level menu, in the order it is shown. */
    private static final String[] MENU = {
        "View portfolios", "Select portfolio", "New portfolio",
        "Delete portfolio", "Quit"};

    /** Menu option that backs out of a select or delete prompt. */
    private static final String BACK = "Back";

    /**
     * This class only has static methods, so it is never instantiated.
     */
    private Main()
    {
        // not used
    }


    /**
     * Starts the game, reading from the keyboard.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args)
    {
        run(new Input(new Scanner(System.in)));
    }


    /**
     * Runs the whole game: shows the top-level menu until the player quits
     * (or the input runs out).
     *
     * @param input
     *            where the player's answers come from
     * @return the portfolios as they were when the game ended, so that a
     *         test can look at what the player did
     */
    public static ArrayList<Portfolio> run(Input input)
    {
        System.out.println("Welcome to InfiniStocks!");
        Market market = Market.createDefault();
        ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();

        try
        {
            String option = "";
            while (!option.equals("Quit"))
            {
                option = input.choose("What would you like to do?", MENU);

                if (option.equals("View portfolios"))
                {
                    viewPortfolios(portfolios);
                }
                else if (option.equals("Select portfolio"))
                {
                    selectPortfolio(input, market, portfolios);
                }
                else if (option.equals("New portfolio"))
                {
                    newPortfolio(input, portfolios);
                }
                else if (option.equals("Delete portfolio"))
                {
                    deletePortfolio(input, portfolios);
                }
                else
                {
                    System.out.println("Have a nice day!");
                }
            }
        }
        catch (InputEndedException e)
        {
            System.out.println("No more input. Have a nice day!");
        }
        return portfolios;
    }


    /**
     * Prints every portfolio.
     *
     * @param portfolios
     *            the portfolios to print
     */
    private static void viewPortfolios(ArrayList<Portfolio> portfolios)
    {
        if (portfolios.isEmpty())
        {
            System.out.println("You don't have any portfolios yet.");
            return;
        }
        for (Portfolio portfolio : portfolios)
        {
            System.out.print(portfolio);
        }
    }


    /**
     * Lets the player pick a portfolio and manage it until they exit it.
     *
     * @param input
     *            where the player's answers come from
     * @param market
     *            the stocks that can be traded
     * @param portfolios
     *            the portfolios to choose from
     */
    private static void selectPortfolio(Input input, Market market,
        ArrayList<Portfolio> portfolios)
    {
        if (portfolios.isEmpty())
        {
            System.out.println("You don't have any portfolios yet.");
            return;
        }
        String choice = input.choose(
            "Which portfolio would you like to select?",
            namesWithBack(portfolios));
        if (!choice.equals(BACK))
        {
            Portfolio.findByName(portfolios, choice).interact(input, market);
        }
    }


    /**
     * Asks for a name and creates a new portfolio with the starting cash.
     * The name has to be valid (see Portfolio.validateName) and not already
     * in use, and the question is repeated until it is.
     *
     * @param input
     *            where the player's answers come from
     * @param portfolios
     *            the list the new portfolio is added to
     */
    private static void newPortfolio(Input input,
        ArrayList<Portfolio> portfolios)
    {
        while (true)
        {
            String name = input.readLine(
                "What is the name of the new portfolio?");
            try
            {
                name = Portfolio.validateName(name);
                if (name.equalsIgnoreCase(BACK))
                {
                    throw new IllegalArgumentException(
                        "\"" + BACK + "\" is reserved for the menus.");
                }
                Portfolio existing = Portfolio.findByName(portfolios, name);
                if (existing != null)
                {
                    throw new IllegalArgumentException(
                        "You already have a portfolio named "
                            + existing.getName() + ".");
                }
                portfolios.add(new Portfolio(name));
                System.out.println("Created portfolio \"" + name + "\" with "
                    + Money.format(Portfolio.STARTING_BALANCE) + ".");
                return;
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage() + " Please try again.");
            }
        }
    }


    /**
     * Lets the player pick a portfolio and removes it.
     *
     * @param input
     *            where the player's answers come from
     * @param portfolios
     *            the portfolios to choose from
     */
    private static void deletePortfolio(Input input,
        ArrayList<Portfolio> portfolios)
    {
        if (portfolios.isEmpty())
        {
            System.out.println("You don't have any portfolios to delete.");
            return;
        }
        String choice = input.choose(
            "Which portfolio would you like to delete?",
            namesWithBack(portfolios));
        if (!choice.equals(BACK))
        {
            Portfolio portfolio = Portfolio.findByName(portfolios, choice);
            portfolios.remove(portfolio);
            System.out.println(portfolio.getName()
                + " has been removed from your portfolios.");
        }
    }


    /**
     * Lists the portfolio names as menu options, followed by Back.
     *
     * @param portfolios
     *            the portfolios to list
     * @return a new array of names ending with Back
     */
    private static String[] namesWithBack(ArrayList<Portfolio> portfolios)
    {
        String[] names = new String[portfolios.size() + 1];
        for (int i = 0; i < portfolios.size(); i++)
        {
            names[i] = portfolios.get(i).getName();
        }
        names[portfolios.size()] = BACK;
        return names;
    }
}
