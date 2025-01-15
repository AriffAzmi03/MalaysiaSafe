package com.example.malaysiasafe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.malaysiasafe.model.DisasterData;

public class OfflineModeActivity extends AppCompatActivity {

    private EditText locationInput, infoInput, centerInput;
    private Button addDisasterButton;
    private DatabaseReference disasterDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);

        // Initialize Firebase Realtime Database reference
        disasterDataRef = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("DisasterData");

        // Initialize UI elements
        locationInput = findViewById(R.id.location_input);
        infoInput = findViewById(R.id.info_input);
        centerInput = findViewById(R.id.center_input);
        addDisasterButton = findViewById(R.id.add_disaster_button);

        // Handle the button click to add disaster data
        addDisasterButton.setOnClickListener(view -> {
            String location = locationInput.getText().toString().trim();
            String info = infoInput.getText().toString().trim();
            String center = centerInput.getText().toString().trim();

            if (location.isEmpty() || info.isEmpty() || center.isEmpty()) {
                Toast.makeText(OfflineModeActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else {
                addDisasterData(location, info, center);
            }
        });
    }

    private void addDisasterData(String location, String info, String center) {
        // Create a DisasterData object
        DisasterData disasterData = new DisasterData(location, info, center);

        // Push disaster data to Firebase Realtime Database
        disasterDataRef.push().setValue(disasterData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(OfflineModeActivity.this, "Disaster data added successfully!", Toast.LENGTH_SHORT).show();

                // Optionally navigate back to the previous activity or clear input fields
                locationInput.setText("");
                infoInput.setText("");
                centerInput.setText("");
            } else {
                Toast.makeText(OfflineModeActivity.this, "Failed to add disaster data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
