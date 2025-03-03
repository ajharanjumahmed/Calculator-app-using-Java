package com.mycompany.calculatorapp;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    JFrame frame = new JFrame("Calculator"); //making the main frame
    JLabel displayLabel = new JLabel(); //making a label for display
    JPanel displayPanel = new JPanel(); //making a panel to kepp display label
    JPanel buttonsPanel = new JPanel(); //making a buttons panel to keep the buttons
    
    Color customLightGray = new Color(212, 212, 210); // setting custom colors
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);
    
    String[] buttonValues = { // keeping the buttons values in a array of string
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "=", "%", "√"}; // keeping the operators in a separate array of string 
    String[] topSymbols = {"AC", "+/-"}; // keeping the special buttons in a separate array of string
    
    String A = "0"; //variable A to store first input
    String operator = null; // variable operator to keep track of operator
    String B = null; // variable B to store second input
    
    Calculator()
    {
        frame.setSize(360, 540); //setting the size of the main frame
        frame.setResizable(false); //disabling the resizing of the main frame
        frame.setLocationRelativeTo(null); //frame apppear at the centre of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close frame on clicking cross
        frame.setLayout(new BorderLayout()); //giving border layout
        frame.setVisible(true); //making the main frame visible
        
        displayLabel.setBackground(customBlack); //setting background color of the label
        displayLabel.setForeground(Color.white); //setting the foreground or font color
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80)); //setting font, font weight and size
        displayLabel.setHorizontalAlignment(JLabel.RIGHT); // alligning the writing of label to right
        displayLabel.setOpaque(true); //setting colour to every portion of the label making opacity 100%
        displayLabel.setText("0"); // setting default text as 0
        
        displayPanel.setLayout(new BorderLayout()); // giving border layout to display panel
        displayPanel.add(displayLabel); //adding display label to display panel
        frame.add(displayPanel, BorderLayout.NORTH); // adding display panel to main frame and using north to keep the panel in top
        
        
        buttonsPanel.setLayout(new GridLayout(5,4)); // setting 5/4 grid for buttons panel below display panel
        buttonsPanel.setBackground(customBlack); //setting background color of butttons panel
        frame.add(buttonsPanel); //adding buttons panel to the main frame
        
        for(int i=0; i<buttonValues.length; i++) // loop to read buttons name and add it to the buttons panel
        {
            JButton button = new JButton(); //making a button
            String buttonName = buttonValues[i]; //reading the button's name from the array of string to a variable
            button.setFont(new Font("Arial", Font.PLAIN, 30)); //setting the font properties of button name
            button.setText(buttonName); //setting the button name
            button.setFocusable(false); //removing the border around the button name when pressed
            button.setBorder(new LineBorder(customBlack)); //sets the border color of buttons from default blue to custom black
            if(Arrays.asList(topSymbols).contains(buttonName)) //checks if the button name is available in the list of top symbols
            {
                button.setBackground(customLightGray); 
                button.setForeground(customBlack);
            }
            else if(Arrays.asList(rightSymbols).contains(buttonName)) //checks if the button name is available in the list of right symbols
            {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }
            else // for all digits
            {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button); //adding the button to the buttons Panel
            
            button.addActionListener(new ActionListener(){ //making action listener for the buttons
                public void actionPerformed(ActionEvent e){ //the action performed is e
                    JButton button = (JButton) e.getSource(); //the source of action will be a button
                    String buttonName = button.getText(); // getting the button name of the clicked button
                    if(Arrays.asList(rightSymbols).contains(buttonName)){ //checks if the clicked button is an element of rightSymbols?
                        if("÷×-+%".contains(buttonName)) //if the clicked button is an operator 
                        {
                            if(operator == null) //checking if the operator is null or not to prevent multiple input of A for changing operator
                            {
                                operator = buttonName;
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                            }
                            operator = buttonName; // changes the operator only without taking input of A again
                        }
                        else if(buttonName == "√") // checks if the button is square root
                        {
                            double numValue = Double.parseDouble(displayLabel.getText()); //convert the display label string to a double type numValue
                            displayLabel.setText(removeZeroDecimal(Math.pow(numValue, 0.5))); //displaying the square root of the number in output
                        }
                        else if(buttonName == "=") //checks if the button is equals to
                        {
                            B = displayLabel.getText(); //store the display label that was displayed while clicking "=" to variable B
                            if(operator=="÷") //if the operator is divide
                            {
                                double C = Double.parseDouble(A); //convert string A to double type C
                                double D = Double.parseDouble(B); ////convert string B to double type D
                                displayLabel.setText(removeZeroDecimal(C/D)); //display the answer to the display label
                            }
                            else if(operator=="×") // same as divide operator
                            {
                                double C = Double.parseDouble(A);
                                double D = Double.parseDouble(B);
                                displayLabel.setText(removeZeroDecimal(C*D));
                            }
                            else if(operator=="+") // same as divide operator
                            {
                                double C = Double.parseDouble(A);
                                double D = Double.parseDouble(B);
                                displayLabel.setText(removeZeroDecimal(C+D));
                            }
                            else if(operator=="-") // same as divide operator
                            {
                                double C = Double.parseDouble(A);
                                double D = Double.parseDouble(B);
                                displayLabel.setText(removeZeroDecimal(C-D));
                            }
                            else if(operator=="%") // same as divide operator
                            {
                                double C = Double.parseDouble(A);
                                double D = Double.parseDouble(B);
                                displayLabel.setText(removeZeroDecimal(C*(D/100)));
                            }
                            clearAll(); //setting the values of A, B and operator to initial or default values
                        }
                    }
                    else if(Arrays.asList(topSymbols).contains(buttonName)) //checks if the clicked button is an element of the topSymbols?
                    {
                        if(buttonName == "AC") //if the clicked button is "AC"
                        {
                            clearAll(); // sets the default values to the variables used for calculating
                            displayLabel.setText("0"); // sets the display level to 0
                        }
                        else if(buttonName == "+/-") //if the clicked button is "+/-"
                        {
                            double numDisplay = Double.parseDouble(displayLabel.getText()); // reads the string from display label as a double and stores it in numDisplay
                            numDisplay *= -1; // operation
                            displayLabel.setText(removeZeroDecimal(numDisplay)); // displays the output of numdisplay to the display label by checking whether it should contain decimal points or not using removeZeroDecimal function
                        }
                    }
                    else // for number buttons and decimal point
                    {
                        if(buttonName == ".") // if the clicked button is a decimal point
                        {
                            if(!displayLabel.getText().contains(buttonName)) //checks if the display label contains a decimal point or not, if it doesn't then a decimal point can be added
                            {
                                displayLabel.setText(displayLabel.getText()+buttonName); // concatenates the decimal point with display label text
                            }
                        }
                        else if("0123456789".contains(buttonName)) // if the clicked button is a number
                        {
                            if(displayLabel.getText() == "0") //if 0 is present in the display label(starting point)
                            {
                                displayLabel.setText(buttonName); // sets the display label to clicked button name
                            }
                            else //if any other number is present and not 0
                            {
                                displayLabel.setText(displayLabel.getText() + buttonName); // concatenates the clicked button name with the existing number in display label
                            }
                        }
                    }
                }
            });
        }
        
        
    }
    void clearAll() // setting the values of the variables to default value or initial value
    {
        A = "0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double numDisplay) // check if the number is whole or not and remove zero after decimal for whole numbers
    {
        if(numDisplay%1==0) // checks if the double number is whole or not
        {
            return Integer.toString((int) numDisplay); //returns the string from an integer by typecasting and hence the decimal is removed
        }
        return Double.toString(numDisplay); // returns the string from a double as it is not a whole number
    }
}