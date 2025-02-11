package com.example.orhospital.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity {
    private EditText editTextPatientName, editTextSymptoms;
    private Spinner spinnerDoctor;
    private Button buttonSelectDate, buttonSelectTime, buttonBookAppointment;
    private TextView textViewSelectedDate, textViewSelectedTime;
    private FirebaseFirestore db;

    private String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Initialize UI components
        editTextPatientName = findViewById(R.id.editTextPatientName);
        editTextSymptoms = findViewById(R.id.editTextSymptoms);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSelectTime = findViewById(R.id.buttonSelectTime);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        textViewSelectedTime = findViewById(R.id.textViewSelectedTime);
        buttonBookAppointment = findViewById(R.id.buttonBookAppointment);

        db = FirebaseFirestore.getInstance();

        // Fetch doctor data from Firestore
        fetchDoctors();

        buttonSelectDate.setOnClickListener(v -> selectDate());
        buttonSelectTime.setOnClickListener(v -> selectTime());
        buttonBookAppointment.setOnClickListener(v -> bookAppointment());
    }

    private void fetchDoctors() {
        db.collection("Doctors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> doctorNames = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String doctorName = document.getString("name");
                            String specialization = document.getString("specialization");
                            doctorNames.add(doctorName + " (" + specialization + ")");
                        }
                        populateDoctorSpinner(doctorNames);
                    } else {
                        Toast.makeText(BookAppointmentActivity.this, "Error getting doctors.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateDoctorSpinner(List<String> doctorNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);
    }

    private void selectDate() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            // Format selected date as "dd/mm/yyyy"
            selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
            textViewSelectedDate.setText("Selected Date: " + selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void selectTime() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            // Format selected time as "hh:mm"
            selectedTime = selectedHour + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute);
            textViewSelectedTime.setText("Selected Time: " + selectedTime);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void bookAppointment() {
        String patientName = editTextPatientName.getText().toString().trim();
        String selectedDoctor = spinnerDoctor.getSelectedItem().toString();
        String symptoms = editTextSymptoms.getText().toString().trim();

        if (patientName.isEmpty() || selectedDoctor.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare appointment data
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("patientName", patientName);
        appointment.put("doctor", selectedDoctor);
        appointment.put("date", selectedDate);
        appointment.put("time", selectedTime);
        appointment.put("symptoms", symptoms.isEmpty() ? "Not Specified" : symptoms);

        // Store in Firebase Firestore
        db.collection("Appointments").add(appointment)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Appointment Booked Successfully!", Toast.LENGTH_SHORT).show();

                    // Reset fields
                    editTextPatientName.setText("");
                    editTextSymptoms.setText("");
                    textViewSelectedDate.setText("Select Date");
                    textViewSelectedTime.setText("Select Time");
                    selectedDate = "";
                    selectedTime = "";
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to Book Appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
