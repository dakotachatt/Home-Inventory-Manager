package exceptions;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class MissingInputsException extends Exception {
    
    public MissingInputsException() {
    }
    
    public MissingInputsException(String message)  {
        super(message);
    }
}
