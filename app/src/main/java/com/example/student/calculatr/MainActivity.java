package com.example.student.calculatr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //These global variables control the live inputs and those stored in memory, the most recent arithmetic operator.

    public static final String CLEARED_DISPLAY_STRING = "0";
    public String currentDisplayString = "0";
    public double currentDisplayDouble = 0;
    public double previousDisplayDouble = 0;
    public double mostRecentInputDouble = 0;
    public boolean firstNumberBoolean = true;
    public boolean negativeNumberBoolean = false;
    public String arithmeticOperatorString;

    public TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayView = (TextView) findViewById(R.id.displayView);
    }

    public void clearDisplay(View inputButton)
    {
        //This function is called when the user presses the "Clear" button.
        //It resets all globabl variables to their default state, and clears the display
        currentDisplayString = CLEARED_DISPLAY_STRING;
        firstNumberBoolean = true;
        negativeNumberBoolean = false;
        currentDisplayDouble = 0;
        previousDisplayDouble = 0;
        arithmeticOperatorString = "";
        mostRecentInputDouble = 0;
        updateDisplay();
    }

    public void numberInputHandler(View inputButton)
    {
        //This function handles the input of any numbers.  Whenever a number is pressed on the numpad,
        //this function will determine the numerical value of that button, and add it to the display.
        //It also has logic to prevent leading zeroes when inputing numbers
        Button input = (Button) inputButton;
        if (firstNumberBoolean==true)
        {
            currentDisplayString = "";
            firstNumberBoolean = false;
        }


        currentDisplayString += input.getText();
        mostRecentInputDouble = Double.parseDouble(currentDisplayString);
        updateDisplay();

    }

    public void eraseCharacter(View inputButton)
    {
        //This function handles the erase button.  When it is pressed, this function will erase the last number on the display.
        //When the last number is erased, the display is reset to 0
        if (currentDisplayString.length() > 1)
        {
            currentDisplayString = currentDisplayString.substring(0, currentDisplayString.length()-1);
        }
        else
        {
            currentDisplayString = "0";
            firstNumberBoolean = true;
        }
        mostRecentInputDouble = Double.parseDouble(currentDisplayString);
        updateDisplay();
    }

    public void decimalInputHandler(View inputButton)
    {
        //This function handles the decimal button.  It ensures that there can only be one decimal on the display at any time.
        //If there is a decimal, it will prevent the addition of a second one
        Button input = (Button) inputButton;
        if (!currentDisplayString.contains("."))
        {
            currentDisplayString += input.getText();
            firstNumberBoolean = false;
        }
        mostRecentInputDouble = Double.parseDouble(currentDisplayString);
        updateDisplay();
    }

    public void negativeInputHandler(View inputButton)
    {
        //This function handles the negative/positive button.  If the number in the display is positive, it will add a minus symbol infront of it.
        //If the number is already negative, it will remove the minus symbol

        //If there is no number on the display, it will not input a minus symbol
        if (firstNumberBoolean == false)
        {
            //If the number displayed is positive
            if (negativeNumberBoolean == false)
            {
                currentDisplayString = "-" + currentDisplayString;
                negativeNumberBoolean = true;
            }

            //If the number is already negative
            else
            {
                currentDisplayString = currentDisplayString.replaceAll("-", "");
                negativeNumberBoolean = false;
            }

            mostRecentInputDouble = Double.parseDouble(currentDisplayString);
        }
        updateDisplay();
    }

    public void operatorInputHandler(View inputButton)
    {
        //This function handles the arithmetic operators, and the equals button.
        //It has separate logic for the operators and for the equals button.


        Button input = (Button) inputButton;
        String buttonText = (String) input.getTag();

        //If the input is not and equals sign, it is either +, -, X, /
        if (!buttonText.equals("equals"))
        {
            previousDisplayDouble = Double.parseDouble(currentDisplayString);
            arithmeticOperatorString = buttonText;
            negativeNumberBoolean = false;
        }

        //If the input is an equals sign
        else
        {
            currentDisplayDouble = Double.parseDouble(currentDisplayString);

            switch (arithmeticOperatorString) {

                    //If the most recent arithmetic operator is division
                case "division":
                    currentDisplayDouble = (previousDisplayDouble/mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);

                    //If the most recent arithmetic operator is multiplication
                case "multiplication":
                    currentDisplayDouble = (previousDisplayDouble * mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);

                    //If the most recent arithmetic operator is addition
                case "addition":
                    currentDisplayDouble = (previousDisplayDouble+mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);

                    //If the most recent arithmetic operator is subtraction
                case "subtraction":
                    currentDisplayDouble = (previousDisplayDouble - mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);
                default:
                    displayView.setText("Invalid input: " + arithmeticOperatorString);
            }
            updateDisplay();
        }
        firstNumberBoolean = true;
    }

    public void updateDisplay()
    {
        //This function updates the display with the contents of currentDisplayString
        //It is called every time the user enters a number, clears the display, deletes a digit, or presses the equals button.
        displayView.setText(currentDisplayString);
    }


}
