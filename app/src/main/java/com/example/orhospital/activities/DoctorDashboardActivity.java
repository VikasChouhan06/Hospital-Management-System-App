package com.example.orhospital.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DoctorDashboardActivity extends AppCompatActivity {

    private TextView textViewDoctorName, textViewDoctorSpecialization, textViewAppointmentsList;
    private FirebaseFirestore db;
    private String doctorUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        // Initialize TextViews
        textViewDoctorName = findViewById(R.id.textViewDoctorName);
        textViewDoctorSpecialization = findViewById(R.id.textViewDoctorSpecialization);
        textViewAppointmentsList = findViewById(R.id.textViewAppointmentsList);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Retrieve doctorUsername from intent
        doctorUsername = getIntent().getStringExtra("DOCTOR_USERNAME");

        if (doctorUsername != null) {
            loadDoctorInfo();  // This will also load appointments after getting doctor info
        } else {
            Toast.makeText(this, "Doctor Username not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Load Doctor Information
    private void loadDoctorInfo() {
        db.collection("Doctors")
                .whereEqualTo("username", doctorUsername) // Query the 'username' field
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String doctorName = document.getString("name");
                            String doctorSpecialization = document.getString("specialization");

                            // Display Doctor Information
                            if (doctorName != null && doctorSpecialization != null) {
                                textViewDoctorName.setText("Dr. " + doctorName);
                                textViewDoctorSpecialization.setText(doctorSpecialization);

                                // Now that we have the doctor's specialization, we can load appointments dynamically
                                loadAppointments(doctorName, doctorSpecialization);  // Pass doctor name and specialization
                            } else {
                                Toast.makeText(this, "Doctor details incomplete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load doctor info", Toast.LENGTH_SHORT).show());
    }

    // Load Appointments for the Doctor dynamically based on the doctor's name and specialization
    private void loadAppointments(String doctorName, String doctorSpecialization) {
        String doctorKey = doctorName + " (" + doctorSpecialization + ")"; // Format the doctor's name with specialization

        db.collection("Appointments")
                .whereEqualTo("doctor", doctorKey)  // Use the dynamic formatted doctor key
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder appointmentsText = new StringBuilder();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String patientName = document.getString("patientName");
                            String date = document.getString("date");
                            String time = document.getString("time");
                            String symptoms = document.getString("symptoms");

                            // Check if data exists before appending
                            if (patientName != null && date != null && time != null) {
                                // Build the appointment text
                                appointmentsText.append("Patient: ").append(patientName).append("\n")
                                        .append("Date: ").append(date).append("\n")
                                        .append("Time: ").append(time).append("\n")
                                        .append("Symptoms: ").append(symptoms != null ? symptoms : "N/A").append("\n\n");
                            }
                        }

                        // If there are appointments, display them; otherwise, display no appointments
                        if (appointmentsText.length() > 0) {
                            textViewAppointmentsList.setText(appointmentsText.toString());
                        } else {
                            textViewAppointmentsList.setText("No appointments available.");
                        }
                    } else {
                        textViewAppointmentsList.setText("No appointments available.");
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load appointments", Toast.LENGTH_SHORT).show());
    }
}
