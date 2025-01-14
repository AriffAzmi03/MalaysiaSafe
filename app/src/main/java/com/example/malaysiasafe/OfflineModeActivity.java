package com.example.malaysiasafe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OfflineModeActivity extends AppCompatActivity {
    private TextView offlineDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);

        offlineDataView = findViewById(R.id.offlineDataView);

        // Simulate fetching pre-downloaded data
        SharedPreferences sharedPreferences = getSharedPreferences("OfflineData", MODE_PRIVATE);
        String offlineData = sharedPreferences.getString("disasterInfo", "No data available");

        offlineDataView.setText(offlineData);
    }

    // Example method to save pre-downloaded data (called during online mode)
    private void saveOfflineData(String data) {
        SharedPreferences sharedPreferences = getSharedPreferences("OfflineData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("disasterInfo", data);
        editor.apply();
    }
}
