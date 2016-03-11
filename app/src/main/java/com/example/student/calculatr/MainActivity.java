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

    public void clearDisplay(View v)
    {
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
        Button input = (Button) inputButton;
        if (firstNumberBoolean==true)
        {
            currentDisplayString = "";
            firstNumberBoolean = false;
        }


        currentDisplayString += input.getText();
        mostRecentInputDouble = currentDisplayDouble;
        updateDisplay();

    }

    public void eraseCharacter(View inputButton)
    {
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
        if (firstNumberBoolean == false)
        {
            if (negativeNumberBoolean == false)
            {
                currentDisplayString = "-" + currentDisplayString;
                negativeNumberBoolean = true;
            } else
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
        Button input = (Button) inputButton;
        String buttonText = (String) input.getText();
        if (buttonText.contains("="))
        {
            previousDisplayDouble = Double.parseDouble(currentDisplayString);
            arithmeticOperatorString = buttonText;
            negativeNumberBoolean = false;
        }

        else
        {
            currentDisplayDouble = Double.parseDouble(currentDisplayString);

            switch (arithmeticOperatorString) {
                case "/":
                    currentDisplayDouble = (previousDisplayDouble / mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);
                case "X":
                    currentDisplayDouble = (previousDisplayDouble * mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);
                case "+":
                    currentDisplayDouble = (previousDisplayDouble + mostRecentInputDouble);
                    currentDisplayString = String.valueOf(currentDisplayDouble);
                    previousDisplayDouble = Double.parseDouble(currentDisplayString);
                case "-":
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
        displayView.setText(currentDisplayString);
    }


}
