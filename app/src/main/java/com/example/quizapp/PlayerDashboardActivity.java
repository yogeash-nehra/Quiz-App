package com.example.quizapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlayerDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOngoing, recyclerViewUpcoming, recyclerViewPast;
    private TournamentAdapter ongoingAdapter, upcomingAdapter, pastAdapter;
    private FirebaseFirestore db;
    private List<Tournament> ongoingTournaments, upcomingTournaments, pastTournaments;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_dashboard);

        db = FirebaseFirestore.getInstance();

        recyclerViewOngoing = findViewById(R.id.recyclerViewOngoing);
        recyclerViewUpcoming = findViewById(R.id.recyclerViewUpcoming);
        recyclerViewPast = findViewById(R.id.recyclerViewPast);

        recyclerViewOngoing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPast.setLayoutManager(new LinearLayoutManager(this));

        ongoingTournaments = new ArrayList<>();
        upcomingTournaments = new ArrayList<>();
        pastTournaments = new ArrayList<>();

        ongoingAdapter = new TournamentAdapter(ongoingTournaments, "ongoing", this);
        upcomingAdapter = new TournamentAdapter(upcomingTournaments, "upcoming", this);
        pastAdapter = new TournamentAdapter(pastTournaments, "past", this);

        recyclerViewOngoing.setAdapter(ongoingAdapter);
        recyclerViewUpcoming.setAdapter(upcomingAdapter);
        recyclerViewPast.setAdapter(pastAdapter);

        loadTournaments();
    }

    private void loadTournaments() {
        db.collection("tournaments").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Date currentDate = new Date();
                ongoingTournaments.clear();
                upcomingTournaments.clear();
                pastTournaments.clear();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Tournament tournament = document.toObject(Tournament.class);
                    tournament.setId(document.getId());  // Set the document ID

                    try {
                        Date startDate = dateFormat.parse(tournament.getStartDate());
                        Date endDate = dateFormat.parse(tournament.getEndDate());

                        if (startDate != null && endDate != null) {
                            if (currentDate.after(startDate) && currentDate.before(endDate)) {
                                ongoingTournaments.add(tournament);
                            } else if (currentDate.before(startDate)) {
                                upcomingTournaments.add(tournament);
                            } else if (currentDate.after(endDate)) {
                                pastTournaments.add(tournament);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("PlayerDashboard", "Date parsing error", e);
                    }
                }

                ongoingAdapter.notifyDataSetChanged();
                upcomingAdapter.notifyDataSetChanged();
                pastAdapter.notifyDataSetChanged();
            } else {
                Log.e("PlayerDashboard", "Error getting documents", task.getException());
            }
        });
    }
}
