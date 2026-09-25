import java.util.Scanner;

/**
 * Reads and validates everything the player types. Every prompt is repeated
 * until the answer is acceptable, so the rest of the game never has to deal
 * with a bad string where a number was expected or an option that does not
 * exist. All prompts share one Scanner, because opening several Scanners on
 * System.in makes them fight over the same buffered input.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class Input
{
    /** Where the answers come from. */
    private Scanner scanner;

    /**
     * Creates an input reader.
     *
     * @param scanner
     *            the scanner to read answers from, usually on System.in
     * @throws IllegalArgumentException
     *             if the scanner is null
     */
    public Input(Scanner scanner)
    {
        if (scanner == null)
        {
            throw new IllegalArgumentException("Input needs a scanner.");
        }
        this.scanner = scanner;
    }


    /**
     * Prints a prompt and reads one line of input.
     *
     * @param prompt
     *            the question to print
     * @return the line the player typed, trimmed of surrounding spaces
     * @throws InputEndedException
     *             if there is no more input to read
     */
    public String readLine(String prompt)
    {
        System.out.println(prompt);
        if (!scanner.hasNextLine())
        {
            throw new InputEndedException(
                "Input ended while waiting for: " + prompt);
        }
        return scanner.nextLine().trim();
    }


    /**
     * Asks the player to pick one of several options. The answer can be the
     * option's text in any capitalization, or its number in the list
     * (starting at 1). Anything else is rejected and the question is asked
     * again.
     *
     * @param prompt
     *            the question to print
     * @param options
     *            the acceptable answers, in the order they are listed
     * @return the matching entry from options, exactly as it was spelled
     *         there
     * @throws IllegalArgumentException
     *             if there are no options to choose from
     * @throws InputEndedException
     *             if there is no more input to read
     */
    public String choose(String prompt, String[] options)
    {
        if (options == null || options.length == 0)
        {
            throw new IllegalArgumentException(
                "choose() needs at least one option.");
        }
        String menu = " (" + String.join("/", options) + ")";
        String answer = readLine(prompt + menu);
        while (true)
        {
            String match = match(answer, options);
            if (match != null)
            {
                return match;
            }
            answer = readLine("Please enter one of the following exactly"
                + menu + " or its number (1-" + options.length + "):");
        }
    }


    /**
     * Finds which option an answer refers to.
     *
     * @param answer
     *            what the player typed
     * @param options
     *            the acceptable answers
     * @return the matching option, or null if the answer matches none
     */
    private String match(String answer, String[] options)
    {
        String cleaned = answer.trim().toLowerCase();
        try
        {
            int index = Integer.parseInt(cleaned) - 1;
            if (index >= 0 && index < options.length)
            {
                return options[index];
            }
        }
        catch (NumberFormatException e)
        {
            // Not a number, so it must be one of the option names.
        }
        for (String option : options)
        {
            if (option.toLowerCase().equals(cleaned))
            {
                return option;
            }
        }
        return null;
    }


    /**
     * Asks the player for a whole number within a range. Text that is not a
     * whole number, and numbers outside the range, are rejected and the
     * question is asked again.
     *
     * @param prompt
     *            the question to print
     * @param min
     *            the smallest acceptable number
     * @param max
     *            the largest acceptable number
     * @return the number the player entered
     * @throws InputEndedException
     *             if there is no more input to read
     */
    public int readInt(String prompt, int min, int max)
    {
        String range = "a whole number between " + min + " and " + max;
        if (max == Integer.MAX_VALUE)
        {
            range = "a whole number of at least " + min;
        }
        while (true)
        {
            String answer = readLine(prompt);
            try
            {
                int value = Integer.parseInt(answer);
                if (value >= min && value <= max)
                {
                    return value;
                }
                System.out.println(value + " is out of range. Please enter "
                    + range + ".");
            }
            catch (NumberFormatException e)
            {
                System.out.println("\"" + answer + "\" is not a whole number. "
                    + "Please enter " + range + ".");
            }
        }
    }


    /**
     * Asks the player for a dollar amount. A leading dollar sign and commas
     * are allowed ("$1,250.75"). Text that is not a number, and amounts
     * below the minimum, are rejected and the question is asked again.
     *
     * @param prompt
     *            the question to print
     * @param min
     *            the smallest acceptable amount
     * @return the amount the player entered, rounded to whole cents
     * @throws InputEndedException
     *             if there is no more input to read
     */
    public double readMoney(String prompt, double min)
    {
        while (true)
        {
            String answer = readLine(prompt);
            String cleaned = answer.replace("$", "").replace(",", "");
            try
            {
                double value = Double.parseDouble(cleaned);
                if (Double.isNaN(value) || Double.isInfinite(value))
                {
                    throw new NumberFormatException(answer);
                }
                if (value >= min)
                {
                    return Money.roundToCents(value);
                }
                System.out.println(Money.format(value)
                    + " is too small. Please enter at least "
                    + Money.format(min) + ".");
            }
            catch (NumberFormatException e)
            {
                System.out.println("\"" + answer + "\" is not an amount of "
                    + "money. Please enter a number like 25.50.");
            }
        }
    }
}
