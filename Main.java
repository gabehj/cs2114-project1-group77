import java.util.ArrayList;
import java.util.Scanner;

/**
 *  The primary input-output interaction btw users and program as assets are managed.

 * @author gabehj
 * @version Sep 24, 2026
 */
public class Main
{
    Scanner input = new Scanner(System.in);
    ArrayList<Portfolio> userPorts = new ArrayList<>();
    String[] validOption = {"View portfolios", "Select portfolio", "New portfolio", "Delete portfolio", "Quit"};
    String option = "Wait";
    String[] validNames;

    
    /**
     * Runs the whole game: shows the top-level menu until the player quits
     * (or the input runs out).
     *
     */
    public Main()
    {
        System.out.println("Welcome to InfiniStocks!");

        while (!option.equals("Quit")) {
            option = validInput("What would you like to do?", validOption);
            
            if (option.equals("View portfolios")) {
                if (userPorts.isEmpty()){
                    System.out.println("You don't have any portfolios yet.");
                }
                for (Portfolio p : userPorts) {
                    System.out.println(p);
                }
            } 
            
            else if (option.equals("Select portfolio")) {
                validNames = new String[userPorts.size()];
                for (int i = 0; i < userPorts.size(); i++) {
                    validNames[i] = userPorts.get(i).getName();
                }
                option = validInput("Which portfolio would you like to select?", validNames);

                for (int i = 0; i < userPorts.size(); i++) {
                    if (option.equals(userPorts.get(i).getName())) {
                        userPorts.get(i).interact();
                    }
                }
            } 
            else if (option.equals("New portfolio")) {
                userPorts.add(new Portfolio());
            } 
            
            else if (option.equals("Delete portfolio")) {
                validNames = new String[userPorts.size()];
                for (int i = 0; i < userPorts.size(); i++) {
                    validNames[i] = userPorts.get(i).getName();
                }
                option = validInput("Which portfolio would you like to select?", validNames);
                
                for (int i = 0; i < userPorts.size(); i++) {
                    if (option.equals(userPorts.get(i).getName())) {
                        userPorts.get(i).remove();
                    }
                }
            } 
            else {
                System.out.println("Have a nice day!");
            }
        }
    }


    /**
     * Starts the game, reading from the keyboard.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args)
    {
        new Main();
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

    public static ArrayList<Portfolio> run(Input input)
    {
        
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
