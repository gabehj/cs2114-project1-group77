import java.util.ArrayList;
import java.util.Scanner;
import student.TestCase;

/**
 * Tests the top-level menu in Main by scripting what the player types and
 * checking what is printed and what the portfolios look like afterwards.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class MainTest extends TestCase
{
    /** The top-level menu as it is printed. */
    private static final String MENU = "What would you like to do? "
        + "(View portfolios/Select portfolio/New portfolio/"
        + "Delete portfolio/Quit)";

    /**
     * Nothing shared to set up; each test runs its own game.
     */
    public void setUp()
    {
        // nothing to do
    }


    /**
     * Runs the game with the given script as the player's typing.
     *
     * @param script
     *            the lines the "player" types, separated by newlines
     * @return the portfolios as they were when the game ended
     */
    private ArrayList<Portfolio> play(String script)
    {
        return Main.run(new Input(new Scanner(script)));
    }


    /**
     * Quitting right away prints the welcome and the farewell and leaves
     * no portfolios behind.
     */
    public void testQuitImmediately()
    {
        ArrayList<Portfolio> portfolios = play("quit\n");

        String out = systemOut().getHistory();
        assertTrue(out.startsWith("Welcome to InfiniStocks!"));
        assertTrue(out.contains(MENU));
        assertTrue(out.contains("Have a nice day!"));
        assertEquals(0, portfolios.size());
    }


    /**
     * With no portfolios, viewing, selecting, and deleting all say so
     * instead of showing an empty menu.
     */
    public void testMenusWithNoPortfolios()
    {
        play("1\n2\n4\n5\n");

        String out = systemOut().getHistory();
        assertTrue(out.contains("You don't have any portfolios yet."));
        assertTrue(out.contains("You don't have any portfolios to delete."));
        assertFalse(out.contains("Which portfolio"));
    }


    /**
     * Creating a portfolio gives it the starting cash, and bad names are
     * re-asked: blank, a duplicate, the reserved word Back, and a number.
     */
    public void testNewPortfolio()
    {
        ArrayList<Portfolio> portfolios = play(
            "new portfolio\n\nGabe\n3\ngabe\nback\n7\nJanberk\n1\nquit\n");

        String out = systemOut().getHistory();
        assertTrue(out.contains("What is the name of the new portfolio?"));
        assertTrue(out.contains("A name can't be blank. Please try again."));
        assertTrue(out.contains(
            "Created portfolio \"Gabe\" with $10,000.00."));
        assertTrue(out.contains("You already have a portfolio named Gabe. "
            + "Please try again."));
        assertTrue(out.contains("\"Back\" is reserved for the menus. "
            + "Please try again."));
        assertTrue(out.contains("A name can't be just a number"));
        assertTrue(out.contains("Created portfolio \"Janberk\""));
        assertTrue(out.contains("Portfolio \"Gabe\""));
        assertTrue(out.contains("Portfolio \"Janberk\""));

        assertEquals(2, portfolios.size());
        assertEquals("Gabe", portfolios.get(0).getName());
        assertEquals("Janberk", portfolios.get(1).getName());
        assertEquals(Portfolio.STARTING_BALANCE,
            portfolios.get(0).getBalance(), 0.0001);
    }


    /**
     * Selecting a portfolio hands control to its own menu, and trades made
     * there are still there when the player comes back out.
     */
    public void testSelectPortfolioAndTrade()
    {
        ArrayList<Portfolio> portfolios = play(
            "3\nGabe\n2\ngabe\nbuy\nNK\n3\nexit\nquit\n");

        String out = systemOut().getHistory();
        assertTrue(out.contains("Which portfolio would you like to select? "
            + "(Gabe/Back)"));
        assertTrue(out.contains("Managing portfolio \"Gabe\"."));
        assertTrue(out.contains("You bought 3 share(s) of NK"));
        assertTrue(out.contains("You run away from the bear"));
        assertTrue(out.contains("Have a nice day!"));

        assertEquals(1, portfolios.size());
        assertEquals(3, portfolios.get(0).getShares("NK"));
        assertTrue(portfolios.get(0).getBalance() < 10000.0);
    }


    /**
     * Choosing Back at the select prompt returns to the main menu without
     * opening anything.
     */
    public void testSelectBack()
    {
        play("3\nGabe\n2\nback\n5\n");

        assertFalse(systemOut().getHistory().contains("Managing portfolio"));
        assertTrue(systemOut().getHistory().contains("Have a nice day!"));
    }


    /**
     * The market is shared, so a day that passes in one portfolio's menu
     * has passed for the next portfolio too.
     */
    public void testMarketIsSharedBetweenPortfolios()
    {
        play("3\nGabe\n3\nJanberk\n2\nGabe\nwait\nexit\n2\nJanberk\nexit\n"
            + "quit\n");

        String out = systemOut().getHistory();
        assertTrue(out.contains("Day 1:"));
        assertTrue(out.contains("Day 2:"));
        assertTrue(out.contains("Day 3:"));
        assertFalse(out.contains("Day 4:"));
    }


    /**
     * Deleting removes the chosen portfolio (matched ignoring case) and
     * leaves the others, and Back cancels.
     */
    public void testDeletePortfolio()
    {
        ArrayList<Portfolio> portfolios = play(
            "3\nGabe\n3\nJanberk\n4\nback\n4\nGABE\n1\nquit\n");

        String out = systemOut().getHistory();
        assertTrue(out.contains("Which portfolio would you like to delete? "
            + "(Gabe/Janberk/Back)"));
        assertTrue(out.contains(
            "Gabe has been removed from your portfolios."));
        assertFalse(out.contains("Janberk has been removed"));

        assertEquals(1, portfolios.size());
        assertEquals("Janberk", portfolios.get(0).getName());
    }


    /**
     * When the input runs out, the game says goodbye instead of crashing,
     * whether that happens at the main menu or deep inside a portfolio's
     * menu, and what the player did up to then is kept.
     */
    public void testInputRunsOut()
    {
        ArrayList<Portfolio> portfolios = play("3\nGabe\n");
        assertTrue(systemOut().getHistory().contains(
            "No more input. Have a nice day!"));
        assertEquals(1, portfolios.size());

        systemOut().clearHistory();
        portfolios = play("3\nGabe\n2\nGabe\nbuy\nUSA\n1\n");
        assertTrue(systemOut().getHistory().contains(
            "No more input. Have a nice day!"));
        assertEquals(1, portfolios.get(0).getShares("USA"));
    }


    /**
     * main() reads from System.in and ignores its arguments.
     */
    public void testMain()
    {
        setSystemIn("3\nGabe\nquit\n");
        Main.main(null);

        String out = systemOut().getHistory();
        assertTrue(out.startsWith("Welcome to InfiniStocks!"));
        assertTrue(out.contains("Created portfolio \"Gabe\""));
        assertTrue(out.contains("Have a nice day!"));

        systemOut().clearHistory();
        setSystemIn("5\n");
        Main.main(new String[] {"ignored"});
        assertTrue(systemOut().getHistory().contains("Have a nice day!"));
    }
}
