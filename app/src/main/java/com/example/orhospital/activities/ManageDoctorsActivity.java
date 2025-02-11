package com.example.orhospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ManageDoctorsActivity extends AppCompatActivity {

    private ListView listViewDoctors;
    private Button btnAddDoctor;
    private FirebaseFirestore db;
    private List<String> doctorList;
    private List<String> doctorIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        listViewDoctors = findViewById(R.id.listViewDoctors);
        btnAddDoctor = findViewById(R.id.btnAddDoctor);
        db = FirebaseFirestore.getInstance();

        doctorList = new ArrayList<>();
        doctorIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorList);
        listViewDoctors.setAdapter(adapter);

        fetchDoctors();

        // Navigate to AddDoctorActivity
        btnAddDoctor.setOnClickListener(v ->
                startActivity(new Intent(ManageDoctorsActivity.this, AddDoctorActivity.class))
        );

        // Navigate to EditDoctorActivity
        listViewDoctors.setOnItemClickListener((adapterView, view, position, id) -> {
            if (position >= 0 && position < doctorIds.size()) {
                String doctorId = doctorIds.get(position);
                Intent intent = new Intent(ManageDoctorsActivity.this, EditDoctorActivity.class);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error: Invalid doctor selection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDoctors(); // Refresh list when returning
    }

    private void fetchDoctors() {
        db.collection("Doctors").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    doctorList.clear();
                    doctorIds.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name");
                        String specialization = document.getString("specialization");

                        if (name != null && specialization != null) {
                            doctorList.add(name + " - " + specialization);
                            doctorIds.add(document.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
