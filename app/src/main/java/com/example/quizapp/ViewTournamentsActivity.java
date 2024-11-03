package com.example.quizapp;
// ViewTournamentsActivity.java
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewTournamentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTournaments;
    private TournamentAdapter adapter;
    private FirebaseFirestore db;
    private List<Tournament> tournamentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tournaments);

        db = FirebaseFirestore.getInstance();
        recyclerViewTournaments = findViewById(R.id.recyclerViewTournaments);
        recyclerViewTournaments.setLayoutManager(new LinearLayoutManager(this));

        tournamentList = new ArrayList<>();
        adapter = new TournamentAdapter(tournamentList, "view", this);
        recyclerViewTournaments.setAdapter(adapter);

        loadTournaments();
    }

    private void loadTournaments() {
        db.collection("tournaments").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tournamentList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Tournament tournament = document.toObject(Tournament.class);
                    tournamentList.add(tournament);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

