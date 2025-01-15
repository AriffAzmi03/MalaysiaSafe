package com.example.malaysiasafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EmergencyActivity extends AppCompatActivity {

    private StringBuilder phoneNumber; // To store the entered phone number
    private TextView phoneNumberDisplay;
    private Button clearButton;
    private Button backspaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        phoneNumber = new StringBuilder();

        // Initialize UI elements
        phoneNumberDisplay = findViewById(R.id.phoneNumberDisplay);
        clearButton = findViewById(R.id.clearButton);
        backspaceButton = findViewById(R.id.backspaceButton);

        // Set up number buttons
        setUpNumberButton(R.id.button1, "1");
        setUpNumberButton(R.id.button2, "2");
        setUpNumberButton(R.id.button3, "3");
        setUpNumberButton(R.id.button4, "4");
        setUpNumberButton(R.id.button5, "5");
        setUpNumberButton(R.id.button6, "6");
        setUpNumberButton(R.id.button7, "7");
        setUpNumberButton(R.id.button8, "8");
        setUpNumberButton(R.id.button9, "9");
        setUpNumberButton(R.id.button0, "0");
        setUpNumberButton(R.id.buttonStar, "*");
        setUpNumberButton(R.id.buttonHash, "#");

        // Predefined emergency numbers
        setUpPredefinedButton(R.id.buttonPolice, "999");
        setUpPredefinedButton(R.id.buttonFire, "994");
        setUpPredefinedButton(R.id.buttonAmbulance, "991");

        // Set up call button
        Button callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(view -> makeCall());

        // Set up clear button
        clearButton.setOnClickListener(view -> clearInput());

        // Set up backspace button
        backspaceButton.setOnClickListener(view -> deleteLastDigit());

        // Update initial state of buttons
        updateButtonStates();
    }

    private void setUpNumberButton(int buttonId, String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(view -> {
            provideHapticFeedback();
            phoneNumber.append(number);
            updateButtonStates();
        });
    }

    private void setUpPredefinedButton(int buttonId, String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(view -> {
            provideHapticFeedback();
            phoneNumber.setLength(0); // Clear existing number
            phoneNumber.append(number);
            updateButtonStates();
        });
    }

    private void makeCall() {
        if (phoneNumber.length() > 0) {
            String emergencyNumber = phoneNumber.toString();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + emergencyNumber));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInput() {
        phoneNumber.setLength(0); // Clear the phone number
        updateButtonStates();
    }

    private void deleteLastDigit() {
        if (phoneNumber.length() > 0) {
            phoneNumber.deleteCharAt(phoneNumber.length() - 1);
            updateButtonStates();
        }
    }

    private void updateButtonStates() {
        phoneNumberDisplay.setText(phoneNumber.length() > 0 ? phoneNumber.toString() : "");
        backspaceButton.setVisibility(phoneNumber.length() > 0 ? View.VISIBLE : View.GONE);
        clearButton.setVisibility(phoneNumber.length() > 0 ? View.VISIBLE : View.GONE);
    }

    private void provideHapticFeedback() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}
