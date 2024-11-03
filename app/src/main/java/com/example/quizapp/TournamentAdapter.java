// TournamentAdapter.java
package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private List<Tournament> tournamentList;
    private String viewType;
    private Context context;

    public TournamentAdapter(List<Tournament> tournamentList, String viewType, Context context) {
        this.tournamentList = tournamentList;
        this.viewType = viewType;
        this.context = context;
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        Tournament tournament = tournamentList.get(position);
        holder.textViewTournamentName.setText(tournament.getName());
        holder.textViewCategory.setText(tournament.getCategory());
        holder.textViewDifficulty.setText(tournament.getDifficulty());

        if ("ongoing".equals(viewType)) {
            holder.buttonParticipate.setVisibility(View.VISIBLE);
            holder.buttonParticipate.setOnClickListener(v -> {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("tournamentId", tournament.getId()); // Pass the tournament ID to QuizActivity
                context.startActivity(intent);
            });
        } else {
            holder.buttonParticipate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTournamentName, textViewCategory, textViewDifficulty;
        Button buttonParticipate;

        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTournamentName = itemView.findViewById(R.id.textViewTournamentName);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDifficulty = itemView.findViewById(R.id.textViewDifficulty);
            buttonParticipate = itemView.findViewById(R.id.buttonParticipate);
        }
    }
}
