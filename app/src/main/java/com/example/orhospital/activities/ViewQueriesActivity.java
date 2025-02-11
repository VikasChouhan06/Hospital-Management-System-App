package com.example.orhospital.activities;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orhospital.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewQueriesActivity extends AppCompatActivity {

    private ListView listViewQueries;
    private List<String> queryList;
    private ArrayAdapter<String> adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_queries);

        listViewQueries = findViewById(R.id.listViewQueries);
        db = FirebaseFirestore.getInstance();

        queryList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, queryList);
        listViewQueries.setAdapter(adapter);

        fetchQueries();
    }

    private void fetchQueries() {
        db.collection("UserQueries") // Ensure your Firestore collection is named "UserQueries"
                .orderBy("timestamp") // Sort queries by timestamp (newest first)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    queryList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String email = document.getString("email");
                        String query = document.getString("query");

                        if (email != null && query != null) {
                            queryList.add("📧 " + email + "\n📝 " + query);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(ViewQueriesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
