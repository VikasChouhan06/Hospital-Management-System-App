package com.example.orhospital.activities;

import com.example.orhospital.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button buttonBookAppointment, buttonViewAppointments, buttonQuery, buttonLogout;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Initialize UI elements
        welcomeText = findViewById(R.id.welcomeText);
        buttonBookAppointment = findViewById(R.id.buttonBookAppointment);
        buttonViewAppointments = findViewById(R.id.buttonViewAppointments);
        buttonQuery = findViewById(R.id.buttonQuery);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Set welcome text
        if (user != null) {
            welcomeText.setText("Welcome, " + user.getEmail());
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Button click listeners
        buttonBookAppointment.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, BookAppointmentActivity.class)));

        buttonViewAppointments.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, ViewAppointmentsActivity.class)));

        buttonQuery.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, QueryActivity.class)));

        buttonLogout.setOnClickListener(view -> {
            auth.signOut();
            Toast.makeText(DashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, AuthActivity.class));
            finish();
        });
    }
}
