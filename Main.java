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
                if (userPorts.isEmpty()){
                    System.out.println("You don't have any portfolios to select.");
                    continue;
                }
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
                String name = "";
                while (name.equals("")) {
                    System.out.println("Please enter a portfolio name");
                    name = input.nextLine();
                    for (Portfolio p : userPorts) {
                        if (name.equals(p.getName())) {
                            System.out.println("You already have a portfolio named " + p.getName() + ".");
                            name = "";
                        }
                    }
                }
                userPorts.add(new Portfolio(name));
            } 
            
            else if (option.equals("Delete portfolio")) {
                validNames = new String[userPorts.size()];
                if (validNames.length == 0) {
                    System.out.println("You don't have any portfolios to delete.");
                    continue;
                }
                for (int i = 0; i < userPorts.size(); i++) {
                    validNames[i] = userPorts.get(i).getName();
                }
                option = validInput("Which portfolio would you like to select?", validNames);
                
                for (int i = 0; i < userPorts.size(); i++) {
                    if (option.equals(userPorts.get(i).getName())) {
                        userPorts.remove(i);
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
}