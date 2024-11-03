package com.example.quizapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTournamentActivity extends AppCompatActivity {

    private EditText editTextName, editTextCategory, editTextDifficulty, editTextStartDate, editTextEndDate;
    private Button buttonCreateTournament;
    private FirebaseFirestore db;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Bind UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextDifficulty = findViewById(R.id.editTextDifficulty);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        buttonCreateTournament = findViewById(R.id.buttonCreateTournament);

        // Set up date pickers
        editTextStartDate.setOnClickListener(v -> showDatePickerDialog(editTextStartDate));
        editTextEndDate.setOnClickListener(v -> showDatePickerDialog(editTextEndDate));

        // Create tournament on button click
        buttonCreateTournament.setOnClickListener(v -> createTournament());
    }

    private void showDatePickerDialog(EditText dateField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    String date = dateFormat.format(selectedDate.getTime());
                    dateField.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void createTournament() {
        String name = editTextName.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();
        String difficulty = editTextDifficulty.getText().toString().trim();
        String startDate = editTextStartDate.getText().toString().trim();
        String endDate = editTextEndDate.getText().toString().trim();

        if (name.isEmpty() || category.isEmpty() || difficulty.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a tournament with 0 likes
        Tournament tournament = new Tournament(name, category, difficulty, startDate, endDate, 0);

        db.collection("tournaments")
                .add(tournament)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Tournament created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to create tournament. Please check your network.", Toast.LENGTH_SHORT).show());
    }
}
