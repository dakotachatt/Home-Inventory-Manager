package exceptions;

/**
 *
 * @author Dakota Chatt
 */
public class DeleteOwnAccountException extends Exception {
    
    public DeleteOwnAccountException() {
    }
    
    public DeleteOwnAccountException(String message)  {
        super(message);
    }
}
