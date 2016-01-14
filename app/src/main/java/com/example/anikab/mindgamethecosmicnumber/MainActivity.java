/*
 * Anika Benons
 * CS193A
 * This activity implements the Cosmic Number mind game where the user inputs a number which will always lead back to
 * the number 4 by some pattern.
 */

package com.example.anikab.mindgamethecosmicnumber;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    //Mapping of numbers (as strings) to their word representation
    private HashMap<String, String> numberToWordMap;

    private static String COSMIC_NUMBER = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * This method creates the cosmic number chain that always leads back to 4. It counts the number of letters within a number and
     * then takes that amount and counts its number of letters and so on. (Ex: 12 has 6 letters; 6 has 3 letters; 3 has 5 letters;
     * and 5 has 4)
     */
    public void createCosmicNumberChain(View view){
        EditText number_input = (EditText) findViewById(R.id.numberInput);
        String number = number_input.getText().toString();

        //Warning message pops up if a number outside the specified range is entered, no number was entered, or a number like 089 is entered
        if (number.isEmpty() || Integer.valueOf(number) < 0 || Integer.valueOf(number) > 150 ||
                (number.length() > 1 && number.startsWith("0"))) {
            Toast.makeText(this, "Choose a number from 0 to 150!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numberToWordMap == null) {
            numberToWordMap = new HashMap<>();
            readNumberWords();
        }

        String numberWord = numberToWordMap.get(number);
        String numberLength = String.valueOf(numberWord.length());

        TextView cosmic_chain = (TextView) findViewById(R.id.cosmicChain);
        cosmic_chain.setText(number + " is " + numberLength + ".");


        //The amount of letters in a number are counted in a continuous chain until the cosmic number 4 is reached
        while (numberLength != COSMIC_NUMBER) {
            String currentText = cosmic_chain.getText().toString();

            String newNumberLength = String.valueOf(numberToWordMap.get(numberLength).length());

            cosmic_chain.setText(currentText + " " + numberLength + " is " + newNumberLength + ".");

            numberLength = newNumberLength;
        }

        cosmic_chain.setText(cosmic_chain.getText() + " 4 is the cosmic number.");
    }

    /*
     * This method reads a file and creates a mapping numbers to their word form (i.e. 8 -> eight)
     */
    private void readNumberWords() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.numberwords));

        //Mapping of a number to its word form is created
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] fileLine = line.split(":");
            numberToWordMap.put(fileLine[0], fileLine[1]);
        }
    }

    /*
     * This method displays a pop up that gives a hint for the mind game
     */
    public void giveHint(View view) {
        Toast.makeText(this, "Think of the numbers in their word form \n(I.e. 5 = five)", Toast.LENGTH_LONG).show();
    }

    /*
     * This method displays a pop up that basically gives the answer to the mind game
     */
    public void giveUp(View view) {
        Button giveUpButton = (Button) findViewById(R.id.giveUpButton);
        giveUpButton.setBackgroundColor(Color.GREEN);
        Toast.makeText(this, "You're thinking too hard! It has to do with the number of letters in the number", Toast.LENGTH_LONG).show();
    }
}
