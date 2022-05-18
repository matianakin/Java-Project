package pl.polsl.grabowski.mateusz.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.grabowski.mateusz.model.*;
import pl.polsl.grabowski.mateusz.view.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
/**
 * Class containing tests related to the function takeFromCommandLine from {@link pl.polsl.grabowski.mateusz.model.Model}
 * 
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class CommandLineHandleTest {

    /** A {@link pl.polsl.grabowski.mateusz.model.Model} object used to call its methods */
    private final Model model = new Model();
    /** A {@link pl.polsl.grabowski.mateusz.view.View} object used to call its methods */
    private final View view = new View();

    /**
     * Function taking a stream of Strings and returning an array of those Strings. Used in the tests in this file
     * 
     * @param streamArgs the stream of Strings given
     * @return the array of those Strings
     */
    private String[] streamToArray(Stream<String> streamArgs)
    {
        String[] streamArray = streamArgs.toArray(String[]::new);
        return streamArray;
    }

    /**
     * Test function testing whether a given command line input would produce and exception and comparing it to the desired result
     */
    @Test
    public void cLErrorTest()
    {
       boolean error=false;
       try
       {
       model.takeFromCommandLine(streamToArray(Stream.of("John", "Marry", "George", "Paul", "Alice", "Ann")));
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
           error=true;
       }
       assertTrue(error);
       
       error=false;
       try
       {
       model.takeFromCommandLine(streamToArray(Stream.of("-s", "2137", "-b", "Java", "please", "work")));
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
           error=true;
       }
       assertFalse(error);
       
       error=false;
       try
       {
       model.takeFromCommandLine(streamToArray(Stream.of("Ala", "-s", "25", "-b", "ma", "kota")));
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
           error=true;
       }
       assertFalse(error);
       
       error=false;
       try
       {
       model.takeFromCommandLine(streamToArray(Stream.of("")));
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
           error=true;
       }
       assertTrue(error);
       
       error=false;
       try
       {
       model.takeFromCommandLine(streamToArray(Stream.of("-s", "2137", "-b", "Dżawa", "please", "łork")));
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
           error=true;
       }
       assertFalse(error);
    }
    
    /**
     * Function giving arguments to the handlingCLTest test method
     * 
     * @return arguments used by handlingCLTest method
     */
    private static Stream<Arguments> provideStringsForHCLT() {
        return Stream.of(
                Arguments.of(Stream.of("ŻÓŁĆ", "-b", "majonez","musztarda", "-s", "2511")),
                Arguments.of(Stream.of("-s", "11", "-e", "qwerty", "zxvc")),
                Arguments.of(Stream.of("-d", "11","peanut", "butter", "-s", "11", "zxvc"))

        );
    }
    
    /**
     * Test function checking whether data from command line is processed and saved correctly by comparing it against desired results
     * 
     * @param streamArgs streams of Strings later transformed into arrays
     */
    @ParameterizedTest
    @MethodSource("provideStringsForHCLT")
    public void handlingCLTest(Stream<String> streamArgs)
    {
       String[] args = streamToArray(streamArgs);
       try
       {
       model.takeFromCommandLine(args);
       }
       catch(InvalidCLInputException e)
       {
           view.printErrorMsg(e);
       }
       int s=0;
       int w=0;
       String word=""; 
       for(int i=0; i<args.length; i++)
       {
           if(args[i].equals("-s")&& args[i+1]!=null)
            {
                s=(int)args[i+1].charAt(0)-32;                
            }
            else if((args[i].equals("-e")||args[i].equals("-d")||args[i].equals("-b"))&& args[i+1]!=null)
            {
                switch (args[i]) {
                    case "-e":
                        w=1;
                        break;
                    case "-d":
                        w=2;
                        break;
                    case "-b":
                        w=3;
                        break;
                    default:
                        break;
                }
                for(int j = i+1; j < args.length; j++)
                    {
                        if(!(args[j].charAt(0) != '-'))
                        {
                            break;
                        }
                        word = word + args[j] + " ";
                       i = j; 
                        
                    }
            }
       }
       assertEquals(w, model.getChoice());
       assertEquals(word, model.getText());
       assertEquals(s, model.getSeed());
    }
}

