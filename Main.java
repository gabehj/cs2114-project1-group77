import java.util.ArrayList;
// The primary input-output interaction btw users 
// and program as assets are managed

import javax.management.ValueExp;

import org.w3c.dom.ranges.RangeException;

public class Main {

    protected String validInput(String message, String[] options) {
        while (true) {
            message += " (";
            for (String option: options) {
                message += option + "/";
            }
            message = message.substring(0,message.length()-2)+") ";
            String testOption = input(message);
            
            try {
                Integer index = Integer.parseInt(testOption);
                
                if ((index > 0) && (index < options.length)) {
                    return options[index];
                } else {
                    throw new Exception("Value out of range!");
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            finally {
                for (int i=0; i<options.length; i++) {
                    if (options[i].toLowerCase().equals(testOption.toLowerCase())) {
                        return options[i];
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to InfiniStocks!");

        ArrayList<Portfolio> userPorts = new ArrayList<>();

        String[] validOption = new String[] {
                "View portfolios",
                "Select portfolio",
                "New Portfolio",
                "Delete Portfolio",
                "Quit"
            };
        String option = "";
        String[] validPortName;

        while (!option.equals("Quit")) {

            option = validInput("What would you like to do?",validOption);

            if (option.equals("View portfolios")) {
                for (Portfolio portfolio : userPorts){
                    System.out.println(portfolio);
                }
            }
            else if (option.equals("Select portfolio")) {
                validPortName = new String[userPorts.size()];

                for (int i = 0; i < userPorts.size(); i++)
                {
                    validPortName[i] = userPorts.get(i).getName();
                }

                String select = validInput(
                    "Which portfolio would you like to select?",
                    validPortName
                );

                for (int i = 0; i < userPorts.size(); i++)
                {
                    if (userPorts.get(i).getName().equals(select))
                    {
                        userPorts.get(i).interact();
                    }
                }
            }
            else if (option.equals("New Portfolio"))
            {
                userPorts.add(new Portfolio());
            }
            else if (option.equals("Delete Portfolio"))
            {
                validOption = new String[userPorts.size()];

                for (int i = 0; i < userPorts.size(); i++)
                {
                    validOption[i] = userPorts.get(i).getName();
                }

                String delete = validInput(
                    "Which portfolio would you like to delete?",
                    validOption
                );

                for (int i = 0; i < userPorts.size(); i++)
                {
                    if (userPorts.get(i).getName().equals(delete))
                    {
                        System.out.println(userPorts.remove(i).getName()
                         + " has been removed from your portfolio.");
                    }
                }
            }
            else if (option.equals("Quit"))
            {
                System.out.println("Have a nice day!");
            }
        }
    }
}