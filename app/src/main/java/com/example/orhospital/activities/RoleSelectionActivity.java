package com.example.orhospital.activities;

import com.example.orhospital.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    private Button btnUser, btnDoctor, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        btnUser = findViewById(R.id.btnUser);
        btnDoctor = findViewById(R.id.btnDoctor);
        btnAdmin = findViewById(R.id.btnAdmin);

        btnUser.setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, AuthActivity.class);
            intent.putExtra("role", "user"); // Pass role data
            startActivity(intent);
        });

        btnDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, DoctorLoginActivity.class);
            intent.putExtra("role", "doctor");
            startActivity(intent);
        });

        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, AdminLoginActivity.class);
            intent.putExtra("role", "admin");
            startActivity(intent);
        });
    }
}
