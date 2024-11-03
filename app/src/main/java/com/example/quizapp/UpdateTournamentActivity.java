package com.example.quizapp;
// UpdateTournamentActivity.java
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateTournamentActivity extends AppCompatActivity {

    private EditText editTextName, editTextStartDate, editTextEndDate;
    private Button buttonUpdate;
    private FirebaseFirestore db;
    private String tournamentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tournament);

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        tournamentId = getIntent().getStringExtra("tournamentId");

        loadTournamentDetails();

        buttonUpdate.setOnClickListener(v -> updateTournament());
    }

    private void loadTournamentDetails() {
        db.collection("tournaments").document(tournamentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        editTextName.setText(documentSnapshot.getString("name"));
                        editTextStartDate.setText(documentSnapshot.getString("startDate"));
                        editTextEndDate.setText(documentSnapshot.getString("endDate"));
                    } else {
                        Toast.makeText(UpdateTournamentActivity.this, "Tournament not found.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(UpdateTournamentActivity.this, "Failed to load tournament details.", Toast.LENGTH_SHORT).show());
    }

    private void updateTournament() {
        String name = editTextName.getText().toString().trim();
        String startDate = editTextStartDate.getText().toString().trim();
        String endDate = editTextEndDate.getText().toString().trim();

        if (!name.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", name);
            updates.put("startDate", startDate);
            updates.put("endDate", endDate);

            db.collection("tournaments").document(tournamentId).update(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UpdateTournamentActivity.this, "Tournament updated successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(UpdateTournamentActivity.this, "Failed to update tournament.", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }
    }
}

