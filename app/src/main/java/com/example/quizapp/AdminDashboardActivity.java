package com.example.quizapp;

// AdminDashboardActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button buttonCreateTournament, buttonViewTournaments, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        buttonCreateTournament = findViewById(R.id.buttonCreateTournament);
        buttonViewTournaments = findViewById(R.id.buttonViewTournaments);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonCreateTournament.setOnClickListener(v -> startActivity(new Intent(AdminDashboardActivity.this, CreateTournamentActivity.class)));
        buttonViewTournaments.setOnClickListener(v -> startActivity(new Intent(AdminDashboardActivity.this, ViewTournamentsActivity.class)));

        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}

