package com.example.orhospital.activities;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewUsersActivity extends AppCompatActivity {

    private TextView textViewUserInfo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        textViewUserInfo = findViewById(R.id.textViewUserInfo);
        mAuth = FirebaseAuth.getInstance();

        fetchLoggedInUser();
    }

    private void fetchLoggedInUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName(); // Get name from Firebase Authentication
            String email = currentUser.getEmail();      // Get email from Firebase Authentication

            if (name == null || name.isEmpty()) {
                name = "No Name Available";  // Default message if name is not set
            }

            String userInfo = "👤 Name: " + name + "\n📧 Email: " + email;
            textViewUserInfo.setText(userInfo);
        } else {
            Toast.makeText(ViewUsersActivity.this, "No user is logged in", Toast.LENGTH_SHORT).show();
            textViewUserInfo.setText("User not logged in.");
        }
    }
}

