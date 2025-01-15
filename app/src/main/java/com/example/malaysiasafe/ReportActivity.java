package com.example.malaysiasafe;



import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import android.widget.ListView;

import android.widget.Toast;



import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;



public class ReportActivity extends AppCompatActivity {



    private Button makeReportButton;

    private ListView reportHistoryListView;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;

    private ArrayList<Report> reportList;

    private ReportAdapter adapter;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);



        // Initialize Firebase

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/")

                .getReference("Reports")

                .child(currentUser.getUid());



        // Initialize UI elements

        makeReportButton = findViewById(R.id.makeReportButton);

        reportHistoryListView = findViewById(R.id.reportHistoryListView);



        reportList = new ArrayList<>();

        adapter = new ReportAdapter(this, reportList);

        reportHistoryListView.setAdapter(adapter);



        // Fetch report history

        fetchReportHistory();



        // Navigate to ReportFormActivity

        makeReportButton.setOnClickListener(view -> {

            Intent intent = new Intent(ReportActivity.this, ReportFormActivity.class);

            startActivity(intent);

        });

    }



    private void fetchReportHistory() {

        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reportList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Report report = snapshot.getValue(Report.class);

                    if (report != null) {

                        reportList.add(report);

                    }

                }

                adapter.notifyDataSetChanged();

            }



            @Override

            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ReportActivity.this, "Failed to fetch reports: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }

}
