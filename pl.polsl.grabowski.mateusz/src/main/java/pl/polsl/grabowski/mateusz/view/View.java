package pl.polsl.grabowski.mateusz.view;
import java.util.Scanner;

/**
 * Class that is responsible for displaying out and err streams and using scanners to collect in streams
 *
 * @deprecated
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class View {



    public View()
    {
        Window window = new Window();
        window.startingWindow();
    }


    /**
     * Function asking user and collecting the answer what is the Key Seed they want to use. 
     * Used in {@link pl.polsl.grabowski.mateusz.controller.Controller} if incomplete information is provided in command line
     *
     * @return Seed provided by the user
     */
    public int getKeySeed()
    {
        int seedNumber =0;

        System.out.println("What is the seed for your key?");
        Scanner seedScanner = new Scanner(System.in);
        while(!seedScanner.hasNextInt())
        {
            System.out.println("Please enter a number");
            seedScanner.nextLine();

        }
        seedNumber = seedScanner.nextInt();
        return seedNumber;
    }

    /**
     * Function collecting the answer from the user whether they want to encode, decode ot both. 
     * Used in {@link pl.polsl.grabowski.mateusz.controller.Controller} if incomplete information is provided in command line
     *
     * @return Choice provided by the user
     */
    public int getChoice()
    {
        int choiceNumber =0;
        do
        {
            Scanner choiceScanner = new Scanner(System.in);
            System.out.println("Please Enter a number from 1 to 3");
            while(!choiceScanner.hasNextInt())
            {
                System.out.println("Please enter a number from 1 to 3");
                choiceScanner.nextLine();

            }
            choiceNumber = choiceScanner.nextInt();
        }
        while(choiceNumber<1||choiceNumber>3);
        return choiceNumber;
    }

    /**
     * Function asking user and collecting the answer what is the text they want to encode. 
     * Used in {@link pl.polsl.grabowski.mateusz.controller.Controller} if incomplete information is provided in command line
     *
     * @return Text provided by the user
     */
    public String toEncode()
    {
        System.out.println("Enter text to be Encoded");
        Scanner scannedEncode = new Scanner(System.in);
        String toEncode = scannedEncode.nextLine();
        return toEncode;
    }

    /**
     * Function printing the exact contents of the string provided to it
     *
     * @param Input The string that is to be printed
     */
    public void printString(String Input)
    {
        System.out.println(Input);
    }

    /**
     * Function asking user and collecting the answer what is the text they want to decode. 
     * Used in {@link pl.polsl.grabowski.mateusz.controller.Controller} if incomplete information is provided in command line
     *
     * @return Text provided by the user
     */
    public String toDecode()
    {
        System.out.println("Enter text in \nXXX XXX XXX(..)\nformat where X is a number");
        Scanner scannedDecode = new Scanner(System.in);
        String toDecode = scannedDecode.nextLine();
        return toDecode;
    }

    /**
     * Function showing a simple text asking user whether he wants to encode, decode or both
     */
    public void showMenu()
    {
        System.out.println("Do you want to\n1. Encode\n2. Decode\n3. Both:");
    }

    /**
     * Function printing exception's error message
     *
     * @param e Exception needing its error message printed
     */
    public void printErrorMsg(Exception e)
    {
        System.err.println(e.getMessage());
    }
}
