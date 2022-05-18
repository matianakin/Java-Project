package pl.polsl.grabowski.mateusz.model;
import java.util.*;

/**
 * Class containing all of the variables as well as methods responsible for checking and actually encoding/decoding the text
 *
 * @author Mateusz Grabowski
 * @version 3.0
 */
public class Model {

    /**
     * The encryption key. Initially empty, later given values using method setKey
     */
    private final List<List<Integer>> key = new ArrayList<List<Integer>>(96);

    /**
     * Enumerated type designed to better show user's desicion whether to encode, decode or both
     *
     * @author Mateusz Grabowski
     * @version 1.2
     */
    public enum EnumChoice
    {

        /**
         * the value of enumChoice showing the user wants only to encrypt, it corresponds to choice value of 1
         */
        ENCRYPT,

        /**
         * the value of enumChoice showing the user wants only to decrypt, it corresponds to choice value of 2
         */
        DECRYPT,

        /**
         * the value of enumChoice showing the user wants to encrypt and decrypt, it corresponds to choice value of 3
         */
        BOTH
    }


    /**
     * The choice whether user wants to encode, decode or both, given either through command line or scanner changed from int to enumerated enumChoice value
     */
    private EnumChoice choose;

    /**
     * The seed for key generation, given either through command line or scanner
     */
    private int seed;

    /**
     * The choice whether user wants to encode, decode or both, given either through command line or scanner
     */
    private int choice;

    /**
     * The text to encode or decode, given either through command line or scanner
     */
    private String text;

    /**
     * Boolean used in {@link pl.polsl.grabowski.mateusz.controller.Controller} to check a loop
     */
    private boolean noError = true;

    /**
     * Boolean used in {@link pl.polsl.grabowski.mateusz.controller.Controller} to check a loop
     */
    private boolean fromCL = true;

    /**
     * Takes a value from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets its enum counterpart as choose
     *
     * @param choiceGiven the value given
     */
    public void setChoose(int choiceGiven)
    {
        EnumChoice[] choices = EnumChoice.values();
        choose = choices[choiceGiven];
    }

    /**
     * Takes a value from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets it as the noError
     *
     * @param boolGiven the value given
     */
    public void setNoError(boolean boolGiven)
    {
        noError=boolGiven;
    }

    /**
     * Takes a value from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets it as the fromCL
     *
     * @param boolGiven the value given
     */
    public void setFromCL(boolean boolGiven)
    {
        fromCL=boolGiven;
    }

    /**
     * Takes a value from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets it as the seed
     *
     * @param seedGiven the value given
     */
    public void setSeed(int seedGiven)
    {
        seed=seedGiven;
    }

    /**
     * Takes a value from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets it as the choice
     *
     * @param choiceGiven the value given
     */
    public void setChoice(int choiceGiven)
    {
        choice=choiceGiven;
    }

    /**
     * Takes a String from {@link pl.polsl.grabowski.mateusz.controller.Controller} and sets it as the text
     *
     * @param textGiven the String given
     */
    public void setText(String textGiven)
    {
        text=textGiven;
    }

    /**
     * Returns the value of the private field "choose"
     *
     * @return enumChoice containing the value of the private field in the {@link Model}.
     */
    public EnumChoice getChoose()
    {
        return choose;
    }

    /**
     * Returns the value of the private field "seed"
     *
     * @return int containing the value of the private field in the {@link Model}.
     */
    public int getSeed()
    {
        return seed;
    }

    /**
     * Returns the value of the private field "choice"
     *
     * @return int containing the value of the private field in the {@link Model}.
     */
    public int getChoice()
    {
        return choice;
    }

    /**
     * Returns the value of the private field "Text"
     *
     * @return String containing the value of the private field in the {@link Model}.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Returns the value of the private field "noError"
     *
     * @return boolean containing the value of the private field in the {@link Model}.
     */
    public boolean getNoError()
    {
        return noError;
    }

    /**
     * Returns the value of the private field "fromCL"
     *
     * @return boolean containing the value of the private field in the {@link Model}.
     */
    public boolean getFromCL()
    {
        return fromCL;
    }

    /**
     * Returns the value of the private field "key"
     *
     * @return List containing the value of the private field in the {@link Model}.
     */
    public List<List<Integer>> getKey()
    {
        return key;
    }

    /**
     * Function that takes the seed (from the private field in the {@link Model}) and uses it, as the name suggests, as the seed for the semi-random generation of the key.
     * It then checks if any of the values repeats and if they do, it changes them
     */
    public void setKey()
    {
        for(int i=0; i<96; i++)
        {
            List<Integer> subList;
            subList = new ArrayList<>(5);
            for(int q=0; q<5; q++)
            {
                int incr=0;
                int insert=0;
                boolean cont;
                do
                {
                    cont=false;
                    Random rand = new Random(seed+incr);
                    insert=rand.nextInt(800)+100;
                    incr++;
                    for(List<Integer> sub : key)
                    {
                        cont=sub.contains(insert);
                        if(cont)
                        {
                            break;
                        }
                    }
                    if(!cont)
                    {
                        cont=subList.contains(insert);
                    }

                }
                while(cont);
                subList.add(insert);
            }
            key.add(subList);
        }
    }

