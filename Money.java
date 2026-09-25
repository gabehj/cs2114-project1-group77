/**
 * Small helpers for working with dollar amounts: rounding to whole cents and
 * formatting amounts the way a bank statement would ("$1,234.56",
 * "-$0.50").
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public final class Money
{
    /** Number of cents in a dollar. */
    private static final double CENTS_PER_DOLLAR = 100.0;

    /**
     * This class only has static helpers, so it is never instantiated.
     */
    private Money()
    {
        // not used
    }


    /**
     * Rounds an amount to the nearest cent.
     *
     * @param amount
     *            the amount in dollars
     * @return the amount rounded to two decimal places
     */
    public static double roundToCents(double amount)
    {
        return Math.round(amount * CENTS_PER_DOLLAR) / CENTS_PER_DOLLAR;
    }


    /**
     * Formats an amount as dollars and cents with a thousands separator, for
     * example 1234.5 becomes "$1,234.50" and -0.5 becomes "-$0.50".
     *
     * @param amount
     *            the amount in dollars
     * @return the formatted amount
     */
    public static String format(double amount)
    {
        double rounded = roundToCents(amount);
        String sign = "";
        if (rounded < 0)
        {
            sign = "-";
        }
        return sign + "$" + String.format("%,.2f", Math.abs(rounded));
    }
}
