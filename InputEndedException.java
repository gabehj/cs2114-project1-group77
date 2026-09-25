/**
 * Thrown by Input when the player's input runs out (for example when the
 * console is closed with Ctrl+D or Ctrl+Z) so that the game can save and
 * exit cleanly instead of crashing with a NoSuchElementException.
 *
 * @author janbe
 * @version Sep 24, 2026
 */
public class InputEndedException
    extends RuntimeException
{
    /** Required by Serializable, which RuntimeException implements. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception.
     *
     * @param message
     *            explains what the game was waiting for
     */
    public InputEndedException(String message)
    {
        super(message);
    }
}
