package com.example.orhospital.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ViewDoctorsActivity extends AppCompatActivity {

    private TextView textViewDoctorList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors);

        textViewDoctorList = findViewById(R.id.textViewDoctorList);
        db = FirebaseFirestore.getInstance();

        loadAllDoctors();
    }

    private void loadAllDoctors() {
        db.collection("Doctors")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder doctorDetails = new StringBuilder();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String doctorName = document.getString("name");
                            String specialization = document.getString("specialization");
                            String experience = document.getString("experience");
                            String college = document.getString("college");

                            // Check if data exists before appending
                            if (doctorName != null && specialization != null && experience != null && college != null) {
                                doctorDetails.append("Dr. ").append(doctorName).append("\n")
                                        .append("Specialization: ").append(specialization).append("\n")
                                        .append("Experience: ").append(experience).append(" years\n")
                                        .append("College: ").append(college).append("\n\n");
                            }
                        }

                        if (doctorDetails.length() > 0) {
                            textViewDoctorList.setText(doctorDetails.toString());
                        } else {
                            textViewDoctorList.setText("No doctors available.");
                        }
                    } else {
                        textViewDoctorList.setText("No doctors available.");
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load doctors", Toast.LENGTH_SHORT).show());
    }
}
