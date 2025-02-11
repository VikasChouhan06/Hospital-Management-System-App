package com.example.orhospital.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddDoctorActivity extends AppCompatActivity {

    private EditText etDoctorName, etSpecialization, etCollege, etExperience, etDoctorUsername, etDoctorPassword;
    private Button btnSubmitDoctor;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        etDoctorName = findViewById(R.id.etDoctorName);
        etSpecialization = findViewById(R.id.etSpecialization);
        etCollege = findViewById(R.id.etCollege);
        etExperience = findViewById(R.id.etExperience);
        etDoctorUsername = findViewById(R.id.etDoctorUsername);
        etDoctorPassword = findViewById(R.id.etDoctorPassword);
        btnSubmitDoctor = findViewById(R.id.btnSubmitDoctor);
        db = FirebaseFirestore.getInstance();

        btnSubmitDoctor.setOnClickListener(v -> addDoctor());
    }

    private void addDoctor() {
        String name = etDoctorName.getText().toString().trim();
        String specialization = etSpecialization.getText().toString().trim();
        String college = etCollege.getText().toString().trim();
        String experience = etExperience.getText().toString().trim();
        String username = etDoctorUsername.getText().toString().trim();
        String password = etDoctorPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(specialization)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> doctor = new HashMap<>();
        doctor.put("name", name);
        doctor.put("specialization", specialization);
        doctor.put("college", college);
        doctor.put("experience", experience);
        doctor.put("username", username);
        doctor.put("password", password);

        db.collection("Doctors").add(doctor)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Doctor added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
