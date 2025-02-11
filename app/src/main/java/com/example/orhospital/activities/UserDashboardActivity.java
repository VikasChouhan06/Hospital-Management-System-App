package com.example.orhospital.activities;

import com.example.orhospital.R;




import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;

public class UserDashboardActivity extends AppCompatActivity {

    private Button bookAppointmentButton, submitQueryButton, viewDoctorsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard); // Ensure this layout exists

        // Initialize buttons
        bookAppointmentButton = findViewById(R.id.bookAppointmentButton);
        submitQueryButton = findViewById(R.id.submitQueryButton);
        viewDoctorsButton = findViewById(R.id.viewDoctorsButton);

        // Redirect to BookAppointmentActivity
        bookAppointmentButton.setOnClickListener(view -> {
            startActivity(new Intent(UserDashboardActivity.this, BookAppointmentActivity.class));
        });

        // Redirect to QueryActivity
        submitQueryButton.setOnClickListener(view -> {
            startActivity(new Intent(UserDashboardActivity.this, QueryActivity.class));
        });

        // Redirect to ViewDoctorsActivity
        viewDoctorsButton.setOnClickListener(view -> {
            startActivity(new Intent(UserDashboardActivity.this, ViewDoctorsActivity.class));
        });
    }
}
