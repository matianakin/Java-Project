package pl.polsl.grabowski.mateusz.tests;

import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.grabowski.mateusz.model.*;

/**
 * Class containing tests related to the key related functions from {@link pl.polsl.grabowski.mateusz.model.Model}
 * 
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class KeyTest {

    /** A {@link pl.polsl.grabowski.mateusz.model.Model} object used to call its methods */
    private final Model model= new Model();
    
    /**
     * Function checking if the key is full, meaning it has 96x5 (480) integers
     */
    private void isFullTest()
    {
        List<List<Integer>> testKey=model.getKey();
        assertFalse(testKey.isEmpty());
        assertTrue(testKey.size()==96);
        testKey.forEach(sublist -> {
            assertTrue(sublist.size()==5);
         });
    }
    
    /**
     * Function checking if no two integers in the key are the same
     */
    private void noRepeatTest()
    {
        List<List<Integer>> testKey=model.getKey();
        for(int i=0; i<testKey.size(); i++)
        {
            for(int q=0; q<testKey.get(i).size(); q++)
            {
                for(int e=0; e<testKey.size(); e++)
                {
                    for(int t=0; t<testKey.get(e).size(); t++)
                    {
                        if(i!=e&&q!=t)
                        {
                            assertFalse(Objects.equals(testKey.get(i).get(q), testKey.get(e).get(t)));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Test function that uses the other two functions in {@link KeyTest} to check if the key works correctly using various seeds
     * 
     * @param seed the seed we want to use to test the key it produces
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE, Integer.MIN_VALUE, -19, -1000432432})
    public void differentSeedsTest(int seed)
    {
        model.setSeed(seed);
        model.setKey();
        isFullTest();
        noRepeatTest();
        
    }
}
