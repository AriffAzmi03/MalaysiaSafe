package com.example.malaysiasafe;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OfflineModeActivity extends AppCompatActivity {
    private TextView offlineDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);

        offlineDataView = findViewById(R.id.offlineDataView);

        // Simulate data download for testing
        simulateDataDownload();

        // Fetch and display pre-downloaded data
        String offlineData = fetchOfflineData();
        System.out.println("Offline Data: " + offlineData);
        displayOfflineData(offlineData);

        // Button to navigate to User Dashboard
        Button dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(v -> navigateToUserDashboard());
    }

    /**
     * Creates example JSON data for testing.
     * @return A JSON array of disaster-prone areas and related information.
     */
    private JSONArray createExampleJSON() {
        JSONArray dataArray = new JSONArray();
        try {
            // Create first object
            JSONObject location1 = new JSONObject();
            location1.put("location", "Area A");
            location1.put("info", "Flood-prone area");
            location1.put("center", "Center 1");
            dataArray.put(location1); // Add to array

            // Create second object
            JSONObject location2 = new JSONObject();
            location2.put("location", "Area B");
            location2.put("info", "Earthquake risk");
            location2.put("center", "Center 2");
            dataArray.put(location2); // Add to array
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataArray;
    }

    /**
     * Fetches pre-downloaded data from SharedPreferences.
     * @return The JSON string of disaster data.
     */
    private String fetchOfflineData() {
        SharedPreferences sharedPreferences = getSharedPreferences("OfflineData", MODE_PRIVATE);
        return sharedPreferences.getString("disasterInfo", "[]"); // Default to an empty array if no data
    }

    /**
     * Displays offline data in a readable format.
     * @param jsonString The JSON string containing disaster data.
     */
    private void displayOfflineData(String jsonString) {
        try {
            JSONArray dataArray = new JSONArray(jsonString); // Correctly initialize jsonArray
            StringBuilder displayText = new StringBuilder();

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String location = dataObject.optString("location", "Unknown location");
                String info = dataObject.optString("info", "No details available");
                String center = dataObject.optString("center", "No details available");

                displayText.append("Location: ").append(location).append("\n")
                        .append("Details: ").append(info).append("\n")
                        .append("Evacuation center: ").append(info).append("\n\n");
            }

            offlineDataView.setText(displayText.toString());
        } catch (JSONException e) {
            offlineDataView.setText("Error parsing offline data.");
            e.printStackTrace();
        }
    }

    /**
     * Saves pre-downloaded data for offline use.
     * This method would be called during online mode to cache data.
     * @param dataArray A JSON array of disaster-prone areas and related information.
     */
    private void saveOfflineData(JSONArray dataArray) {
        SharedPreferences sharedPreferences = getSharedPreferences("OfflineData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("disasterInfo", dataArray.toString());
        editor.apply();
    }

    /**
     * Example usage to save data (to be called in online mode).
     */
    private void simulateDataDownload() {
        try {
            JSONArray dataArray = new JSONArray();

            JSONObject location1 = new JSONObject();
            location1.put("location", "Area A");
            location1.put("info", "Flood-prone area");
            location1.put("center", "Center 1");
            dataArray.put(location1);

            JSONObject location2 = new JSONObject();
            location2.put("location", "Area B");
            location2.put("info", "Earthquake risk");
            location2.put("center", "Center 2");
            dataArray.put(location2);

            saveOfflineData(dataArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the User Dashboard activity.
     */
    private void navigateToUserDashboard() {
        Intent intent = new Intent(OfflineModeActivity.this, UserDashboardActivity.class);
        startActivity(intent);
    }
}
