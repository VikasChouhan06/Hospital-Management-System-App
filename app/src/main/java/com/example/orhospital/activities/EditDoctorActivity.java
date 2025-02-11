package com.example.orhospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditDoctorActivity extends AppCompatActivity {

    private EditText etEditDoctorName, etEditSpecialization, etEditCollege, etEditExperience;
    private Button btnUpdateDoctor, btnDeleteDoctor;
    private FirebaseFirestore db;
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        etEditDoctorName = findViewById(R.id.etEditDoctorName);
        etEditSpecialization = findViewById(R.id.etEditSpecialization);
        etEditCollege = findViewById(R.id.etEditCollege);
        etEditExperience = findViewById(R.id.etEditExperience);
        btnUpdateDoctor = findViewById(R.id.btnUpdateDoctor);
        btnDeleteDoctor = findViewById(R.id.btnDeleteDoctor);
        db = FirebaseFirestore.getInstance();

        // Get doctor ID from Intent
        doctorId = getIntent().getStringExtra("doctorId");

        // Load existing doctor details
        if (doctorId != null) {
            loadDoctorDetails();
        }

        btnUpdateDoctor.setOnClickListener(v -> updateDoctor());
        btnDeleteDoctor.setOnClickListener(v -> deleteDoctor());
    }

    private void loadDoctorDetails() {
        db.collection("Doctors").document(doctorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etEditDoctorName.setText(documentSnapshot.getString("name"));
                        etEditSpecialization.setText(documentSnapshot.getString("specialization"));
                        etEditCollege.setText(documentSnapshot.getString("college"));
                        etEditExperience.setText(documentSnapshot.getString("experience"));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading doctor details", Toast.LENGTH_SHORT).show());
    }

    private void updateDoctor() {
        String name = etEditDoctorName.getText().toString().trim();
        String specialization = etEditSpecialization.getText().toString().trim();
        String college = etEditCollege.getText().toString().trim();
        String experience = etEditExperience.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(specialization)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference doctorRef = db.collection("Doctors").document(doctorId);
        doctorRef.update("name", name, "specialization", specialization, "college", college, "experience", experience)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Doctor updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show());
    }

    private void deleteDoctor() {
        db.collection("Doctors").document(doctorId).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Doctor deleted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete doctor!", Toast.LENGTH_SHORT).show());
    }
}
