import java.util.ArrayList;
import java.util.Scanner;
import student.TestCase;

/**
 * Tests the Portfolio class: names, cash, buying and selling (including the
 * refusals for overbuying and overselling), profit tracking, the printed
 * summary, and the interact() menu loop. The market used here is made of
 * zero-volatility stocks so prices never move and every amount can be
 * checked exactly.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class PortfolioTest extends TestCase
{
    /** Tolerance for double comparisons. */
    private static final double DELTA = 0.0001;

    private Market market;
    private Stock cheap;
    private Stock pricey;
    private Portfolio portfolio;

    /**
     * Builds a two-stock market with fixed prices ($10 and $4,000) and a
     * fresh portfolio with the standard $10,000 before every test.
     */
    public void setUp()
    {
        cheap = new Stock("CHEAP", 10.0, 0, 100);
        pricey = new Stock("PRICEY", 4000.0, 0, 100);
        market = new Market();
        market.add(cheap);
        market.add(pricey);
        portfolio = new Portfolio("Test");
    }


    /**
     * Builds an Input that will read the given text.
     *
     * @param script
     *            the lines the "player" types, separated by newlines
     * @return the input reader
     */
    private Input scripted(String script)
    {
        return new Input(new Scanner(script));
    }


    // ----------------------------------------------------------
    // Construction and names

    /**
     * A new portfolio has the starting cash, no holdings, and counts the
     * starting cash as deposited.
     */
    public void testNewPortfolio()
    {
        assertEquals("Test", portfolio.getName());
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            DELTA);
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getDeposited(),
            DELTA);
        assertEquals(0, portfolio.getHoldings().size());
        assertEquals(0, portfolio.ownedNames().length);
        assertEquals(0.0, portfolio.getStockValue(), DELTA);
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getTotalValue(),
            DELTA);
        assertEquals(0.0, portfolio.getProfit(), DELTA);
        assertFalse(portfolio.isProfitable());
    }


    /**
     * The three-argument constructor sets a particular balance and deposit
     * total instead of the standard starting cash.
     */
    public void testCustomBalancePortfolio()
    {
        Portfolio custom = new Portfolio("  Custom ", 250.5, 1000.0);

        assertEquals("Custom", custom.getName());
        assertEquals(250.5, custom.getBalance(), DELTA);
        assertEquals(1000.0, custom.getDeposited(), DELTA);
        assertEquals(-749.5, custom.getProfit(), DELTA);
        assertFalse(custom.isProfitable());
    }


    /**
     * Negative, NaN, and infinite amounts are refused by the constructor.
     */
    public void testConstructorRejectsBadAmounts()
    {
        double[] bad = {-1.0, Double.NaN, Double.POSITIVE_INFINITY};
        for (double amount : bad)
        {
            try
            {
                new Portfolio("Bad", amount, 0.0);
                fail("balance " + amount + " should be rejected");
            }
            catch (IllegalArgumentException e)
            {
                assertNotNull(e.getMessage());
            }
            try
            {
                new Portfolio("Bad", 0.0, amount);
                fail("deposited " + amount + " should be rejected");
            }
            catch (IllegalArgumentException e)
            {
                assertNotNull(e.getMessage());
            }
        }
    }


    /**
     * validateName() trims good names and explains what is wrong with bad
     * ones: blank, too long, or made only of digits.
     */
    public void testValidateName()
    {
        assertEquals("Gabe", Portfolio.validateName("  Gabe  "));
        assertEquals("My Fund 2", Portfolio.validateName("My Fund 2"));
        assertEquals("abcdefghijklmnopqrst",
            Portfolio.validateName("abcdefghijklmnopqrst"));

        String[] bad = {null, "", "   ", "abcdefghijklmnopqrstu", "42",
            "007"};
        for (String name : bad)
        {
            try
            {
                Portfolio.validateName(name);
                fail("name \"" + name + "\" should be rejected");
            }
            catch (IllegalArgumentException e)
            {
                assertTrue(e.getMessage().startsWith("A name can't"));
            }
        }
    }


    /**
     * The constructor and setName() both go through validateName().
     */
    public void testConstructorAndSetNameValidate()
    {
        try
        {
            new Portfolio(" ");
            fail("a blank name should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }

        portfolio.setName("  Renamed ");
        assertEquals("Renamed", portfolio.getName());

        try
        {
            portfolio.setName("12345");
            fail("a name made only of digits should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("Renamed", portfolio.getName());
        }
    }


    /**
     * findByName() ignores case and spaces and returns null when nothing
     * matches.
     */
    public void testFindByName()
    {
        ArrayList<Portfolio> list = new ArrayList<Portfolio>();
        Portfolio other = new Portfolio("Other");
        list.add(portfolio);
        list.add(other);

        assertSame(portfolio, Portfolio.findByName(list, "test"));
        assertSame(other, Portfolio.findByName(list, " OTHER "));
        assertNull(Portfolio.findByName(list, "nobody"));
        assertNull(Portfolio.findByName(list, null));
        assertNull(Portfolio.findByName(new ArrayList<Portfolio>(), "test"));
    }


    // ----------------------------------------------------------
    // Cash

    /**
     * Deposits raise both the balance and the deposit total, so they never
     * count as profit.
     */
    public void testDeposit()
    {
        portfolio.deposit(25.5);

        assertEquals(10025.5, portfolio.getBalance(), DELTA);
        assertEquals(10025.5, portfolio.getDeposited(), DELTA);
        assertEquals(0.0, portfolio.getProfit(), DELTA);
        assertFalse(portfolio.isProfitable());
    }


    /**
     * Deposits of less than a cent, negative amounts, NaN, and infinity
     * are refused and change nothing.
     */
    public void testDepositRejectsBadAmounts()
    {
        double[] bad = {0.0, 0.001, -5.0, Double.NaN,
            Double.POSITIVE_INFINITY};
        for (double amount : bad)
        {
            try
            {
                portfolio.deposit(amount);
                fail("deposit of " + amount + " should be rejected");
            }
            catch (IllegalArgumentException e)
            {
                assertEquals(Portfolio.STARTING_BALANCE,
                    portfolio.getBalance(), DELTA);
            }
        }
    }


    // ----------------------------------------------------------
    // Buying

    /**
     * Buying takes the cost out of the cash and records the shares.
     */
    public void testBuy()
    {
        portfolio.buy(cheap, 3);

        assertEquals(9970.0, portfolio.getBalance(), DELTA);
        assertEquals(3, portfolio.getShares("CHEAP"));
        assertEquals(3, portfolio.getShares("cheap"));
        assertEquals(1, portfolio.getHoldings().size());
        assertSame(cheap, portfolio.getHolding("CHEAP").getStock());
        assertEquals(30.0, portfolio.getStockValue(), DELTA);
        assertEquals(10000.0, portfolio.getTotalValue(), DELTA);
        assertEquals(0.0, portfolio.getProfit(), DELTA);
    }


    /**
     * Buying the same stock twice adds to the one holding instead of
     * making a second one, while a different stock gets its own holding.
     */
    public void testBuyMergesHoldings()
    {
        portfolio.buy(cheap, 3);
        portfolio.buy(cheap, 2);
        portfolio.buy(pricey, 1);

        assertEquals(2, portfolio.getHoldings().size());
        assertEquals(5, portfolio.getShares("CHEAP"));
        assertEquals(1, portfolio.getShares("PRICEY"));
        assertEquals(5950.0, portfolio.getBalance(), DELTA);
        assertEquals(4050.0, portfolio.getStockValue(), DELTA);

        String[] owned = portfolio.ownedNames();
        assertEquals(2, owned.length);
        assertEquals("CHEAP", owned[0]);
        assertEquals("PRICEY", owned[1]);
    }


    /**
     * The player can spend every last dollar, but not one dollar more.
     */
    public void testBuyExactlyAllCash()
    {
        portfolio.buy(cheap, 1000);

        assertEquals(0.0, portfolio.getBalance(), DELTA);
        assertEquals(1000, portfolio.getShares("CHEAP"));

        try
        {
            portfolio.buy(cheap, 1);
            fail("buying with no cash should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1000, portfolio.getShares("CHEAP"));
        }
    }


    /**
     * Overbuying is refused with a message that says what it would have
     * cost, and nothing changes.
     */
    public void testBuyRejectsOverbuying()
    {
        try
        {
            portfolio.buy(pricey, 3);
            fail("a $12,000 purchase should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("$12,000.00"));
            assertTrue(e.getMessage().contains("$10,000.00"));
            assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
                DELTA);
            assertEquals(0, portfolio.getShares("PRICEY"));
            assertEquals(0, portfolio.getHoldings().size());
        }
    }


    /**
     * Buying zero or negative shares, or a null stock, is refused.
     */
    public void testBuyRejectsBadArguments()
    {
        try
        {
            portfolio.buy(cheap, 0);
            fail("buying zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(0, portfolio.getShares("CHEAP"));
        }
        try
        {
            portfolio.buy(cheap, -2);
            fail("buying negative shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(0, portfolio.getShares("CHEAP"));
        }
        try
        {
            portfolio.buy(null, 1);
            fail("buying a null stock should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
                DELTA);
        }
    }


    /**
     * affordableShares() is the most shares the cash covers: exact
     * divisions, leftover change, a stock that costs more than the cash,
     * and a free or missing stock, which count as unaffordable.
     */
    public void testAffordableShares()
    {
        assertEquals(1000, portfolio.affordableShares(cheap));
        assertEquals(2, portfolio.affordableShares(pricey));

        Portfolio small = new Portfolio("Small", 35.0, 35.0);
        assertEquals(3, small.affordableShares(cheap));
        assertEquals(0, small.affordableShares(pricey));

        assertEquals(0, portfolio.affordableShares(null));
        assertEquals(0,
            portfolio.affordableShares(new Stock("FREE", 0.0, 0, 1)));
    }


    /**
     * floor(balance / price) can be one share too many because of
     * floating-point rounding: 3546.62 / 3.43 comes out as 1034, but 1034
     * shares at $3.43 cost a hair more than $3,546.62. affordableShares()
     * has to agree with buy(), so it reports 1033, and buying that many
     * works while buying 1034 is refused.
     */
    public void testAffordableSharesMatchesBuy()
    {
        Portfolio exact = new Portfolio("Exact", 3546.62, 3546.62);
        Stock stock = new Stock("ODD", 3.43, 0, 1);

        assertEquals(1034, (int)Math.floor(3546.62 / 3.43));
        assertEquals(1033, exact.affordableShares(stock));

        try
        {
            exact.buy(stock, 1034);
            fail("1034 shares cost more than the balance");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(0, exact.getShares("ODD"));
        }
        exact.buy(stock, 1033);
        assertEquals(1033, exact.getShares("ODD"));
    }


    // ----------------------------------------------------------
    // Selling

    /**
     * Selling part of a holding adds the proceeds to the cash and keeps
     * the rest of the shares.
     */
    public void testSellPart()
    {
        portfolio.buy(cheap, 10);
        portfolio.sell(cheap, 4);

        assertEquals(9940.0, portfolio.getBalance(), DELTA);
        assertEquals(6, portfolio.getShares("CHEAP"));
        assertEquals(1, portfolio.getHoldings().size());
    }


    /**
     * Selling every share removes the holding entirely.
     */
    public void testSellAll()
    {
        portfolio.buy(cheap, 10);
        portfolio.buy(pricey, 1);
        portfolio.sell(cheap, 10);

        assertEquals(0, portfolio.getShares("CHEAP"));
        assertNull(portfolio.getHolding("CHEAP"));
        assertEquals(1, portfolio.getHoldings().size());
        assertEquals("PRICEY", portfolio.ownedNames()[0]);
        assertEquals(6000.0, portfolio.getBalance(), DELTA);
    }


    /**
     * Overselling is refused with a message that says how many shares are
     * really owned, and nothing changes. That covers selling more than
     * owned as well as selling a stock that is not owned at all.
     */
    public void testSellRejectsOverselling()
    {
        portfolio.buy(cheap, 5);

        try
        {
            portfolio.sell(cheap, 6);
            fail("selling 6 of 5 shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("You only own 5 share(s)"));
            assertEquals(5, portfolio.getShares("CHEAP"));
            assertEquals(9950.0, portfolio.getBalance(), DELTA);
        }
        try
        {
            portfolio.sell(pricey, 1);
            fail("selling a stock that is not owned should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("You only own 0 share(s)"));
            assertEquals(9950.0, portfolio.getBalance(), DELTA);
        }
    }


    /**
     * Selling zero or negative shares, or a null stock, is refused.
     */
    public void testSellRejectsBadArguments()
    {
        portfolio.buy(cheap, 5);

        try
        {
            portfolio.sell(cheap, 0);
            fail("selling zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(5, portfolio.getShares("CHEAP"));
        }
        try
        {
            portfolio.sell(cheap, -1);
            fail("selling negative shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(5, portfolio.getShares("CHEAP"));
        }
        try
        {
            portfolio.sell(null, 1);
            fail("selling a null stock should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(5, portfolio.getShares("CHEAP"));
        }
    }


    // ----------------------------------------------------------
    // Holdings and values

    /**
     * addHolding() records shares without charging for them, and merges
     * repeated additions of the same stock into one holding.
     */
    public void testAddHolding()
    {
        portfolio.addHolding(cheap, 7);
        portfolio.addHolding(cheap, 3);

        assertEquals(10, portfolio.getShares("CHEAP"));
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            DELTA);
        assertEquals(100.0, portfolio.getStockValue(), DELTA);
        assertEquals(100.0, portfolio.getProfit(), DELTA);
        assertTrue(portfolio.isProfitable());

        try
        {
            portfolio.addHolding(null, 1);
            fail("a null stock should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1, portfolio.getHoldings().size());
        }
        try
        {
            portfolio.addHolding(pricey, 0);
            fail("zero shares should be rejected");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1, portfolio.getHoldings().size());
        }
    }


    /**
     * getHoldings() hands back a copy, so changing it does not change the
     * portfolio.
     */
    public void testGetHoldingsIsACopy()
    {
        portfolio.buy(cheap, 1);
        ArrayList<Holding> copy = portfolio.getHoldings();
        copy.clear();

        assertEquals(1, portfolio.getHoldings().size());
    }


    /**
     * getHolding() and getShares() cope with names that are not owned and
     * with null.
     */
    public void testGetHoldingMisses()
    {
        assertNull(portfolio.getHolding("CHEAP"));
        assertNull(portfolio.getHolding(null));
        assertEquals(0, portfolio.getShares("CHEAP"));
        assertEquals(0, portfolio.getShares(null));
    }


    /**
     * Profit follows the market: a price rise after buying is a profit, a
     * fall is a loss, and selling locks the result into the cash.
     */
    public void testProfitFollowsPrices()
    {
        portfolio.buy(cheap, 100);
        assertEquals(0.0, portfolio.getProfit(), DELTA);
        assertFalse(portfolio.isProfitable());

        cheap.setPrice(12.0);
        assertEquals(200.0, portfolio.getProfit(), DELTA);
        assertTrue(portfolio.isProfitable());

        cheap.setPrice(9.0);
        assertEquals(-100.0, portfolio.getProfit(), DELTA);
        assertFalse(portfolio.isProfitable());

        portfolio.sell(cheap, 100);
        assertEquals(9900.0, portfolio.getBalance(), DELTA);
        assertEquals(-100.0, portfolio.getProfit(), DELTA);
        assertEquals(0, portfolio.getHoldings().size());
    }


    /**
     * A gain of less than half a cent is not called a profit, so rounding
     * noise from a buy and sell at the same price never shows up as one.
     */
    public void testIsProfitableIgnoresNoise()
    {
        Portfolio tiny = new Portfolio("Tiny", 100.004, 100.0);
        assertFalse(tiny.isProfitable());

        Portfolio penny = new Portfolio("Penny", 100.01, 100.0);
        assertTrue(penny.isProfitable());
    }


    // ----------------------------------------------------------
    // toString

    /**
     * An empty portfolio's summary says so and reports break-even.
     */
    public void testToStringEmpty()
    {
        assertEquals("Portfolio \"Test\"\n"
            + "  Cash: $10,000.00\n"
            + "  Holdings: none yet\n"
            + "  Stocks worth: $0.00\n"
            + "  Total value: $10,000.00\n"
            + "  Break even: $0.00 on $10,000.00 put in\n",
            portfolio.toString());
    }


    /**
     * A portfolio with holdings lists each one and reports the profit or
     * loss.
     */
    public void testToStringWithHoldings()
    {
        portfolio.buy(cheap, 5);
        portfolio.buy(pricey, 1);
        cheap.setPrice(20.0);

        assertEquals("Portfolio \"Test\"\n"
            + "  Cash: $5,950.00\n"
            + "  Holdings:\n"
            + "    5 shares of CHEAP (Stock) $20.00 (+0.00%) worth $100.00\n"
            + "    1 share of PRICEY (Stock) $4,000.00 (+0.00%) "
            + "worth $4,000.00\n"
            + "  Stocks worth: $4,100.00\n"
            + "  Total value: $10,050.00\n"
            + "  Profit: $50.00 on $10,000.00 put in\n",
            portfolio.toString());

        cheap.setPrice(5.0);
        assertTrue(portfolio.toString().contains(
            "  Loss: -$25.00 on $10,000.00 put in\n"));
    }


    // ----------------------------------------------------------
    // interact()

    /**
     * Exit leaves the menu right away, after one trading day has passed,
     * and prints the farewell.
     */
    public void testInteractExit()
    {
        portfolio.interact(scripted("exit\n"), market);

        String out = systemOut().getHistory();
        assertEquals(1, market.getDay());
        assertTrue(out.contains("Managing portfolio \"Test\"."));
        assertTrue(out.contains("Day 1: CHEAP $10.00 (+0.00%) | "
            + "PRICEY $4,000.00 (+0.00%)"));
        assertTrue(out.contains("What would you like to do? "
            + "(Display/Market/Buy/Sell/Deposit/Wait/Exit)"));
        assertTrue(out.contains("You run away from the bear"));
    }


    /**
     * Display prints the portfolio summary and Market prints the price
     * list; both can also be chosen by number.
     */
    public void testInteractDisplayAndMarket()
    {
        portfolio.interact(scripted("display\n2\n7\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("  Cash: $10,000.00"));
        assertTrue(out.contains("  Holdings: none yet"));
        assertTrue(out.contains("Market prices on day 2:"));
        assertTrue(out.contains("  2. PRICEY (Stock) $4,000.00 (+0.00%)"));
        assertEquals(3, market.getDay());
    }


    /**
     * Wait just lets a day pass and prints the flavor text.
     */
    public void testInteractWait()
    {
        portfolio.interact(scripted("wait\nwait\nexit\n"), market);

        assertEquals(3, market.getDay());
        assertTrue(systemOut().getHistory().contains(
            "You survey the market with the regality of a lion"));
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            DELTA);
    }


    /**
     * Buying through the menu: the player picks a stock, is told what it
     * costs and how many they can afford, and bad share counts are
     * re-asked until a good one arrives.
     */
    public void testInteractBuy()
    {
        portfolio.interact(scripted("buy\ncheap\nlots\n0\n3\nexit\n"),
            market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("Which stock would you like to buy? "
            + "(CHEAP/PRICEY/Back)"));
        assertTrue(out.contains("CHEAP costs $10.00 per share. You have "
            + "$10,000.00, enough for 1000 share(s)."));
        assertTrue(out.contains("\"lots\" is not a whole number."));
        assertTrue(out.contains("0 is out of range."));
        assertTrue(out.contains("between 1 and 1000"));
        assertTrue(out.contains("You bought 3 share(s) of CHEAP for $30.00. "
            + "Cash left: $9,970.00."));
        assertEquals(3, portfolio.getShares("CHEAP"));
        assertEquals(9970.0, portfolio.getBalance(), DELTA);
    }


    /**
     * The share prompt is capped at what the cash can pay for, so trying
     * to overbuy is re-asked instead of accepted.
     */
    public void testInteractBuyCannotOverbuy()
    {
        portfolio.interact(scripted("buy\n2\n3\n2\nexit\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("PRICEY costs $4,000.00 per share. You have "
            + "$10,000.00, enough for 2 share(s)."));
        assertTrue(out.contains("3 is out of range."));
        assertTrue(out.contains("You bought 2 share(s) of PRICEY for "
            + "$8,000.00. Cash left: $2,000.00."));
        assertEquals(2, portfolio.getShares("PRICEY"));
    }


    /**
     * When the player cannot afford even one share, they are told so and
     * are not asked for a share count.
     */
    public void testInteractBuyWithNoCash()
    {
        Portfolio broke = new Portfolio("Broke", 5.0, 5.0);
        broke.interact(scripted("buy\ncheap\nexit\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("enough for 0 share(s)."));
        assertTrue(out.contains(
            "You can't afford any shares of CHEAP right now."));
        assertFalse(out.contains("How many shares would you like to buy?"));
        assertEquals(0, broke.getHoldings().size());
        assertEquals(5.0, broke.getBalance(), DELTA);
    }


    /**
     * A stock whose price is zero cannot be bought, and the affordability
     * message copes with it instead of dividing by zero.
     */
    public void testInteractBuyZeroPriceStock()
    {
        Market odd = new Market();
        odd.add(new Stock("FREE", 0.0, 0, 1));
        portfolio.interact(scripted("buy\nfree\nexit\n"), odd);

        String out = systemOut().getHistory();
        assertTrue(out.contains("enough for 0 share(s)."));
        assertTrue(out.contains(
            "You can't afford any shares of FREE right now."));
        assertEquals(0, portfolio.getHoldings().size());
    }


    /**
     * Choosing Back at the stock prompt cancels the purchase.
     */
    public void testInteractBuyBack()
    {
        portfolio.interact(scripted("buy\nback\nexit\n"), market);

        assertFalse(systemOut().getHistory().contains("costs"));
        assertEquals(0, portfolio.getHoldings().size());
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            DELTA);
    }


    /**
     * Selling through the menu: only owned stocks are offered, the player
     * is told what they own, and the share count is capped at that.
     */
    public void testInteractSell()
    {
        portfolio.buy(cheap, 10);
        portfolio.interact(scripted("sell\ncheap\n11\n4\nexit\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("Which stock would you like to sell? "
            + "(CHEAP/Back)"));
        assertTrue(out.contains("You own 10 share(s) of CHEAP at $10.00 each, "
            + "worth $100.00."));
        assertTrue(out.contains("11 is out of range."));
        assertTrue(out.contains("You sold 4 share(s) of CHEAP for $40.00. "
            + "Cash now: $9,940.00."));
        assertEquals(6, portfolio.getShares("CHEAP"));
        assertEquals(9940.0, portfolio.getBalance(), DELTA);
    }


    /**
     * Selling every share through the menu removes the holding, so the
     * next Sell reports that nothing is owned.
     */
    public void testInteractSellEverything()
    {
        portfolio.buy(cheap, 2);
        portfolio.interact(scripted("sell\n1\n2\nsell\nexit\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("You sold 2 share(s) of CHEAP for $20.00."));
        assertTrue(out.contains("You don't own any shares yet."));
        assertEquals(0, portfolio.getHoldings().size());
        assertEquals(Portfolio.STARTING_BALANCE, portfolio.getBalance(),
            DELTA);
    }


    /**
     * Sell with nothing owned says so, and Back at the stock prompt
     * cancels the sale.
     */
    public void testInteractSellNothingAndBack()
    {
        portfolio.interact(scripted("sell\nexit\n"), market);
        assertTrue(systemOut().getHistory().contains(
            "You don't own any shares yet."));

        systemOut().clearHistory();
        portfolio.buy(cheap, 1);
        portfolio.interact(scripted("sell\nback\nexit\n"), market);

        assertFalse(systemOut().getHistory().contains("You own"));
        assertEquals(1, portfolio.getShares("CHEAP"));
    }


    /**
     * Deposit through the menu asks for an amount, rejects bad ones, and
     * adds the good one to the cash.
     */
    public void testInteractDeposit()
    {
        portfolio.interact(scripted("deposit\nabc\n0\n$25.50\nexit\n"),
            market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("How much money would you like to add?"));
        assertTrue(out.contains("\"abc\" is not an amount of money."));
        assertTrue(out.contains("$0.00 is too small."));
        assertTrue(out.contains("Added $25.50. Cash now: $10,025.50."));
        assertEquals(10025.5, portfolio.getBalance(), DELTA);
        assertEquals(10025.5, portfolio.getDeposited(), DELTA);
    }


    /**
     * A menu answer that matches nothing is re-asked, following the scope
     * document's "BUY!" example, and then handled normally.
     */
    public void testInteractRepromptsBadOption()
    {
        portfolio.interact(scripted("BUY!\nbuy\ncheap\n1\nexit\n"), market);

        String out = systemOut().getHistory();
        assertTrue(out.contains("Please enter one of the following exactly "
            + "(Display/Market/Buy/Sell/Deposit/Wait/Exit)"));
        assertTrue(out.contains("You bought 1 share(s) of CHEAP"));
    }


    /**
     * If the input runs out in the middle of the menu, the exception
     * escapes so that Main can save the game and exit cleanly.
     */
    public void testInteractInputEnds()
    {
        try
        {
            portfolio.interact(scripted("wait\n"), market);
            fail("running out of input should throw");
        }
        catch (InputEndedException e)
        {
            assertEquals(2, market.getDay());
        }
    }
}
