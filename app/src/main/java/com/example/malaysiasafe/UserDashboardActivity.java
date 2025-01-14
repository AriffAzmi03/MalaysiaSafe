package com.example.malaysiasafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboardActivity extends AppCompatActivity {

    private GridView dashboardGrid;
    private Button logoutButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        auth = FirebaseAuth.getInstance();

        // Initialize GridView
        dashboardGrid = findViewById(R.id.dashboardGrid);

        // Initialize Logout Button
        logoutButton = findViewById(R.id.userLogoutButton);

        // Set up the adapter
        String[] gridItems = {"Profile", "Multi Hazard Alert", "Maps", "Information", "Emergency", "Report"};
        int[] gridIcons = {
                R.drawable.ic_profile, // Replace with your actual drawable
                R.drawable.ic_hazard,   // Replace with your actual drawable
                R.drawable.ic_map,    // Replace with your actual drawable
                R.drawable.ic_info,    // Replace with your actual drawable
                R.drawable.ic_emergency, // Replace with your actual drawable
                R.drawable.ic_report   // Replace with your actual drawable
        };

        DashboardAdapter adapter = new DashboardAdapter(this, gridItems, gridIcons);
        dashboardGrid.setAdapter(adapter);

        // Handle GridView item clicks
        dashboardGrid.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String selectedItem = gridItems[position];
            Toast.makeText(UserDashboardActivity.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

            // Add navigation logic here based on the clicked item
            switch (position) {
                case 0: // Profile
                    // Navigate to ProfileActivity
                    break;
                case 1: // Multi Hazard Alert
                    // Navigate to MultiHazardAlertActivity
                    break;
                case 2: // Maps
                    // Navigate to MapsActivity
                    Intent mapIntent = new Intent(UserDashboardActivity.this, MapsActivity.class);
                    startActivity(mapIntent);
                    break;
                case 3: // Information
                    // Navigate to InformationActivity
                    Intent offlineIntent = new Intent(UserDashboardActivity.this, OfflineModeActivity.class);
                    startActivity(offlineIntent);
                    break;
                case 4: // Emergency
                    // Navigate to EmergencyActivity
                    break;
                case 5: // Report
                    // Navigate to ReportActivity
                    break;
                default:
                    break;
            }
        });

        // Handle Logout Button Click
        logoutButton.setOnClickListener(view -> {
            auth.signOut();
            Toast.makeText(UserDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect to LoginActivity
            Intent intent = new Intent(UserDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the dashboard activity
        });
    }
}
