package com.example.orhospital;



import com.example.orhospital.activities.RoleSelectionActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start RoleSelectionActivity
        Intent intent = new Intent(MainActivity.this, RoleSelectionActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity so it doesn't stay in the back stack
    }
}
