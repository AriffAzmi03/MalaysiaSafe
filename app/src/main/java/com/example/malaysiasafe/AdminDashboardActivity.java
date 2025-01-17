package com.example.malaysiasafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    private GridView adminDashboardGrid;
    private Button logoutButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        auth = FirebaseAuth.getInstance();

        // Initialize GridView
        adminDashboardGrid = findViewById(R.id.adminDashboardGrid);

        // Initialize Logout Button
        logoutButton = findViewById(R.id.adminLogoutButton);

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
        adminDashboardGrid.setAdapter(adapter);

        // Handle GridView item clicks
        adminDashboardGrid.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String selectedItem = gridItems[position];
            Toast.makeText(AdminDashboardActivity.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

            // Add navigation logic here based on the clicked item
            switch (position) {
                case 0: // Manage Users
                    // Navigate to ManageUsersActivity
                    break;
                case 1: // View Reports
                    // Navigate to ViewReportsActivity
                    break;
                case 2: // Settings
                    // Navigate to SettingsActivity
                    break;
                case 3: // Notifications
                    // Navigate to NotificationsActivity
                    break;
                case 4: // Statistics
                    // Navigate to StatisticsActivity
                    break;
                case 5: // Support
                    // Navigate to SupportActivity
                    Intent intent = new Intent(AdminDashboardActivity.this, ViewReportsActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });

        // Handle Logout Button Click
        logoutButton.setOnClickListener(view -> {
            auth.signOut();
            Toast.makeText(AdminDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect to LoginActivity
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the dashboard activity
        });
    }
}
