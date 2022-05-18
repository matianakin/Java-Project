package pl.polsl.grabowski.mateusz.model;
/**
 * Exception that is thrown if the text that is to be encrypted or decrypted doesn't meet the criteria of this operation
 * 
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class IncorrectEncryptOrDecryptTextException 
    extends Exception
        {
    
    /**
     * Exception constructor with error message
     * 
     * @param errorMessage Message shown when the exception is caught
     */
    public IncorrectEncryptOrDecryptTextException(String errorMessage) 
            {
            super(errorMessage);
            }
}
