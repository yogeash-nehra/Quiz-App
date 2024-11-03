package com.example.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion, textViewScore;
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOption4;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList = new ArrayList<>();

        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonOption4 = findViewById(R.id.buttonOption4);

        fetchQuestionsFromOpenTDB();

        // Set listeners for answer buttons
        buttonOption1.setOnClickListener(v -> checkAnswer(buttonOption1.getText().toString()));
        buttonOption2.setOnClickListener(v -> checkAnswer(buttonOption2.getText().toString()));
        buttonOption3.setOnClickListener(v -> checkAnswer(buttonOption3.getText().toString()));
        buttonOption4.setOnClickListener(v -> checkAnswer(buttonOption4.getText().toString()));
    }

    private void fetchQuestionsFromOpenTDB() {
        OpenTDBApiService apiService = ApiClient.getApiService();
        apiService.getQuestions(10, 9, "medium", "multiple").enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questionList = response.body().getResults();
                    if (questionList.isEmpty()) {
                        Log.e("QuizActivity", "No questions found from OpenTDB");
                        Toast.makeText(QuizActivity.this, "No questions available for this quiz.", Toast.LENGTH_SHORT).show();
                    } else {
                        displayQuestion();
                    }
                } else {
                    Toast.makeText(QuizActivity.this, "Failed to load questions. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Log.e("QuizActivity", "Error fetching questions", t);
                Toast.makeText(QuizActivity.this, "Failed to load questions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            textViewQuestion.setText(currentQuestion.getQuestion());

            // Combine correct and incorrect answers, then shuffle
            List<String> answers = new ArrayList<>(currentQuestion.getIncorrect_answers());
            answers.add(currentQuestion.getCorrect_answer());
            Collections.shuffle(answers);

            // Set the answer choices to buttons
            buttonOption1.setText(answers.get(0));
            buttonOption2.setText(answers.get(1));
            buttonOption3.setText(answers.get(2));
            buttonOption4.setText(answers.get(3));
        } else {
            showFinalScore();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        if (currentQuestion.getCorrect_answer().equals(selectedAnswer)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            score++;  // Increment score for a correct answer
            textViewScore.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private void showFinalScore() {
        String feedbackMessage;

        // Provide feedback based on the score
        if (score == questionList.size()) {
            feedbackMessage = "Excellent! You got a perfect score!";
        } else if (score >= questionList.size() * 0.75) {
            feedbackMessage = "Great job! You scored " + score + " out of " + questionList.size();
        } else if (score >= questionList.size() * 0.5) {
            feedbackMessage = "Good effort! You scored " + score + " out of " + questionList.size();
        } else {
            feedbackMessage = "Keep trying! You scored " + score + " out of " + questionList.size();
        }

        // Show the final score in a dialog
        new AlertDialog.Builder(this)
                .setTitle("Quiz Complete")
                .setMessage("Your final score: " + score + "/" + questionList.size() + "\n" + feedbackMessage)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
