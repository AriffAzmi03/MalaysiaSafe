package com.example.malaysiasafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName, matricId, email, password;
    private Spinner roleSpinner;
    private Button registerButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://malaysiasafe-150fb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

        // Initialize UI elements
        fullName = findViewById(R.id.full_name_input);
        matricId = findViewById(R.id.matric_id_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        roleSpinner = findViewById(R.id.role_spinner);
        registerButton = findViewById(R.id.register_button);

        // Handle registration button click
        registerButton.setOnClickListener(view -> {
            String userFullName = fullName.getText().toString().trim();
            String userMatricId = matricId.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            String userRole = roleSpinner.getSelectedItem().toString();

            if (userFullName.isEmpty() || userMatricId.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(userFullName, userMatricId, userEmail, userPassword, userRole);
            }
        });
    }

    private void registerUser(String name, String matricId, String email, String password, String role) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();

                    // Save user data in Firebase Realtime Database
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("Name", name);
                    userData.put("Matric", matricId);
                    userData.put("Email", email);
                    userData.put("Role", role);

                    databaseReference.child(userId).setValue(userData).addOnCompleteListener(saveTask -> {
                        if (saveTask.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                            // Navigate to LoginActivity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to save user data: " + saveTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
