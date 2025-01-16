package com.example.malaysiasafe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.util.HashMap;
public class ReportFormActivity extends AppCompatActivity {

    private Spinner locationSpinner, typeSpinner, severitySpinner;

    private EditText descriptionInput, timeInput;

    private Button submitButton, uploadImageButton;

    private ImageView imageView;

    private Uri imageUri;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;


    private static final int PICK_IMAGE_REQUEST = 1; // Request code for image picking


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_form);


        // Initialize Firebase

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Reports").child(currentUser.getUid());


        // Initialize UI elements

        locationSpinner = findViewById(R.id.location_spinner);

        typeSpinner = findViewById(R.id.type_spinner);

        severitySpinner = findViewById(R.id.severity_spinner);

        descriptionInput = findViewById(R.id.description_input);

        timeInput = findViewById(R.id.time_input);

        submitButton = findViewById(R.id.submit_button);

        uploadImageButton = findViewById(R.id.upload_image_button);

        imageView = findViewById(R.id.imageView);


        // Set up spinners with data

        setupSpinners();


        // Set up the image upload functionality

        uploadImageButton.setOnClickListener(view -> openImageChooser());


        // Submit the report

        submitButton.setOnClickListener(view -> {

            String location = locationSpinner.getSelectedItem().toString();

            String type = typeSpinner.getSelectedItem().toString();

            String severity = severitySpinner.getSelectedItem().toString();

            String description = descriptionInput.getText().toString().trim();

            String time = timeInput.getText().toString().trim();


            if (location.isEmpty() || type.isEmpty() || severity.isEmpty() || description.isEmpty() || time.isEmpty()) {

                Toast.makeText(ReportFormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

            } else {

                saveReport(location, type, severity, description, time);

            }

        });

    }


// Setup the spinners with data

    private void setupSpinners() {

        // Example data for the spinners

        String[] locations = {

                "Kuala Lumpur", "Penang", "Johor Bahru", "Melaka", "Ipoh", "Kota Kinabalu",

                "Kuching", "Shah Alam", "Putrajaya", "Seremban", "Alor Setar", "Sungai Petani"

        };


        String[] disasterTypes = {

                "Flood", "Landslide", "Haze", "Earthquake", "Forest Fire", "Storm", "Drought"

        };


        String[] severities = {

                "Low", "Medium", "High", "Critical"

        };


        // Set up adapters for each spinner

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, disasterTypes);

        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, severities);


        // Set the adapters to the spinners

        locationSpinner.setAdapter(locationAdapter);

        typeSpinner.setAdapter(typeAdapter);

        severitySpinner.setAdapter(severityAdapter);

    }


// Opens the image chooser intent to pick an image from the gallery

    private void openImageChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


// Handle the image selection result

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


// Save report data to Firebase

    private void saveReport(String location, String type, String severity, String description, String time) {

        String reportId = databaseReference.push().getKey();

        if (reportId != null) {

            HashMap<String, String> reportData = new HashMap<>();

            reportData.put("location", location);

            reportData.put("type", type);

            reportData.put("severity", severity);

            reportData.put("description", description);

            reportData.put("time", time);


            // You can add image URL or save it to Firebase Storage here if needed

            if (imageUri != null) {

                reportData.put("ImageUrl", imageUri.toString()); // Save the image URI as string

            }


            // Save report data to Firebase

            databaseReference.child(reportId).setValue(reportData).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(ReportFormActivity.this, "Report has been sent", Toast.LENGTH_SHORT).show();

                    finish(); // Return to previous activity (ReportActivity)

                } else {

                    Toast.makeText(ReportFormActivity.this, "Failed to send report", Toast.LENGTH_SHORT).show();

                }

            });

        }

    }

}


