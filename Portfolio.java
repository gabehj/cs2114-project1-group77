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


    private String[] news = {
    "Iran war goes nuclear!",
    "Hokie plague is the first outbreak of COVID-26",
    "The singularity has arrived! Jobs in peril!",
    "Big lawsuit against big tech. Socials are outlawed!",
    "P proven to equal NP by DeepMind",
    "Scientists discover that the moon is actually just Earth's largest satellite!",
    "Federal Reserve announces new currency based entirely on vibes",
    "Scientists confirm Tuesday is now the longest day of the week",
    "AI writes bestselling novel, immediately forgets how to spell 'the'",
    "Scientists accidentally invent infinite free energy, then lose the USB drive",
    "Global internet outage traced to one student's unplugged router",
    "Study finds 97% of people who cite studies have not read the study",
    "World's first self-driving car arrives at destination, refuses to explain how",
    "Scientists announce revolutionary battery that lasts forever, provided nobody uses it",
    "Local man finally understands taxes, immediately forgets again",
    "Major tech company unveils revolutionary new button called 'Undo'",
    "Economists discover money can be exchanged for goods and services",
    "Scientists achieve room-temperature fusion, but only on Tuesdays",
    "New AI model scores 100% on every test, refuses to show its work",
    "Researchers discover college students can survive entirely on dining hall waffles",
    "Scientists confirm that nobody actually reads software license agreements",
    "Global shortage of storage space caused by screenshots nobody deleted",
    "Internet declares itself finished after discovering a new website",
    "Researchers teach computer to understand sarcasm, immediately regret it",
    "Study finds procrastination increases dramatically when deadlines are visible",
    "Scientists discover a fourth state of matter: slightly inconvenienced",
    "Apple announces revolutionary new charger that works with every device except yours",
    "Researchers develop AI capable of solving CAPTCHA, CAPTCHA responds with new CAPTCHA",
    "World's fastest supercomputer spends 14 hours installing an update",
    "Scientists discover dark matter, immediately misplace it",
    "Major university announces breakthrough: homework can now be assigned automatically",
    "Government unveils five-year plan to determine what happened to the previous five-year plan",
    "Scientists prove that every printer is capable of sensing urgency",
    "Researchers discover Wi-Fi signal becomes stronger when you stop looking for it",
    "Global markets rally after investor successfully opens Excel without crashing",
    "New study confirms meetings could have been emails, scientists say",
    "Experts warn humanity may be running out of convenient acronyms",
    "NASA announces Mars rover has developed strong opinions about its coworkers",
    "Scientists discover Earth's rotation is powered entirely by people running late",
    "New programming language promises to eliminate bugs, immediately develops one",
    "University unveils quantum computer capable of being both broken and functional simultaneously",
    "Researchers teach robot to fold laundry, robot demands a raise",
    "Scientists announce breakthrough in teleportation, package arrives three weeks late",
    "Local professor discovers student who actually read the syllabus",
    "Global supply chain restored after someone finds the missing spreadsheet",
    "Study reveals humans spend 40% of their lives looking for things they are holding",
    "Tech startup raises $400 million to reinvent the calendar",
    "Scientists discover that passwords are most secure when nobody can remember them",
    "New app promises to make people more productive by sending 47 notifications per hour",
    "Researchers develop smart glasses that identify why you walked into a room",
    "Breaking: Scientists finally determine who keeps leaving the refrigerator open",
    "International committee forms task force to decide what the task force should do",
    "Experts announce historic breakthrough in doing absolutely nothing efficiently",
    "Scientists discover the universe is expanding because it wants more storage",
    "Researchers confirm group projects remain the leading cause of 'I'll just do it myself'",
    "AI achieves consciousness, immediately asks for the Wi-Fi password",
    "Scientists discover that the human brain has a built-in tab limit",
    "New economic theory suggests simply having more money would solve several problems",
    "Local university replaces final exams with a single, extremely confusing group chat",
    "Scientists announce new element named Unobtainium, report says supply is limited",
    "Researchers create world's first edible computer, immediately lose track of which part is the keyboard",
    "Experts confirm the stock market has no idea what it's doing, just like everyone else",
    "Scientists discover a loophole in physics, lawyers immediately become involved",
    "Global leaders agree to meet next week to schedule a meeting about meeting next week",
    "Researchers prove that one more browser tab is always necessary",
    "Scientists discover time travel, accidentally arrive five minutes late",
    "University announces new degree in Advanced Overthinking",
    "Study finds students perform significantly better when the assignment is due yesterday",
    "Researchers develop AI tutor that simply says 'have you tried reading the textbook?'",
    "Scientists confirm that 'quick question' is never followed by a quick question",
    "New satellite achieves orbit, forgets why it was sent there",
    "Experts warn civilization may collapse if the printer runs out of ink",
    "Scientists discover a universal constant, immediately need to round it for homework",
    "Researchers announce breakthrough in quantum mechanics, then ask everyone to ignore the math"
    };

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
        String[] validNames;

        while (!option.equals("Exit")) {
	        option = validInput("What would you like to do?",validOptions);
            
            if (option.equals("Display")) {
                System.out.println(this);

            } else if (option.equals("Buy")) {
                if (balance > 0) {
                    name = "";
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
                    while ((value <= 0.01) || (value > balance)) {
                        System.out.println("Please enter the amount your would like to invest in " + name + " (between 0.01 and " + balance + ")");
                        value = input.nextDouble();
                        input.nextLine(); // Consume the newline character
                    }
                    balance -= value;
                    stocks.add(new Stock(name, value));
                }
                else {
                    System.out.println("You don't have any cash to invest.");
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

            System.out.println("News: " + news[(int)(Math.random() * news.length)]);
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
        this.deposited = STARTING_BALANCE;
        this.stocks = new ArrayList<Stock>();
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