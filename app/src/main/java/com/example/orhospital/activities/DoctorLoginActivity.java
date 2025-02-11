package com.example.orhospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orhospital.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // UI Elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DoctorLoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    verifyDoctor(username, password);
                }
            }
        });
    }

    private void verifyDoctor(String username, String password) {
        db.collection("Doctors")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Get Doctor Firestore data
                            String doctorUsername = document.getString("username");
                            String doctorName = document.getString("name");
                            String doctorSpecialization = document.getString("specialization");
                            String doctorExperience = document.getString("experience");

                            // Display doctor details or handle further processing
                            Toast.makeText(DoctorLoginActivity.this, "Welcome Dr. " + doctorName, Toast.LENGTH_SHORT).show();

                            // Pass doctor details to DoctorDashboardActivity
                            Intent intent = new Intent(DoctorLoginActivity.this, DoctorDashboardActivity.class);
                            intent.putExtra("DOCTOR_USERNAME", doctorUsername); // Passing username
                            intent.putExtra("DOCTOR_NAME", doctorName);  // Passing doctorName
                            intent.putExtra("DOCTOR_SPECIALIZATION", doctorSpecialization);  // Passing specialization
                            intent.putExtra("DOCTOR_EXPERIENCE", doctorExperience);  // Passing experience
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(DoctorLoginActivity.this, "Doctor not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }}
