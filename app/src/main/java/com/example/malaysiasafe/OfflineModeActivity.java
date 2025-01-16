package com.example.malaysiasafe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// import com.example.malaysiasafe.model.DisasterData;
import com.example.malaysiasafe.DisasterDataAdapter;

public class OfflineModeActivity extends AppCompatActivity {

    private EditText locationInput, infoInput, centerInput;
    private Button addDisasterButton, goToDashboardButton;
    private ListView disasterListView;
    private DatabaseReference disasterDataRef;
    private ArrayList<com.example.malaysiasafe.DisasterData> disasterList;
    private DisasterDataAdapter adapter;

    @SuppressLint("MissingInflatedId")
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
        goToDashboardButton = findViewById(R.id.dashboardButton);
        disasterListView = findViewById(R.id.disaster_list_view);

        // Initialize data list and adapter
        disasterList = new ArrayList<>();
        adapter = new DisasterDataAdapter(this, disasterList);
        disasterListView.setAdapter(adapter);

        // Load existing disaster data
        loadDisasterData();

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

        // Handle navigation to dashboard
        goToDashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(OfflineModeActivity.this, UserDashboardActivity.class);
            startActivity(intent);
        });
    }

    private void loadDisasterData() {
        disasterDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                disasterList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DisasterData disasterData = dataSnapshot.getValue(DisasterData.class);
                    if (disasterData != null) {
                        String id = dataSnapshot.getKey();
                        disasterData.setId(dataSnapshot.getKey());
                        disasterList.add(disasterData);
                        System.out.println("Loaded disaster data with ID: " + id);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(OfflineModeActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDisasterData(String location, String info, String center) {
        // Create a DisasterData object
        com.example.malaysiasafe.DisasterData disasterData = new com.example.malaysiasafe.DisasterData(location, info, center);

        // Push disaster data to Firebase Realtime Database
        disasterDataRef.push().setValue(disasterData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(OfflineModeActivity.this, "Disaster data added successfully!", Toast.LENGTH_SHORT).show();

                // Clear input fields
                locationInput.setText("");
                infoInput.setText("");
                centerInput.setText("");
            } else {
                Toast.makeText(OfflineModeActivity.this, "Failed to add disaster data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // public void deleteDisasterData(String id, Object newLocation, Object newInfo, Object newCenter) {
        // Access the specific child node by its ID and remove it
    public void deleteDisasterData(String id) {
        disasterDataRef.child(id).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Notify the user of successful deletion
                Toast.makeText(OfflineModeActivity.this, "Disaster data deleted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Notify the user of the failure
                Toast.makeText(OfflineModeActivity.this, "Failed to delete data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateDisasterData(String id, String newLocation, String newInfo, String newCenter) {
        // Create a map to hold the updated values
        if (id == null || id.isEmpty()) {
            Map<String, Object> updates = new HashMap<>();
        updates.put("location", newLocation);
        updates.put("info", newInfo);
        updates.put("center", newCenter);

        // Access the specific child node by its ID and update its values
        disasterDataRef.child(id).updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Notify the user of successful update
                Toast.makeText(OfflineModeActivity.this, "Disaster data updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Notify the user of the failure
                Toast.makeText(OfflineModeActivity.this, "Failed to update data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }}
}
