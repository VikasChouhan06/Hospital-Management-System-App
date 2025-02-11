package com.example.orhospital.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button buttonViewUsers, buttonViewQueries, buttonManageDoctors, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize buttons
        buttonViewUsers = findViewById(R.id.buttonViewUsers);
        buttonViewQueries = findViewById(R.id.buttonViewQueries);
        buttonManageDoctors = findViewById(R.id.buttonManageDoctors);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Handle button clicks
        buttonViewUsers.setOnClickListener(v ->
                startActivity(new Intent(AdminDashboardActivity.this, ViewUsersActivity.class))
        );

        buttonViewQueries.setOnClickListener(v ->
                startActivity(new Intent(AdminDashboardActivity.this, ViewQueriesActivity.class))
        );

        buttonManageDoctors.setOnClickListener(v ->
                startActivity(new Intent(AdminDashboardActivity.this, ManageDoctorsActivity.class))
        );

        buttonLogout.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AdminLoginActivity.class));
            finish();
        });
    }
}
