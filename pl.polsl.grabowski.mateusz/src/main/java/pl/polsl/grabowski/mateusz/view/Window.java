package pl.polsl.grabowski.mateusz.view;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.NumberFormatter;

/**
 * Class that is responsible for displaying the GUI
 *
 * @deprecated
 * @author Mateusz Grabowski
 * @version 1.1
 */
public class Window extends JFrame {

    /**
     * Window initializator running a function that starts all of its components
     */
    public Window()
    {
        initComponents();
    }

    /**
     * Listener function for the menu item no 1, displaying a popup window with instruction for a program
     * @param d action event signal triggered by a press of the menu item
     */
    private void menuItem1ActionPerformed(ActionEvent d)
    {
        JOptionPane.showMessageDialog(frame,
                "Put the text to be encrypted or decrypted in the first field. \n" +
                        "In the second put your encryption seed. \n" +
                        "From the combo box choose what operation you want to perform\n" +
                        "Then click the button to perform this operation", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Listener function for the menu item no 2, printing the result window into a txt file
     * @param f action event signal triggered by a press of the menu item
     */
    private void menuItem2ActionPerformed(ActionEvent f)
    {
        String date = java.util.Calendar.getInstance().getTime() + "_Result.txt";
        String date2 = dateTextCleanup(date);
        String out = textResult.getText();
        boolean caught =false;
        try (PrintWriter printer = new PrintWriter(date2)) {
            printer.println(out);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            caught=true;
        }
        if(!caught)
        {
            JOptionPane.showMessageDialog(frame,
                    "The result has been saved to "+date2+" file", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Function removing spaces and colons from the date text in order to use it in file name
     *
     * @param date the text file containing date
     * @return the modified text file
     */
    private String dateTextCleanup(String date) {
        StringBuilder date2= new StringBuilder();
        for(int i=0; i<date.length(); i++)
        {
            if(date.charAt(i)==' '||(date.charAt(i)==':'&&i>2))
            {
                date2.append("_");
            }
            else
            {
                date2.append(date.charAt(i));
            }
        }
        return date2.toString();
    }

    /**
     * Listener function for the menu item no 3, putting history table into a txt file
     * @param x action event signal triggered by a press of the menu item
     */
    private void menuItem3ActionPerformed(ActionEvent x)
    {
        {
            String date = java.util.Calendar.getInstance().getTime() + "_History.txt";
            String date2 = dateTextCleanup(date);
            StringBuilder out = new StringBuilder();
            boolean caught = false;
            for(int i=0; i<array.getRowCount(); i++)
            {

                for(int j=0; j<array.getColumnCount(); j++)
                {
                    out.append(array.getValueAt(i, j)).append(" ");
                }
                out.append("\n");

            }
            try (PrintWriter printer = new PrintWriter(date2)) {
                printer.println(out);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                caught=true;
            }
            if(!caught)
            {
                JOptionPane.showMessageDialog(frame,
                        "The result has been saved to "+date2+" file", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    /**
     * Listener function for the menu item no 4, clearing the history table
     * @param g action event signal triggered by a press of the menu item
     */
    private void menuItem4ActionPerformed(ActionEvent g)
    {
        for(int i=array.getRowCount()-1; i>0; i--)
        {
            array.removeRow(i);
        }
    }

    /**
     *
     * Listener function for the button, checking if input is not empty, sending signal to controller and adding a row to the history table
     * @param e action event signal triggered by a press of the button
     */
    private void button1ActionPerformed(ActionEvent e)
    {
        if(textFieldSeed.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(frame,
                    "Please provide a number as a seed",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(textFieldData.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(frame,
                    "Please provide a text to perform an operation on",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            synchronized (button1) {
                button1.notify();
            }
            waitForWaiter();
            fillTable();
        }
    }

    /**
     * Function waiting for the button to be clicked.
     */
    public void buttonClicked()
    {
        synchronized (button1)
        {
            try
            {
                button1.wait();
            }
            catch (InterruptedException ignored)
            {

            }
        }
    }

    /**
     * Wait for waiter.
     */
    public void waitForWaiter()
    {
        synchronized (waiter)
        {
            try
            {
                waiter.wait();
            }
            catch (InterruptedException ignored)
            {

            }
        }
    }

    /**
     * Notify waiter.
     */
    public void notifyWaiter()
    {
        synchronized (waiter)
        {
            waiter.notify();
        }
    }

    /**
     *
     * The frame - window
     */
    private JFrame frame;

    /**
     *
     * Function preparing all elements of the window
     */
    private void initComponents() {
        tabsPane = new JTabbedPane();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        labelData = new JLabel();
        textFieldData = new JTextField();
        textFieldData.setText("Example of a text");
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        textFieldSeed = new JFormattedTextField(formatter);
        String[] options ={"  encode", "  decode", "  both"};
        comboBox = new JComboBox(options);
        comboBox.setSelectedIndex(0);
        emptyLabelSW = new JLabel();
        textResult = new JTextArea();
        textResult.setLineWrap(true);
        textResult.setWrapStyleWord(true);
        textResult.setEnabled(false);
        textResult.setDisabledTextColor(Color.black);
        emptyLabelSE = new JLabel();
        button1 = new JButton();
        scrollPane1 = new JScrollPane(table);
        scrollPane2 = new JScrollPane(textResult);
        menuBar = new JMenuBar();
        menu1 = new JMenu("Menu");
        menu2 = new JMenu("Save to file");

        setMinimumSize(new Dimension(640, 360));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setMinimumSize(new Dimension(640, 360));
            dialogPane.setPreferredSize(new Dimension(640, 360));
            dialogPane.setMaximumSize(new Dimension(640, 360));
            dialogPane.setLayout(new BorderLayout());

            {
                contentPanel.setLayout(new GridLayout(2, 3));

                textFieldData.setHorizontalAlignment(SwingConstants.CENTER);
                textFieldData.setToolTipText("Text to encrypt/decrypt");
                contentPanel.add(textFieldData);

                textFieldSeed.setHorizontalAlignment(SwingConstants.CENTER);
                textFieldSeed.setToolTipText("Seed");
                contentPanel.add(textFieldSeed);
                contentPanel.add(comboBox);
                contentPanel.add(emptyLabelSW);


                button1.setText("Proceed");
                button1.addActionListener(e -> button1ActionPerformed(e));
                contentPanel.add(button1);
                contentPanel.add(emptyLabelSE);
            }
            labelData.setText("Put your data here");
            labelData.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(labelData, BorderLayout.NORTH);
            labelData.setMinimumSize(new Dimension(640, 90));
            labelData.setPreferredSize(new Dimension(640, 90));
            labelData.setMaximumSize(new Dimension(640, 90));

            dialogPane.add(contentPanel, BorderLayout.CENTER);

            textResult.setText("Result");
            dialogPane.add(scrollPane2, BorderLayout.SOUTH);
            scrollPane2.setMinimumSize(new Dimension(640, 90));
            scrollPane2.setPreferredSize(new Dimension(640, 90));
            scrollPane2.setMaximumSize(new Dimension(640, 90));
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        tabsPane.add("Program", dialogPane);
        tabsPane.add("History", scrollPane1);
        array.addColumn("Date");
        array.addColumn("Text");
        array.addColumn("Seed");
        array.addColumn("Operation");
        array.addColumn("Result");
        array.addRow(new Object[]{"Date", "Text", "Seed", "Operation", "Result"});
        menu1.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu1);
        menuBar.add(menu2);

        menuItem1 = new JMenuItem("Info",
                KeyEvent.VK_T);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem1.addActionListener(d -> menuItem1ActionPerformed(d));
        menu1.add(menuItem1);

        menuItem2 = new JMenuItem("Result");
        menuItem2.addActionListener(f -> menuItem2ActionPerformed(f));
        menu2.add(menuItem2);

        menuItem3 = new JMenuItem("History");
        menuItem3.addActionListener(x -> menuItem3ActionPerformed(x));
        menu2.add(menuItem3);

        menuItem4 = new JMenuItem("Clear history");
        menuItem4.addActionListener(g -> menuItem4ActionPerformed(g));
        menu1.add(menuItem4);
    }

    /**
     * Tabbed pane being the basis of the shown window
     */
    private JTabbedPane tabsPane;

    /**
     * Contener in the window
     */
    private JPanel dialogPane;

    /**
     * Contener in the window
     */
    private JPanel contentPanel;


    /**
     * A text label on the top of the window
     */
    private JLabel labelData;


    /**
     * The text field taking the text to encode/decode or both
     */
    private JTextField textFieldData;


    /**
     * The text field taking the seed
     */
    private JFormattedTextField textFieldSeed;

    /**
     * A dropdown window with choice to encode, decode or both
     */
    private JComboBox comboBox;

    /**
     * Empty label to center other elements correctly
     */
    private JLabel emptyLabelSW;

    /**
     * A text window showing results of the operations
     */
    private JTextArea textResult;

    /**
     * Empty label to center other elements correctly
     */
    private JLabel emptyLabelSE;

    /**
     * The only button in the window. Launching the program
     */
    private JButton button1;

    /**
     * Scroll Pane used to store the history table
     */
    private JScrollPane scrollPane1;

    /**
     * Scroll Pane used to store the result text area
     */
    private JScrollPane scrollPane2;

    /**
     * Gets user choice regarding which operation is to be performed
     *
     * @return the choice
     */
    public int getChoice()
    {
        return comboBox.getSelectedIndex();
    }

    /**
     * Gets key seed
     *
     * @return the key seed
     */
    public int getKeySeed()
    {
        String text =textFieldSeed.getText();
        int ret;
        StringBuilder newText= new StringBuilder();
        for(int i=0; i<text.length(); i++)
        {
            if(text.charAt(i)>=48&&text.charAt(i)<=57)
            {
                newText.append(text.charAt(i));
            }
        }
        ret = Integer.parseInt(newText.toString());
        return ret;
    }

    /**
     * Gets text to perform an operation on
     *
     * @return the text
     */
    public String getText()
    {
        return textFieldData.getText();
    }

    /**
     * Sets result text
     *
     * @param text the text
     */
    public void setText(String text)
    {
        String newText = textResult.getText()+text;
        textResult.setText(newText);
    }

    /**
     * Cleans result text
     */
    public void cleanText()
    {
        textResult.setText("");
    }

    /**
     * The history table
     */
    private final DefaultTableModel array = new DefaultTableModel();

    /**
     * The history table in a form that can be added to the window
     */
    private final JTable table = new JTable(array);

    /**
     * Waiter object used for correct synchronization between {@link pl.polsl.grabowski.mateusz.view.Window} and {@link pl.polsl.grabowski.mateusz.controller.Controller}
     */
    private final Object waiter = new Object();

    /**
     * The menu bar used in the window
     */
    private JMenuBar menuBar;

    /**
     * The first menu in the menu bar
     */
    private JMenu menu1;

    /**
     * The second menu in the menu bar
     */
    private JMenu menu2;

    /**
     * The menu item showing info how to use the program
     */
    private JMenuItem menuItem1;

    /**
     * The menu item extracting results into a txt file
     */
    private JMenuItem menuItem2;

    /**
     * The menu item extracting history table into a txt file
     */
    private JMenuItem menuItem3;

    /**
     * The menu item clearing history table
     */
    private JMenuItem menuItem4;

    /**
     * Function used to add a row in the history table
     */
    private void fillTable()
    {
        array.addRow(new Object[]{java.util.Calendar.getInstance().getTime(), textFieldData.getText(), textFieldSeed.getText(), comboBox.getSelectedItem(), textResult.getText()});
    }

    /**
     * Function starting the entire window
     */
    public void startingWindow()
    {
        frame = new JFrame("Homophonic Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabsPane);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Prints error message in a popup window
     *
     * @param e the exception
     */
    public void printErrorMsg(Exception e)
    {
        JOptionPane.showMessageDialog(null,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }


}
