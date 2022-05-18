package pl.polsl.grabowski.mateusz.controller;
/**
 * Main class. It's only purpose is to initialize the {@link pl.polsl.grabowski.mateusz.controller.Controller} object
 *
 * @deprecated
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class Main {
    /**
     * Gets command line parameters and passes it to {@link pl.polsl.grabowski.mateusz.controller.Controller} while initializing it
     *
     * @param args command line parameters
     */
    public static void main(String... args) {
        Controller controller = new Controller(args);
    }
}
