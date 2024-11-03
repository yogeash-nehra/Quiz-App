// OpenTDBApiService.java
package com.example.quizapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTDBApiService {
    @GET("api.php")
    Call<QuizResponse> getQuestions(
            @Query("amount") int amount,
            @Query("category") int category, // Use appropriate category ID
            @Query("difficulty") String difficulty,
            @Query("type") String type // Set to "multiple" or "boolean"
    );
}
