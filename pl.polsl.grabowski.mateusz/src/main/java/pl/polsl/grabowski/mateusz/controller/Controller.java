package pl.polsl.grabowski.mateusz.controller;
import pl.polsl.grabowski.mateusz.model.*;
import pl.polsl.grabowski.mateusz.view.*;
/**
 * Class responsible for accurately calling {@link pl.polsl.grabowski.mateusz.model.Model} and {@link pl.polsl.grabowski.mateusz.view.Window} methods
 *
 * @deprecated
 * @author Mateusz Grabowski
 * @version 3.0
 */
public class Controller {

    /** A {@link pl.polsl.grabowski.mateusz.model.Model} object used to call its methods */
    private Model model;
    /** A {@link pl.polsl.grabowski.mateusz.view.Window} object used to call its methods */
    private Window window;


    /**
     * A {@link Controller} initializer. It takes command line arguments and passes it to controller method.
     * It also initializes the {@link pl.polsl.grabowski.mateusz.view.Window} and {@link pl.polsl.grabowski.mateusz.model.Model} objects present inside of this class
     *
     * @param args command line parameters passed from main
     */
    Controller(String[] args)
    {
        this.model = new Model();
        //this.view = new View();
        this.window= new Window();
        window.startingWindow();
        this.controller(args);
    }

    /**
     * Function that operates the whole program, deciding when to call which method from {@link pl.polsl.grabowski.mateusz.view.Window} or {@link pl.polsl.grabowski.mateusz.model.Model}
     *
     * @param args command line arguments passed from main through the {@link Controller} initializer
     */
    private void controller(String[] args)
    {

        try
        {
            model.takeFromCommandLine(args);
        }
        catch(InvalidCLInputException e)
        {
            window.printErrorMsg(e);
            model.setFromCL(false);
        }

        while (true) {
            model.setNoError(true);
            if (model.getFromCL()) {
                window.cleanText();
            }
            if (!model.getFromCL()) {
                window.buttonClicked();
                window.cleanText();
                model.setSeed(window.getKeySeed());
                model.setChoice(window.getChoice());
            }
            model.setKey();
            model.setChoose(model.getChoice());
            switch (model.getChoose()) {
                case ENCRYPT: {
                    if (!model.getFromCL()) {
                        //model.setText(view.toEncode());
                        model.setText(window.getText());
                    }
                    try {
                        //view.printString(model.encryptText());
                        window.setText(model.encryptText());
                    } catch (IncorrectEncryptOrDecryptTextException e) {
                        window.notifyWaiter();
                        window.printErrorMsg(e);
                        window.setText(e.getMessage());
                        model.setFromCL(false);
                        model.setNoError(false);
                    }
                    break;
                }
                case DECRYPT: {
                    if (!model.getFromCL()) {
                        //model.setText(view.toDecode());
                        model.setText(window.getText());
                    }
                    try {
                        //view.printString(model.decryptText());
                        window.setText(model.decryptText());
                    } catch (IncorrectEncryptOrDecryptTextException e) {
                        window.notifyWaiter();
                        window.printErrorMsg(e);
                        window.setText(e.getMessage());
                        model.setFromCL(false);
                        model.setNoError(false);
                    }

                    break;
                }
                case BOTH: {
                    if (!model.getFromCL()) {
                        //model.setText(view.toEncode());
                        model.setText(window.getText());
                    }
                    try {
                        //model.setText(model.encryptText());
                        model.setText(model.encryptText());
                    } catch (IncorrectEncryptOrDecryptTextException e) {
                        window.notifyWaiter();
                        window.printErrorMsg(e);
                        window.setText(e.getMessage());
                        model.setFromCL(false);
                        model.setNoError(false);
                        break;
                    }
                    //view.printString(model.getText());
                    window.setText(model.getText());
                    try {
                        //view.printString(model.decryptText());
                        window.setText(model.decryptText());
                    } catch (IncorrectEncryptOrDecryptTextException e) {
                        window.notifyWaiter();
                        window.printErrorMsg(e);
                        window.setText(e.getMessage());
                        model.setFromCL(false);
                        model.setNoError(false);
                    }
                    break;
                }
            }
            window.notifyWaiter();
            model.destroyKey();
            model.setFromCL(false);
        }
    }
}
