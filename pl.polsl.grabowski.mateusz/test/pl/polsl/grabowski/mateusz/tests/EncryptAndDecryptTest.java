package pl.polsl.grabowski.mateusz.tests;

import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.grabowski.mateusz.model.*;
import pl.polsl.grabowski.mateusz.view.*;

/**
 * Class containing tests related to the functions encryptText and decryptText from {@link pl.polsl.grabowski.mateusz.model.Model}
 * 
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class EncryptAndDecryptTest {

    /** A {@link pl.polsl.grabowski.mateusz.model.Model} object used to call its methods */
    private final Model model = new Model();
    /** A {@link pl.polsl.grabowski.mateusz.view.View} object used to call its methods */
    private final View view = new View();
    
    /**
     * Function making sure that the key is exsisting with the same seed before each test
     */
    @BeforeEach
    public void setUp() {
        model.setSeed(5);
        model.setKey();
    }
    
    /**
     * Function giving arguments to the encryptExceptionTest test method
     * 
     * @return arguments used by encryptExceptionTest method
     */
    private static Stream<Arguments> provideStringsForEET() {
        return Stream.of(
          Arguments.of("ŻÓŁĆ", true),
          Arguments.of("mag Jerzy przyjaciel młodzieży czuwa nad wszystkim ze szczytu swojej wieży", true),
          Arguments.of("There once was a ship that put to sea, the name of that ship was Billy'O'Tea", false),
          Arguments.of("                                                !!!! ", false),
          Arguments.of("", false)

        );
    }
    
    /**
     * Function giving arguments to the decryptExceptionTest test method
     * 
     * @return arguments used by decryptExceptionTest method
     */
    private static Stream<Arguments> provideStringsForDET() {
        return Stream.of(
          Arguments.of("ŻÓŁĆ", true),
          Arguments.of("123 567 272 123", false),
          Arguments.of("883409792", true),
          Arguments.of("Hello World", true),
          Arguments.of("", false)

        );
    }
    
    /**
     * Test function checking whether the exception is thrown with the correct input in the encryptText function
     * 
     * @param input the input we want to perform our test on
     * @param expected boolean representing the expectation of whether there will be an exception thrown or not
     */
    @ParameterizedTest
    @MethodSource("provideStringsForEET")
    public void encryptExceptionTest(String input, boolean expected)
    {
        boolean error=false;
        model.setText(input);
        try
        {    
            model.setText(model.encryptText());
        }
        catch(IncorrectEncryptOrDecryptTextException e)
        {
            view.printErrorMsg(e);
            error=true;
        }
        assertEquals(error, expected);
    }
    
     /**
     * Test function checking whether the exception is thrown with the correct input in the decryptText function
     * 
     * @param input the input we want to perform our test on
     * @param expected boolean representing the expectation of whether there will be an exception thrown or not
     */
    @ParameterizedTest
    @MethodSource("provideStringsForDET")
    public void decryptExceptionTest(String input, boolean expected)
    {
        boolean error=false;
        model.setText(input);
        try
        {    
            model.setText(model.decryptText());
        }
        catch(IncorrectEncryptOrDecryptTextException e)
        {
            view.printErrorMsg(e);
            error=true;
        }
         assertEquals(error, expected);
    }
    
    /**
     * Function checking correctness of the encryptText and decryptText by comparing the original text to one that has been encrypted and then decrypted
     * 
     * @param input the text we want to perform our test on
     */
    @ParameterizedTest
    @ValueSource(strings={"0", "Hello World", "In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of"})
    public void corectnessBothTest(String input)
    {
        model.setText(input);
        try
        {    
            model.setText(model.encryptText());
            model.setText(model.decryptText());
        }
        catch(IncorrectEncryptOrDecryptTextException e)
        {
            view.printErrorMsg(e);
        }
        assertEquals(input, model.getText());
        
    }
    
}