    /**
     * Function taking encrypted symbol and returning its ASCII code
     *
     * @param input The encrypted symbol
     * @return The ASCII code of the given sybmbol
     */
    private int findDecryption(String input)
    {
        int inputInt = (((int)input.charAt(0)-48)*100)+(((int)input.charAt(1)-48)*10)+(int)input.charAt(2)-48;
        int out=0;
        int i=0;
        for(var subList : key)
        {
            for(var inList : subList)
            {
                if(inputInt==inList)
                {
                    out=i+32;
                    break;
                }
            }
            i++;
        }
        return out;
    }

    /**
     * Function that takes encrypted text (from the private field in {@link Model}), decrypts it and returns it decrypted
     *
     * @return Decrypted text
     * @throws IncorrectEncryptOrDecryptTextException if the text formatting is incorect for decryption
     */
    public String decryptText () throws IncorrectEncryptOrDecryptTextException
    {
        StringBuilder output= new StringBuilder();
        for(int i=0; i<text.length(); i++)
        {
            if(((i+1)%4==0)&&(text.charAt(i)!=' '))
            {
                throw new IncorrectEncryptOrDecryptTextException("Enter text in \nXXX XXX XXX(..)\n format where X is a number");
            }
            else if(text.charAt(0)<'0'||text.charAt(0)>'9')
            {
                throw new IncorrectEncryptOrDecryptTextException("Enter text in \nXXX XXX XXX(..)\n format where X is a number");
            }
        }
        for(int i=0; i<text.length(); i++)
        {
            StringBuilder full= new StringBuilder();
            for(int q=0; q<3; q++)
            {
                full.append(text.charAt(i));
                i++;
            }
            char symbol = (char)findDecryption(full.toString());
            output.append(symbol);
        }
        return output.toString();
    }

    /**
     * Function that takes text (from the private field in {@link Model}), encrypts it and returns it encrypted
     *
     * @return Encrypted text
     * @throws IncorrectEncryptOrDecryptTextException if the text contains forbidden symbols, for example letters outside of english alphabet
     */
    public String encryptText() throws IncorrectEncryptOrDecryptTextException
    {
        StringBuilder output= new StringBuilder();
        for(int i=0; i<text.length(); i++)
        {
            final char DELETE= (char)127;
            if((text.charAt(i))< ' ' ||(text.charAt(i))> DELETE )
            {
                throw new IncorrectEncryptOrDecryptTextException("Text not supported");
            }
        }
        for(int i=0; i<text.length(); i++)
        {
            Random rand = new Random();
            int rn = rand.nextInt(5);
            int ascii = (int)text.charAt(i)-32;
            output.append(key.get(ascii).get(rn)).append(" ");
        }
        return output.toString();
    }

    /**
     * Function that checks command line arguments and saves them into appropiate variables
     *
     * @param commandLineArgs command line argument passed from main through {@link pl.polsl.grabowski.mateusz.controller.Controller}
     * @throws InvalidCLInputException if not all of the required fields are filled using command line
     */
    public void takeFromCommandLine(String[] commandLineArgs) throws InvalidCLInputException
    {
        boolean seedChange=false;
        boolean choiceChange=false;
        int i=0;
        for(String arg : commandLineArgs)
        {
            if(i==commandLineArgs.length)
            {
                break;
            }
            if((!(arg.equals(commandLineArgs[i]))))
            {
                continue;
            }
            if(arg.equals("-s")&& commandLineArgs[i+1]!=null)
            {
                seed=(int)commandLineArgs[i+1].charAt(0)-32;
                seedChange=true;
                i++;
            }
            else if((arg.equals("-e")||arg.equals("-d")||arg.equals("-b"))&& commandLineArgs[i+1]!=null)
            {
                text="";
                switch (arg) {
                    case "-e":
                        choice =0;
                        break;
                    case "-d":
                        choice =1;
                        break;
                    case "-b":
                        choice =2;
                        break;
                    default:
                        break;
                }
                for(int j = i+1; j < commandLineArgs.length; j++)
                {
                    if(commandLineArgs[j].charAt(0) == '-')
                    {
                        break;
                    }
                    text = text + commandLineArgs[j] + " ";
                    i = j;

                }
                choiceChange=true;
            }
            i++;
        }

        if(!choiceChange||!seedChange)
        {
            throw new InvalidCLInputException("invalid command line input\n"
                    + "put a \"-s\" and then the seed number\n"
                    + "put also \"-e\", \"-d\" or \"-b\" flag to encode, decode or both and "
                    + "then the text you want to perform given operation on\n"
                    + "but now input the data manually\n\n");
        }
    }

    /**
     * Clear key.
     */
    public void destroyKey()
    {
        key.clear();
    }
}

