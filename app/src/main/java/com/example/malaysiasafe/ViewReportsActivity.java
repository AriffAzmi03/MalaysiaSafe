package com.example.malaysiasafe;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewReportsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportAdapterAdmin reportAdapterAdmin;
    private DatabaseReference reportsRef;
    private List<Report> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports); // Updated layout file

        recyclerView = findViewById(R.id.reportsRecyclerView); // Updated ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager
        reportList = new ArrayList<>();
        reportAdapterAdmin = new ReportAdapterAdmin(this, reportList);
        recyclerView.setAdapter(reportAdapterAdmin);

        // Initialize Firebase reference
        reportsRef = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Reports");

        // Load reports
        loadReports();
    }

    private void loadReports() {
        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reportList.clear(); // Clear the list before adding new data
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot reportSnapshot : userSnapshot.getChildren()) {
                        Report report = reportSnapshot.getValue(Report.class);
                        if (report != null) {
                            reportList.add(report); // Add to the list
                        }
                    }
                }
                reportAdapterAdmin.notifyDataSetChanged(); // Notify the adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewReportsActivity.this, "Failed to load reports: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
