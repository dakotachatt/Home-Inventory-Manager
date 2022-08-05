package exceptions;

/**
 *
 * @author Dakota Chatt
 */
public class OwnAccountException extends Exception {
    
    public OwnAccountException() {
    }
    
    public OwnAccountException(String message)  {
        super(message);
    }
}
