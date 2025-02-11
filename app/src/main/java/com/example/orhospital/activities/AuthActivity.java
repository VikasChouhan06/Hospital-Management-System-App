package com.example.orhospital.activities;




import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orhospital.R;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextName;
    private Button buttonAuth;
    private TextView toggleText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private boolean isLoginMode = true; // To switch between login & register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        buttonAuth = findViewById(R.id.buttonAuth);
        toggleText = findViewById(R.id.toggleText);
        progressBar = findViewById(R.id.progressBar);

        buttonAuth.setOnClickListener(view -> {
            if (isLoginMode) {
                loginUser();
            } else {
                registerUser();
            }
        });

        toggleText.setOnClickListener(view -> switchAuthMode());
    }

    // ✅ Function for Logging In (Direct Redirection to UserDashboardActivity)
    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                startActivity(new Intent(AuthActivity.this, UserDashboardActivity.class));
                finish(); // Close AuthActivity
            } else {
                Toast.makeText(AuthActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Function for Registering a New User
    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(AuthActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AuthActivity.this, UserDashboardActivity.class));
                finish(); // Close AuthActivity
            } else {
                Toast.makeText(AuthActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Function to Switch Between Login & Register
    private void switchAuthMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            editTextName.setVisibility(View.GONE);
            buttonAuth.setText("Login");
            toggleText.setText("Don't have an account? Register");
        } else {
            editTextName.setVisibility(View.VISIBLE);
            buttonAuth.setText("Register");
            toggleText.setText("Already have an account? Login");
        }
    }
}
