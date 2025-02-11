package com.example.orhospital.activities;



import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QueryActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextQuery;
    private Button buttonSubmitQuery;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextQuery = findViewById(R.id.editTextQuery);
        buttonSubmitQuery = findViewById(R.id.buttonSubmitQuery);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();

        buttonSubmitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuery();
            }
        });
    }

    private void submitQuery() {
        String email = editTextEmail.getText().toString().trim();
        String query = editTextQuery.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a data structure to store in Firestore
        Map<String, Object> queryData = new HashMap<>();
        queryData.put("email", email);
        queryData.put("query", query);
        queryData.put("timestamp", System.currentTimeMillis());

        // Save to Firestore
        db.collection("UserQueries").add(queryData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(QueryActivity.this, "Query Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(QueryActivity.this, "Failed to Submit Query", Toast.LENGTH_SHORT).show());
    }
}
