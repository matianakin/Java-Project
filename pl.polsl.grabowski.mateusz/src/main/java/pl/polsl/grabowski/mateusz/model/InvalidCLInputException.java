package pl.polsl.grabowski.mateusz.model;
/**
 * Exception that is thrown if the input in the command line does not include all of the required fields
 * 
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class InvalidCLInputException 
        extends Exception
        {
 
    /**
     * Exception constructor with error message
     * 
     * @param errorMessage Message shown when the exception is caught
     */
    public InvalidCLInputException(String errorMessage) 
            {
            super(errorMessage);
            }
}
